package co.id.jss.jssrestapi.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "P_USER")
public class User implements Serializable {

    @Id
    @GeneratedValue(generator = "P_USER_SEQ")
    @SequenceGenerator(name = "P_USER_SEQ", sequenceName = "P_USER_SEQ", allocationSize = 1)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Basic
    @Column(name = "USERLOGIN", nullable = false)
    private String userlogin;

    @Basic
    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Basic
    @Column(name = "PASSWORD_EXP", nullable = false)
    private LocalDateTime passwordExp;

    @Basic
    @Column(name = "NAME", nullable = false)
    private String name;

    @Basic
    @Column(name = "EMAIL", nullable = false)
    private String email;

    @Basic
    @Column(name = "PHONE_NUMBER", nullable = false)
    private String phoneNumber;

    @Basic
    @Column(name = "GENDER")
    private String gender;

    @Basic
    @Column(name = "BIRTHDATE", nullable = false)
    private LocalDate birthdate;

    @Basic
    @Column(name = "ADDRESS")
    private String address;

    @Basic
    @Column(name = "CITY")
    private String city;

    @Basic
    @Column(name = "PROVINCE")
    private String province;

    @Basic
    @Column(name = "COUNTRY")
    private String country;

    @Basic
    @Column(name = "IMAGE")
    private String image;

    @Basic
    @Column(name = "IS_ACTIVE", nullable = false)
    private boolean isActive;

    @Basic
    @Column(name = "IS_BLOCK", nullable = false)
    private boolean isBlock;

    @Basic
    @Column(name = "STATUS")
    private String status;

    @Basic
    @Column(name = "PASSWORD_HIST_1")
    private String passwordHist1;

    @Basic
    @Column(name = "PASSWORD_HIST_2")
    private String passwordHist2;

    @Basic
    @Column(name = "PASSWORD_HIST_3")
    private String passwordHist3;

    @Basic
    @Column(name = "OTP_CODE")
    private String otpCode;

    @Basic
    @Column(name = "OTP_EXP")
    private LocalDateTime otpExp;

    @Basic
    @Column(name = "LOGIN_FAIL_COUNT")
    private Integer loginFailCount;

    @Basic
    @Column(name = "SESSION_ID")
    private String sessionId;

    @Basic
    @Column(name = "SESSION_EXP")
    private LocalDateTime sessionExp;

    @Basic
    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @Basic
    @Column(name = "CREATED_BY")
    private String createdBy;

    @Basic
    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;

    @Basic
    @Column(name = "UPDATED_BY")
    private String updatedBy;

    @OneToMany(mappedBy = "userByUserId")
    private Collection<UserRole> userRoleById;

    public User(Long id) {
        this.id = id;
    }

    public User() {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getPasswordExp() {
        return passwordExp;
    }

    public void setPasswordExp(LocalDateTime passwordExp) {
        this.passwordExp = passwordExp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isBlock() {
        return isBlock;
    }

    public void setBlock(boolean block) {
        isBlock = block;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPasswordHist1() {
        return passwordHist1;
    }

    public void setPasswordHist1(String passwordHist1) {
        this.passwordHist1 = passwordHist1;
    }

    public String getPasswordHist2() {
        return passwordHist2;
    }

    public void setPasswordHist2(String passwordHist2) {
        this.passwordHist2 = passwordHist2;
    }

    public String getPasswordHist3() {
        return passwordHist3;
    }

    public void setPasswordHist3(String passwordHist3) {
        this.passwordHist3 = passwordHist3;
    }

    public String getOtpCode() {
        return otpCode;
    }

    public void setOtpCode(String otpCode) {
        this.otpCode = otpCode;
    }

    public LocalDateTime getOtpExp() {
        return otpExp;
    }

    public void setOtpExp(LocalDateTime otpExp) {
        this.otpExp = otpExp;
    }

    public Integer getLoginFailCount() {
        return loginFailCount;
    }

    public void setLoginFailCount(Integer loginFailCount) {
        this.loginFailCount = loginFailCount;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public LocalDateTime getSessionExp() {
        return sessionExp;
    }

    public void setSessionExp(LocalDateTime sessionExp) {
        this.sessionExp = sessionExp;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Collection<UserRole> getUserRoleById() {
        return userRoleById;
    }

    public void setUserRoleById(Collection<UserRole> userRoleById) {
        this.userRoleById = userRoleById;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return isActive == user.isActive && isBlock == user.isBlock && Objects.equals(id, user.id) && Objects.equals(userlogin, user.userlogin) && Objects.equals(password, user.password) && Objects.equals(passwordExp, user.passwordExp) && Objects.equals(name, user.name) && Objects.equals(email, user.email) && Objects.equals(phoneNumber, user.phoneNumber) && Objects.equals(gender, user.gender) && Objects.equals(birthdate, user.birthdate) && Objects.equals(address, user.address) && Objects.equals(city, user.city) && Objects.equals(province, user.province) && Objects.equals(country, user.country) && Objects.equals(image, user.image) && Objects.equals(status, user.status) && Objects.equals(passwordHist1, user.passwordHist1) && Objects.equals(passwordHist2, user.passwordHist2) && Objects.equals(passwordHist3, user.passwordHist3) && Objects.equals(otpCode, user.otpCode) && Objects.equals(otpExp, user.otpExp) && Objects.equals(loginFailCount, user.loginFailCount) && Objects.equals(sessionId, user.sessionId) && Objects.equals(sessionExp, user.sessionExp) && Objects.equals(createdAt, user.createdAt) && Objects.equals(createdBy, user.createdBy) && Objects.equals(updatedAt, user.updatedAt) && Objects.equals(updatedBy, user.updatedBy) && Objects.equals(userRoleById, user.userRoleById);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userlogin, password, passwordExp, name, email, phoneNumber, gender, birthdate, address, city, province, country, image, isActive, isBlock, status, passwordHist1, passwordHist2, passwordHist3, otpCode, otpExp, loginFailCount, sessionId, sessionExp, createdAt, createdBy, updatedAt, updatedBy, userRoleById);
    }
}
