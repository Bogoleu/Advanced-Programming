@Test
public class SampleTest {
    @Test
    public static void staticTest() {
        System.out.println("Static test executed");
    }

    @Test
    public void instanceTest(String arg, int number) {
        System.out.println("Instance test executed with: " + arg + ", " + number);
    }
}
