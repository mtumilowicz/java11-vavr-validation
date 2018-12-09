package address;

import lombok.Builder;
import lombok.Value;
import patterns.PostalCode;
import patterns.Word;

/**
 * Created by mtumilowicz on 2018-12-09.
 */
@Value
@Builder
public class ValidAddressRequest {
    PostalCode postalCode;
    Word city;
}
