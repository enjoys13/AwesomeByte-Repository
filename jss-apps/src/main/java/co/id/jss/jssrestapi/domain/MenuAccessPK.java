package co.id.jss.jssrestapi.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class MenuAccessPK implements Serializable {

    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "ACCESS_ID", nullable = false)
    private Long accessId;

    public MenuAccessPK() {
    }

    public MenuAccessPK(Long id, Long accessId) {
        this.id = id;
        this.accessId = accessId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAccessId() {
        return accessId;
    }

    public void setAccessId(Long accessId) {
        this.accessId = accessId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuAccessPK that = (MenuAccessPK) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(accessId, that.accessId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, accessId);
    }
}
