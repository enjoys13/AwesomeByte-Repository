package co.id.jss.jssrestapi.dto;

public class LoginUserDTO {

    private String userlogin;
    private String password;

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

    @Override
    public String toString() {
        return "LoginUserFormDTO{" +
                "username='" + userlogin + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
