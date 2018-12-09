package patterns;

import com.google.common.base.Preconditions;
import lombok.Value;

/**
 * Created by mtumilowicz on 2018-12-09.
 */
@Value
public class Age {
    int age;

    private Age(int age) {
        this.age = age;
    }
    
    public static Age of(int age) {
        Preconditions.checkArgument(age > 0);
        
        return new Age(age);
    }
}
