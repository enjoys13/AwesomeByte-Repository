package co.id.jss.jssrestapi.service;

import co.id.jss.jssrestapi.common.AuthUtils;
import co.id.jss.jssrestapi.common.exception.VarException;
import co.id.jss.jssrestapi.common.misc.CommonUtils;
import co.id.jss.jssrestapi.common.misc.Constants;
import co.id.jss.jssrestapi.domain.*;
import co.id.jss.jssrestapi.dto.AccessDTO;
import co.id.jss.jssrestapi.dto.RoleDTO;
import co.id.jss.jssrestapi.dto.RoleDTO_2;
import co.id.jss.jssrestapi.repository.AccessRepository;
import co.id.jss.jssrestapi.repository.RoleAccessRepository;
import co.id.jss.jssrestapi.repository.RoleRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QSort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoleService {

    private final Logger LOG = LoggerFactory.getLogger(RoleService.class);

    private final CommonUtils commonUtils;

    private final RoleRepository roleRepository;

    private final AccessRepository accessRepository;

    private final RoleAccessRepository roleAccessRepository;

    private final AuthUtils authUtils;

    //TODO all message error should get from Constant

    @Autowired
    public RoleService(RoleRepository roleRepository, AccessRepository accessRepository, RoleAccessRepository roleAccessRepository, AuthUtils authUtils, CommonUtils commonUtils) {

        this.roleRepository = roleRepository;
        this.accessRepository = accessRepository;
        this.roleAccessRepository = roleAccessRepository;
        this.authUtils = authUtils;
        this.commonUtils = commonUtils;
    }

    @Transactional(readOnly = true)
    public RoleDTO getRoleById(Long id) throws VarException {

        Optional<Role> role = roleRepository.findById(id);

        if(!role.isPresent()){

            throw new VarException(HttpStatus.BAD_REQUEST, "ROLE NOT FOUND");
        }

        return new RoleDTO(role.get());
    }

    @Transactional (readOnly = true)
    public List<RoleDTO> getRolesList(Boolean isActive) throws VarException {

        /* PREPARING QUERY */
        QRole qRole = QRole.role;
        BooleanBuilder query = new BooleanBuilder();

        if (commonUtils.isNotNull(isActive)) {
            query.and(qRole.isActive.eq(isActive));
        }

        OrderSpecifier<String> ascName = qRole.name.asc();
        QSort sortByName = new QSort(ascName);

        return commonUtils.toList(roleRepository.findAll(query, sortByName)).stream().map(RoleDTO::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<RoleDTO> getRolesPageable(RoleDTO_2 filter, Pageable pageable) throws VarException {

        /* PREPARING QUERY */
        QRole qRole = QRole.role;
        BooleanBuilder query = new BooleanBuilder();

        if (commonUtils.isNotNull(filter.getName())) {
            query.and(qRole.name.containsIgnoreCase(filter.getName()));
        }
        if(commonUtils.isNotNull(filter.getActive())){
            query.and(qRole.isActive.eq(filter.getActive()));
        }

        return roleRepository.findAll(query, pageable).map(RoleDTO::new);
    }

    @Transactional (readOnly = true)
    public AccessDTO getAccessById(Long id) throws VarException {

        Optional<Access> access = accessRepository.findById(id);

        if(!access.isPresent()){

            throw new VarException(HttpStatus.BAD_REQUEST, "ACCESS NOT FOUND");
        }

        return new AccessDTO(access.get());
    }

    @Transactional (readOnly = true)
    public List<AccessDTO> getAllAccessActive() throws VarException {

        /* PREPARING QUERY */
        QAccess qAccess = QAccess.access;
        BooleanBuilder query = new BooleanBuilder();

        query.and(qAccess.isActive.eq(Constants.DEFAULT_STATUS_TRUE));

        List<Access> allAccess = commonUtils.toList(accessRepository.findAll(query));

        if(allAccess.isEmpty()){

            throw new VarException(HttpStatus.BAD_REQUEST, "ACCESS NOT FOUND");
        }

        return allAccess.stream().map(AccessDTO::new).collect(Collectors.toList());
    }

    @Transactional
    public void addAccess(AccessDTO accessDTO) throws VarException {

        String accessName = accessDTO.getName().startsWith("ROLE_") ? accessDTO.getName() : "ROLE_"+accessDTO.getName();

        Optional<Access> checkAccess = accessRepository.findOneByName(accessName);

        if(checkAccess.isPresent()){

            throw new VarException(HttpStatus.BAD_REQUEST, "ACCESS NAME ALREADY EXIST");
        }

        try{

            Access newAccess = new Access();

            newAccess.setName(accessName);
            newAccess.setUri(accessDTO.getUriAccess());

            //todo validation httpMethod value
            newAccess.setHttpMethod(accessDTO.getHttpMethod());
            newAccess.setCreatedAt(LocalDateTime.now());
            newAccess.setCreatedBy(authUtils.getCurrentUserDetail().getUserId());
            newAccess.setUpdatedAt(LocalDateTime.now());
            newAccess.setUpdatedBy(authUtils.getCurrentUserDetail().getUserId());
            newAccess.setActive(Constants.DEFAULT_STATUS_TRUE);

            accessRepository.save(newAccess);

        }catch (Exception ex){

            LOG.error(ex.getMessage());
            throw new VarException(HttpStatus.INTERNAL_SERVER_ERROR, "ERROR ADD NEW ACCESS");
        }
    }

    @Transactional
    public void updateAccess(AccessDTO accessDTO) throws VarException {

        Optional<Access> existAccess = accessRepository.findById(accessDTO.getId());

        if(!existAccess.isPresent()){

            throw new VarException(HttpStatus.BAD_REQUEST, "ACCESS NOT FOUND");
        }

        String accessName = accessDTO.getName().startsWith("ROLE_") ? accessDTO.getName() : "ROLE_"+accessDTO.getName();

        Optional<Access> checkNameExist = accessRepository.findOneByName(accessName);

        if(checkNameExist.isPresent()){
            throw new VarException(HttpStatus.BAD_REQUEST, "ACCESS NAME ALREADY EXIST");
        }

        try {
            existAccess.get().setName(accessName);
            existAccess.get().setUri(accessDTO.getUriAccess());
            //todo validation httpMethod value
            existAccess.get().setHttpMethod(accessDTO.getHttpMethod());
            existAccess.get().setUpdatedAt(LocalDateTime.now());
            existAccess.get().setUpdatedBy(authUtils.getCurrentUserDetail().getUserId());
            existAccess.get().setActive(accessDTO.isActive());

        }catch (Exception ex){

            LOG.error(ex.getMessage());
            throw new VarException(HttpStatus.INTERNAL_SERVER_ERROR, "ERROR UPDATE EXIST ACCESS");
        }
    }

    @Transactional
    public void addRoleAccess(RoleDTO roleDTO) throws VarException {

        //todo check null

        String roleName = roleDTO.getRoleName().toUpperCase();//.startsWith("ROLE_") ? roleName.toUpperCase() : "ROLE_"+roleName.toUpperCase();

        Optional<Role> checkRole = roleRepository.findOneByName(roleName);

        if(checkRole.isPresent()){

            throw new VarException(HttpStatus.BAD_REQUEST, "ROLE NAME ALREADY EXIST");
        }

        try{

            Role newRole = new Role();

            newRole.setName(roleName);
            newRole.setCreatedAt(LocalDateTime.now());
            newRole.setCreatedBy(authUtils.getCurrentUserDetail().getUserId());
            newRole.setUpdatedAt(LocalDateTime.now());
            newRole.setUpdatedBy(authUtils.getCurrentUserDetail().getUserId());
            newRole.setActive(Constants.DEFAULT_STATUS_TRUE);

            Long roleId = roleRepository.save(newRole).getId();

            if(commonUtils.isNotNull(roleId)){

                List<Long> accessListDTO = roleDTO.getAccessList();

                if(!accessListDTO.isEmpty()){

                    saveRoleAccess(accessListDTO, roleId);
                }
            }

        }catch (Exception ex){

            LOG.error(ex.getMessage());
            throw new VarException(HttpStatus.INTERNAL_SERVER_ERROR, "ERROR ADD NEW ROLE");
        }
    }

    @Transactional
    public void updateRoleAccess(RoleDTO roleDTO) throws VarException {

        //todo check null

        Optional<Role> roleDB = roleRepository.findById(roleDTO.getId());

        if(!roleDB.isPresent()){

            throw new VarException(HttpStatus.BAD_REQUEST, "ROLE IS NOT FOUND");

        }

        String roleName = roleDTO.getRoleName().toUpperCase();
        Long roleId = roleDTO.getId();

        //roleName = roleName.startsWith("ROLE_") ? roleName : "ROLE_"+roleName;

        if(!roleName.equals(roleDB.get().getName())) {

            Optional<Role> checkNameExist = roleRepository.findOneByName(roleName);

            if(checkNameExist.isPresent()){

                throw new VarException(HttpStatus.BAD_REQUEST, "ROLE NAME ALREADY EXIST");
            }
        }

        try {

            roleDB.get().setName(roleName);
            roleDB.get().setUpdatedAt(LocalDateTime.now());
            roleDB.get().setUpdatedBy(authUtils.getCurrentUserDetail().getUserId());
            roleDB.get().setActive(roleDTO.isActive());

            roleRepository.save(roleDB.get());

        }catch (Exception ex){

            LOG.error(ex.getMessage());
            throw new VarException(HttpStatus.INTERNAL_SERVER_ERROR, "ERROR UPDATE EXIST ROLE");
        }

        List<Long> accessListDTO = roleDTO.getAccessList();

        if(!accessListDTO.isEmpty()){

            roleAccessRepository.deleteAll(roleDB.get().getRoleAccessById());

            saveRoleAccess(accessListDTO, roleId);
        }
    }

    @Transactional
    void saveRoleAccess(List<Long> accessListDTO, Long roleId) throws VarException {

        RoleAccess newAccessToRole;
        Role roleDomain;
        Access accessDomain;

        for(Long accessIdDTO : accessListDTO){

            Optional<Access> access = accessRepository.findById(accessIdDTO);

            if(access.isPresent()){

                Long accessId = access.get().getId();

                Optional<RoleAccess> checkRoleAccess = roleAccessRepository.findByRoleByRoleId_IdAndAccessByAccessId_Id(roleId, accessId);

                if(!checkRoleAccess.isPresent()){

                    try{
                        newAccessToRole = new RoleAccess();

                        roleDomain = new Role(roleId);
                        accessDomain = new Access(accessId);

                        newAccessToRole.setRoleByRoleId(roleDomain);
                        newAccessToRole.setAccessByAccessId(accessDomain);

                        roleAccessRepository.save(newAccessToRole);

                    }catch (Exception ex){

                        LOG.error(ex.getMessage());
                        throw new VarException(HttpStatus.INTERNAL_SERVER_ERROR, "ERROR ADD ACCESS TO ROLE");
                    }
                }
            }
        }
    }
}
