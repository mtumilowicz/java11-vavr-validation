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
public class Word {
    public static final Predicate<String> VALIDATOR = Pattern.compile("[\\w]+").asMatchPredicate();
    
    String word;

    private Word(String word) {
        this.word = word;
    }

    public static Word of(@NonNull String word) {
        Preconditions.checkArgument(VALIDATOR.test(word));
        
        return new Word(word);
    }

    public static Validation<String, String> validate(String word) {
        return VALIDATOR.test(word)
                ? Validation.valid(word)
                : Validation.invalid(word + " is not a proper word!");
    }
}
