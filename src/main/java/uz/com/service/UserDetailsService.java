package uz.com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import uz.com.dto.auth.CustomUserDetails;
import uz.com.hibernate.dao.auth.UserDao;
import uz.com.hibernate.domain.auth.User;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UserDao userDao;

    @Autowired
    public UserDetailsService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByUsername(username);
        if (user == null)
            throw new RuntimeException(String.format("User with username '%s' not found", username));
        if (user.isLocked())
            throw new RuntimeException(String.format("User with username '%s' is locked", username));

        return new CustomUserDetails(user);
    }
}
