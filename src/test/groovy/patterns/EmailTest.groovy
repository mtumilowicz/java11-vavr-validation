package patterns

import io.vavr.collection.List
import spock.lang.Specification 
/**
 * Created by mtumilowicz on 2018-12-09.
 */
class EmailTest extends Specification {
    def "test of - valid"() {
        expect:
        Email.of("a@a.pl").email == "a@a.pl"
    }

    def "test validate - invalid"() {
        when:
        Email.of("a@a")

        then:
        thrown(IllegalArgumentException)
    }

    def "validate - valid emails"() {
        given:
        def emails = Email.validate(List.of("a@a.pl", "b@b.com"))

        expect:
        emails.isValid()
        emails.get() == ["a@a.pl","b@b.com"] as List
    }

    def "validate - invalid emails"() {
        given:
        def emails = Email.validate(List.of("a", "b"))
        
        expect:
        emails.isInvalid()
        emails.getError() == ["a is not a valid email!", "b is not a valid email!"] as List
    }

    def "validate - partially valid emails"() {
        given:
        def emails = Email.validate(List.of("a@a.pl", "b"))

        expect:
        emails.isInvalid()
        emails.getError() == ["b is not a valid email!"] as List
    }
}
