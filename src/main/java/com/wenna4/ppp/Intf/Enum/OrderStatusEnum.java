package com.wenna4.ppp.Intf.Enum;


public enum OrderStatusEnum {

    NotPay(0, "未支付"),
    PayFail(5, "支付失败"),
    PaySuccess(1, "已支付");

    private int id;
    private String name;

    OrderStatusEnum(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static OrderStatusEnum getById(int id) {
        for (OrderStatusEnum period : OrderStatusEnum.values()) {
            if (period.getId() == id)
                return period;
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
