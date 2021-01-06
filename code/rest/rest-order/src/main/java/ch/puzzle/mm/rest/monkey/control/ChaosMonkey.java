package ch.puzzle.mm.rest.monkey.control;

import javax.enterprise.util.Nonbinding;
import javax.interceptor.InterceptorBinding;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Inherited
@InterceptorBinding
@Target({TYPE, METHOD})
@Retention(RUNTIME)
public @interface ChaosMonkey {
    @Nonbinding
    boolean latency() default true;
    @Nonbinding
    boolean errorRate() default true;
    @Nonbinding
    boolean rateLimit() default true;
    @Nonbinding
    boolean exception() default true;
}
