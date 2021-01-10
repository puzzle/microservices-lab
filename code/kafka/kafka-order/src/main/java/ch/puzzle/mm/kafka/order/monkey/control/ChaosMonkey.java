package ch.puzzle.mm.kafka.order.monkey.control;

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
    boolean errors() default true;
    @Nonbinding
    boolean rateLimit() default true;
}
