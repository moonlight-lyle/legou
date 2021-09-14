package com.changgou.pay.controller;

import com.alibaba.fastjson.JSON;
import com.changgou.pay.service.WeixinPayService;
import com.github.wxpay.sdk.WXPayUtil;
import entity.Result;
import entity.StatusCode;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/weixin/pay")
@RestController
public class WeixinPayController {

    @Autowired
    private WeixinPayService weixinPayService;

    /**
     * 根据订单号和金额进行生成支付二维码链接地址
     *
     * @param out_trade_no 订单号
     * @param total_fee    金额
     * @return
     */
    @RequestMapping("/create/native")
    public Result<Map> createNative(String out_trade_no, String total_fee) {
        // 1.调用统一下单的API即可
        Map<String, String> resultMap = weixinPayService.createNative(out_trade_no, total_fee);
        // 2.获取到返回值解析 返回map
        return new Result<Map>(true, StatusCode.OK, "生成支付二维码链接成功", resultMap);
    }

    /**
     * 根据订单号查询该订单的支付的状态
     *
     * @param out_trade_no
     * @return
     */
    @RequestMapping("/status/query")
    public Result<Map> queryStatus(String out_trade_no) {
        Map<String, String> map = weixinPayService.queryStatus(out_trade_no);
        return new Result<Map>(true, StatusCode.OK, "查询支付的状态成功，具体的状态请看具体数据", map);
    }

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private Environment environment;

    // 写一个请求 接收微信支付的通知  目的：接收通知之后标识支付成功，发送消息 进行更新状态
    @RequestMapping("/notify/url")
    public String notifyUrl(HttpServletRequest request) {
        ServletInputStream inputStream = null;
        ByteArrayOutputStream outputStream = null;
        try {
            // 1.接收微信通知的数据
            inputStream = request.getInputStream();
            outputStream = new ByteArrayOutputStream();
            byte[] bytes = new byte[1024];
            int len = 0;
            while ((len = inputStream.read(bytes)) != -1) {
                // 有数据
                outputStream.write(bytes, 0, len);
            }
            byte[] bytes1 = outputStream.toByteArray(); // 数据
            String xmlstr = new String(bytes1, "utf-8");
            // 获取到的就是微信通知传递过来的数据
            Map<String, String> map = WXPayUtil.xmlToMap(xmlstr);
            // 2.发送消息到MQ
            rabbitTemplate.convertAndSend(environment.getProperty("mq.pay.exchange.order"),
                    environment.getProperty("mq.pay.routing.key"), JSON.toJSONString(map));
            // 3.及时返回固定的数据格式的的数据给微信
            Map<String, String> resultMap = new HashMap<>();
            resultMap.put("return_code", "SUCCESS");
            resultMap.put("return_msg", "OK");
            return WXPayUtil.mapToXml(resultMap);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
