package patterns;


import com.google.common.base.Preconditions;
import lombok.NonNull;
import lombok.Value;

import java.util.regex.Pattern;

/**
 * Created by mtumilowicz on 2018-12-09.
 */
@Value
public class Word {
    public static final Pattern WORD_PATTERN = Pattern.compile("[\\w]+");
    
    String word;

    private Word(String word) {
        this.word = word;
    }

    public static Word of(@NonNull String word) {
        Preconditions.checkArgument(WORD_PATTERN.matcher(word).matches());
        
        return new Word(word);
    }
}
