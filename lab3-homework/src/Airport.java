import java.util.ArrayList;
import java.util.List;

public class Airport {
    private final List<Runway> runways;

    public Airport() {
        this.runways = new ArrayList<>();
    }

    public List<Runway> getRunways() {
        return runways;
    }

    public void addRunway(Runway runway) {
        runways.add(runway);
    }

    public Runway assignRunway(Flight flight) {
        for (Runway runway : runways) {
            if (runway.isAvailable(flight.getStart(), flight.getEnd())) {
                runway.scheduleFlight(flight);
                return runway;
            }
        }


        Runway newRunway = new Runway(runways.size() + 1);
        newRunway.scheduleFlight(flight);
        runways.add(newRunway);
        return newRunway;
    }
}
