public class Test {
    public static void main(string[] args){
        Aircraft[] aircrafts={
                new Airliner("boeing 991",200)
                new Freighter("pinto 2",30)
                new Drone("ABC212",0)

        };
        Arrays.sort(aircrafts);
        System.out.println("Aircrafts:");
        for(Aircraft aircraft: aircrafts)
            system.out.println(aircraft);
    }
    aircraft[]cargoAircrafts=arrays.stream(aircrafts)
            .filter(a->a instanceof CargoCapable)
            .toArray(Aircraft[]::new);
    system.out.println("\nAircraft wich can transport;");
        for(Aircraft cargoAircraft:cargoAircrafts){
            system.out.println(cargoAircraft);
    }
}
