package com.wenna4.ppp.service;

import com.wenna4.ppp.Intf.DO.PayChannelDo;
import com.wenna4.ppp.Intf.DO.PayUnipayDO;
import com.wenna4.ppp.Intf.Strategy;
import org.springframework.stereotype.Service;

@Service("wxAPPStrategy")
public class WXAppStrategy implements Strategy {

    public String pay(PayChannelDo unipayDO) {

        return null;

    }


    @Override
    public String pay(PayChannelDo channelDo, String url) {
        return null;
    }
}
