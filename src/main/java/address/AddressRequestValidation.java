package address;

import io.vavr.collection.Seq;
import io.vavr.control.Validation;
import validation.RegexPatternValidation;

/**
 * Created by mtumilowicz on 2018-12-09.
 */
public class AddressRequestValidation {
    public static Validation<Seq<String>, ValidAddressRequest> validate(AddressRequest request) {

        return Validation
                .combine(
                        RegexPatternValidation.validate(request.getCity(), RegexPatternValidation.WORD_PATTERN, "NAME!"),
                        RegexPatternValidation.validate(request.getPostalCode(), RegexPatternValidation.POSTAL_CODE_PATTERN, "POSTAL CODE!"))
                .ap((city, postalCode) -> ValidAddressRequest.builder()
                        .city(city)
                        .postalCode(postalCode)
                        .build());
    }
}
