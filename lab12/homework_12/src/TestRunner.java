import java.io.File;
import java.io.IOException;
import java.lang.annotation.*;
import java.lang.reflect.*;
import java.net.*;
import java.util.*;
import java.util.jar.*;

public class TestRunner {

    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            System.out.println("Usage: java TestRunner <path to class | folder | jar>");
            return;
        }

        File input = new File(args[0]);
        List<Class<?>> testClasses = new ArrayList<>();

        if (input.isDirectory()) {
            List<File> classFiles = findClassFiles(input);
            URLClassLoader classLoader = new URLClassLoader(new URL[]{input.toURI().toURL()});
            for (File classFile : classFiles) {
                String className = getClassNameFromFile(input, classFile);
                try {
                    Class<?> clazz = Class.forName(className, true, classLoader);
                    if (clazz.isAnnotationPresent(Test.class)) {
                        testClasses.add(clazz);
                    }
                } catch (Throwable ignored) {}
            }
        } else if (input.getName().endsWith(".jar")) {
            try (JarFile jar = new JarFile(input)) {
                Enumeration<JarEntry> entries = jar.entries();
                URLClassLoader loader = new URLClassLoader(new URL[]{input.toURI().toURL()});
                while (entries.hasMoreElements()) {
                    JarEntry entry = entries.nextElement();
                    if (entry.getName().endsWith(".class")) {
                        String className = entry.getName().replace("/", ".").replace(".class", "");
                        try {
                            Class<?> clazz = Class.forName(className, true, loader);
                            if (clazz.isAnnotationPresent(Test.class)) {
                                testClasses.add(clazz);
                            }
                        } catch (Throwable ignored) {}
                    }
                }
            }
        } else if (input.getName().endsWith(".class")) {
            URLClassLoader loader = new URLClassLoader(new URL[]{input.getParentFile().toURI().toURL()});
            String className = input.getName().replace(".class", "");
            Class<?> clazz = Class.forName(className, true, loader);
            if (clazz.isAnnotationPresent(Test.class)) {
                testClasses.add(clazz);
            }
        }

        int totalTests = 0, passed = 0, failed = 0;

        for (Class<?> clazz : testClasses) {
            for (Method method : clazz.getDeclaredMethods()) {
                if (method.isAnnotationPresent(Test.class)) {
                    Object instance = null;
                    if (!Modifier.isStatic(method.getModifiers())) {
                        instance = clazz.getDeclaredConstructor().newInstance();
                    }

                    Object[] argsToPass = Arrays.stream(method.getParameterTypes())
                            .map(TestRunner::mockValue)
                            .toArray();

                    try {
                        method.setAccessible(true);
                        method.invoke(instance, argsToPass);
                        passed++;
                    } catch (Exception e) {
                        failed++;
                    }
                    totalTests++;
                }
            }
        }

        System.out.println("Total tests: " + totalTests);
        System.out.println("Passed: " + passed);
        System.out.println("Failed: " + failed);
    }

    static List<File> findClassFiles(File dir) {
        List<File> result = new ArrayList<>();
        for (File file : dir.listFiles()) {
            if (file.isDirectory()) result.addAll(findClassFiles(file));
            else if (file.getName().endsWith(".class")) result.add(file);
        }
        return result;
    }

    static String getClassNameFromFile(File base, File classFile) {
        String path = classFile.getAbsolutePath().replace(base.getAbsolutePath() + File.separator, "")
                     .replace(File.separator, ".").replace(".class", "");
        return path;
    }

    static Object mockValue(Class<?> type) {
        if (type == int.class || type == Integer.class) return 42;
        if (type == long.class || type == Long.class) return 123L;
        if (type == boolean.class || type == Boolean.class) return true;
        if (type == double.class || type == Double.class) return 3.14;
        if (type == float.class || type == Float.class) return 1.23f;
        if (type == char.class || type == Character.class) return 'A';
        if (type == byte.class || type == Byte.class) return (byte) 1;
        if (type == short.class || type == Short.class) return (short) 2;
        if (type == String.class) return "mock";
        return null;
    }
}
