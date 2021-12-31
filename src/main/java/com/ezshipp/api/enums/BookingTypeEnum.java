package com.ezshipp.api.enums;

/**
 * Created by srinivasseri on 3/1/18.
 */
public enum BookingTypeEnum {
    NA("NA", 0.00),
    INSTANT("Instant", 199.00),
    FOURHOURS("FourHours", 199.00),
    SAMEDAY("SameDay", 99.00);

    BookingTypeEnum(final String type, final double price)   {
        this.type = type;
        this.price = price;
    }

    private final String type;
    private final Double price;

    public String getType()    {
        return type;
    }

    public Double getPrice()    {
        return price;
    }

    public static BookingTypeEnum getByType(String type) {
        for(BookingTypeEnum e : values()) {
            if(e.type.equalsIgnoreCase(type))
                return e;
        }
        return NA;
    }

    public static BookingTypeEnum getByPrice(Double price) {
        for(BookingTypeEnum e : values()) {
            if(e.price == price)
                return e;
        }
        return NA;
    }
}
