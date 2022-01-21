package co.id.jss.jssrestapi.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "P_CLIENT")
public class Client implements Serializable {

    @Id
    @Column(name = "ID", nullable = false)
    private String id;

    @Basic
    @Column(name = "VERSION", nullable = false)
    private String version;

    @Basic
    @Column(name = "IS_ACTIVE", nullable = false)
    private boolean isActive;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String name) {
        this.version = name;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client that = (Client) o;
        return isActive == that.isActive && Objects.equals(id, that.id) && Objects.equals(version, that.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, version, isActive);
    }
}