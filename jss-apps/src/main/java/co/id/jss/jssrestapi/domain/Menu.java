package co.id.jss.jssrestapi.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name = "P_MENU")
public class Menu implements Serializable {

    @Id
    @Column(name = "ID", nullable = false)
    private Long id;

    @Basic
    @Column(name = "NAME", nullable = false)
    private String name;

    @Basic
    @Column(name = "PARENT")
    private Long parent;

    @Basic
    @Column(name = "PATH")
    private String path;

    @Basic
    @Column(name = "CLASS")
    private String clazz;

    @Basic
    @Column(name = "ICON")
    private String icon;

    @Basic
    @Column(name = "SORTING")
    private Integer sorting;

    @Basic
    @Column(name = "TYPE")
    private String type;

    @OneToMany(mappedBy = "menu")
    private Collection<MenuAccess> menuAccessCollection;

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

    public Long getParent() {
        return parent;
    }

    public void setParent(Long parent) {
        this.parent = parent;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Collection<MenuAccess> getMenuAccessCollection() {
        return menuAccessCollection;
    }

    public void setMenuAccessCollection(Collection<MenuAccess> menuAccessCollection) {
        this.menuAccessCollection = menuAccessCollection;
    }

    public Integer getSorting() {
        return sorting;
    }

    public void setSorting(Integer sorting) {
        this.sorting = sorting;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
