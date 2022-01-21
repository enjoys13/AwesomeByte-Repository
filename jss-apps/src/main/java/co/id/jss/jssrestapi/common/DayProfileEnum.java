package co.id.jss.jssrestapi.common;

public enum DayProfileEnum {
    WEEKDAYS ("WEEKDAYS"), WEEKEND ("WEEKEND"), HOLIDAY ("HOLIDAY");

    private String value;

    DayProfileEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static DayProfileEnum parse(String val) {
        DayProfileEnum dayProfileEnum = null;
        for (DayProfileEnum item : DayProfileEnum.values()) {
            if(item.getValue().equals(val)) {
                dayProfileEnum = item;
                break;
            }
        }
        return dayProfileEnum;
    }
}
