package uz.com.validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import uz.com.hibernate.dao.settings.ErrorMessageDao;
import uz.com.utils.BaseUtils;


/**
 * Created by 'javokhir' on 27/06/2019
 */

public abstract class AbstractValidator<T> {

    protected final Log logger;
    protected ErrorMessageDao dao;
    protected BaseUtils utils;
//    private IErrorsRepository repository;

    @SuppressWarnings("unchecked")
    @Autowired
    public AbstractValidator(BaseUtils utils, ErrorMessageDao dao) {
        this.utils = utils;
        this.dao = dao;
        this.logger = LogFactory.getLog(getClass());
    }
}
