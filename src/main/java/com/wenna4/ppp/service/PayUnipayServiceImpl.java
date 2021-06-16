package com.wenna4.ppp.service;

import com.wenna4.ppp.Intf.Entity.PayUnipay;
import com.wenna4.ppp.Intf.Mapper.PayUnipayMapper;
import com.wenna4.ppp.Intf.PayUnipayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PayUnipayServiceImpl implements PayUnipayService {
    private static final Logger log = LoggerFactory.getLogger(PayUnipayServiceImpl.class);

    @Resource
    private PayUnipayMapper payUnipayMapper;

    @Override
    public PayUnipay loadById(int id) {
        return payUnipayMapper.loadById(id);
    }

    @Override
    public int insert(PayUnipay payUnipay) {
        return payUnipayMapper.insert(payUnipay);
    }

    @Override
    public int update(PayUnipay payUnipay) {
        return 0;
    }

}
