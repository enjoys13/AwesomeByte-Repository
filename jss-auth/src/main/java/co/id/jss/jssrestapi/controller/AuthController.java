package co.id.jss.jssrestapi.controller;

import co.id.jss.jssrestapi.common.exception.VarException;
import co.id.jss.jssrestapi.common.exception.VarExceptionEngine;
import co.id.jss.jssrestapi.common.misc.Constants;
import co.id.jss.jssrestapi.dto.LoginUserDTO;
import co.id.jss.jssrestapi.dto.RegistrationUserDTO;
import co.id.jss.jssrestapi.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public abstract class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private VarExceptionEngine varExceptionEngine;


    @RequestMapping(
            value = "/register",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('AdminAddUsers')")
    public ResponseEntity register(
            //HEADER FOR SWAGGER ONLY
            @RequestHeader(value = Constants.JWT_AUTHORIZATION) String jwt,
            @RequestHeader(value = Constants.HEADER_CLIENT_ID) String clientId,
            @RequestHeader(value = Constants.HEADER_CLIENT_VERSION) String clientVersion,
            @RequestBody RegistrationUserDTO user) {
        try {

            authService.registerUser(user);

            return new ResponseEntity(HttpStatus.OK);

        } catch (VarException ex) {

            return varExceptionEngine.generate(ex);

        } catch (Exception ex){

            return varExceptionEngine.generate(new VarException(HttpStatus.INTERNAL_SERVER_ERROR, Constants.ERROR_GENERAL));
        }
    }

    @RequestMapping(
            value = "/login",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity login(
            //HEADER FOR SWAGGER ONLY
            @RequestHeader(value = Constants.HEADER_CLIENT_ID) String clientId,
            @RequestHeader(value = Constants.HEADER_CLIENT_VERSION) String clientVersion,
            @RequestBody LoginUserDTO user) {
        try {

            return ResponseEntity.ok(authService.loginUser(user));

        } catch (VarException ex) {

            return varExceptionEngine.generate(ex);
        }
    }

    @RequestMapping(
            value = "/logout",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('AuthLogOut')")
    public ResponseEntity logout(
            //HEADER FOR SWAGGER ONLY
            @RequestHeader(value = Constants.JWT_AUTHORIZATION) String jwt,
            @RequestHeader(value = Constants.HEADER_CLIENT_ID) String clientId,
            @RequestHeader(value = Constants.HEADER_CLIENT_VERSION) String clientVersion) {
        try {

            authService.logout();

            return new ResponseEntity(HttpStatus.OK);

        } catch (VarException ex) {

            return varExceptionEngine.generate(ex);
        }
    }
}
