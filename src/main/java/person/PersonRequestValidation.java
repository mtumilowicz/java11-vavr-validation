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
                        RegexPatternValidation.validate(request.emails, RegexPatternValidation.EMAIL_PATTERN, "EMAIL!").mapError(x -> x.mkString(", ")),
                        AddressRequestValidation.validate(request.address).mapError(x -> x.mkString(", ")),
                        NumberValidation.validate(request.age))
                .ap((name, emails, address, age) -> ValidPersonRequest.builder()
                        .name(name)
                        .emails(emails)
                        .address(address)
                        .age(age)
                        .build());
    }
}
