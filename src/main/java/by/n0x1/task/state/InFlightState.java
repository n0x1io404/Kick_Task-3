package by.n0x1.task.state;

import by.n0x1.task.entity.Plane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.concurrent.TimeUnit;

public class InFlightState implements PlaneState {
    private static final Logger logger = LogManager.getLogger(InFlightState.class);

    @Override
    public void handle(Plane plane) {
        try {
            logger.info("The plane {} is in flight, approaching the airport...", plane.getId());
            TimeUnit.SECONDS.sleep(1);
            plane.setState(new WaitingForGateState());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}