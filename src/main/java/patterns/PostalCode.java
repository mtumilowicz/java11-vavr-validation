package patterns;

import com.google.common.base.Preconditions;
import io.vavr.control.Validation;
import lombok.NonNull;
import lombok.Value;

import java.util.function.Predicate;
import java.util.regex.Pattern;

/**
 * Created by mtumilowicz on 2018-12-09.
 */
@Value
public class PostalCode {
    public static final Predicate<String> VALIDATOR = Pattern.compile("\\d{2}-\\d{3}").asMatchPredicate();
    
    String postalCode;

    private PostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    
    public static PostalCode of(@NonNull String postalCode) {
        Preconditions.checkArgument(VALIDATOR.test(postalCode));
        
        return new PostalCode(postalCode);
    }

    public static Validation<String, String> validate(String postalCode) {
        return VALIDATOR.test(postalCode)
                ? Validation.valid(postalCode)
                : Validation.invalid(postalCode + " is not a proper postal code!");
    }
}
