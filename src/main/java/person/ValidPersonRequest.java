package person;

import address.ValidAddressRequest;
import io.vavr.collection.List;
import lombok.Builder;

/**
 * Created by mtumilowicz on 2018-12-09.
 */
@Builder
public class ValidPersonRequest {
    String name;
    ValidAddressRequest address;
    List<String> emails;
    int age;
}
