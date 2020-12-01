package uz.com.enums;

public enum LanguageType {
    RU("RUSSIAN"),
    UZ("UZBEK"),
    EN("ENGLISH");

    public String code;

    LanguageType(String code) {
        this.code = code;
    }
}
