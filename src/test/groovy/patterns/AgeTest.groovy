package patterns

import spock.lang.Specification

/**
 * Created by mtumilowicz on 2018-12-09.
 */
class AgeTest extends Specification {

    def "test of - invalid"() {
        when:
        Age.of(-1)
        
        then:
        thrown(IllegalArgumentException)
    }
    
    def "test of - valid"() {
        given:
        def age = Age.of(1)

        expect:
        age.getAge() == 1
    }
}
