package address;

import lombok.Builder;
import lombok.Value;

/**
 * Created by mtumilowicz on 2018-12-09.
 */
@Value
@Builder
public class AddressRequest {
    String postalCode;
    String city;
}
