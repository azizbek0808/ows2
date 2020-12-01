package uz.com.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import uz.com.dto.auth.CustomUserDetails;
import uz.com.hibernate.dao.auth.UserSessionRepository;
import uz.com.hibernate.domain.auth.User;

import javax.servlet.http.HttpServletRequest;

@Component
public class UserSession {

    HttpServletRequest request;
    BaseUtils utils;
    UserSessionRepository repository;

    @Autowired
    public UserSession(HttpServletRequest request, BaseUtils utils, UserSessionRepository repository) {
        this.request = request;
        this.utils = utils;
        this.repository = repository;
    }

    public User getUser() {
        User user = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            if (authentication.getPrincipal() instanceof User) {
                user = (User) authentication.getPrincipal();
            }
            if (authentication.getPrincipal() instanceof CustomUserDetails) {
                user = ((CustomUserDetails) authentication.getPrincipal()).getUser();
            }
        }
        return user;
    }

    public String getUserName() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
