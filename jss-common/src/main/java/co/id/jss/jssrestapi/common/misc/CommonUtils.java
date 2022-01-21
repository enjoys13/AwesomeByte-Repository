package co.id.jss.jssrestapi.common.misc;

import co.id.jss.jssrestapi.common.exception.VarException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
public class CommonUtils {


    private final Logger LOG = LoggerFactory.getLogger(CommonUtils.class);

    public boolean isEmpty(String data) {
        try {
            //return data == null || data.isEmpty() || data.length() == 0 || data.equalsIgnoreCase("");
            //SIMPLIFY
            return data == null || data.isEmpty();
        } catch (NullPointerException npe) {
            return true;
        }
    }

    public LocalDate toLocalDate(String date) {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern(Constants.DEFAULT_DATE_FORMAT));
    }

    public String getUniqueCode() {
        char[] validChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456879".toCharArray();
        int numChars = 6;
        SecureRandom srand = new SecureRandom();
        Random rand = new Random();
        char[] buff = new char[numChars];
        for (int i = 0; i < numChars; ++i) {
            if ((i % 10) == 0) {
                rand.setSeed(srand.nextLong()); // 64 bits of random!
            }
            buff[i] = validChars[rand.nextInt(validChars.length)];
        }
        return new String(buff).toUpperCase();
    }

//    public boolean isEmpty(LocalDate date) {
//        try {
//            long tr = date.toEpochDay();
//            return true;
//        } catch (Exception ex) {
//            return false;
//        }
//    }

    public boolean isClassContainNull(Object obj) throws IllegalArgumentException, IllegalAccessException {
        for (Field f : obj.getClass().getDeclaredFields()) {
            if (f.get(obj) == null) {
                return true;
            }
        }
        return false;
    }

    public String cut(String str, int limit) {
        try {
            return str.length() > limit ? str.substring(0, limit - 1) : str;
        } catch (Exception ex) {
            return str;
        }
    }

    public boolean isNotNull(Object var) throws VarException {
        return !isNull(var);
    }

    public boolean isNull(Object var) throws VarException {
        try {
            if (var == null) {
                return true;
            } else if (var.getClass().equals(String.class)) {
                String data = (String) var;
                return data.isEmpty() || "".equals(data.trim()) || data.equalsIgnoreCase("null");
            } else if (var.getClass().equals(Integer.class)) {
                return false;
            } else if (var.getClass().equals(Long.class)) {
                return false;
            } else if (var.getClass().equals(Date.class)) {
                return false;
            } else if (var.getClass().equals(LocalDate.class)) {
                return false;
            } else if (var.getClass().equals(LocalDateTime.class)) {
                return false;
            } else if (var.getClass().equals(Byte.class)) {
                return false;
            } else if (var instanceof Collection<?>) {
                return ((Collection<?>) var).size() == 0;
            } else if (var instanceof BigDecimal) {
//                return var.equals(BigDecimal.ZERO);
                return false;
            } else if (var.getClass().equals(Boolean.class)) {
                return false;
            } else {
                LOG.error("UNKNOWN CLASS WHEN CHECK NULL FOR OBJECT " + var.getClass().getCanonicalName().toUpperCase());
                throw new VarException(HttpStatus.INTERNAL_SERVER_ERROR, Constants.ERROR_GENERAL);
            }
        } catch (NullPointerException npe) {
            return true;
        }
    }

    public <E> List<E> toList(Iterable<E> iterable) {
        if(iterable instanceof List) {
            return (List<E>) iterable;
        }
        ArrayList<E> list = new ArrayList<E>();
        if(iterable != null) {
            for(E e: iterable) {
                list.add(e);
            }
        }
        return list;
    }

}
