package co.id.jss.jssrestapi.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "P_ROLE_ACCESS")
public class RoleAccess implements Serializable {

    @Id
    @GeneratedValue(generator = "P_ROLE_ACCESS_SEQ")
    @SequenceGenerator(name = "P_ROLE_ACCESS_SEQ", sequenceName = "P_ROLE_ACCESS_SEQ", allocationSize = 1)
    @Column(name = "ID", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ROLE_ID", referencedColumnName = "ID", nullable = false)
    private Role roleByRoleId;

    @ManyToOne
    @JoinColumn(name = "ACCESS_ID", referencedColumnName = "ID", nullable = false)
    private Access accessByAccessId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Role getRoleByRoleId() {
        return roleByRoleId;
    }

    public void setRoleByRoleId(Role roleByRoleId) {
        this.roleByRoleId = roleByRoleId;
    }

    public Access getAccessByAccessId() {
        return accessByAccessId;
    }

    public void setAccessByAccessId(Access accessByAccessId) {
        this.accessByAccessId = accessByAccessId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoleAccess that = (RoleAccess) o;
        return Objects.equals(id, that.id) && Objects.equals(roleByRoleId, that.roleByRoleId) && Objects.equals(accessByAccessId, that.accessByAccessId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, roleByRoleId, accessByAccessId);
    }
}
