import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Runway {
    private final int id;
    private final List<Flight> scheduledFlights;

    public Runway(int id) {
        this.id = id;
        this.scheduledFlights = new ArrayList<>();
    }

    public boolean isAvailable(LocalTime start, LocalTime end) {
        for (Flight flight : scheduledFlights) {
            if (!(end.isBefore(flight.getStart()) || start.isAfter(flight.getEnd()))) {
                return false;
            }
        }
        return true;
    }

    public void scheduleFlight(Flight flight) {
        scheduledFlights.add(flight);
    }

    @Override
    public String toString() {
        return "Runway " + id;
    }
}
