package co.id.jss.jssrestapi.service;

import co.id.jss.jssrestapi.common.AuthUtils;
import co.id.jss.jssrestapi.common.UserValidation;
import co.id.jss.jssrestapi.common.crypto.HashPassword;
import co.id.jss.jssrestapi.common.exception.VarException;
import co.id.jss.jssrestapi.common.misc.CommonUtils;
import co.id.jss.jssrestapi.common.misc.Constants;
import co.id.jss.jssrestapi.config.AuthConfig;
import co.id.jss.jssrestapi.domain.Role;
import co.id.jss.jssrestapi.domain.User;
import co.id.jss.jssrestapi.domain.UserRole;
import co.id.jss.jssrestapi.dto.LoginUserDTO;
import co.id.jss.jssrestapi.dto.RegistrationUserDTO;
import co.id.jss.jssrestapi.jwt.JwtAuthenticationResponse;
import co.id.jss.jssrestapi.jwt.JwtClaimDTO;
import co.id.jss.jssrestapi.jwt.JwtTokenEngine;
import co.id.jss.jssrestapi.repository.UserRepository;
import co.id.jss.jssrestapi.repository.UserRoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AuthService {

    private final Logger LOG = LoggerFactory.getLogger(AuthService.class);

    private final CommonUtils commonUtils;

    private final AuthUtils authUtils;

    private final UserRepository userRepository;

    private final UserRoleRepository userRoleRepository;

    private final UserValidation userValidation;

    private final AuthConfig authConfig;

    private final JwtTokenEngine jwtTokenEngine;

    @Autowired
    public AuthService(CommonUtils commonUtils, AuthUtils authUtils, UserRepository userRepository, UserRoleRepository userRoleRepository, UserValidation userValidation, AuthConfig authConfig, JwtTokenEngine jwtTokenEngine) {
        this.commonUtils = commonUtils;
        this.authUtils = authUtils;
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.userValidation = userValidation;
        this.authConfig = authConfig;
        this.jwtTokenEngine = jwtTokenEngine;
    }

    @Transactional
    public void registerUser(RegistrationUserDTO register) throws VarException {

        List<User> existUsers = userRepository.findByUserloginOrPhoneNumber(register.getUserlogin(), register.getPhoneNumber());

        userValidation.userNameOrPhoneNumberNotRegistered(existUsers);

        userValidation.passwordStrenght(register.getUserlogin(), register.getPassword());

        userValidation.userNameAcceptable(register.getUserlogin());

        userValidation.phoneNumberAcceptable(register.getPhoneNumber());

//        String codeOTP = commonUtils.getUniqueCode();

        User newUser = new User();

        try {

            newUser.setUserlogin(register.getUserlogin());
            newUser.setPassword(HashPassword.createHash(register.getPassword()));
            newUser.setName(register.getName());
            newUser.setBirthdate(commonUtils.toLocalDate(register.getBirthDate()));
            newUser.setAddress(register.getAddress());
            newUser.setEmail(register.getEmail());
            newUser.setPhoneNumber(register.getPhoneNumber());
//            newUser.setOtpCode(codeOTP);
//            newUser.setOtpCodeExp(LocalDateTime.now().plusMinutes(authYamlConfig.getOtpExpiration()));
            newUser.setCreatedAt(LocalDateTime.now());
            newUser.setCreatedBy(authUtils.getCurrentUserDetail().getUserId());
            newUser.setUpdatedAt(LocalDateTime.now());
            newUser.setUpdatedBy(authUtils.getCurrentUserDetail().getUserId());
            newUser.setBlock(Constants.DEFAULT_STATUS_FALSE); // default unblocked
            newUser.setActive(Constants.DEFAULT_STATUS_TRUE); // default not active waiting for OTP Activation
            newUser.setStatus(Constants.STATUS_ACTIVATED_COMMON);
            newUser.setPasswordExp(LocalDateTime.now().plusMinutes(authConfig.getPasswordExpiration()));
            newUser.setLoginFailCount(Constants.DEFAULT_FAIL_COUNT);//default Login Attempt

            userRepository.save(newUser);

            Iterable<UserRole> roleList = new ArrayList<>();

            UserRole newRole;

            for(Long role : register.getRoleList()){
                newRole = new UserRole();
                newRole.setUserByUserId(newUser);
                newRole.setRoleByRoleId(new Role(role));

                ((ArrayList<UserRole>) roleList).add(newRole);
            }

            userRoleRepository.saveAll(roleList);


        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
            throw new VarException(HttpStatus.INTERNAL_SERVER_ERROR, Constants.ERROR_SERVER);
        }

//        if(customersRepository.exists(customer.getId())) {
//
//            /* SEND OTP */
//            LOG.info("=========================== MASUK KE KIRIM SMS");
//            otpService.sendActivationMessage(customer.getPhoneNumber(), form.getUsername(), codeOTP);
//
//            /* SEND EMAIL */
//            LOG.info("=========================== MASUK KE KIRIM EMAIL");
//            mailingService.sendActivationMail(customer.getEmail(), form.getUsername(), codeOTP);
//        }
    }

    @Transactional
    public JwtAuthenticationResponse loginUser(LoginUserDTO login) throws VarException {

        Optional<User> existUser = userRepository.findOneByUserlogin(login.getUserlogin());

        /* VALIDATION */
        User user = userValidation.userExistAndNotBlockedAndActive(existUser);

        if(!userValidation.password(user.getPassword(), login.getPassword())){

            int loginFailCount = user.getLoginFailCount() + 1;

            if(loginFailCount >= Constants.MAX_FAIL_COUNT){

                user.setLoginFailCount(Constants.MAX_FAIL_COUNT);
                user.setBlock(Constants.DEFAULT_STATUS_TRUE);
                user.setStatus(Constants.STATUS_BLOCKED_COMMON);

                throw new VarException(HttpStatus.BAD_REQUEST, Constants.STATUS_FAIL_3_TIMES);
            }
            else {

                user.setLoginFailCount(loginFailCount);

                throw new VarException(HttpStatus.BAD_REQUEST, Constants.ERR_USER_PASSWORD_WRONG);
            }
        } else {

            user.setLoginFailCount(Constants.DEFAULT_FAIL_COUNT);
        }

        userValidation.passwordExpiration(user.getPasswordExp());

        /* SAVE SESSION ID TO DB */
        try {

            String sessionId = LocalDateTime.now().toString() + user.getId() + commonUtils.getUniqueCode();

            user.setSessionId(sessionId);

            user.setSessionExp(LocalDateTime.now().plusMinutes(authConfig.getJwtExpiration()));


            return new JwtAuthenticationResponse(jwtTokenEngine.generate(
                    new JwtClaimDTO(user.getId(), sessionId/*, Constants.DEFAULT_STATUS_TRUE*/)));

        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
            throw new VarException(HttpStatus.INTERNAL_SERVER_ERROR, Constants.ERROR_SERVER);
        }
    }

    @Transactional
    public void logout() throws VarException {

        Optional<User> existUser = userRepository.findById(authUtils.getCurrentUserDetail().getUserId());

        User user = userValidation.userExistAndNotBlockedAndActive(existUser);
        try {

            user.setSessionExp(null);
            user.setSessionId(null);

        } catch (Exception ex) {

            LOG.error(ex.getMessage());

            LOG.error("ERROR ON LOG OUT USER BUT JUST IGNORE IT IS OK");

        }
    }

}
