package patterns;

import com.google.common.base.Preconditions;
import io.vavr.control.Validation;
import lombok.NonNull;
import lombok.Value;

import java.util.regex.Pattern;

/**
 * Created by mtumilowicz on 2018-12-09.
 */
@Value
public class PostalCode {
    public static final Pattern PATTERN = Pattern.compile("\\d{2}-\\d{3}");
    
    String postalCode;

    private PostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    
    public static PostalCode of(@NonNull String postalCode) {
        Preconditions.checkArgument(PATTERN.matcher(postalCode).matches());
        
        return new PostalCode(postalCode);
    }

    public static Validation<String, String> validate(String postalCode) {
        return PATTERN.matcher(postalCode).matches()
                ? Validation.valid(postalCode)
                : Validation.invalid(postalCode + " is not a proper postal code!");
    }
}
