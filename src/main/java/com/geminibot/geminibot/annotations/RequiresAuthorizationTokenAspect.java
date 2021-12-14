package com.geminibot.geminibot.annotations;

import com.geminibot.geminibot.services.JwtService;
import org.apache.catalina.connector.RequestFacade;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.MissingFormatArgumentException;

@Aspect
@Component
public class RequiresAuthorizationTokenAspect {

    @Autowired
    JwtService jwtService;

    @Around("@annotation(RequiresAuthorizationToken))")
    public Object checkRequest(ProceedingJoinPoint joinPoint) throws Throwable {
        boolean hasHttpServletRequest = false;
        RequestFacade httpServletRequest = null;
        Object[] args = joinPoint.getArgs();

        for (Object arg: args) {
            if (arg instanceof RequestFacade) {

                hasHttpServletRequest = true;
                httpServletRequest = (RequestFacade) arg;
            }
        }

        if (!hasHttpServletRequest) {
            throw new MissingFormatArgumentException("Http Servlet argument must be present in controller action");
        }

        try {
            String headerAuthorizationToken = httpServletRequest.getHeader("Authorization").substring(7).trim();
            assert jwtService.verifyToken(headerAuthorizationToken);
            return joinPoint.proceed();
        } catch(Exception exception) {
            return new ResponseEntity<String>("JSON web token is not present or valid in request headers. Please login to get a new JSON web token.", HttpStatus.UNAUTHORIZED);
        }
    }
}

