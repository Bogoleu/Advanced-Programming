class Freighter extends Aircraft implements CargoCapable {
    private double cargoCapacity;
    public Freighter(string name,double cargoCapacity){
        super(name);
        this.cargoCapacity=cargoCapacity;

    }

    @Override
    public double getCargoCapacity() {
        return cargoCapacity;
    }
}
