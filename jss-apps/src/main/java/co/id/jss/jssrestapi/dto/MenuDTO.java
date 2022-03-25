package co.id.jss.jssrestapi.dto;

import co.id.jss.jssrestapi.domain.Menu;

import java.util.ArrayList;
import java.util.List;

public class MenuDTO {

    private Long id;
    private String menuName;
    private String menuPath;
    private String menuIcon;
    private String menuClass;
    private Integer sorting;
    private String type;
    private List<MenuDTO> menuChild = new ArrayList<>();

    public MenuDTO() {
    }

    public MenuDTO(Menu menu) {

        this.id = menu.getId();
        this.menuName = menu.getName();
        this.menuPath = menu.getPath();
        this.menuIcon = menu.getIcon();
        this.menuClass = menu.getClazz();
        this.sorting = menu.getSorting();
        this.type = menu.getType();
        this.menuClass = menu.getClazz();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuPath() {
        return menuPath;
    }

    public void setMenuPath(String menuPath) {
        this.menuPath = menuPath;
    }

    public String getMenuIcon() {
        return menuIcon;
    }

    public void setMenuIcon(String menuIcon) {
        this.menuIcon = menuIcon;
    }

    public String getMenuClass() {
        return menuClass;
    }

    public void setMenuClass(String menuClass) {
        this.menuClass = menuClass;
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

    public List<MenuDTO> getMenuChild() {
        return menuChild;
    }

    public void setMenuChild(List<MenuDTO> menuChild) {
        this.menuChild = menuChild;
    }
}
