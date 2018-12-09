package address;

import lombok.Builder;
import lombok.Value;
import person.Word;

/**
 * Created by mtumilowicz on 2018-12-09.
 */
@Value
@Builder
public class ValidAddressRequest {
    PostalCode postalCode;
    Word city;
}
