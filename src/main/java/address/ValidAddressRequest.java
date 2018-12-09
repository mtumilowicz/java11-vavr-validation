package address;

import lombok.Builder;
import lombok.Value;

/**
 * Created by mtumilowicz on 2018-12-09.
 */
@Value
@Builder
public class ValidAddressRequest {
    String postalCode;
    String city;
}
