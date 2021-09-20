package com.changgou.pay.service.impl;

import com.alibaba.fastjson.JSON;
import com.changgou.pay.service.WeixinPayService;
import com.github.wxpay.sdk.WXPayUtil;
import entity.HttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class WeixinPayServiceImpl implements WeixinPayService {

    @Value("${weixin.appid}")
    private String appid;

    @Value("${weixin.partner}")
    private String partner;

    @Value("${weixin.notifyurl}")
    private String notifyurl;

    @Value("${weixin.partnerkey}")
    private String partnerkey;

    @Override
    public Map<String, String> createNative(Map<String,String> parameter) {
        //调用统一下单的api 发送请求到微信支付系统
        //获取微信支付系统返回的数据解析返回给页面即可

        try {
            //1.组装参数集合map对象
            Map<String,String> paramMap = new HashMap<String,String>();

            paramMap.put("appid",appid);
            paramMap.put("mch_id",partner);
            paramMap.put("nonce_str", WXPayUtil.generateNonceStr());
            //paramMap.put("appid",appid);//todo 签名必须要填写 自动填写
            paramMap.put("body","畅购的商品");
            paramMap.put("out_trade_no",parameter.get("out_trade_no"));//订单号
            paramMap.put("total_fee",parameter.get("total_fee"));//单位是分
            paramMap.put("spbill_create_ip","127.0.0.1");



            paramMap.put("notify_url",notifyurl); //todo 以后再调整
            paramMap.put("trade_type","NATIVE");

            // 添加附加数据
            paramMap.put("attach", JSON.toJSONString(parameter));//加入附加数据 需要from，username了
            // 2.将map 转成XML
            String xmlParam = WXPayUtil.generateSignedXml(paramMap, partnerkey);//自动的添加了签名

            // 3.调用httpclient 模拟浏览器携带参数XML 发送https POST 请求
            HttpClient httpClient = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
            httpClient.setHttps(true);
            httpClient.setXmlParam(xmlParam);
            httpClient.post();
            // 4.调用httpclient 模拟浏览器接收响应 XML
            String content = httpClient.getContent();//获取微信支付系统返回来的响应
            System.out.println(content);//XML
            // 5.xml再转换成MAP   返回一个新的map 携带极少的必要的数据
            Map<String, String> resultMap = WXPayUtil.xmlToMap(content);

            Map<String,String> dataMap = new HashMap<String,String>();
            dataMap.put("code_url",resultMap.get("code_url"));
            dataMap.put("out_trade_no",parameter.get("out_trade_no"));
            dataMap.put("total_fee",parameter.get("total_fee"));

            //6.返回
            return dataMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Map<String, String> queryStatus(String out_trade_no) {
        try {
            // 1.组装参数集合map对象
            Map<String, String> paramMap = new HashMap<String, String>();
            paramMap.put("appid", appid);
            paramMap.put("mch_id", partner);
            paramMap.put("nonce_str", WXPayUtil.generateNonceStr());
            // paramMap.put("appid",appid); // todo 签名必须要填写 自动填写
            paramMap.put("out_trade_no", out_trade_no); // 订单号

            // 2.将map 转成XML
            String xmlParam = WXPayUtil.generateSignedXml(paramMap, partnerkey);//自动的添加了签名

            // 3.调用httpclient 模拟浏览器携带参数XML 发送https POST 请求
            HttpClient httpClient = new HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
            httpClient.setHttps(true);
            httpClient.setXmlParam(xmlParam);
            httpClient.post();
            // 4.调用httpclient 模拟浏览器接收响应 XML
            String content = httpClient.getContent(); // 获取微信支付系统返回来的响应
            System.out.println(content); // XML
            // 5.xml再转换成MAP   返回一个新的map 携带极少的必要的数据
            Map<String, String> resultMap = WXPayUtil.xmlToMap(content);
            // 6.返回
            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
