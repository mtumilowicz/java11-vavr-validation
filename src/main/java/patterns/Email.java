package patterns;


import com.google.common.base.Preconditions;
import lombok.NonNull;
import lombok.Value;

import java.util.regex.Pattern;

/**
 * Created by mtumilowicz on 2018-12-09.
 */
@Value
public class Email {
    public static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w._%+-]+@[\\w.-]+\\.[\\w]{2,}$");
    
    String email;

    private Email(String email) {
        this.email = email;
    }
    
    public static Email of(@NonNull String email) {
        Preconditions.checkArgument(EMAIL_PATTERN.matcher(email).matches());
        
        return new Email(email);
    }
}
