class Airliner extends Aircraft implements PassengerCapable {
    private int passengerCapacity;
    public Airliner(string name, int passengerCapacity){
        super(name);
        this.passengerCapacity = passengerCapacity;
    }
    @Override
    public int getPassengerCapacity() {
        return passengerCapacity;
    }
}
