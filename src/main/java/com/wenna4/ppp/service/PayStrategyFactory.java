package com.wenna4.ppp.service;

import com.wenna4.ppp.Intf.Enum.PayChannelEnum;
import com.wenna4.ppp.Intf.Enum.PayMethodEnum;
import com.wenna4.ppp.Intf.Strategy;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.stereotype.Service;

@Service
public class PayStrategyFactory implements BeanFactoryAware {

    private BeanFactory beanFactory;

    public Strategy getStrategy(PayChannelEnum payType, PayMethodEnum payMethodEnum) {
        if (payType == PayChannelEnum.Alipay && payMethodEnum == PayMethodEnum.AccountPay) {
            return (AliPayPCStrategy) beanFactory.getBean("aliPayStrategy");
        } else if (payType == PayChannelEnum.WeiXin && payMethodEnum == PayMethodEnum.ScanCodePay)
            return null;//(WeixinPayStrategy)beanFactory.getBean("weixinPayStrategy");
        return null;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    public BeanFactory getBeanFactory() throws BeansException {
        return beanFactory;
    }
}
