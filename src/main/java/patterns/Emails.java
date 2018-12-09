package patterns;

import io.vavr.collection.List;
import lombok.NonNull;
import lombok.Value;

/**
 * Created by mtumilowicz on 2018-12-09.
 */
@Value
public class Emails {
    List<Email> emails;

    public Emails(@NonNull List<Email> emails) {
        this.emails = emails;
    }
}
