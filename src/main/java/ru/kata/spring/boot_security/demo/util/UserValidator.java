package ru.kata.spring.boot_security.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.ServiceUserImp;

@Component
public class UserValidator implements Validator {

    private final ServiceUserImp serviceUserImp;

    @Autowired
    public UserValidator(ServiceUserImp serviceUserImp) {
        this.serviceUserImp = serviceUserImp;
    }


    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        try {
            serviceUserImp.loadUserByUsername(user.getUsername());
        } catch (UsernameNotFoundException ignored) {
             return; // все ок, пользователь не найден
        }
        errors.rejectValue("username", "", "Человек с таким именем пользователя уже существует");
    }
}
