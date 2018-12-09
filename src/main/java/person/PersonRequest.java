package person;

import address.AddressRequest;
import io.vavr.collection.List;
import lombok.Builder;
import lombok.Value;

/**
 * Created by mtumilowicz on 2018-12-09.
 */
@Builder
@Value
class PersonRequest {
    String name;
    AddressRequest address;
    List<String> emails;
    int age;
}
