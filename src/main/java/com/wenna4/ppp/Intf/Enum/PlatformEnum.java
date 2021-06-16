package com.wenna4.ppp.Intf.Enum;

public enum PlatformEnum {
    PC(1000, "PC端"),
    M(2000, "M站"),
    IOS(3001, "IOS系统");

    private int id;
    private String name;

    PlatformEnum(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static PlatformEnum getById(int id) {
        for (PlatformEnum platform : PlatformEnum.values()) {
            if (platform.getId() == id)
                return platform;
        }
        return null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
