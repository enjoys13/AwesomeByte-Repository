package co.id.jss.jssrestapi.common;

import co.id.jss.jssrestapi.common.crypto.HashPassword;
import co.id.jss.jssrestapi.common.exception.VarException;
import co.id.jss.jssrestapi.common.misc.CommonUtils;
import co.id.jss.jssrestapi.common.misc.Constants;
import co.id.jss.jssrestapi.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.regex.Pattern;

@Component
public class UserValidation {

    private final Logger LOG = LoggerFactory.getLogger(UserValidation.class);

    private final CommonUtils commonUtils;

    @Autowired
    public UserValidation(CommonUtils commonUtils) {
        this.commonUtils = commonUtils;
    }

    public boolean password(String hashedPassword, String password) throws VarException {
        boolean passwordIsMatch;
        try {
            passwordIsMatch = HashPassword.validatePassword(password, hashedPassword);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new VarException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    Constants.ERROR_SERVER);
        }
        return passwordIsMatch;
    }

    public User userExistAndNotBlockedAndActive(Optional<User> user) throws VarException {

        if (!user.isPresent()) {
            throw new VarException(
                    HttpStatus.BAD_REQUEST,
                    Constants.ERROR_USER_NOT_FOUND);
        } else if (user.get().isBlock()) {
            throw new VarException(
                    HttpStatus.UNAUTHORIZED,
                    Constants.ERROR_USER_IS_BLOCKED);
        } else if (!user.get().isActive()) {
            throw new VarException(
                    HttpStatus.UNAUTHORIZED,
                    Constants.ERROR_USER_NOT_ACTIVE);
        }

        return user.get();
    }


    public User userExistAndNotBlocked(Optional<User> user) throws VarException {
        if (!user.isPresent()) {

            throw new VarException(
                    HttpStatus.BAD_REQUEST,
                    Constants.ERROR_USER_NOT_FOUND);
        } else if (user.get().isBlock()) {


            throw new VarException(
                    HttpStatus.UNAUTHORIZED,
                    Constants.ERROR_USER_IS_BLOCKED);
        }

        return user.get();
    }



    public User userExist(Optional<User> user) throws VarException {
        if (!user.isPresent()) {
            throw new VarException(
                    HttpStatus.BAD_REQUEST,
                    Constants.ERROR_USER_NOT_FOUND);
        }

        return user.get();
    }



    public void userNameOrPhoneNumberNotRegistered(Optional<User> user) throws VarException {
        if (user.isPresent()) {
            throw new VarException(
                    HttpStatus.BAD_REQUEST,
                    Constants.ERROR_USER_OR_PHONE_ALREADY_REGISTERED);
        }
    }

    public void userNameNotRegistered(Optional<User> user) throws VarException {
        if (user.isPresent()) {
            throw new VarException(
                    HttpStatus.BAD_REQUEST,
                    Constants.ERROR_USER_ALREADY_REGISTERED);
        }
    }

    public void newPassword(String currentPassword, String hist1, String hist2, String hist3, String newPassword) throws VarException {

        try {
            if ( (commonUtils.isNotNull(currentPassword) && HashPassword.validatePassword(newPassword, currentPassword))
                    || (commonUtils.isNotNull(hist1) && HashPassword.validatePassword(newPassword, hist1))
                    || (commonUtils.isNotNull(hist2) && HashPassword.validatePassword(newPassword, hist2))
                    || (commonUtils.isNotNull(hist3) && HashPassword.validatePassword(newPassword, hist3))) {
                throw new VarException(
                        HttpStatus.BAD_REQUEST,
                        Constants.ERROR_USER_PASSWORD_NOT_NEW);
            }
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            LOG.error(e.getMessage(), e);
            throw new VarException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    Constants.ERROR_SERVER);
        }
    }

    public void passwordExpiration(LocalDateTime passwordExp) throws VarException {
        if(passwordExp.isBefore(LocalDateTime.now())){
            throw new VarException(HttpStatus.UNAUTHORIZED, Constants.ERROR_USER_PASSWORD_EXPIRED);
        }
    }

    public void passwordStrenght(String username, String password) throws VarException {
        /*
         - panjang minimal 8 karakter
         - kombinasi alfabet, numeric (optional)
         - tidak mengandung special karakter ; - + ' \ ( ) = > < @
         - tidak sama / mengandung user id

        --------------------------------------------------------------------
        REGEX
        ^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\S+$).{8,}$

        ^                 # start-of-string
        (?=.*[0-9])       # a digit must occur at least once
        (?=.*[a-z])       # a lower case letter must occur at least once  -- Simponi not use
        (?=.*[A-Z])       # an upper case letter must occur at least once -- Simponi not use
        (?=.*[@#$%^&+=])  # a special character must occur at least once
        (?=\S+$)          # no whitespace allowed in the entire string
        .{8,}             # anything, at least eight places though
        $                 # end-of-string

         */

        String pattern_alfabet = ".*[a-zA-Z].*";
        String pattern_numeric = ".*[0-9].*";
        String[] pattern_chjar = {";", "-", "+", "'", "\\", "(", ")", "=", ">", "<", "@"};

        if (password.toLowerCase().contains(username.toLowerCase())) {
            throw new VarException(
                    HttpStatus.BAD_REQUEST,
                    Constants.ERR_USER_PASSWORD_NOT_ACCEPTABLE);
        } else if (password.length() > 254) {
            throw new VarException(
                    HttpStatus.BAD_REQUEST,
                    Constants.ERR_USER_PASSWORD_NOT_ACCEPTABLE);
        } else if (password.length() < 8) {
            throw new VarException(
                    HttpStatus.BAD_REQUEST,
                    Constants.ERR_USER_PASSWORD_NOT_ACCEPTABLE);
        } else if (!password.matches(pattern_alfabet)) {
            throw new VarException(
                    HttpStatus.BAD_REQUEST,
                    Constants.ERR_USER_PASSWORD_NOT_ACCEPTABLE);
        } /*else if (!password.matches(pattern_numeric)) {
            throw new VarException(
                    HttpStatus.BAD_REQUEST,
                    Constants.ERR_USER_PASSWORD_NOT_ACCEPTABLE);
        }*/

        for (String str : pattern_chjar) {
            if (password.contains(str)) {
                throw new VarException(
                        HttpStatus.BAD_REQUEST,
                        Constants.ERR_USER_PASSWORD_NOT_ACCEPTABLE);
            }
        }
    }

    public void userNameAcceptable(String userName) throws VarException {

        final Pattern pattern = Pattern.compile("^[A-Za-z0-9]++$");

        if(userName.length() < 5){
            throw new VarException(
                    HttpStatus.BAD_REQUEST,
                    Constants.ERR_USER_USERNAME_NOT_VALID);
        } else if(userName.length() > 25){
            throw new VarException(
                    HttpStatus.BAD_REQUEST,
                    Constants.ERR_USER_USERNAME_NOT_VALID);
        } else if (!pattern.matcher(userName).matches()) {
            throw new VarException(
                    HttpStatus.BAD_REQUEST,
                    Constants.ERR_USER_USERNAME_NOT_VALID);
        }
    }

    public void phoneNumberAcceptable(String phoneNumber) throws VarException {

        final Pattern pattern = Pattern.compile("^[0-9+]++$");

        if (!pattern.matcher(phoneNumber).matches()) {

            throw new VarException(
                    HttpStatus.BAD_REQUEST,
                    Constants.ERR_USER_PHONE_NUMBER_NOT_VALID);
        }
    }
}
