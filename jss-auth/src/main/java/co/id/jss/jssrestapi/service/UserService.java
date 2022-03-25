package co.id.jss.jssrestapi.service;

import co.id.jss.jssrestapi.common.AuthUtils;
import co.id.jss.jssrestapi.common.UserValidation;
import co.id.jss.jssrestapi.common.crypto.HashPassword;
import co.id.jss.jssrestapi.common.exception.VarException;
import co.id.jss.jssrestapi.common.misc.CommonUtils;
import co.id.jss.jssrestapi.common.misc.Constants;
import co.id.jss.jssrestapi.config.AuthConfig;
import co.id.jss.jssrestapi.domain.*;
import co.id.jss.jssrestapi.dto.ChangePasswordUserDTO;
import co.id.jss.jssrestapi.dto.FilterUserDTO;
import co.id.jss.jssrestapi.dto.UpdateUserDTO;
import co.id.jss.jssrestapi.dto.UserDTO;
import co.id.jss.jssrestapi.repository.UserRepository;
import co.id.jss.jssrestapi.repository.UserRoleRepository;
import com.querydsl.core.BooleanBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final Logger LOG = LoggerFactory.getLogger(UserService.class);

    private final CommonUtils commonUtils;

    private final AuthUtils authUtils;

    private final AuthConfig authConfig;

    private final UserRepository userRepository;

    private final UserRoleRepository userRoleRepository;

    private final UserValidation userValidation;

    @Autowired
    public UserService(CommonUtils commonUtils, AuthUtils authUtils, UserRepository userRepository, UserRoleRepository userRoleRepository,
                       UserValidation userValidation, AuthConfig authConfig) {
        this.commonUtils = commonUtils;
        this.authUtils = authUtils;
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.userValidation = userValidation;
        this.authConfig = authConfig;
    }

    @Transactional(readOnly = true)
    public Optional<User> getUser(Long userId) {
        return userRepository.findById(userId);
    }

    @Transactional(readOnly = true)
    public UserDTO getUser() throws VarException {

        User user = userValidation.userExistAndNotBlockedAndActive(getUser(authUtils.getCurrentUserDetail().getUserId()));

        UserDTO userDTO = new UserDTO(user);

        List<String> accessList = new ArrayList<>();

        String accessName;

        for(UserRole userRole : user.getUserRoleById()){

            for(RoleAccess roleAccess : userRole.getRoleByRoleId().getRoleAccessById()){

                accessName = roleAccess.getAccessByAccessId().getName();

                if(!accessList.contains(accessName) && roleAccess.getAccessByAccessId().isActive()){
                    accessList.add(accessName);
                }
            }
        }

        userDTO.setAccessList(accessList);

        return userDTO;
    }

    @Transactional(readOnly = true)
    public Page<UserDTO> getUserPageable(FilterUserDTO filter, Pageable pageable) throws VarException {

        /* PREPARING QUERY */
        QUser users = QUser.user;
        BooleanBuilder query = new BooleanBuilder();

        query.andNot(users.id.eq(Constants.DEFAULT_ID_SYSTEM));
        query.andNot(users.id.eq(Constants.DEFAULT_ID_SYSTEM));

        if (commonUtils.isNotNull(filter.getName())) {
            query.and(users.name.containsIgnoreCase(filter.getName()));
        }
        if (commonUtils.isNotNull(filter.getBirthDate())) {
            query.and(users.birthdate.eq(filter.getBirthDate()));
        }
        if(commonUtils.isNotNull(filter.getAddress1())){
            query.and(users.address.containsIgnoreCase(filter.getAddress1()));
        }
        if(commonUtils.isNotNull(filter.getActive())){
            query.and(users.isActive.eq(filter.getActive()));
        }
        if(commonUtils.isNotNull(filter.getBlock())){
            query.and(users.isBlock.eq(filter.getBlock()));
        }

        return userRepository.findAll(query, pageable).map(UserDTO::new);
    }

    @Transactional
    public void changePassword(ChangePasswordUserDTO request) throws VarException {

        String userlogin = request.getUserlogin();
        String oldPassword = request.getOldPassword();
        String newPassword = request.getNewPassword();

        /* VALIDATE */
        User user = userValidation.userExistAndNotBlockedAndActive(userRepository.findOneByUserlogin(userlogin));
        userValidation.password(user.getPassword(), oldPassword);
        userValidation.passwordStrenght(userlogin, newPassword);
        userValidation.newPassword(user.getPassword(), user.getPasswordHist1(), user.getPasswordHist2(), user.getPasswordHist3(), newPassword);

        try {
            user.setPasswordHist3(user.getPasswordHist2());
            user.setPasswordHist2(user.getPasswordHist1());
            user.setPasswordHist1(user.getPassword());
            user.setPassword(HashPassword.createHash(newPassword));
            user.setUpdatedBy(authUtils.getCurrentUserDetail().getUserId());
            user.setUpdatedAt(LocalDateTime.now());

        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
            throw new VarException(HttpStatus.INTERNAL_SERVER_ERROR, Constants.ERROR_SERVER);
        }
    }


    @Transactional
    public void updateUser(UpdateUserDTO request) throws VarException {

        /* VALIDATION */
        User existUser = userValidation.userExistAndNotBlockedAndActive(userRepository.findOneByUserlogin(authUtils.getCurrentUserDetail().getUserlogin()));

        /* CHECK UPDATE USERNAME */
        if (commonUtils.isNotNull(request.getUsername()) && !request.getUsername().equals(existUser.getUserlogin())) {

            userValidation.userNameNotRegistered(userRepository.findOneByUserlogin(request.getUsername()));

            userValidation.userNameAcceptable(request.getUsername());
        }

        /* CHECK UPDATE PASSWORD */
        if (commonUtils.isNotNull(request.getNewPassword())) {

            if(commonUtils.isNotNull(request.getOldPassword())){
                if(userValidation.password(existUser.getPassword(), request.getOldPassword())){

                    userValidation.passwordStrenght(existUser.getUserlogin(), request.getNewPassword());

                    userValidation.newPassword(existUser.getPassword(), existUser.getPasswordHist1(), existUser.getPasswordHist2(), existUser.getPasswordHist3(), request.getNewPassword());
                }
                else {
                    throw new VarException(HttpStatus.BAD_REQUEST, Constants.ERR_USER_PASSWORD_WRONG);
                }
            }
            else {
                throw new VarException(HttpStatus.BAD_REQUEST, Constants.ERR_USER_OLD_PASSWORD_EMPTY);
            }
        }

        /* CHECK UPDATE PHONE NO */
        if(commonUtils.isNotNull(request.getPhoneNumber()) && !request.getPhoneNumber().equals(existUser.getPhoneNumber())){

            userValidation.phoneNumberNotRegistered(userRepository.findOneByPhoneNumber(request.getPhoneNumber()));

            userValidation.phoneNumberAcceptable(request.getPhoneNumber());
        }

        /* UPDATING */
        try {

            if (commonUtils.isNotNull(request.getNewPassword()) && commonUtils.isNotNull(request.getOldPassword())) {

                existUser.setPasswordHist3(existUser.getPasswordHist2());
                existUser.setPasswordHist2(existUser.getPasswordHist1());
                existUser.setPasswordHist1(existUser.getPassword());
                existUser.setPassword(HashPassword.createHash(request.getNewPassword()));
            }

            existUser.setUserlogin(commonUtils.isNull(request.getUsername()) ? existUser.getUserlogin() : request.getUsername());
            existUser.setPhoneNumber(commonUtils.isNull(request.getPhoneNumber()) ? existUser.getPhoneNumber() : request.getPhoneNumber());
            existUser.setName(commonUtils.isNull(request.getName()) ? existUser.getName() : request.getName());
            existUser.setBirthdate(commonUtils.isNull(request.getBirthDate()) ? existUser.getBirthdate() : request.getBirthDate());
            existUser.setAddress(commonUtils.isNull(request.getAddress()) ? existUser.getAddress() : request.getAddress());
            existUser.setEmail(commonUtils.isNull(request.getEmail()) ? existUser.getEmail() : request.getEmail());
            existUser.setUpdatedAt(LocalDateTime.now());
            existUser.setUpdatedBy(authUtils.getCurrentUserDetail().getUserId());

        } catch (Exception ex) {

            LOG.error(ex.getMessage(), ex);
            throw new VarException(HttpStatus.INTERNAL_SERVER_ERROR, Constants.ERROR_SERVER);
        }
    }

    @Transactional
    public void updateUserByAdmin(UserDTO request) throws VarException {

        /* VALIDATION */
        User existUser = userValidation.userExist(userRepository.findById(request.getId()));

        /* CHECK UPDATE USERNAME */
        if (commonUtils.isNotNull(request.getUserlogin()) && !request.getUserlogin().equals(existUser.getUserlogin())) {

            userValidation.userNameNotRegistered(userRepository.findOneByUserlogin(request.getUserlogin()));

            userValidation.userNameAcceptable(request.getUserlogin());
        }

        /* CHECK UPDATE PASSWORD */
        if (commonUtils.isNotNull(request.getPassword())) {

            userValidation.passwordStrenght(existUser.getUserlogin(), request.getPassword());
            userValidation.newPassword(existUser.getPassword(), existUser.getPasswordHist1(), existUser.getPasswordHist2(), existUser.getPasswordHist3(), request.getPassword());
        }

        /* CHECK UPDATE PHONE NO */
        if(commonUtils.isNotNull(request.getPhoneNumber()) && !request.getPhoneNumber().equals(existUser.getPhoneNumber())){

            userValidation.phoneNumberNotRegistered(userRepository.findOneByPhoneNumber(request.getPhoneNumber()));

            userValidation.phoneNumberAcceptable(request.getPhoneNumber());
        }

        /* UPDATING */
        try {

            if (commonUtils.isNotNull(request.getPassword())) {

                existUser.setPasswordHist3(existUser.getPasswordHist2());
                existUser.setPasswordHist2(existUser.getPasswordHist1());
                existUser.setPasswordHist1(existUser.getPassword());
                existUser.setPassword(HashPassword.createHash(request.getPassword()));
                existUser.setPasswordExp(LocalDateTime.now().plusMinutes(authConfig.getPasswordExpiration()));
                existUser.setLoginFailCount(Constants.DEFAULT_FAIL_COUNT);//default Login Attempt
            }

            existUser.setUserlogin(commonUtils.isNull(request.getUserlogin()) ? existUser.getUserlogin() : request.getUserlogin());
            existUser.setPhoneNumber(commonUtils.isNull(request.getPhoneNumber()) ? existUser.getPhoneNumber() : request.getPhoneNumber());
            existUser.setName(commonUtils.isNull(request.getName()) ? existUser.getName() : request.getName());
            existUser.setBirthdate(commonUtils.isNull(request.getBirthDate()) ? existUser.getBirthdate() : commonUtils.toLocalDate(request.getBirthDate()));
            existUser.setAddress(commonUtils.isNull(request.getAddress()) ? existUser.getAddress() : request.getAddress());
            existUser.setEmail(commonUtils.isNull(request.getEmail()) ? existUser.getEmail() : request.getEmail());
            existUser.setUpdatedAt(LocalDateTime.now());
            existUser.setUpdatedBy(authUtils.getCurrentUserDetail().getUserId());
            existUser.setActive(request.isActive());
            existUser.setBlock(request.isBlock());

            if(request.isBlock()){
                existUser.setStatus(Constants.STATUS_BLOCKED_COMMON);
            }
            else if(!request.isActive()){
                existUser.setStatus(Constants.STATUS_NEW_REGISTERED_COMMON);
            }
            else {
                existUser.setStatus(Constants.STATUS_ACTIVATED_COMMON);
            }

            userRepository.save(existUser);

            if(request.getRoleList().size()>0){

                userRoleRepository.deleteAll(existUser.getUserRoleById());

                List<UserRole> userRoleList = new ArrayList<>();

                UserRole userRole;

                Role role;

                for(Long roleId : request.getRoleList()){

                    userRole = new UserRole();
                    role = new Role();
                    role.setId(roleId);

                    userRole.setUserByUserId(existUser);
                    userRole.setRoleByRoleId(role);

                    userRoleList.add(userRole);
                }

                userRoleRepository.saveAll(userRoleList);
            }

        } catch (Exception ex) {

            LOG.error(ex.getMessage(), ex);
            throw new VarException(HttpStatus.INTERNAL_SERVER_ERROR, Constants.ERROR_SERVER);
        }
    }
}
