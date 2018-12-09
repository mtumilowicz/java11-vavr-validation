package validation


import spock.lang.Specification 
/**
 * Created by mtumilowicz on 2018-12-09.
 */
class NumberValidationTest extends Specification {
    def "test positive - int > 0"() {
        when:
        def report = NumberValidation.positive(10)

        then:
        report.isValid()
        report.get() == 10
    }

    def "test positive - int < 0"() {
        when:
        def report = NumberValidation.positive(-1)

        then:
        report.isInvalid()
        report.getError() == "-1 is not > 0"
    }
}
