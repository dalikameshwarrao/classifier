package com.lumen.classifier.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.lumen.classifier.exception.ClassifierException;

@Aspect
@Component
public class ClassifierLoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(ClassifierLoggingAspect.class);

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void controllerMethods() {}

    @Pointcut("within(@org.springframework.stereotype.Service *)")
    public void serviceMethods() {}

    @Pointcut("controllerMethods() || serviceMethods()")
    public void applicationMethods() {}

    @Around("applicationMethods()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Signature signature = joinPoint.getSignature();
        String methodName = signature.toShortString();
        Object[] args = joinPoint.getArgs();

        logger.info("Entering method: {} with arguments: {}", methodName, args);

        try {
            Object result = joinPoint.proceed();
            logger.info("Exiting method: {} with result: {}", methodName, result);
            return result;
        } catch (IllegalArgumentException ex) {
            logger.warn("Illegal argument in method: {} with message: {}", methodName, ex.getMessage(), ex);
            throw ex;
        } catch (ClassifierException ex) {
            logger.error("Unable to execute function dh_classifier.fn_Get_Segregation_Info : {} with message: {}", methodName, ex.getMessage(), ex);
            throw ex;
        } catch (Exception ex) {
            logger.error("Exception in method: {} with message: {}", methodName, ex.getMessage(), ex);
            throw ex;
        }
    }
}

