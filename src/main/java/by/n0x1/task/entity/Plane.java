package by.n0x1.task.entity;

import by.n0x1.task.state.PlaneState;
import by.n0x1.task.state.InFlightState;
import java.util.concurrent.Callable;

public class Plane implements Callable<Boolean> {
    private final int id;
    private final int arrivingPassengers;
    private final int departingPassengers;
    private PlaneState state;
    private Gate assignedGate;

    public Plane(int id, int arrivingPassengers, int departingPassengers) {
        this.id = id;
        this.arrivingPassengers = arrivingPassengers;
        this.departingPassengers = departingPassengers;
        this.state = new InFlightState();
    }

    @Override
    public Boolean call() {
        while (state != null) {
            state.handle(this);
        }
        return true;
    }

    public int getId() { return id; }
    public int getArrivingPassengers() { return arrivingPassengers; }
    public int getDepartingPassengers() { return departingPassengers; }
    public void setState(PlaneState state) { this.state = state; }
    public Gate getAssignedGate() { return assignedGate; }
    public void setAssignedGate(Gate assignedGate) { this.assignedGate = assignedGate; }
}