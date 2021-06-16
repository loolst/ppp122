package com.wenna4.ppp.service;

import com.wenna4.ppp.Intf.Entity.VipOrder;
import com.wenna4.ppp.Intf.Mapper.VipOrderMapper;
import com.wenna4.ppp.Intf.VipOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class VipOrderServiceImpl implements VipOrderService {

    @Resource
    private VipOrderMapper vipOrderMapper;

    private Logger logger = LoggerFactory.getLogger(VipOrderServiceImpl.class);

    @Override
    public VipOrder loadById(int id) {
        return vipOrderMapper.loadById(id);
    }

    @Override
    public int insert(VipOrder order) {
        return vipOrderMapper.insert(order);
    }

    @Override
    public int update(VipOrder order) {
        return 0;
    }


}
