package com.wenna4.ppp.service;

import com.wenna4.ppp.Intf.Entity.PayOrder;
import com.wenna4.ppp.Intf.Enum.BizEnum;
import com.wenna4.ppp.Intf.Mapper.PayOrderMapper;
import com.wenna4.ppp.Intf.PayOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PayOrderServiceImpl implements PayOrderService {
    private Logger log = LoggerFactory.getLogger(PayOrderServiceImpl.class);

    @Resource
    private PayOrderMapper payOrderMapper;

    private Logger logger = LoggerFactory.getLogger(PayOrderServiceImpl.class);

    @Override
    public PayOrder loadById(int id) {
        return payOrderMapper.loadById(id);
    }

    @Override
    public int insert(PayOrder order) {
        return payOrderMapper.insert(order);
    }

    @Override
    public int update(PayOrder order) {
        return payOrderMapper.update(order);
    }

    @Override
    public PayOrder loadByBizIdAndBizOrderId(BizEnum bizEnum, int bizOrderId) {
        return payOrderMapper.loadByBizIdAndBizOrderId(bizEnum.getId(), bizOrderId);
    }


}
