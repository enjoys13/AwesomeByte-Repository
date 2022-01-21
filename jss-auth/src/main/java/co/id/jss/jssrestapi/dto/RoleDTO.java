package co.id.jss.jssrestapi.dto;

import co.id.jss.jssrestapi.domain.Role;

import java.util.List;
import java.util.stream.Collectors;

public class RoleDTO {

    private Long id;
    private String roleName;
    private boolean isActive;
    private List<Long> accessList;

    public RoleDTO() {
    }

    public RoleDTO(Role role) {
        this.id = role.getId();
        this.roleName = role.getName();
        this.isActive = role.isActive();
        this.accessList = role.getRoleAccessById().stream().map(
                access -> access.getAccessByAccessId().getId()).collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public List<Long> getAccessList() {
        return accessList;
    }

    public void setAccessList(List<Long> accessList) {
        this.accessList = accessList;
    }
}
