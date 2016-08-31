package com.cmcc.anal.common.persistence.uuid;

public class UUID {
    private static UUIDHexGenerator uuid = new UUIDHexGenerator();

    private UUID() {

    }

    public static String getUuid() {
        return uuid.generate();
    }
}
