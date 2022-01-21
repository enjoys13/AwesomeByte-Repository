package co.id.jss.jssrestapi.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "P_USER_ROLE")
public class UserRole implements Serializable {

    @Id
    @GeneratedValue(generator = "P_USER_ROLE_SEQ")
    @SequenceGenerator(name = "P_USER_ROLE_SEQ", sequenceName = "P_USER_ROLE_SEQ", allocationSize = 1)
    @Column(name = "ID", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID", nullable = false)
    private User userByUserId;

    @ManyToOne
    @JoinColumn(name = "ROLE_ID", referencedColumnName = "ID", nullable = false)
    private Role roleByRoleId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUserByUserId() {
        return userByUserId;
    }

    public void setUserByUserId(User userByUserId) {
        this.userByUserId = userByUserId;
    }

    public Role getRoleByRoleId() {
        return roleByRoleId;
    }

    public void setRoleByRoleId(Role roleByRoleId) {
        this.roleByRoleId = roleByRoleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRole userRole = (UserRole) o;
        return Objects.equals(id, userRole.id) && Objects.equals(userByUserId, userRole.userByUserId) && Objects.equals(roleByRoleId, userRole.roleByRoleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userByUserId, roleByRoleId);
    }
}