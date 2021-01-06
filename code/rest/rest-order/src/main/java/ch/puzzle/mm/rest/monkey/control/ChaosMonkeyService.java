package ch.puzzle.mm.rest.monkey.control;

import com.google.common.base.Strings;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.NotFoundException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class ChaosMonkeyService {

    public Map<String, Monkey> monkeys = new HashMap<>();

    private static String DELIMITER = "#";

    public Monkey getMonkey(Class<?> clazz, Method method) {
        return this.getMonkey(this.toCallerId(clazz, method));
    }

    public Monkey getMonkey(String clazzName, String methodName) {
        return this.getMonkey(this.toCallerId(clazzName, methodName));
    }

    Monkey getMonkey(String callerId) {
        if (callerId != null) {
            if (monkeys.containsKey(callerId)) {
                return monkeys.get(callerId);
            } else if (callerId.contains(DELIMITER)) {
                String clazz = callerId.split(DELIMITER)[0];
                if (monkeys.containsKey(clazz)) {
                    return monkeys.get(clazz);
                }
            }
        }

        return new Monkey();
    }

    public void addMonkey(Monkey monkey) {
        this.addMonkey(monkey, this.toCallerId(monkey.getClazzName(), monkey.getMethodName()));
    }

    void addMonkey(Monkey monkey, String callerId) {
        if (monkey == null || callerId == null) {
            return;
        }

        this.monkeys.put(callerId, monkey);
    }

    public void removeMonkey(String clazzName, String methodName) {
        this.removeMonkey(this.toCallerId(clazzName, methodName));
    }

    public void removeMonkey(String callerId) {
        if (callerId != null && this.monkeys.containsKey(callerId)) {
            this.monkeys.remove(callerId);
        } else {
            throw new NotFoundException("Monkey '" + callerId + "' not found");
        }
    }


    String toCallerId(Class<?> clazz, Method method) {
        if (clazz == null) {
            return null;
        }

        if (method == null) {
            return toCallerId(clazz.getSimpleName(), null);
        }

        return toCallerId(clazz.getSimpleName(), method.getName());
    }

    String toCallerId(String clazzName, String methodName) {
        if (clazzName == null && methodName == null) {
            return null;
        }

        if (Strings.isNullOrEmpty(methodName)) {
            return clazzName;
        } else {
            return clazzName + DELIMITER + methodName;
        }
    }

    public Map<String, Monkey> getAllMonkeys() {
        return new HashMap<>(monkeys);
    }
}
