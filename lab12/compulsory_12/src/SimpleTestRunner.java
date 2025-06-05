import java.lang.reflect.Method;

public class SimpleTestRunner {
    public static void main(String[] args) throws Exception {
        String className = "com.example.MyTestClass";
        Class<?> clazz = Class.forName(className);
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(com.example.Test.class) &&
                java.lang.reflect.Modifier.isStatic(method.getModifiers()) &&
                method.getParameterCount() == 0) {
                method.invoke(null);
            }
        }
    }
}
