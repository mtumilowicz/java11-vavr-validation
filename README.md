[![Build Status](https://travis-ci.com/mtumilowicz/java11-vavr-validation.svg?branch=master)](https://travis-ci.com/mtumilowicz/java11-vavr-validation)

# java11-vavr-validation
Overview of vavr `Validation API`.

_Reference_: https://softwaremill.com/javaslang-data-validation/  
_Reference_: https://www.vavr.io/vavr-docs/#_validation  
_Reference_: https://www.baeldung.com/vavr-validation-api

# preface
The `Validation` control is an applicative functor (
https://softwaremill.com/applicative-functor/) 
and facilitates 
accumulating errors. When trying to compose `Monads`, the 
combination process will short circuit at the first encountered 
error. But `Validation` will continue processing the combining 
functions, accumulating all errors. This is especially useful 
when doing validation of multiple fields, say a web form, and 
you want to know all errors encountered, instead of one at a time.

Preparing `Validator` is quite straight-forward:
1. combine validations with `combine` method:
    ```
    static <E, T1, T2> Builder<E, T1, T2> combine(Validation<E, T1> validation1, Validation<E, T2> validation2)
    ```
    * up to 8 arguments
1. then use `ap` function to get requested Validator:
    ```
    public <R> Validation<Seq<E>, R> ap(Function2<T1, T2, R> f)
    ```
    * **same number of arguments as combine**

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
    with patterns:
    * `name ~ [\w]`
    * `AddressRequest`:
        * `postalCode ~ [\d{2}-\d{3}]`
        * `city ~ [\w]`
    * `emails -> all email ~ [\w._%+-]+@[\w.-]+\.[\w]{2,}`
    * `age > 0`
1. we create classes that abstract concept mentioned above
    * age
        ```
        @Value
        public class Age {
            int age;
        
            private Age(int age) {
                this.age = age;
            }
            
            public static Age of(int age) {
                Preconditions.checkArgument(age > 0);
                
                return new Age(age);
            }
        }
        ```
    * email / emails
        ```
        @Value
        public class Email {
            public static final Pattern PATTERN = Pattern.compile("[\\w._%+-]+@[\\w.-]+\\.[\\w]{2,}");
        
            String email;
        
            private Email(String email) {
                this.email = email;
            }
        
            public static Email of(@NonNull String email) {
                Preconditions.checkArgument(PATTERN.matcher(email).matches());
        
                return new Email(email);
            }
        
            public static Validation<List<String>, List<String>> validate(List<String> emails) {
                return emails.partition(PATTERN.asMatchPredicate())
                        .apply((successes, failures) -> failures.isEmpty()
                                ? Validation.valid(successes)
                                : Validation.invalid(failures.map(email -> email + " is not a valid email!")));
            }
        }
        ```
        ```
        @Value
        public class Emails {
            List<Email> emails;
        
            public Emails(@NonNull List<Email> emails) {
                this.emails = emails;
            }
        }
        ```
    * postal code
        ```
        @Value
        public class PostalCode {
            public static final Pattern PATTERN = Pattern.compile("\\d{2}-\\d{3}");
            
            String postalCode;
        
            private PostalCode(String postalCode) {
                this.postalCode = postalCode;
            }
            
            public static PostalCode of(@NonNull String postalCode) {
                Preconditions.checkArgument(PATTERN.matcher(postalCode).matches());
                
                return new PostalCode(postalCode);
            }
        
            public static Validation<String, String> validate(String postalCode) {
                return PATTERN.matcher(postalCode).matches()
                        ? Validation.valid(postalCode)
                        : Validation.invalid(postalCode + " is not a proper postal code!");
            }
        }
        ```
    * word
        ```
        @Value
        public class Word {
            public static final Pattern PATTERN = Pattern.compile("[\\w]+");
            
            String word;
        
            private Word(String word) {
                this.word = word;
            }
        
            public static Word of(@NonNull String word) {
                Preconditions.checkArgument(PATTERN.matcher(word).matches());
                
                return new Word(word);
            }
        
            public static Validation<String, String> validate(String word) {
                return PATTERN.matcher(word).matches()
                        ? Validation.valid(word)
                        : Validation.invalid(word + " is not a proper word!");
            }
        }
        ```
1. we provide validators (some mentioned above, but here 
we get all validators together):
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
        **note that in the end we construct** `ValidAddressRequest`:
        ```
        @Value
        @Builder
        public class ValidAddressRequest {
            PostalCode postalCode;
            Word city;
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
    * `PersonRequestValidation`
        ```
        public class PersonRequestValidation {
            public static Validation<Seq<String>, ValidPersonRequest> validate(PersonRequest request) {
        
                return Validation
                        .combine(
                                Word.validate(request.getName()),
                                Email.validate(request.getEmails()).mapError(error -> error.mkString(", ")),
                                AddressRequestValidation.validate(request.getAddress()).mapError(error -> error.mkString(", ")),
                                NumberValidation.positive(request.getAge()))
                        .ap((name, emails, address, age) -> ValidPersonRequest.builder()
                                .name(Word.of(name))
                                .emails(emails.map(Email::of).transform(Emails::new))
                                .address(address)
                                .age(Age.of(age))
                                .build());
            }
        }
        ```
        **note that in the end we construct** `ValidPersonRequest`:
        ```
        @Builder
        @Value
        public class ValidPersonRequest {
            Word name;
            ValidAddressRequest address;
            Emails emails;
            Age age;
        }
        ```