package uz.com.exception;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@Getter
@Setter
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class CustomSqlException extends RuntimeException {

    /**
     * Common logger for use in subclasses.
     */
    protected final Log logger = LogFactory.getLog(getClass());

    private Integer sqlErrorCode;
    private HttpStatus httpStatus;
    private String friendlyMessage;
    private String systemMessage;

    public CustomSqlException(String message) {
        super(message);
        initMessage();
    }

    public CustomSqlException(String message, Integer sqlErrorCode) {
        super(message);
        this.sqlErrorCode = sqlErrorCode;
        httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        initMessage();
    }

    public CustomSqlException(String message, Throwable cause, Integer sqlErrorCode) {
        super(message, cause);
        this.sqlErrorCode = sqlErrorCode;
        httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        initMessage();
    }

    public CustomSqlException(String message, Throwable cause) {
        super(message, cause);
        httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        initMessage();
    }

    private void initMessage() {
        httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        friendlyMessage = null;
        systemMessage = null;

        String message = super.getMessage();

        systemMessage = message.trim();

        try {
            friendlyMessage = message.substring(message.lastIndexOf("detail:") + 7, message.indexOf("hint:")).trim();
            if (friendlyMessage.isEmpty()) {
                friendlyMessage = message.substring(message.lastIndexOf("message:") + 8, message.indexOf("detail:")).trim();
            }
            if (friendlyMessage.isEmpty()) {
                friendlyMessage = message.substring(message.lastIndexOf("SQLERRM:") + 8).trim();
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }

        if (friendlyMessage.contains("ERROR_CODE_400")) {
            httpStatus = HttpStatus.BAD_REQUEST;
            friendlyMessage = friendlyMessage.replace("ERROR_CODE_400", "").trim();
        } else if (friendlyMessage.contains("ERROR_CODE_403")) {
            httpStatus = HttpStatus.FORBIDDEN;
            friendlyMessage = friendlyMessage.replace("ERROR_CODE_403", "").trim();
        } else if (friendlyMessage.contains("ERROR_CODE_404")) {
            httpStatus = HttpStatus.NOT_FOUND;
            friendlyMessage = friendlyMessage.replace("ERROR_CODE_404", "").trim();
        }

    }


    private String getErrorMessageSplitted(String message) {
        String[] messages = message.split(":");

        if (messages.length > 1) {
            String[] messageCode = messages[message.contains("PL/SQL") ? 2 : 1].split("\n");
            String errorMessage = messageCode[0];
            String errorCode = messageCode[1];

            return errorMessage;
        }

        return message;
    }
}
