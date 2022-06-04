package ru.animalcare.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.web.server.MatcherSecurityWebFilterChain;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import ru.animalcare.domain.User;
import ru.animalcare.service.UserDetailsServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class UserValidator implements Validator {
    private final UserDetailsServiceImpl userService;

    @Autowired
    public UserValidator(@Qualifier("UserDetailsServiceImpl") UserDetailsServiceImpl userService) {
        this.userService = userService;
    }
//
//    public boolean isEmailValid(String email,Errors errors) {
//        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "error.not_empty");
//        String error_message;
//        if (email.endsWith(".")) {
//            errors.rejectValue(email,"register.error.email.ENDS_WITH_DOT");
//            return false;
//        }
//        // Code to find out last index of '@' sign
//
//
//        //Code to check occurrence of @ in the email address
//       if(!checkSymbol(email,'@')){
//           errors.rejectValue(email,"register.error.email.MORE_THAN_ONE_AT");
//           return false;
//       }
//        // Code to check occurrence of [period sign i..e, "."] after @
//        String buffering = email.substring(email.indexOf('@') + 1, email.length());
//        int len = buffering.length();
//
//        int countOfDotAfterAt = 0;
//        for (int i = 0; i < len; i++) {
//            if (buffering.charAt(i) == '.')
//                countOfDotAfterAt++;
//        }
//
//
//        // Code to print userName & domainName
//        String userName = email.substring(0, email.indexOf('@'));
//        String domainName = email.substring(email.indexOf('@') + 1, email.length());
//
//        if ((countOfAt == 1) && (userName.endsWith(".") == false) && (countOfDotAfterAt == 1) &&
//                ((indexOfAt + 3) <= (lastIndexOfAt) && !checkEndDot)) {
//
//            System.out.println("\"Valid email address\"");
//        } else {
//            System.out.println("\n\"Invalid email address\"");
//        }
//
//
//        System.out.println("\n");
//        System.out.println("User name: " + userName + "\n" + "Domain name: " + domainName);
//
//
//    }
//    private boolean checkSymbol(String email,Character symbol){
//        int count= 0;
//        for (int i = 0; i < email.length(); i++) {
//            if (email.charAt(i) == symbol){
//                count++;
//            }
//        }
//        return count == 1;
//    }

    private static final String EMAIL_PATTERN =
            "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\\\.[A-Za-z0-9-]+)*(\\\\.[A-Za-z]{2,})$\n" + "\n";

    //phone number pattern
    private static final String NUMBER_PATTERN = "^(^8|7|\\+7)((\\d{10})|(\\s\\(\\d{3}\\)\\s\\d{3}\\s\\d{2}\\s\\d{2}))\n^";

    // password must have at least one digit
    private static final String DIGITS_PATTERN = "^(?=.*[0-9])^";
    // password must have at least one lowercase letter
    private static final String LOWERCASE_PATTERN = "^(?=.*[a-z])^";
    // password must have at least one uppercase letter
    private static final String UPPERCASE_PATTERN = "^(?=.*[A-Z])^";
    // password must have at least one symbol
    private static final String SYMBOLS_PATTERN = "^(?=.*[!@#&()–[{}]:;',?/*~$^+=<>])^";

    private static final Pattern email_pattern = Pattern.compile(EMAIL_PATTERN);

    public static boolean isEmailValid(String email) {
        Matcher matcher = email_pattern.matcher(email);
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

        if (user.getEmail().length() < 3) {
            errors.rejectValue("email", "register.error.email.under_3");
        }
        if (user.getEmail().length() > 32) {
            errors.rejectValue("email", "register.error.email.over_255");
        }
        if (!isEmailValid(user.getEmail())) {
            errors.rejectValue("email", "invalid email format");
        }
        if (userService.checkByEmail(user.getEmail())) {
            errors.rejectValue("email", "register.error.duplicated.email");
        }
        if (user.getPassword().matches(DIGITS_PATTERN)) {
            errors.rejectValue("password", "password should contain at least one digit");
        }
        if (user.getPassword().matches(SYMBOLS_PATTERN)){
            errors.rejectValue("password", "password should contain at least one symbol");
        }
        if (user.getPassword().matches(UPPERCASE_PATTERN)) {
            errors.rejectValue("password", "password should contain at least one uppercase");
        }
        if(user.getPassword().matches(LOWERCASE_PATTERN)){
            errors.rejectValue("password", "password should contain at least one lowercase");
        }
        if(user.getContact_number().matches(NUMBER_PATTERN)){
            errors.rejectValue("number","number is in incorrect format");
        }
    }
}