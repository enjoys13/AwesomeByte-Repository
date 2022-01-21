package co.id.jss.jssrestapi.dto;

import co.id.jss.jssrestapi.domain.Access;

public class AccessDTO {

    private Long id;
    private String name;
    private String uriAccess;
    private String httpMethod;
    private Boolean active;

    public AccessDTO() {
    }

    public AccessDTO(Access access) {
        this.id = access.getId();
        this.name = access.getName();
        this.uriAccess = access.getUri();
        this.httpMethod = access.getHttpMethod();
        this.active = access.isActive();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUriAccess() {
        return uriAccess;
    }

    public void setUriAccess(String uriAccess) {
        this.uriAccess = uriAccess;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
