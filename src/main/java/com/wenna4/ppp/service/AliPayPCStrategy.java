package com.wenna4.ppp.service;

import com.wenna4.ppp.Intf.DO.PayChannelDo;
import com.wenna4.ppp.Intf.Entity.PayChannelAudit;
import com.wenna4.ppp.Intf.Entity.PayOrder;
import com.wenna4.ppp.Intf.Enum.PaySourceEnum;
import com.wenna4.ppp.Intf.PayChannelService;
import com.wenna4.ppp.Intf.PayOrderService;
import com.wenna4.ppp.Intf.Strategy;
import com.wenna4.ppp.Intf.utils.ParameterUtil;
import com.wenna4.ppp.Intf.utils.security.MD5Signature;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service("aliPayStrategy")
public class AliPayPCStrategy implements Strategy {

    @Resource
    private PayChannelService payChannelService;
    @Resource
    private PayOrderService payOrderService;

    public String pay(PayChannelDo channelDo, String userIP) {
        //1.unipay校验

        //2.落地

        //3.请求支付宝alipay，tradeNo  16位随机
        String tradeNo = new Date().getTime() + "666";

        //请求alipay
        PayChannelAudit channelAudit = new PayChannelAudit();
        channelAudit.setUserId(channelDo.getUserId());
        channelAudit.setOrderId(channelDo.getOrderId());
        channelAudit.setPayChannel(channelDo.getPayChannel());
        channelAudit.setPayMethod(channelDo.getPayMethod());
        channelAudit.setPayAmount(channelDo.getAmount());
        channelAudit.setStatus(1);
        channelAudit.setSource(PaySourceEnum.Out.getId());
        channelAudit.setTradeNo(tradeNo);
        channelAudit.setOutTradeNo("");
        channelAudit.setPartnerId("2345678");
        channelAudit.setMsg("success");
        channelAudit.setUserIp(userIP);

        payChannelService.insert(channelAudit);

        PayOrder payOrder = payOrderService.loadById(channelDo.getOrderId());

        Map<String, String> params = new HashMap<String, String>();
        params.put("service", "create_direct_pay_by_user");
        params.put("partner", "23213");
        params.put("_input_charset", "utf-8");
        params.put("sign_type", "MD5");
        params.put("out_trade_no", tradeNo);
        params.put("subject", "订单编号:" + channelDo.getOrderId());
        params.put("payment_type", "1");
        params.put("total_fee", String.valueOf(channelDo.getAmount()));
        params.put("seller_id", "12343");
        params.put("notify_url", payOrder.getCallbackUrl());
        params.put("return_url", payOrder.getReturnUrl());

        //下面方法作用是把上面的地段，除了某两个，其他按照字母排序，拼接后的报文
        String unsignedData = ParameterUtil.getSignData(params);

        //3.请求支付宝alipay，
        try {
            String signedData = MD5Signature.sign(unsignedData, "123145");
            params.put("sign", signedData);
            String paramsStr = ParameterUtil.mapToUrl(params, false);
            String url1 = "https://mapi.alipay.com/gateway.do?" + paramsStr;
            //支付宝回调

            return url1;
        } catch (Exception e) {
            return null;
        }

    }


}
