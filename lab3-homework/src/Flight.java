import java.time.LocalTime;

public class Flight {
    private final String code;
    private final LocalTime start;
    private final LocalTime end;

    public Flight(String code, LocalTime start, LocalTime end) {
        this.code = code;
        this.start = start;
        this.end = end;
    }

    public LocalTime getStart() {
        return start;
    }

    public LocalTime getEnd() {
        return end;
    }

    @Override
    public String toString() {
        return "Flight " + code + " (" + start + " - " + end + ")";
    }
}
