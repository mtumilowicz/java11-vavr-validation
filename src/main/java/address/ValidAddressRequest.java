package address;

import lombok.Builder;

/**
 * Created by mtumilowicz on 2018-12-09.
 */
@Builder
public class ValidAddressRequest {
    String postalCode;
    String city;
}
