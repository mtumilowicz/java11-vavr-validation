package validation;

import io.vavr.Tuple2;
import io.vavr.collection.List;
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
                : Validation.invalid(error);
    }

    public static Validation<List<String>, List<String>> validate(List<String> values, Pattern pattern, String error) {
        Tuple2<List<String>, List<String>> matches = values.partition(WORD_PATTERN.asMatchPredicate());
        return matches._2.isEmpty()
                ? Validation.valid(matches._1)
                : Validation.invalid(matches._2);
    }
}
