package com.switchfully.apps.betterparkshark.utility;

import com.switchfully.apps.betterparkshark.domain.Member;
import com.switchfully.apps.betterparkshark.exception.MemberNotFoundException;
import com.switchfully.apps.betterparkshark.exception.InvalidInputException;
import com.switchfully.apps.betterparkshark.webapi.dto.AddressDtoInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

public class Validation {

    private static final Logger logger = LoggerFactory.getLogger(Validation.class);

    private Validation() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static <T> T validateArgument(T argumentToValidate, String exceptionMessage, Predicate<T> invalidWhen) throws IllegalArgumentException {
        if(invalidWhen.test(argumentToValidate)){
            logger.error(exceptionMessage);
            throw new IllegalArgumentException(exceptionMessage);
        }
        return argumentToValidate;
    }

    public static <T> T validateArgumentWithBooleanCondition(T argumentToValidate, String exceptionMessage, Boolean invalidWhen) throws IllegalArgumentException {
        if(invalidWhen){
            logger.error(exceptionMessage);
            throw new IllegalArgumentException(exceptionMessage);
        }
        return argumentToValidate;
    }

    public static <T> T validateArgument(T argumentToValidate, String exceptionMessage, Predicate<T> invalidWhen, Function<String,RuntimeException> exceptionFunction) throws RuntimeException {
        if(invalidWhen.test(argumentToValidate)){
            logger.error(exceptionMessage);
            throw exceptionFunction.apply(exceptionMessage);
        }
        return argumentToValidate;
    }

    public static <T> T validateArgumentWithBooleanCondition(T argumentToValidate, String exceptionMessage, Boolean invalidWhen, Function<String,RuntimeException> exceptionFunction) throws RuntimeException {
        if(invalidWhen){
            logger.error(exceptionMessage);
            throw exceptionFunction.apply(exceptionMessage);
        }
        return argumentToValidate;
    }

    public static String validateNonBlank(String string, String exceptionMessage, Function<String, RuntimeException> exceptionFunction) throws RuntimeException {
        return validateArgumentWithBooleanCondition(string, exceptionMessage,string==null||string.isBlank(),exceptionFunction);
    }

    public static <T> T checkArgumentAndLogWarn(T argumentToValidate, String LogMessage, Predicate<T> warnWhen) {
        if(warnWhen.test(argumentToValidate)){
            logger.warn(LogMessage);
        }
        return argumentToValidate;
    }

    public static <T> T logWarningIfConditionMet(T argumentToValidate, String LogMessage, boolean warnWhen) {
        if(warnWhen){
            logger.warn(LogMessage);
        }
        return argumentToValidate;
    }

    private void EXAMPLES() {
        int intArg = 100;
        boolean boolArg = true;
        Member memberArg = null;
        AddressDtoInput addressDtoInput = null;
        //to use these validation methods you can either use a predicate as a condition, or a boolean (sometimes predicates are needed for more complex logic)
        //the below examples all use predicates

        //If you dont provide validateArgument() with an Exception constructor via method reference, it will by default throw an illegal argument exception
        //the message below will both be the exception message and the logging message with logger.error(exceptionmessage)
        validateArgument(intArg, "This argument cannot be higher than 50", i->i>50);

        //here, a method reference (or Function lambda when the type of exception needs more than just the message as an argument)
        // to the runtimeException constructor is given, the method is overloaded and now ta runtime exception is thrown with the input message
        //here logger.error(exceptionMessage) is called as well
        validateArgument(boolArg, "This boolean cannot be true", b-> b==true, RuntimeException::new);

        //another example with MemberNotFoundException
        validateArgument(memberArg, "Member does not exist in database", m->Objects.isNull(m), MemberNotFoundException::new);

        //another example with a function lambda instead of a method reference (i dont think we will use this ever but hey, we can)
        String street = validateNonBlank(addressDtoInput.getStreet(),"Street field cannot be null", m->new ResponseStatusException(HttpStatus.BAD_REQUEST,m));

        // Its easy to combine these in your own specialized methods if you need/want to check multiple things at a time as these static methods are available everywhere in the project :)
        validateAddressInput(addressDtoInput);

    }

    private void validateAddressInput(AddressDtoInput input) {
        String regex = "[a-zA-Z]+";

        validateNonBlank(input.getStreet(),"Street field cannot be empty", InvalidInputException::new);
        validateArgument(input.getStreet(),"Street field must contain at least 3 characters", s->s.length()<3, InvalidInputException::new);
        validateArgument(input.getStreet(),"Street field cannot contain any special characters", s->!s.matches(regex), InvalidInputException::new);

        validateArgument(input.getPostalCode(),"Postalcode cannot be null", String::isEmpty, InvalidInputException::new);
        validateArgument(input.getCity(),"city cannot be null", String::isEmpty, InvalidInputException::new);
        validateArgument(input.getCountry(),"Country cannot be null", String::isEmpty, InvalidInputException::new);}
}


