package shay.space.station.infra.base;


public record RobotData(String name, String callSign, String model) {
    @Override
    public String toString() {
        return "Name: " + name + '\n' +
                "CallSign: " + callSign + '\n' +
                "Model: " + model;
    }
}


