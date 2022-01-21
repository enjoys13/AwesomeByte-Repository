package co.id.jss.jssrestapi.jwt;

import co.id.jss.jssrestapi.common.misc.Constants;
import io.jsonwebtoken.Claims;

public class JwtClaimDTO{

    //private String userName;
    private Long userId;
    //private boolean isCustomer;
    //private int roleId;
    private String sessionId;
    private String currentURI;

    public JwtClaimDTO() {
    }

    public JwtClaimDTO(Long userId, String sessionId/*, boolean isCustomer*/) {
        //this.userName = userName;
        this.userId = userId;
        //this.roleId = roleId;
        this.sessionId = sessionId;
        //this.isCustomer = isCustomer;
    }

    public JwtClaimDTO(Claims body) {
        //this.userName = body.getSubject();
        this.userId = body.get(Constants.JWT_CLAIM_USER_ID, Long.class);
        //this.roleId = (Integer) body.get(Constants.JWT_CLAIM_ROLE_ID);
        this.sessionId = body.get(Constants.JWT_CLAIM_SESSION, String.class);
        //this.isCustomer = (boolean) body.get(Constants.JWT_CLAIM_IS_CUSTOMER);
    }

    public String getCurrentURI() {
        return currentURI;
    }

    public void setCurrentURI(String currentURI) {
        this.currentURI = currentURI;
    }

    /*public String getUsername() {
        return userName;
    }

    public void setUsername(String userName) {
        this.userName = userName;
    }*/

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /*public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }*/

    public String getSessionId() {
        return sessionId;
    }

    public void setSession(String sessionId) {
        this.sessionId = sessionId;
    }

//    public boolean isCustomer() {
//        return isCustomer;
//    }
//
//    public void setCustomer(boolean customer) {
//        isCustomer = customer;
//    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
