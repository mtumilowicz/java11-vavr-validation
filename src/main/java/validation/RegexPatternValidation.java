package validation;

import io.vavr.control.Validation;

import java.util.regex.Pattern;

/**
 * Created by mtumilowicz on 2018-12-09.
 */
public class RegexPatternValidation {
    public static final Pattern WORD_PATTERN = Pattern.compile("[\\w]+");
    public static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w._%+-]+@[\\w.-]+\\.[\\w]{2,}$");
    public static final Pattern POSTAL_CODE_PATTERN = Pattern.compile("\\d{2}-\\d{4}");

    public static Validation<String, String> validate(String field, Pattern pattern, String error) { 
        return pattern.matcher(field).matches() 
                ? Validation.valid(field) 
                : Validation.invalid(error );
    }
}
