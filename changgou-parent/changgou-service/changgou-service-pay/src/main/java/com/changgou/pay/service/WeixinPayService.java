package com.changgou.pay.service;

import java.util.Map;

public interface WeixinPayService {

    Map<String,String> createNative(String out_trade_no, String total_fee);

    Map<String,String> queryStatus(String out_trade_no);

}
