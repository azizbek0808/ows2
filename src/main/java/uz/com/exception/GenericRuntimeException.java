package uz.com.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
@Getter
@Setter
public class GenericRuntimeException extends RuntimeException {

    private String key;

    public GenericRuntimeException(String message) {
        super(message);
    }

    public GenericRuntimeException(String message, String key) {
        super(message);
        this.key = key;
    }

    //    public GenericRuntimeException(ErrorMessage message) {
//        super(message.getMessage());
//    }

    public GenericRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public GenericRuntimeException(String message, Throwable cause, String key) {
        super(message, cause);
        this.key = key;
    }

    //    public GenericRuntimeException(ErrorMessage message, Throwable cause) {
//        super(message.getMessage(), cause);
//    }
}
