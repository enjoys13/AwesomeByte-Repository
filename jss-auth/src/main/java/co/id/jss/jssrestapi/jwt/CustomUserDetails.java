package co.id.jss.jssrestapi.jwt;

import co.id.jss.jssrestapi.domain.RoleAccess;
import co.id.jss.jssrestapi.domain.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class CustomUserDetails implements UserDetails {

    private String userlogin;
    private Long userId;
    private Collection<UserRole> userRoleList;
    private String sessionId;
    private String currentURI;
    private Collection<? extends GrantedAuthority> authorities;

    private CustomUserDetails(String userlogin, Long userId, Collection<UserRole> userRoleList, String sessionId, String currentURI,
                              Collection<? extends GrantedAuthority> authorities) {
        this.userlogin = userlogin;
        this.userId = userId;
        this.userRoleList = userRoleList;
        this.sessionId = sessionId;
        this.currentURI = currentURI;
        this.authorities = authorities;
    }

    public static CustomUserDetails createContextHolderUser(String userName, Long id, Collection<UserRole> userRoleList,
                                                            String sessionId, String currentUri) {

        Collection<String> accessNameList = new ArrayList<>();

        String accessName;

        for(UserRole userRole : userRoleList){

            for(RoleAccess roleAccess : userRole.getRoleByRoleId().getRoleAccessById()){

                accessName = roleAccess.getAccessByAccessId().getName();

                if(!accessNameList.contains(accessName) && roleAccess.getAccessByAccessId().isActive()){
                    accessNameList.add(accessName);
                }
            }
        }

        List<GrantedAuthority> authorities = accessNameList.stream().map(SimpleGrantedAuthority::new
        ).collect(Collectors.toList());

        return new CustomUserDetails(
                userName,
                id,
                userRoleList,
                sessionId,
                currentUri,
                authorities
        );
    }

    public Collection<UserRole> getUserRoleList() {
        return userRoleList;
    }

    public void setUserRoleList(Collection<UserRole> userRoleList) {
        this.userRoleList = userRoleList;
    }

    public String getUserlogin() {
        return userlogin;
    }

    public void setUserlogin(String userlogin) {
        this.userlogin = userlogin;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getCurrentURI() {
        return currentURI;
    }

    public void setCurrentURI(String currentURI) {
        this.currentURI = currentURI;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return userlogin;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }
}
