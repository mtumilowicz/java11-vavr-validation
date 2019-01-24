package patterns;


import com.google.common.base.Preconditions;
import io.vavr.collection.List;
import io.vavr.control.Validation;
import lombok.NonNull;
import lombok.Value;

import java.util.function.Predicate;
import java.util.regex.Pattern;

/**
 * Created by mtumilowicz on 2018-12-09.
 */
@Value
public class Email {
    public static final Predicate<String> VALIDATOR = Pattern.compile("[\\w._%+-]+@[\\w.-]+\\.[\\w]{2,}")
            .asMatchPredicate();

    String email;

    private Email(String email) {
        this.email = email;
    }

    public static Email of(@NonNull String email) {
        Preconditions.checkArgument(VALIDATOR.test(email));

        return new Email(email);
    }

    public static Validation<List<String>, List<String>> validate(List<String> emails) {
        return emails.partition(VALIDATOR)
                .apply((successes, failures) -> failures.isEmpty()
                        ? Validation.valid(successes)
                        : Validation.invalid(failures.map(email -> email + " is not a valid email!")));
    }
}
