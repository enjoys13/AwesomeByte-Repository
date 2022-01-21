package co.id.jss.jssrestapi.controller;

import co.id.jss.jssrestapi.common.exception.VarException;
import co.id.jss.jssrestapi.common.exception.VarExceptionEngine;
import co.id.jss.jssrestapi.common.misc.Constants;
import co.id.jss.jssrestapi.dto.ChangePasswordUserDTO;
import co.id.jss.jssrestapi.dto.FilterUserDTO;
import co.id.jss.jssrestapi.dto.UpdateUserDTO;
import co.id.jss.jssrestapi.dto.UserDTO;
import co.id.jss.jssrestapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public abstract class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private VarExceptionEngine varExceptionEngine;

    @RequestMapping(
            value = "/me",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('UserGetProfile')")
    public ResponseEntity getUserProfile(
            //HEADER FOR SWAGGER ONLY
            @RequestHeader(value = Constants.JWT_AUTHORIZATION) String jwt,
            @RequestHeader(value = Constants.HEADER_CLIENT_ID) String clientId,
            @RequestHeader(value = Constants.HEADER_CLIENT_VERSION) String clientVersion){

        try {
            return ResponseEntity.ok(userService.getUser());

        } catch (VarException ex) {

            return varExceptionEngine.generate(ex);
        }
    }

    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('AdminGetPageableUsers')")
    public ResponseEntity getPageableUser(
            //HEADER FOR SWAGGER
            @RequestHeader(value = Constants.JWT_AUTHORIZATION) String jwt,
            @RequestHeader(value = Constants.HEADER_CLIENT_ID) String clientId,
            @RequestHeader(value = Constants.HEADER_CLIENT_VERSION) String clientVersion,
            FilterUserDTO filter,
            Pageable pageable) {

        try {

            Page<UserDTO> pageableUsers = userService.getUserPageable(filter, pageable);

            return new ResponseEntity<>(pageableUsers, HttpStatus.OK);

        } catch (VarException ex) {

            return varExceptionEngine.generate(ex);
        }
    }

    @RequestMapping(
            value = "/password",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('UserChangePassword')")
    public ResponseEntity changePassword(
            //HEADER FOR SWAGGER ONLY
            @RequestHeader(value = Constants.HEADER_CLIENT_ID) String clientId,
            @RequestHeader(value = Constants.HEADER_CLIENT_VERSION) String clientVersion,
            @RequestBody ChangePasswordUserDTO user) {
        try {

            userService.changePassword(user);

            return new ResponseEntity(HttpStatus.OK);

        } catch (VarException ex) {

            return varExceptionEngine.generate(ex);
        }
    }

    @RequestMapping(
            value = "/me",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('UserUpdateProfile')")
    public ResponseEntity updateUserProfile(
            //HEADER FOR SWAGGER ONLY
            @RequestHeader(value = Constants.JWT_AUTHORIZATION) String jwt,
            @RequestHeader(value = Constants.HEADER_CLIENT_ID) String clientId,
            @RequestHeader(value = Constants.HEADER_CLIENT_VERSION) String clientVersion,
            @RequestBody UpdateUserDTO user) {

        try {

            userService.updateUser(user);

            return new ResponseEntity(HttpStatus.OK);

        } catch (VarException ex) {

            return varExceptionEngine.generate(ex);
        }
    }

    @RequestMapping(
            value = "/byadmin",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('AdminUpdateUserProfile')")
    public ResponseEntity updateUserProfileByAdmin(
            //HEADER FOR SWAGGER ONLY
            @RequestHeader(value = Constants.JWT_AUTHORIZATION) String jwt,
            @RequestHeader(value = Constants.HEADER_CLIENT_ID) String clientId,
            @RequestHeader(value = Constants.HEADER_CLIENT_VERSION) String clientVersion,
            @RequestBody UserDTO user) {

        try {

            userService.updateUserByAdmin(user);

            return new ResponseEntity(HttpStatus.OK);

        } catch (VarException ex) {

            return varExceptionEngine.generate(ex);
        }
    }
}
