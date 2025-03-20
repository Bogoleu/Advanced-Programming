import java.util.Comparator;
import java.util.List;

public class SchedulingProblem {
    private final Airport airport;
    private final List<Flight> flights;

    public SchedulingProblem(Airport airport, List<Flight> flights) {
        this.airport = airport;
        this.flights = flights;
    }

    public void solve() {
        flights.sort(Comparator.comparing(Flight::getStart));

        for (Flight flight : flights) {
            Runway assignedRunway = airport.assignRunway(flight);
            System.out.println(flight + " -> " + assignedRunway);
        }
    }
}
