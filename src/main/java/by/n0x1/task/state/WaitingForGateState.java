package by.n0x1.task.state;


import by.n0x1.task.entity.Airport;
import by.n0x1.task.entity.Gate;
import by.n0x1.task.entity.Plane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WaitingForGateState implements PlaneState {
    private static final Logger logger = LogManager.getLogger(WaitingForGateState.class);

    @Override
    public void handle(Plane plane) {
        logger.info("The plane {} is requesting a gate...", plane.getId());
        Gate gate = Airport.getInstance().acquireGate();
        if (gate != null) {
            plane.setAssignedGate(gate);
            logger.info("The plane {} received gate No.{}", plane.getId(), gate.getId());
            plane.setState(new AtGateState());
        }
    }
}