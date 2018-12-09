package person;

import address.AddressRequest;
import io.vavr.collection.List;

/**
 * Created by mtumilowicz on 2018-12-09.
 */
class PersonRequest {
    String name;
    AddressRequest address;
    List<String> emails;
    int age;
}
