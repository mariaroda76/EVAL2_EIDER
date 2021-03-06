package Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtils {

    public static final String TEXT_FORMAT = "^[a-zA-Z0-9_]*$";

    //^ : start of string
    //[ : beginning of character group
    //a-z : any lowercase letter
    //A-Z : any uppercase letter
    //0-9 : any digit
    //_ : underscore
    //] : end of character group
    //* : zero or more of the given characters
    //$ : end of string

    public static final String NOMBRE_APELLIDO ="^[a-zA-Z ]*$";

    public static final String NOMBRE_APELLIDO_ESPAÑOL ="^[a-zA-ZÀ-ÿ\\u00f1\\u00d1 ]*$";
    // /^[a-zA-ZÀ-ÿ\u00f1\u00d1]+(\s*[a-zA-ZÀ-ÿ\u00f1\u00d1]*)*[a-zA-ZÀ-ÿ\u00f1\u00d1]+$/g;
    // "^[aábcdeéfghijklmnñoópqrstuúüvwxyzAÁBCDEÉFGHIJKLMNÑOÓPQRSTUÚÜVWXYZ]*$"

    public static final String NON_NEGATIVE_INTEGER_FORMAT = "(\\d){1,9}";
    public static final String INTEGER_FORMAT = "(-)?" + NON_NEGATIVE_INTEGER_FORMAT;
    public static final String NON_NEGATIVE_FLOATING_POINT_FORMAT = "(\\d){1,10}\\.(\\d){1,10}";
    public static final String FLOATING_POINT_FORMAT = "(-)?" + NON_NEGATIVE_FLOATING_POINT_FORMAT;
    public static final String NON_NEGATIVE_MONEY_FORMAT = "(\\d){1,15}(\\.(\\d){1,2})?";//XXXXXXXXXX.XX
    public static final String MONEY_FORMAT = "(-)?" + NON_NEGATIVE_MONEY_FORMAT;
    public static final String DATE_DDMMYYYY = "(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)";
    public static final String EMAIL = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    public static final String IP_ADDRESS = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
    public static final String HEX_COLOR_CODE = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$";
    public static final String NON_EMPTY_ALPHA_NUMERIC_STRING = "\\w?";
    public static final String TELEPHONE_REGEX = "[0-9\\-]*";

    //pply DeMorgan's theorem, and write a regex that matches invalid passwords:
    // anything with less than eight characters OR anything with no numbers OR anything with no uppercase
    // OR or anything with no lowercase OR anything with no special characters.
    public static final String PASSWORD_REGEX = "^(.{0,7}|[^0-9]*|[^A-Z]*|[^a-z]*|[a-zA-Z0-9]*)$";


    public static boolean matches(String input, String regex) {

        if (StringUtils.isEmpty(input)) {
            return false;
        }

        if (StringUtils.isEmpty(regex)) {
            return true;
        }

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        if (matcher.matches()) {
            return true;
        }
        return false;
    }
    
}
