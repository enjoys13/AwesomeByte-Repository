package co.id.jss.jssrestapi.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "P_MENU_ACCESS")
public class MenuAccess implements Serializable {

    @EmbeddedId
    private MenuAccessPK menuAccessPK;

    @OneToOne
    @JoinColumn(name = "ACCESS_ID", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false)
    private Access access;

    @ManyToOne
    @JoinColumn(name = "MENU_ID", referencedColumnName = "ID", nullable = false)
    private Menu menu;

    public MenuAccessPK getMenuAccessPK() {
        return menuAccessPK;
    }

    public void setMenuAccessPK(MenuAccessPK menuAccessPK) {
        this.menuAccessPK = menuAccessPK;
    }

    public Access getAccess() {
        return access;
    }

    public void setAccess(Access access) {
        this.access = access;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuAccess that = (MenuAccess) o;
        return Objects.equals(menuAccessPK, that.menuAccessPK) && Objects.equals(access, that.access) && Objects.equals(menu, that.menu);
    }

    @Override
    public int hashCode() {
        return Objects.hash(menuAccessPK, access, menu);
    }
}
