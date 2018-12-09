package person;

import address.AddressRequestValidation;
import io.vavr.collection.Seq;
import io.vavr.control.Validation;
import patterns.Age;
import patterns.Email;
import patterns.Word;
import validation.NumberValidation;
import validation.RegexPatternValidation;

/**
 * Created by mtumilowicz on 2018-12-09.
 */
public class PersonRequestValidation {
    public Validation<Seq<String>, ValidPersonRequest> validate(PersonRequest request) {

        return Validation
                .combine(
                        RegexPatternValidation.validate(request.getName(), RegexPatternValidation.WORD_PATTERN, "NAME!"),
                        RegexPatternValidation.validate(request.getEmails(), RegexPatternValidation.EMAIL_PATTERN, "EMAIL!").mapError(x -> x.mkString(", ")),
                        AddressRequestValidation.validate(request.getAddress()).mapError(x -> x.mkString(", ")),
                        NumberValidation.validate(request.getAge()))
                .ap((name, emails, address, age) -> ValidPersonRequest.builder()
                        .name(Word.of(name))
                        .emails(emails.map(Email::of))
                        .address(address)
                        .age(Age.of(age))
                        .build());
    }
}
