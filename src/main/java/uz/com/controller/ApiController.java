package uz.com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import uz.com.service.IAbstractService;

@CrossOrigin(origins = {"http://localhost:4200", "http://orientws.uz"})
public abstract class ApiController<T extends IAbstractService> {

    public static final String API_PATH = "/api";
    public static final String V_1 = "/v1";
    public static final String AUTH = API_PATH + V_1 + "/auth";

    public static final String LOGIN_URL = AUTH + "/login";
    public static final String REFRESH_TOKEN_URL = AUTH + "/refresh-token";
    public static final String FORGOT_PASSWORD = AUTH + "/forgot/password";
    public static final String LOGOUT_URL = API_PATH + V_1 + "/logout";

    protected T service;

    @Autowired
    public ApiController(T service) {
        this.service = service;
    }
}
