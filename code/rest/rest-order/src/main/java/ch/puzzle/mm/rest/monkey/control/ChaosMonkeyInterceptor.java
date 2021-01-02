package ch.puzzle.mm.rest.monkey.control;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@ChaosMonkey
@Interceptor
@Priority(Interceptor.Priority.APPLICATION)
public class ChaosMonkeyInterceptor {

    @Inject
    ChaosMonkeyService monkeyService;

    @AroundInvoke
    public Object monkey(InvocationContext context) throws Exception {
        Monkey monkey = monkeyService.getMonkey(context.getMethod().getDeclaringClass(), context.getMethod());
        if(monkey.isEnabled()) {
            ChaosMonkey monkeyAnnotation = context.getMethod().getAnnotation(ChaosMonkey.class);

            if(monkeyAnnotation.errors()) {
                monkey.runErrorMonkey();
            }

            if(monkeyAnnotation.latency()) {
                monkey.runLatencyMonkey();
            }

            if(monkeyAnnotation.rateLimit()) {
                monkey.runRateLimiterMonkey();
            }
        }

        return context.proceed();
    }
}
