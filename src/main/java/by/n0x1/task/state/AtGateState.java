package by.n0x1.task.state;

import by.n0x1.task.entity.Airport;
import by.n0x1.task.entity.Plane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.concurrent.TimeUnit;

public class AtGateState implements PlaneState {
    private static final Logger logger = LogManager.getLogger(AtGateState.class);

    @Override
    public void handle(Plane plane) {
        try {
            Airport airport = Airport.getInstance();

            logger.info("The plane {} began disembarking at gate No.{}", plane.getId(), plane.getAssignedGate().getId());
            airport.getTerminal().unboardPassengers(plane.getArrivingPassengers(), plane.getId());

            TimeUnit.SECONDS.sleep(2);

            logger.info("The plane {} has started landing", plane.getId());
            airport.getTerminal().boardPassengers(plane.getDepartingPassengers(), plane.getId());

            TimeUnit.SECONDS.sleep(1);

            airport.releaseGate(plane.getAssignedGate());
            logger.info("The plane {} cleared gate No.{}", plane.getId(), plane.getAssignedGate().getId());

            plane.setState(new DepartedState());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}