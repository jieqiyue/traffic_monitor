package com.jie.traffic.emnus;

/**
 * @Classname transProtocol
 * @Description TODO
 * @Date 2022/5/17 21:15
 * @Created by Jieqiyue
 */
public enum TransType {
    Reserved(0,"Reserved"),
    ICMP(1,"ICMP"),

    TCP(6,"TCP"),
    UDP(17,"UDP")
    ;


    TransType(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
    public static String getProtocol(Integer code) {
        if(code==null){
            return null;
        }
        for (TransType channelType : TransType.values()) {
            if (channelType.getCode().equals(code)) {
                return channelType.getName();
            }
        }
        return null;
    }


    private String name;

    private Integer code;
}
