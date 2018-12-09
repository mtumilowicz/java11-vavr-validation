package person;

import address.ValidAddressRequest;
import io.vavr.collection.List;
import lombok.Builder;
import lombok.Value;

/**
 * Created by mtumilowicz on 2018-12-09.
 */
@Builder
@Value
public class ValidPersonRequest {
    Word name;
    ValidAddressRequest address;
    List<Email> emails;
    Age age;
}
