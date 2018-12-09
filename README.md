# java11-vavr-validation
Overview of vavr Validation API.

_Reference_: https://softwaremill.com/javaslang-data-validation/  
_Reference_: https://www.vavr.io/vavr-docs/#_validation  
_Reference_: https://www.baeldung.com/vavr-validation-api

# preface
The Validation control is an applicative functor (
https://softwaremill.com/applicative-functor/) 
and facilitates 
accumulating errors. When trying to compose Monads, the 
combination process will short circuit at the first encountered 
error. But 'Validation' will continue processing the combining 
functions, accumulating all errors. This is especially useful 
when doing validation of multiple fields, say a web form, and 
you want to know all errors encountered, instead of one at a time.

# project description
1. suppose we want to validate `PersonRequest`:
    ```
    class PersonRequest {
        String name;
        AddressRequest address;
        List<String> emails;
        int age;
    }
    ```
    ```
    public class AddressRequest {
        String postalCode;
        String city;
    }
    ```
    patterns that we want to validate:
    * `name ~ [\w]`
    * `AddressRequest`:
        * `postalCode ~ [\d{2}-\d{3}]`
        * `city ~ [\w]`
    * `emails -> all email ~ [\w._%+-]+@[\w.-]+\.[\w]{2,}`
    * `age > 0`
1. we provide validators:
    * `AddressRequest`
        ```
        public class AddressRequestValidation {
            public static Validation<Seq<String>, ValidAddressRequest> validate(AddressRequest request) {
        
                return Validation
                        .combine(
                                Word.validate(request.getCity()),
                                PostalCode.validate(request.getPostalCode()))
                        .ap((city, postalCode) -> ValidAddressRequest.builder()
                                .city(Word.of(city))
                                .postalCode(PostalCode.of(postalCode))
                                .build());
            }
        }
        ```
    * emails
        ```
        public static final Pattern PATTERN = Pattern.compile("[\\w._%+-]+@[\\w.-]+\\.[\\w]{2,}");
        
        public static Validation<List<String>, List<String>> validate(List<String> emails) {
            return emails.partition(PATTERN.asMatchPredicate())
                    .apply((successes, failures) -> failures.isEmpty()
                            ? Validation.valid(successes)
                            : Validation.invalid(failures.map(email -> email + " is not a valid email!")));
        }
        ```
    * postal code
        ```
        public static final Pattern PATTERN = Pattern.compile("\\d{2}-\\d{3}");
        
        public static Validation<String, String> validate(String postalCode) {
            return PATTERN.matcher(postalCode).matches()
                    ? Validation.valid(postalCode)
                    : Validation.invalid(postalCode + " is not a proper postal code!");
        }
        ```
    * word
        ```
        public static final Pattern PATTERN = Pattern.compile("[\\w]+");
        
        public static Validation<String, String> validate(String word) {
            return PATTERN.matcher(word).matches()
                    ? Validation.valid(word)
                    : Validation.invalid(word + " is not a proper word!");
        }
        ```
    * positive number
        ```
        public class NumberValidation {
            public static Validation<String, Integer> positive(int number) {
                return number > 0
                        ? Validation.valid(number)
                        : Validation.invalid(number + " is not > 0");
            }
        }
        ```