package uz.com.hibernate.dao.settings;

import uz.com.criteria.settings.ErrorMessageCriteria;
import uz.com.enums.ErrorCodes;
import uz.com.hibernate.base.Dao;
import uz.com.hibernate.domain.settings.ErrorMessage;

public interface ErrorMessageDao extends Dao<ErrorMessage, ErrorMessageCriteria> {
    String getErrorMessage(ErrorCodes errorCode, String params);

}
