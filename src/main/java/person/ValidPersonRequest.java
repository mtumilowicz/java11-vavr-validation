package person;

import address.ValidAddressRequest;
import lombok.Builder;
import lombok.Value;
import patterns.Age;
import patterns.Emails;
import patterns.Word;

/**
 * Created by mtumilowicz on 2018-12-09.
 */
@Builder
@Value
public class ValidPersonRequest {
    Word name;
    ValidAddressRequest address;
    Emails emails;
    Age age;
}
