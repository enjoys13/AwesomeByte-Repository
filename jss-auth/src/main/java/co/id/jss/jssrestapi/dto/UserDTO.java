package co.id.jss.jssrestapi.dto;

import co.id.jss.jssrestapi.domain.User;
import co.id.jss.jssrestapi.domain.UserRole;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserDTO {

    private Long id;
    private String userlogin;
    private String name;
    private String birthDate;
    private String email;
    private String password;
    private String phoneNumber;
    private String address;
    private List<Long> roleList;
    private List<String> roleListName;
    private List<String> accessList;
    private String createdAt;
    private Long createdBy;
    private String  updatedAt;
    private Long updatedBy;
    private String status;
    private boolean active;
    private boolean block;
    private  String branchCode;
    private  String branchName;
    private  String branchShortName;
    private LocalDate localDateNow;


    public UserDTO() {
    }


    public UserDTO(User user) {

        this.id = user.getId();
        this.userlogin = user.getUserlogin();
        this.name = user.getName();
        this.birthDate = user.getBirthdate().toString();
        this.email = user.getEmail();
        // this.password = user.getPassword();
        this.phoneNumber = user.getPhoneNumber();
        this.address = user.getAddress();
        this.createdAt = user.getCreatedAt().toString();
        this.createdBy = user.getCreatedBy();
        this.updatedAt = user.getUpdatedAt().toString();
        this.updatedBy = user.getUpdatedBy();
        this.status = user.getStatus();
        this.active = user.isActive();
        this.block = user.isBlock();
        this.localDateNow = LocalDate.now();

        // Map User Roles
        List<Long> roleLists = new ArrayList<>();
        List<String> roleListNames = new ArrayList<>();

        for (UserRole userRole : user.getUserRoleById()) {
            roleLists.add(userRole.getRoleByRoleId().getId());
            roleListNames.add(userRole.getRoleByRoleId().getName());
        }

        this.roleList = roleLists;
        this.roleListName = roleListNames;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserlogin() {
        return userlogin;
    }

    public void setUserlogin(String userlogin) {
        this.userlogin = userlogin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Long> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Long> roleList) {
        this.roleList = roleList;
    }

    public List<String> getRoleListName() {
        return roleListName;
    }

    public void setRoleListName(List<String> roleListName) {
        this.roleListName = roleListName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isBlock() {
        return block;
    }

    public void setBlock(boolean block) {
        this.block = block;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getBranchShortName() {
        return branchShortName;
    }

    public void setBranchShortName(String branchShortName) {
        this.branchShortName = branchShortName;
    }

    public LocalDate getLocalDateNow() {
        return localDateNow;
    }

    public void setLocalDateNow(LocalDate localDateNow) {
        this.localDateNow = localDateNow;
    }

    public List<String> getAccessList() {
        return accessList;
    }

    public void setAccessList(List<String> accessList) {
        this.accessList = accessList;
    }
}
