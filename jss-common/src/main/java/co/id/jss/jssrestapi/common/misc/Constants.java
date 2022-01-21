package co.id.jss.jssrestapi.common.misc;

import java.util.Arrays;
import java.util.List;

public class Constants {

    /* DATE DEFAULT */
    public final static String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

    /* SPRING PROFILE CONSTANT VALUE */
    public final static String SPRING_PROFILE_PRODUCTION = "prod";

    /* STATUS DEFAULT */
    public final static boolean DEFAULT_STATUS_TRUE = true;
    public final static boolean DEFAULT_STATUS_FALSE = false;
    public final static int DEFAULT_FAIL_COUNT = 0;
    public final static int MAX_FAIL_COUNT = 3;
    public final static String STATUS_FAIL_3_TIMES = "USER BLOCKED BECAUSE LOGIN FAILED 3 TIMES";
    public final static Long DEFAULT_ID_SYSTEM = 0L;

    public final static String STATUS_ACTIVATED_COMMON = "ACTIVE";
    public final static String STATUS_NEW_REGISTERED_COMMON = "NOT ACTIVE";
    public final static String STATUS_BLOCKED_COMMON = "BLOCKED";

    /* DEFAULT HEADER CONSTANT */
    public final static String HEADER_CLIENT_ID = "Client-Id";
    public final static String HEADER_CLIENT_VERSION = "Client-Version";

    /* JWT */
    public final static String JWT_CLAIM_USER_ID = "userId";
    public final static String JWT_CLAIM_ROLE_ID = "roleId";
    public final static String JWT_CLAIM_SESSION = "session";
    public final static String JWT_CLAIM_IS_CUSTOMER = "isCustomer";
    public final static String JWT_CLAIM_AUTHORITIES = "authorities";
    public final static String JWT_AUTHORIZATION = "Authorization";

    /* URI BY PASS CUSTOM AUTHENTICATION - START WITH THESE URI SHALL BY PASSED  */
    public final static List<String> URI_SWAGGER = Arrays.asList("/webjars/springfox-swagger-ui", "/swagger-resources", "/v2/api-docs", "/swagger-ui.html");
    public final static List<String> URI_API_REQUEST_WITHOUT_JWT = Arrays.asList("/api/auth/login", "/api/user/otp", "/api/user/activation", "/api/user/password", "/api/calendar/create");
    public final static List<String> URI_API_BYPASS_PARAMETER_GET = Arrays.asList("/api/parameter/common");

    /* ACTION */
    public final static String ACTION_CREATE = "CREATE";
    public final static String ACTION_UPDATE = "UPDATE";
    public final static String ACTION_DELETE = "DELETE";
    public final static String ACTION_UNDEFINED = "UNDEFINED";

    /* ERROR */
    public final static String PREFIX_KNOWN_ERROR = "ERR_";
    public final static String ERROR_GENERAL = "ERR_GEN: GENERAL ERROR";
    public final static String ERROR_SERVER = "ERR_SRV: INTERNAL SERVER ERROR";
    public final static String ERROR_USER_DETAIL = "ERR_SRV: ERROR GET USER DETAIL";
    public final static String ERROR_HEADER_CLIENT_NOT_FOUND = "ERR_USR: REQUIRED HEADER NOT FOUND";
    public final static String ERROR_HEADER_CLIENT_NOT_FOUND_OR_NOT_ACTIVE = "ERR_USR: REQUIRED HEADER NOT FOUND OR NOT ACTIVE";
    public final static String ERROR_HEADER_CLIENT_VERSION_NOT_MATCH = "ERR_USR: CLIENT VERSION NOT MATCH";
    public final static String ERROR_USER_NOT_FOUND = "ERR_USR: USER IS NOT FOUND";
    public final static String ERROR_DATA_NOT_FOUND = "ERR_USR: DATA IS NOT FOUND";
    public final static String ERROR_BRANCH_NOT_FOUND = "ERR_SRV: BRANCH IS NOT FOUND";
    public final static String ERROR_USER_IS_BLOCKED = "ERR_USR: USER IS BLOCKED";
    public final static String ERROR_USER_NOT_ACTIVE = "ERR_USR: USER IS NOT ACTIVE";
    public final static String ERROR_USER_SESSION_NOT_MATCH = "USER ALREADY LOGGED IN OTHER DEVICE";
    public final static String ERROR_USER_ALREADY_LOGGED_OUT = "USER ALREADY LOGGED OUT";
    public final static String ERROR_USER_SESSION_EXPIRED = "SESSION EXPIRED, PLEASE RE-LOGIN";
    public final static String ERROR_USER_PASSWORD_EXPIRED = "PASSWORD EXPIRED";
    public final static String ERROR_REQUEST_NOT_AUTHORIZED = "ERR_USR: NOT AUTHORIZED ACCESS";
    public final static String ERROR_USER_ALREADY_REGISTERED = "ERR_USR: USERNAME ALREADY REGISTERED";
    public final static String ERROR_USER_OR_PHONE_ALREADY_REGISTERED = "ERR_USR: USERNAME OR PHONE NUMBER ALREADY REGISTERED";
    public final static String ERROR_USER_PASSWORD_NOT_NEW = "ERR_USR: PASSWORD ALREADY USED BY THIS USER";
    public final static String ERR_USER_PASSWORD_NOT_ACCEPTABLE = "ERR_USR: PASSWORD IS NOT ACCEPTABLE";
    public final static String ERR_USER_USERNAME_NOT_VALID = "ERR_USR: USERNAME IS NOT ACCEPTABLE";
    public final static String ERR_USER_PHONE_NUMBER_NOT_VALID = "ERR_USR: PHONE NUMBER IS NOT ACCEPTABLE";
    public final static String ERR_USER_PASSWORD_WRONG = "ERR_USR: PASSWORD IS WRONG";
    public final static String ERR_USER_OLD_PASSWORD_EMPTY = "ERR_USR: PLEASE INPUT YOUR OLD PASSWORD";
    public final static String RANGE_DATE_NOT_PICKED = "ERR: PLEASE PICK YOUR RANGE DATE";

