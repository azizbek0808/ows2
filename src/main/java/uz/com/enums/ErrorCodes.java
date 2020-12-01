package uz.com.enums;

/**
 * Created by 'Javokhir Mamadiyarov Uygunovich' on 10/5/18.
 */

public enum ErrorCodes {

    ERROR_MESSAGE_NOT_FOUND("ERROR_MESSAGE_NOT_FOUND", "Error message with code ~ not found"),
    USER_NOT_FOUND_AUTH("USER_NOT_FOUND_AUTH_EMAIL", "User with provided ~ not found"),
    USER_NOT_FOUND_ID("USER_NOT_FOUND_ID", "User with provided id: ~ not found"),
    OBJECT_IS_NULL("OBJECT_IS_NULL", "Provided object: '%s' is null"),
    OBJECT_IS_EMPTY("OBJECT_IS_EMPTY", "Provided object: '%s' is empty"),
    RUSSIAN_LANGUAGE_SHOULD_NOT_BE_NULL("RUSSIAN_LANGUAGE_SHOULD_NOT_BE_NULL", "Russian language should not be null or empty"),
    OBJECT_GIVEN_FIELD_REQUIRED("OBJECT_FIELD_NULL", "Provided field :'%s' of '%s'  is required"),
    OBJECT_COULD_NOT_CREATED("OBJECT_COULD_NOT_CREATED", "Provided object: '%s' could not created "),
    OBJECT_COULD_NOT_UPDATED("OBJECT_COULD_NOT_UPDATED", "Could not update '%s' with id '%s'"),
    OBJECT_COULD_NOT_DELETED("OBJECT_COULD_NOT_DELETED", "Could not delete '%s' with id '%s'"),
    COULD_NOT_ATTACH("COULD_NOT_ATTACH", "Could not attach '%s' to '%s' with id '%s'"),
    COULD_NOT_GENERATE("COULD_NOT_GENERATE", "Could not generate '%s' "),
    FILE_NOT_FOUND("FILE_NOT_FOUND", "File not found '%s'"),
    FAILED_TO_STORE("FAILED_TO_STORE", "Failed to store"),
    COULD_NOT_STORE_FILE("COULD_NOT_STORE_FILE", "Could not store file"),
    COULD_NOT_MOVE("COULD_NOT_MOVE", "Could not move '%s'  with id '%s'"),
    COULD_NOT_COPY("COULD_NOT_COPY", "Could not copy '%s'  with id '%s'"),
    EMAIL_NOT_VALID("EMAIL_NOT_VALID", "Email '%s' is not valid"),
    PASSWORD_INCORRECT("PASSWORD_INCORRECT", "Password is incorrect"),
    FAILED_STORE_ILLEGAL_CHARACTERS("FAILED_STORE_ILLEGAL_CHARACTERS", "Failed to store file with relative path outside current directory '%s'" ),
    FAILED_STORE_EMPTY_FILE("FAILED_STORE_EMPTY_FILE", "Failed to store empty filter '%s'" ),
    INVALID_FILE_PATH("OBJECT_IS_NULL", "Sorry! Filename contains invalid path sequence ~"),
    OBJECT_NOT_FOUND_ID("OBJECT_NOT_FOUND_ID", "~ with provided id: ~ not found"),
    OBJECT_NOT_FOUND("OBJECT_NOT_FOUND_ID", "~ not found"),
    OBJECT_ID_REQUIRED("OBJECT_ID_REQUIRED", "~ id not provided"),
    END_BEFORE_BEGIN_ON_DATE("END_BEFORE_BEGIN_ON_DATE", "The beginning of the selected Date should not be more than the end of the selected Date"),
    ID_REQUIRED("ID_REQUIRED", "Object id not provided"),
    INPUT_LENGTH_VALIDATION("INPUT_LENGTH_VALIDATION", "INPUT_LENGTH_VALIDATION"),
    EXTERNAL_SERVICE_ERROR("EXTERNAL_SERVICE_ERROR", "Service not working."),
    OTP_NOT_CONFIRMED("OTP_NOT_CONFIRMED", "One time password not confirmed."),
    ACCESS_DENIED("ACCESS_DENIED", "You do not have access for '%s'"),
    ESTIMATE_DATE_ERROR("ESTIMATE_DATE_ERROR", "Start date must be before than the deadline"),
    EMAIL_NOT_FOUND("EMAIL_NOT_FOUND", "Email is not found"),
    USER_TELEGRAM_CHAT_ID_NOT_FOUND("USER_TELEGRAM_CHAT_ID_NOT_FOUND", "User telegram chat id is not found");

    public String code;
    public String example;

    ErrorCodes(String code, String example) {
        this.code = code;
        this.example = example;
    }
}
