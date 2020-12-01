package uz.com.enums;

public enum GenderType {
    Male("MALE"),
    Female("FEMALE");

    private String code;

    GenderType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
