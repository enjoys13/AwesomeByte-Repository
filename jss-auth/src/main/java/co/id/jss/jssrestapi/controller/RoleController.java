package co.id.jss.jssrestapi.controller;

import co.id.jss.jssrestapi.common.exception.VarException;
import co.id.jss.jssrestapi.common.exception.VarExceptionEngine;
import co.id.jss.jssrestapi.common.misc.Constants;
import co.id.jss.jssrestapi.dto.AccessDTO;
import co.id.jss.jssrestapi.dto.RoleDTO;
import co.id.jss.jssrestapi.dto.RoleDTO_2;
import co.id.jss.jssrestapi.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

public abstract class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private VarExceptionEngine varExceptionEngine;

    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasRole('AdminGetListRoles')")
    public ResponseEntity getListRoles(
            //HEADER FOR SWAGGER ONLY
            @RequestHeader(value = Constants.JWT_AUTHORIZATION) String jwt,
            @RequestHeader(value = Constants.HEADER_CLIENT_ID) String clientId,
            @RequestHeader(value = Constants.HEADER_CLIENT_VERSION) String clientVersion,
            @RequestParam Boolean isActive){

        try {
            return ResponseEntity.ok(roleService.getRolesList(isActive));
        } catch (VarException ex) {
            return varExceptionEngine.generate(ex);
        }
    }

    @RequestMapping(
            value = "/pageable",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('AdminGetPageableRoles')")
    public ResponseEntity getPageableRoles(
            //HEADER FOR SWAGGER
            @RequestHeader(value = Constants.JWT_AUTHORIZATION) String jwt,
            @RequestHeader(value = Constants.HEADER_CLIENT_ID) String clientId,
            @RequestHeader(value = Constants.HEADER_CLIENT_VERSION) String clientVersion,
            RoleDTO_2 filter,
            Pageable pageable) {

        try {
            Page<RoleDTO> pageableUsers = roleService.getRolesPageable(filter, pageable);
            return new ResponseEntity<>(pageableUsers, HttpStatus.OK);
        } catch (VarException ex) {
            return varExceptionEngine.generate(ex);
        }
    }

    @RequestMapping(
            value = "/{roleId}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasRole('AdminGetRoleById')")
    public ResponseEntity getOneRoleById(
            //HEADER FOR SWAGGER ONLY
            @RequestHeader(value = Constants.JWT_AUTHORIZATION) String jwt,
            @RequestHeader(value = Constants.HEADER_CLIENT_ID) String clientId,
            @RequestHeader(value = Constants.HEADER_CLIENT_VERSION) String clientVersion,
            @PathVariable(value = "roleId") Long roleId){

        try {
            return ResponseEntity.ok(roleService.getRoleById(roleId));

        } catch (VarException ex) {

            return varExceptionEngine.generate(ex);
        }
    }

    @RequestMapping(
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasRole('AdminAddRoles')")
    public ResponseEntity addNewRoles(
            //HEADER FOR SWAGGER ONLY
            @RequestHeader(value = Constants.JWT_AUTHORIZATION) String jwt,
            @RequestHeader(value = Constants.HEADER_CLIENT_ID) String clientId,
            @RequestHeader(value = Constants.HEADER_CLIENT_VERSION) String clientVersion,
            @RequestBody RoleDTO roleDTO){

        try {
            roleService.addRoleAccess(roleDTO);
            return new ResponseEntity(HttpStatus.OK);
        } catch (VarException ex) {
            return varExceptionEngine.generate(ex);
        }
    }

    @RequestMapping(
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasRole('AdminUpdateRoleById')")
    public ResponseEntity updateRoleById(
            //HEADER FOR SWAGGER ONLY
            @RequestHeader(value = Constants.JWT_AUTHORIZATION) String jwt,
            @RequestHeader(value = Constants.HEADER_CLIENT_ID) String clientId,
            @RequestHeader(value = Constants.HEADER_CLIENT_VERSION) String clientVersion,
            @RequestBody RoleDTO roleDTO){

        try {
            roleService.updateRoleAccess(roleDTO);
            return new ResponseEntity(HttpStatus.OK);
        } catch (VarException ex) {
            return varExceptionEngine.generate(ex);
        }
    }

    @RequestMapping(
            value = "/access",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasRole('AdminGetAllAccess')")
    public ResponseEntity getAllAccess(
            //HEADER FOR SWAGGER ONLY
            @RequestHeader(value = Constants.JWT_AUTHORIZATION) String jwt,
            @RequestHeader(value = Constants.HEADER_CLIENT_ID) String clientId,
            @RequestHeader(value = Constants.HEADER_CLIENT_VERSION) String clientVersion){

        try {
            return ResponseEntity.ok(roleService.getAllAccessActive());
        } catch (VarException ex) {
            return varExceptionEngine.generate(ex);
        }
    }

    @RequestMapping(
            value = "/access/{accessId}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasRole('AdminGetOneAccessById')")
    public ResponseEntity getOneAccessById(
            //HEADER FOR SWAGGER ONLY
            @RequestHeader(value = Constants.JWT_AUTHORIZATION) String jwt,
            @RequestHeader(value = Constants.HEADER_CLIENT_ID) String clientId,
            @RequestHeader(value = Constants.HEADER_CLIENT_VERSION) String clientVersion,
            @PathVariable(value = "accessId") Long accessId){

        try {
            return ResponseEntity.ok(roleService.getAccessById(accessId));
        } catch (VarException ex) {
            return varExceptionEngine.generate(ex);
        }
    }

    @RequestMapping(
            value = "/access",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasRole('AdminAddAccess')")
    public ResponseEntity addAccess(
            //HEADER FOR SWAGGER ONLY
            @RequestHeader(value = Constants.JWT_AUTHORIZATION) String jwt,
            @RequestHeader(value = Constants.HEADER_CLIENT_ID) String clientId,
            @RequestHeader(value = Constants.HEADER_CLIENT_VERSION) String clientVersion,
            @RequestBody AccessDTO accessDTO){

        try {
            roleService.addAccess(accessDTO);
            return new ResponseEntity(HttpStatus.OK);
        } catch (VarException ex) {
            return varExceptionEngine.generate(ex);
        }
    }

    @RequestMapping(
            value = "/access",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasRole('AdminUpdateOneAccessById')")
    public ResponseEntity updateOneAccessById(
            //HEADER FOR SWAGGER ONLY
            @RequestHeader(value = Constants.JWT_AUTHORIZATION) String jwt,
            @RequestHeader(value = Constants.HEADER_CLIENT_ID) String clientId,
            @RequestHeader(value = Constants.HEADER_CLIENT_VERSION) String clientVersion,
            @RequestBody AccessDTO accessDTO){

        try {
            roleService.updateAccess(accessDTO);
            return new ResponseEntity(HttpStatus.OK);
        } catch (VarException ex) {
            return varExceptionEngine.generate(ex);
        }
    }
}
