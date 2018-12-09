package patterns;


import com.google.common.base.Preconditions;
import io.vavr.Tuple2;
import io.vavr.collection.List;
import io.vavr.control.Validation;
import lombok.NonNull;
import lombok.Value;

import java.util.regex.Pattern;

/**
 * Created by mtumilowicz on 2018-12-09.
 */
@Value
public class Email {
    public static final Pattern PATTERN = Pattern.compile("^[\\w._%+-]+@[\\w.-]+\\.[\\w]{2,}$");

    String email;

    private Email(String email) {
        this.email = email;
    }

    public static Email of(@NonNull String email) {
        Preconditions.checkArgument(PATTERN.matcher(email).matches());

        return new Email(email);
    }

    public static Validation<String, String> validate(@NonNull String email) {
        return PATTERN.matcher(email).matches()
                ? Validation.valid(email)
                : Validation.invalid(email + " is not a valid email!");
    }

    public static Validation<List<String>, List<String>> validate(List<String> emails) {
        Tuple2<List<String>, List<String>> matches = emails.partition(PATTERN.asMatchPredicate());

        return matches._2.isEmpty()
                ? Validation.valid(matches._1)
                : Validation.invalid(matches._2);
    }
}
