package com.wenna4.ppp.service;

import com.wenna4.ppp.Intf.DO.PayChannelDo;
import com.wenna4.ppp.Intf.DO.PayUnipayDO;
import com.wenna4.ppp.Intf.Strategy;
import org.springframework.stereotype.Service;

@Service("wxCodeStrategy")
public class WXScanCodeStrategy implements Strategy {

    public String pay(PayChannelDo channelDo) {

        return null;

    }


    @Override
    public String pay(PayChannelDo channelDo, String url) {
        return null;
    }
}
