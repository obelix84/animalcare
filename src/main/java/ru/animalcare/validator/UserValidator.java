package ru.animalcare.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import ru.animalcare.domain.User;
import ru.animalcare.service.UserDetailsServiceImpl;
import org.apache.commons.validator.routines.EmailValidator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class UserValidator implements Validator {
    private final UserDetailsServiceImpl userService;

    @Autowired
    public UserValidator(@Qualifier("UserDetailsServiceImpl") UserDetailsServiceImpl userService) {
        this.userService = userService;
    }

    //phone number pattern: +7xxxyyyzzjj or 8xxxyyyzzjj
    private static final String NUMBER_PATTERN = "^(\\+?7{1}|8{1})[ -]?\\(?[0-9]{0,3}\\)?[ -]?[0-9]{3}[ -]?[0-9]{2}[ -]?[0-9]{2}$";
    private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,32}$";


    private static final Pattern password_pattern = Pattern.compile(PASSWORD_PATTERN);
    private static final Pattern number_pattern = Pattern.compile(NUMBER_PATTERN);

    public boolean isPasswordValid(String password) {
        Matcher matcher = password_pattern.matcher(password);
        return matcher.matches();
    }

    public boolean isNumberValid(String number){
        Matcher matcher = number_pattern.matcher(number);
        return matcher.matches();
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "error.not_empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "error.not_empty");

        if (userService.checkByEmail(user.getEmail())) {
            errors.rejectValue("email", "register.error.duplicated.email","email is in use"
            );
        }
        if (!CustomEmailValidator.getInstance().isValid(user.getEmail())) {
            errors.rejectValue("email", "register.error.email","Email is invalid");
        }
        //Works
        //TODO displaying in html
        if(!isPasswordValid(user.getPassword().toString())){
            errors.rejectValue("password","register.error.password","password is invalid");
        }
        if(!isNumberValid(user.getContactNumber())){
            errors.rejectValue("contactNumber","register.error.number","contact number is invalid");
        }
    }

}