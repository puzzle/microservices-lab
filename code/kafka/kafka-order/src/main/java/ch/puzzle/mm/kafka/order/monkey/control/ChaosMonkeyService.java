package ch.puzzle.mm.kafka.order.monkey.control;

import com.google.common.base.Strings;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.NotFoundException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class ChaosMonkeyService {

    private Monkey defaultMonkey = new Monkey();
    public Map<String, Monkey> classMonkeys = new HashMap<>();

    private static String DEFAULT_ID = "DEFAULT";
    private static String DELIMITER = "#";

    public Monkey getDefaultMonkey() {
        return this.defaultMonkey;
    }

    public Monkey getMonkey(Class<?> clazz, Method method) {
        return this.getMonkey(this.toCallerId(clazz, method));
    }

    public Monkey getMonkey(String clazzName, String methodName) {
        return this.getMonkey(this.toCallerId(clazzName, methodName));
    }

    Monkey getMonkey(String callerId) {
        if (callerId != null) {
            if (classMonkeys.containsKey(callerId)) {
                return classMonkeys.get(callerId);
            } else if (callerId.contains(DELIMITER)) {
                String clazz = callerId.split(DELIMITER)[0];
                if (classMonkeys.containsKey(clazz)) {
                    return classMonkeys.get(clazz);
                }
            }
        }

        return this.defaultMonkey;
    }

    public void addMonkey(Monkey monkey, String clazzName, String methodName) {
        this.addMonkey(monkey, this.toCallerId(clazzName, methodName));
    }

    void addMonkey(Monkey monkey, String callerId) {
        if (monkey == null) {
            return;
        }

        if (callerId == null) {
            this.defaultMonkey = monkey;
        } else {
            this.classMonkeys.put(callerId, monkey);
        }
    }

    public void removeMonkey(String clazzName, String methodName) {
        this.removeMonkey(this.toCallerId(clazzName, methodName));
    }

    public void removeMonkey(String callerId) {
        if (callerId != null && this.classMonkeys.containsKey(callerId)) {
            this.classMonkeys.remove(callerId);
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
        Map<String, Monkey> config = new HashMap<String, Monkey>(classMonkeys);
        config.put(DEFAULT_ID, defaultMonkey);
        return config;
    }
}