    /* COMMON RESPONSE */
    public final static String ERR_NULL_VARIABLES = "ERR: CANNOT BE NULL : ";
    public final static String ERR_HOLIDAY_EXIST = "ERR: HOLIDAY PARAMETER ALREADY EXIST";
    public final static String ERR_HOLIDAY_NOT_EXIST = "ERR: HOLIDAY PARAMETER DOES NOT EXIST";
    public final static String ERR_HOLIDAY_NOT_AVAILABLE = "ERR: HOLIDAY PARAMETER IS NOT AVAILABLE FOR YEAR = ";
    public final static String ERR_VARLIMIT_EXIST = "ERR: VAR LIMIT PARAMETER IS EXIST";
    public final static String ERR_VARLIMIT_NOT_EXIST = "ERR: VAR LIMIT PARAMETER DOES NOT EXIST";
    public final static String ERR_SAME_VALUE_CURRENCY = "ERR: SAME VALUES BETWEEN BASE AND QUOTE CURRENCY";
    public final static String ERR_CURRENCY_EXIST = "ERR: FOREX CURRENCY ALREADY EXIST";
    public final static String ERR_CURRENCY_NOT_EXIST = "ERR: FOREX CURRENCY DOES NOT EXIST";
    public final static String WARN_CURRENCY_PAIR_EXIST = "WARN: FOREX CURRENCY PAIR ALREADY EXIST";
    public final static String ERR_CURRENCY_PAIR_NOT_EXIST = "ERR: FOREX CURRENCY PAIR DOES NOT EXIST";
    public final static String WARN_OPEN_POSITION_EXIST = "WARN: FOREX OPEN POSITION ALREADY EXIST";
    public final static String ERR_OPEN_POSITION_NOT_EXIST = "ERR: FOREX OPEN POSITION DOES NOT EXIST";
    public final static String WARN_CLOSING_RATE_EXIST = "WARN: FOREX CLOSING RATE ALREADY EXIST";
    public final static String ERR_CLOSING_RATE_NOT_EXIST = "ERR: FOREX CLOSING RATE DOES NOT EXIST";
    public final static String WARN_BOND_DATA_EXIST = "WARN: BOND DATA ALREADY EXIST";
    public final static String ERR_BOND_DATA_NOT_EXIST = "ERR: BOND DATA DOES NOT EXIST";
    public final static String WARN_BOND_OPEN_POSITION_EXIST = "WARN: BOND OPEN POSITION ALREADY EXIST";
    public final static String ERR_BOND_OPEN_POSITION_NOT_EXIST = "ERR: BOND OPEN POSITION DOES NOT EXIST";
    public final static String WARN_BOND_CLOSING_POSITION_EXIST = "WARN: BOND CLOSING POSITION ALREADY EXIST";
    public final static String ERR_BOND_CLOSING_POSITION_NOT_EXIST = "ERR: BOND CLOSING POSITION DOES NOT EXIST";
    public final static String ERR_FILE_IS_NULL = "ERR: FILE CANNOT BE NULL";
    public final static String ERR_FORMAT_FILE_NOT_ACCEPTED = "ERR: FORMAT FILE NOT ACCEPTED";

    /* PARAMETER */
    public final static String VAR_REPORT_NAME = "VaR Report - By ";
    public final static Long DAY_PLUS_1 = 1L;
    public final static String CALENDAR_NEW_YEAR = "NEW YEAR";

    /* VAR RESULT */
    public final static String VAR_RESULT_FOREX = "FOREX";
    public final static String VAR_RESULT_BOND = "BOND";
}
