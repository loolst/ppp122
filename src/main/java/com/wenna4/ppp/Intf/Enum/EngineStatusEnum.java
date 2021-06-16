package com.wenna4.ppp.Intf.Enum;

public enum EngineStatusEnum {
    CouponUsed(1, "券正常使用(券支付%s元)"),
    AmountFrozen(2, "余额冻结(余额冻结%s元)"),
    AmountReduce(3, "余额扣减(余额扣减%s元)"),
    AmountResume(4, "余额恢复(余额恢复%s元)"),
    ThirdPartPaySuccess(5, "第三方支付成功%s元"),
    ThirdPartPayFail(6, "第三方支付失败%s元"),
    ThirdPartPayStart(7, "第三方支付发起%s元");
    private int id;
    private String name;

    EngineStatusEnum(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static EngineStatusEnum getById(int id) {
        for (EngineStatusEnum engineStatusEnum : EngineStatusEnum.values()) {
            if (engineStatusEnum.getId() == id)
                return engineStatusEnum;
        }
        return null;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter method for property <tt>name</tt>.
     *
     * @return property value of name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter method for property <tt>name</tt>.
     *
     * @param name value to be assigned to property name
     */
    public void setName(String name) {
        this.name = name;
    }
}
