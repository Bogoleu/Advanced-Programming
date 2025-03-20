import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Airport airport = new Airport();
        List<Flight> flights = Arrays.asList(
                new Flight("F101", LocalTime.of(10, 0), LocalTime.of(10, 30)),
                new Flight("F102", LocalTime.of(12, 15), LocalTime.of(9, 45)),
                new Flight("F103", LocalTime.of(13, 50), LocalTime.of(11, 20)),
                new Flight("F104", LocalTime.of(18, 35), LocalTime.of(12, 0))
        );

        SchedulingProblem problem = new SchedulingProblem(airport, flights);
        problem.solve();
    }
}
