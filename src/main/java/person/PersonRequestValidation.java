package person;

import address.AddressRequestValidation;
import io.vavr.collection.Seq;
import io.vavr.control.Validation;
import patterns.Age;
import patterns.Email;
import patterns.Emails;
import patterns.Word;
import validation.NumberValidation;

/**
 * Created by mtumilowicz on 2018-12-09.
 */
public class PersonRequestValidation {
    public Validation<Seq<String>, ValidPersonRequest> validate(PersonRequest request) {

        return Validation
                .combine(
                        Word.validate(request.getName()),
                        Email.validate(request.getEmails()).mapError(error -> error.mkString(", ")),
                        AddressRequestValidation.validate(request.getAddress()).mapError(error -> error.mkString(", ")),
                        NumberValidation.positive(request.getAge()))
                .ap((name, emails, address, age) -> ValidPersonRequest.builder()
                        .name(Word.of(name))
                        .emails(emails.map(Email::of).transform(Emails::new))
                        .address(address)
                        .age(Age.of(age))
                        .build());
    }
}
