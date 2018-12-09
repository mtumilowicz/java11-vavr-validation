package validation;

import io.vavr.control.Validation;

/**
 * Created by mtumilowicz on 2018-12-09.
 */
public class NumberValidation {
    public static Validation<String, Integer> validate(int number) {
        return number > 0
                ? Validation.valid(number)
                : Validation.invalid("has to be > 0");
    }
}
