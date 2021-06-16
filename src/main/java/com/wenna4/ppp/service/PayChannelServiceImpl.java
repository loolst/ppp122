package com.wenna4.ppp.service;

import com.wenna4.ppp.Intf.DO.PayChannelDo;
import com.wenna4.ppp.Intf.Entity.PayChannelAudit;
import com.wenna4.ppp.Intf.Enum.PayChannelEnum;
import com.wenna4.ppp.Intf.Enum.PayMethodEnum;
import com.wenna4.ppp.Intf.Mapper.PayChannelMapper;
import com.wenna4.ppp.Intf.PayChannelService;
import com.wenna4.ppp.Intf.Strategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PayChannelServiceImpl implements PayChannelService {
    private static final Logger log = LoggerFactory.getLogger(PayChannelServiceImpl.class);

    @Resource
    private PayChannelMapper payChannelMapper;
    @Resource
    private PayStrategyFactory payStrategyFactory;


    @Override
    public PayChannelAudit loadById(int id) {
        return payChannelMapper.loadById(id);
    }

    @Override
    public int insert(PayChannelAudit channelAudit) {
        return payChannelMapper.insert(channelAudit);
    }

    @Override
    public int update(PayChannelAudit channelAudit) {
        return 0;
    }

    @Override
    public String pay(PayChannelDo payChannelDo, PayChannelEnum channelEnum, PayMethodEnum methodEnum, String userIp) {
        PayChannelEnum payChannelEnum = PayChannelEnum.getById(channelEnum.getId());
        PayMethodEnum payMethodEnum = PayMethodEnum.getById(methodEnum.getId());
        Strategy strategy = payStrategyFactory.getStrategy(payChannelEnum, payMethodEnum);
        if (strategy == null) {
            return null;
        }
        //支付方式不同,返回类型不同
        String url = strategy.pay(payChannelDo, userIp);
        return url;
    }


}
