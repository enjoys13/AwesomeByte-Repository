package co.id.jss.jssrestapi.common;

public enum SourceDataEnum {

    INPUT(1L), UPLOAD(2L), GOTRUST(3L);

    private Long value;

    SourceDataEnum(Long value) {
        this.value = value;
    }

    public Long getValue() {
        return value;
    }

    public static SourceDataEnum parse(Long id) {
        SourceDataEnum sourceDataEnum = null;
        for (SourceDataEnum item : SourceDataEnum.values()) {
            if(item.getValue().equals(id)) {
                sourceDataEnum = item;
                break;
            }
        }
        return sourceDataEnum;
    }
}
