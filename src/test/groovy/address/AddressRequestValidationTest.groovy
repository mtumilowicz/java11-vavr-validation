package address

import io.vavr.collection.List
import patterns.PostalCode
import patterns.Word
import spock.lang.Specification

/**
 * Created by mtumilowicz on 2018-12-09.
 */
class AddressRequestValidationTest extends Specification {

    def "valid AddressRequest"() {
        given:
        def request = AddressRequest.builder()
                .city("Olkusz")
                .postalCode("32-300")
                .build()

        when:
        def report = AddressRequestValidation.validate(request)

        then:
        report.isValid()
        report.get() == ValidAddressRequest.builder()
                .city(Word.of("Olkusz"))
                .postalCode(PostalCode.of("32-300"))
                .build()
    }

    def "full not valid AddressRequest"() {
        given:
        def request = AddressRequest.builder()
                .city("wrong!")
                .postalCode("wrong!")
                .build()

        when:
        def report = AddressRequestValidation.validate(request)

        then:
        report.isInvalid()
        report.getError() == ["wrong! is not a proper word!", "wrong! is not a proper postal code!"] as List
    }

    def "partially valid AddressRequest"() {
        given:
        def request = AddressRequest.builder()
                .city("Warsaw")
                .postalCode("wrong!")
                .build()

        when:
        def report = AddressRequestValidation.validate(request)

        then:
        report.isInvalid()
        report.getError() == ["wrong! is not a proper postal code!"] as List
    }
}
