class Drone extends Aircraft implements CargoCapable {
    private double cargoCapacity;
    public Drone(string name,double cargoCapacity){
        super(name);
        this.cargoCapacity=cargoCapacity;
    }

    @Override
    public double getCargoCapacity() {
        return cargoCapacity;
    }
}
