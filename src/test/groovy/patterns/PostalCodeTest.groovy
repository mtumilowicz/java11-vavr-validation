package patterns


import spock.lang.Specification 
/**
 * Created by mtumilowicz on 2018-12-09.
 */
class PostalCodeTest extends Specification {
    def "test of - valid"() {
        expect:
        PostalCode.of("32-300").postalCode == "32-300"
    }

    def "test of - invalid"() {
        when:
        PostalCode.of("a")
        
        then:
        thrown(IllegalArgumentException)
    }

    def "test validate - valid"() {
        when:
        def report = PostalCode.validate("32-300")

        then:
        report.isValid()
        report.get() == "32-300"
    }

    def "test validate - invalid"() {
        when:
        def report = PostalCode.validate("a")

        then:
        report.isInvalid()
        report.getError() == "a is not a proper postal code!"
    }
}
