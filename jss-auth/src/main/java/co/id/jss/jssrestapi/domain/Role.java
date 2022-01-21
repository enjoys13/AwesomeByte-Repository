package co.id.jss.jssrestapi.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "P_ROLE")
public class Role implements Serializable {

    @Id
    @GeneratedValue(generator = "P_ROLE_SEQ")
    @SequenceGenerator(name = "P_ROLE_SEQ", sequenceName = "P_ROLE_SEQ", allocationSize = 1)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Basic
    @Column(name = "NAME", nullable = false)
    private String name;

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

    @OneToMany(mappedBy = "roleByRoleId")
    private Collection<RoleAccess> roleAccessById;

    @OneToMany(mappedBy = "roleByRoleId")
    private Collection<UserRole> userRoleById;

    public Role() {
    }

    public Role(Long id) {
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
        Role role = (Role) o;
        return isActive == role.isActive && Objects.equals(id, role.id) && Objects.equals(name, role.name) && Objects.equals(createdAt, role.createdAt) && Objects.equals(createdBy, role.createdBy) && Objects.equals(updatedAt, role.updatedAt) && Objects.equals(updatedBy, role.updatedBy) && Objects.equals(roleAccessById, role.roleAccessById) && Objects.equals(userRoleById, role.userRoleById);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, isActive, createdAt, createdBy, updatedAt, updatedBy, roleAccessById, userRoleById);
    }
}