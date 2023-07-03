package com.identifyfault.enums;

import java.util.Arrays;

/**
 * @author ashmita.tandon on 01/07/23
 */
public enum FaultTypeEnum {
    SA0("SA0","0"),
    SA1("SA1","1"),
    ;

    FaultTypeEnum(String faultType, String value) {
        this.faultType = faultType;
        this.value = value;
    }

    private String faultType;
    private String value;

    public String getFaultType() {
        return faultType;
    }

    public String getValue() {
        return value;
    }

    public static FaultTypeEnum getByName(String name){
        return Arrays.stream(values()).filter(a-> a.getFaultType().equals(name)).findAny().orElse(null);
    }
}
