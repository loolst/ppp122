package com.wenna4.ppp.Intf.Enum;

public enum EngineTypeEnum {
    TotalCommand(0, "总指令"),
    CouponCommand(1, "券"),
    BalanceCommand(2, "余额"),
    ThirdPartPayCommand(3, "第三方支付");
    private int id;
    private String name;

    EngineTypeEnum(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static EngineTypeEnum getById(int id) {
        for (EngineTypeEnum engineStatusEnum : EngineTypeEnum.values()) {
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
