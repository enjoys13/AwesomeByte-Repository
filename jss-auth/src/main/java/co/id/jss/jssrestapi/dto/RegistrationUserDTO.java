package co.id.jss.jssrestapi.dto;

//import java.time.LocalDate;

import java.util.List;

public class RegistrationUserDTO {

    private String userlogin;
    private String password;
    private String name;
    private String birthDate;
    private String address;
    private String email;
    private String phoneNumber;
    private List<Long> roleList;

    public String getUserlogin() {
        return userlogin;
    }

    public void setUserlogin(String userlogin) {
        this.userlogin = userlogin;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    //    public LocalDate getBirthDate() {
//        return birthDate;
//    }
//
//    public void setBirthDate(LocalDate birthDate) {
//        this.birthDate = birthDate;
//    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<Long> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Long> roleList) {
        this.roleList = roleList;
    }

    @Override
    public String toString() {
        return "RegistrationUserDTO{" +
                "username='" + userlogin + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + name + '\'' +
//                ", lastName='" + lastName + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", address1='" + address + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", roleList=" + roleList +
                '}';
    }
}
