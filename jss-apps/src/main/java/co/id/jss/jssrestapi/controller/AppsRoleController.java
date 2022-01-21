//package co.id.jss.jssrestapi.controller;
//
//import co.id.jss.jssrestapi.common.AuthUtils;
//import co.id.jss.jssrestapi.common.exception.VarException;
//import co.id.jss.jssrestapi.common.exception.VarExceptionEngine;
//import co.id.jss.jssrestapi.common.misc.Constants;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.RequestHeader;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/api/role")
//public class AppsRoleController extends RoleController {
//
//    private final MenuService menuService;
//
//    private final VarExceptionEngine varExceptionEngine;
//
//    private final AuthUtils authUtils;
//
//    @Autowired
//    public AppsRoleController(MenuService menuService, VarExceptionEngine varExceptionEngine, AuthUtils authUtils) {
//        this.menuService = menuService;
//        this.varExceptionEngine = varExceptionEngine;
//        this.authUtils = authUtils;
//    }
//
//    @RequestMapping(
//            value = "/menu",
//            method = RequestMethod.GET,
//            produces = MediaType.APPLICATION_JSON_VALUE)
//    // @PreAuthorize("hasRole('MenuGenerateByAccess')")
//    public ResponseEntity generateMenu(
//            // HEADER FOR SWAGGER ONLY
//            @RequestHeader(value = Constants.JWT_AUTHORIZATION) String jwt,
//            @RequestHeader(value = Constants.HEADER_CLIENT_ID) String clientId,
//            @RequestHeader(value = Constants.HEADER_CLIENT_VERSION) String clientVersion){
//
//        try {
//            Long userId = authUtils.getCurrentUserDetail().getUserId();
//            return ResponseEntity.ok(menuService.getMenuByAccess(userId));
//        } catch (VarException ex) {
//            return varExceptionEngine.generate(ex);
//        }
//    }
//}
