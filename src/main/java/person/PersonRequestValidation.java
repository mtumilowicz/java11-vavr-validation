package person;

import address.AddressRequestValidation;
import io.vavr.collection.Seq;
import io.vavr.control.Validation;
import validation.NumberValidation;
import validation.RegexPatternValidation;

/**
 * Created by mtumilowicz on 2018-12-09.
 */
public class PersonRequestValidation {
    public Validation<Seq<String>, ValidPersonRequest> validate(PersonRequest request) {

        return Validation
                .combine(
                        RegexPatternValidation.validate(request.name, RegexPatternValidation.WORD_PATTERN, "NAME!"),
                        RegexPatternValidation.validate(request.email, RegexPatternValidation.EMAIL_PATTERN, "EMAIL!"),
                        AddressRequestValidation.validate(request.address).mapError(x -> x.mkString(", ")),
                        NumberValidation.validate(request.age))
                .ap((name, email, address, age) -> ValidPersonRequest.builder()
                        .name(name)
                        .email(email)
                        .address(address)
                        .age(age)
                        .build());
    }
}
