package com.codewithmosh.store.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    // 🎯 Target ALL layers in your project
    @Pointcut(
            "execution(* com.codewithmosh.store.controllers..*(..)) || " +
            "execution(* com.codewithmosh.store.services..*(..)) || " +
            "execution(* com.codewithmosh.store.repositories..*(..))"
    )
    public void applicationPackage() {
    }

    @Around("applicationPackage()")
    public Object logMethod(ProceedingJoinPoint joinPoint) throws Throwable {

        String className = joinPoint.getSignature().getDeclaringType().getSimpleName();
        String methodName = joinPoint.getSignature().getName();

        String fullMethod = className + "." + methodName + "()";

        long start = System.currentTimeMillis();

        try {
            // 🔹 BEFORE
            log.info("➡️ [{}] Started", fullMethod);

            Object result = joinPoint.proceed();

            long duration = System.currentTimeMillis() - start;
            long executionTime = (System.nanoTime() - start) / 1_000_000;

            if (duration > 50) {
                log.warn("Slow Method: {} took {}ms", joinPoint.getSignature().getName(), executionTime);
            }

            // 🔹 AFTER SUCCESS
            log.info("✅ [{}] Completed in {} ms", fullMethod, duration);

            return result;

        } catch (Throwable ex) {

            long duration = System.currentTimeMillis() - start;

            // 🔹 ERROR
            log.error("❌ [{}] Failed after {} ms → {}",
                    fullMethod,
                    duration,
                    ex.getMessage());

            throw ex;
        }
    }
}
