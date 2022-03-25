package co.id.jss.jssrestapi.service;

import co.id.jss.jssrestapi.common.exception.VarException;
import co.id.jss.jssrestapi.domain.Access;
import co.id.jss.jssrestapi.domain.Menu;
import co.id.jss.jssrestapi.domain.Role;
import co.id.jss.jssrestapi.dto.MenuDTO;
import co.id.jss.jssrestapi.repository.AccessRepository;
import co.id.jss.jssrestapi.repository.MenuRepository;
import co.id.jss.jssrestapi.repository.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Handling menu
 *
 * @author efriandika
 */
@Service
public class MenuService {

    private final Logger LOG = LoggerFactory.getLogger(MenuService.class);

    private final AccessRepository accessRepository;

    private final RoleRepository roleRepository;

    private final MenuRepository varMenuRepository;

    @Autowired
    public MenuService(AccessRepository accessRepository, RoleRepository roleRepository, MenuRepository menuRepository) {
        this.accessRepository = accessRepository;
        this.roleRepository = roleRepository;
        this.varMenuRepository = menuRepository;
    }

    /**
     * Build tree from the list of menu with O(n) complexity
     *
     * @param userId User ID
     * @return List<VarMenuDTO>
     * @throws VarException default
     * @author efriandika
     */
    @Transactional(readOnly = true)
    public List<MenuDTO> getMenuByAccess(Long userId) throws VarException {
        // Retrieve basic list from DB
        List<Role> userRoles = roleRepository.findRolesByUser(userId);
        List<Access> accessList = accessRepository.findAccessByRoles(userRoles);
        List<Menu> varMenuList = varMenuRepository.findByAccess(accessList);

        // Preparing required variable
        Queue<MenuDTO> parentQueue = new LinkedList<>();
        Map<Long, Queue<MenuDTO>> menuDTOMap = new LinkedHashMap<>();
        List<MenuDTO> menuTree = new LinkedList<>();

        // Building Map and Parent Queue
        for (Menu menu : varMenuList) {
            if (menu.getParent().equals(0L)) {
                parentQueue.add(new MenuDTO(menu));
            } else {
                if (!menuDTOMap.containsKey(menu.getParent())) {
                    menuDTOMap.put(menu.getParent(), new LinkedList<>());
                }

                menuDTOMap.get(menu.getParent()).add(new MenuDTO(menu));
            }
        }

        // Build the tree
        while (parentQueue.size() > 0) {
            MenuDTO parent = parentQueue.poll();
            buildTreeChildren(menuDTOMap, parent);
            menuTree.add(parent);
        }

        return menuTree;
    }

    private void buildTreeChildren(Map<Long, Queue<MenuDTO>> varMenuDTOMap, MenuDTO parent) {
        if (varMenuDTOMap.get(parent.getId()) != null) {
            Queue<MenuDTO> children = varMenuDTOMap.get(parent.getId());

            while (children.size() > 0) {
                MenuDTO child = children.poll();
                buildTreeChildren(varMenuDTOMap, child);
                parent.getMenuChild().add(child);
            }
        }
    }
}
