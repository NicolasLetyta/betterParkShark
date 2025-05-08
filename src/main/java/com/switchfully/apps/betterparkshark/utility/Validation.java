package com.switchfully.apps.betterparkshark.utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.function.Function;
import java.util.function.Predicate;

public class Validation {

    private static final Logger logger = LoggerFactory.getLogger(Validation.class);

    public static <T> T validateArgument(T argumentToValidate, String exceptionMessage, Predicate<T> predicate) throws IllegalArgumentException {
        if(predicate.test(argumentToValidate)){
            logger.error(exceptionMessage);
            throw new IllegalArgumentException(exceptionMessage);
        }
        return argumentToValidate;
    }

    public static <T> T validateArgument(T argumentToValidate, String exceptionMessage, Predicate<T> predicate, Function<String,RuntimeException> exceptionFunction) throws RuntimeException {
        if(predicate.test(argumentToValidate)){
            logger.error(exceptionMessage);
            throw exceptionFunction.apply(exceptionMessage);
        }
        return argumentToValidate;
    }

    public static <T> T checkArgumentAndLog(T argumentToValidate, String LogMessage, Predicate<T> predicate) {
        if(predicate.test(argumentToValidate)){
            logger.warn(LogMessage);
        }
        return argumentToValidate;
    }
}


