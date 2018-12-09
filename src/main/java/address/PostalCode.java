package address;

import com.google.common.base.Preconditions;
import lombok.NonNull;
import lombok.Value;

import java.util.regex.Pattern;

/**
 * Created by mtumilowicz on 2018-12-09.
 */
@Value
public class PostalCode {
    public static final Pattern POSTAL_CODE_PATTERN = Pattern.compile("\\d{2}-\\d{4}");
    
    String postalCode;

    private PostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    
    public static PostalCode of(@NonNull String postalCode) {
        Preconditions.checkArgument(POSTAL_CODE_PATTERN.matcher(postalCode).matches());
        
        return new PostalCode(postalCode);
    }
}
