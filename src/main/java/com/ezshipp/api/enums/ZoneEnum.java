package com.ezshipp.api.enums;

/**
 * Created by srinivasseri on 5/25/18.
 */
public enum ZoneEnum {
    NA(0, "NA"),
    HITECCITY(1, "Hitec City"),
    DILSUKHNAGAR(3, "Dilsukhnagar"),
    KHAIRTABAD(6, "Khairtabad"),
    SECUNDERABAD(5, "Secunderabad"),
    CHARMINAR(4, "Charminar"),
    BALANAGAR(7, "Balanagar"),
    MEHIDIPATNAM(2, "Mehdipatnam"),
    UPPAL(8, "Uppal");


    ZoneEnum(final int zoneId, final String name)   {
        this.zoneId = zoneId;
        this.name = name;
    }

    private final int zoneId;
    private final String name;

    public int getZoneId()  {
        return this.zoneId;
    }

    public String getName()  {
        return this.name;
    }

    public static ZoneEnum getById(int zoneId) {
        for(ZoneEnum e : values()) {
            if(e.zoneId == zoneId)
                return e;
        }
        return NA;
    }

    public static ZoneEnum getByName(String name) {
        for(ZoneEnum e : values()) {
            if(e.name.equals(name))
                return e;
        }
        return NA;
    }
}
