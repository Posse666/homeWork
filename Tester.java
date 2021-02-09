import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class Tester {
    private static SomeClassToTest someClassToTest;

    public static void main(String[] args) {
        start(SomeClassToTest.class);
    }

    static void start(Class<?> testClass) {
        try {
            someClassToTest = (SomeClassToTest) testClass.getDeclaredConstructor().newInstance();
            Map<Integer, ArrayList<Method>> methods = getMethods();
            List<Integer> keys = new ArrayList<>(methods.keySet());
            keys.sort(Collections.reverseOrder());
            for (Integer key : keys) {
                List<Method> methodList = methods.get(key);
                for (Method method : methodList) {
                    method.invoke(new Tester());
                }
            }
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private static Map<Integer, ArrayList<Method>> getMethods() {
        Map<Integer, ArrayList<Method>> result = new HashMap<>();
        Method[] testClassMethods = Tester.class.getDeclaredMethods();
        for (Method testClassMethod : testClassMethods) {
            testClassMethod.setAccessible(true);
            Annotation[] anno = testClassMethod.getDeclaredAnnotations();
            for (Annotation annotation : anno) {
                if (annotation instanceof BeforeSuite) {
                    if (!result.containsKey(Integer.MAX_VALUE)) {
                        result.put(Integer.MAX_VALUE, new ArrayList<>(Collections.singleton(testClassMethod)));
                    } else throw new RuntimeException("2 or more annotations BeforeSuite");
                }
                if (annotation instanceof AfterSuite) {
                    if (!result.containsKey(Integer.MIN_VALUE)) {
                        result.put(Integer.MIN_VALUE, new ArrayList<>(Collections.singleton(testClassMethod)));
                    } else throw new RuntimeException("2 or more annotations AfterSuite");
                }
                if (annotation instanceof Test) {
                    int priority = ((Test) annotation).priority();
                    if (result.containsKey(priority)) {
                        result.get(priority).add(testClassMethod);
                    } else result.put(priority, new ArrayList<>(Collections.singleton(testClassMethod)));
                }
            }
        }
        return result;
    }

    @Test(priority = 7)
    static void ex3Test() {
        someClassToTest.divide(6, 3);

    }

    @BeforeSuite
    private void initClass() {
        System.out.println("Start test");
    }

    @AfterSuite
    private void endTesting() {
        System.out.println("End of all tests");
    }

    @Test(priority = 2)
    private void ex1Test() {
        someClassToTest.ex1();
    }

    @Test(priority = 9)
    private void ex2Test() {
        System.out.println(someClassToTest.ex2(55));
    }

    @Test(priority = 7)
    public void ex3ExceptionTest() {
        try {
            someClassToTest.divide(2, 0);
        } catch (ArithmeticException e) {
            System.out.println("Can not divide by 0");
        }
    }

}
