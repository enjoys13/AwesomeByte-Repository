package co.id.jss.jssrestapi.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "P_ACCESS")
public class Access implements Serializable {

    @Id
    @GeneratedValue(generator = "P_ACCESS_SEQ")
    @SequenceGenerator(name = "P_ACCESS_SEQ", sequenceName = "P_ACCESS_SEQ", allocationSize = 1)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Basic
    @Column(name = "NAME", nullable = false)
    private String name;

    @Basic
    @Column(name = "URI", nullable = false)
    private String uri;

    @Basic
    @Column(name = "HTTP_METHOD")
    private String httpMethod;

    @Basic
    @Column(name = "IS_ACTIVE", nullable = false)
    private boolean isActive;

    @Basic
    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @Basic
    @Column(name = "CREATED_BY")
    private Long createdBy;

    @Basic
    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;

    @Basic
    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @OneToMany(mappedBy = "accessByAccessId")
    private Collection<RoleAccess> roleAccessById;

    public Access() {
    }

    public Access(Long id) {
        this.id = id;
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

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Collection<RoleAccess> getRoleAccessById() {
        return roleAccessById;
    }

    public void setRoleAccessById(Collection<RoleAccess> roleAccessById) {
        this.roleAccessById = roleAccessById;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Access access = (Access) o;
        return isActive == access.isActive && Objects.equals(id, access.id) && Objects.equals(name, access.name) && Objects.equals(uri, access.uri) && Objects.equals(httpMethod, access.httpMethod) && Objects.equals(createdAt, access.createdAt) && Objects.equals(createdBy, access.createdBy) && Objects.equals(updatedAt, access.updatedAt) && Objects.equals(updatedBy, access.updatedBy) && Objects.equals(roleAccessById, access.roleAccessById);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, uri, httpMethod, isActive, createdAt, createdBy, updatedAt, updatedBy, roleAccessById);
    }
}