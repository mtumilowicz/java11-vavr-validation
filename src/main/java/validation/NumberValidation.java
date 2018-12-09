package validation;

import io.vavr.control.Validation;

/**
 * Created by mtumilowicz on 2018-12-09.
 */
public class NumberValidation {
    public static Validation<String, Integer> positive(int number) {
        return number > 0
                ? Validation.valid(number)
                : Validation.invalid(number + " is not > 0");
    }
}
