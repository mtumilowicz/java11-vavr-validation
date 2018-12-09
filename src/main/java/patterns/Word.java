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
public class Word {
    public static final Pattern PATTERN = Pattern.compile("[\\w]+");
    
    String word;

    private Word(String word) {
        this.word = word;
    }

    public static Word of(@NonNull String word) {
        Preconditions.checkArgument(PATTERN.matcher(word).matches());
        
        return new Word(word);
    }

    public static Validation<String, String> validate(String word) {
        return PATTERN.matcher(word).matches()
                ? Validation.valid(word)
                : Validation.invalid(word + " is not a proper word!");
    }
}
