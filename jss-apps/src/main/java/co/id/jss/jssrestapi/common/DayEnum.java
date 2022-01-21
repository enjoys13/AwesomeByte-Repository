package co.id.jss.jssrestapi.common;

public enum DayEnum {
    SUNDAY ("SUNDAY"), MONDAY ("MONDAY"), TUESDAY ("TUESDAY"),
    WEDNESDAY ("WEDNESDAY"), THURSDAY ("THURSDAY"), FRIDAY ("FRIDAY"), SATURDAY ("SATURDAY");

    private String value;

    DayEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static DayEnum parse(String val) {
        DayEnum dayEnum = null;
        for (DayEnum item : DayEnum.values()) {
            if(item.getValue().equals(val)) {
                dayEnum = item;
                break;
            }
        }
        return dayEnum;
    }
}
