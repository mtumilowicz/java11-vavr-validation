package patterns


import spock.lang.Specification 
/**
 * Created by mtumilowicz on 2018-12-09.
 */
class WordTest extends Specification {
    def "test of - valid"() {
        expect:
        Word.of("abc").getWord() == "abc"
    }

    def "test of - invalid"() {
        when:
        Word.of("_^")
        
        then:
        thrown(IllegalArgumentException)
    }

    def "test validate - valid"() {
        when:
        def report = Word.validate("abc")

        then:
        report.isValid()
        report.get() == "abc"
    }

    def "test validate - invalid"() {
        when:
        def report = Word.validate("^")

        then:
        report.isInvalid()
        report.getError() == "^ is not a proper word!"
    }
}
