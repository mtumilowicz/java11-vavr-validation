package person;

import address.ValidAddressRequest;
import lombok.Builder;

/**
 * Created by mtumilowicz on 2018-12-09.
 */
@Builder
public class ValidPersonRequest {
    String name;
    ValidAddressRequest address;
    String email;
    int age;
}
