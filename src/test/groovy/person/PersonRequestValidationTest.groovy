package person

import address.AddressRequest
import address.ValidAddressRequest
import io.vavr.collection.List
import patterns.*
import spock.lang.Specification

/**
 * Created by mtumilowicz on 2018-12-09.
 */
class PersonRequestValidationTest extends Specification {
    def "test validate - valid"() {
        given:
        def addressRequest = AddressRequest.builder()
                .city("Warsaw")
                .postalCode("00-001")
                .build()
        and:
        def validAddressRequest = ValidAddressRequest.builder()
                .city(Word.of("Warsaw"))
                .postalCode(PostalCode.of("00-001"))
                .build()

        and:
        def personRequest = PersonRequest.builder()
                .name("Michal")
                .age(1)
                .emails(List.of("michal@gmail.com"))
                .address(addressRequest)
                .build()

        when:
        def report = PersonRequestValidation.validate(personRequest)

        then:
        report.isValid()
        report.get() == ValidPersonRequest.builder()
                .name(Word.of("Michal"))
                .age(Age.of(1))
                .emails(new Emails(List.of(Email.of("michal@gmail.com"))))
                .address(validAddressRequest)
                .build()
    }

    def "test validate - invalid"() {
        given:
        def addressRequest = AddressRequest.builder()
                .city("Warsaw^")
                .postalCode("a")
                .build()

        and:
        def personRequest = PersonRequest.builder()
                .name("Michal_")
                .age(-1)
                .emails(List.of("b"))
                .address(addressRequest)
                .build()

        when:
        def report = PersonRequestValidation.validate(personRequest)

        then:
        report.isInvalid()
        report.getError() == ["b is not a valid email!", 
                              "Warsaw^ is not a proper word!, a is not a proper postal code!", 
                              "-1 is not > 0"] as List
    }

    def "test validate - partially valid"() {
        given:
        def addressRequest = AddressRequest.builder()
                .city("Warsaw")
                .postalCode("00-001")
                .build()

        and:
        def personRequest = PersonRequest.builder()
                .name("Michal")
                .emails(List.of("b"))
                .address(addressRequest)
                .build()

        when:
        def report = PersonRequestValidation.validate(personRequest)

        then:
        report.isInvalid()
        report.getError() == ["b is not a valid email!", "0 is not > 0"] as List
    }
}
