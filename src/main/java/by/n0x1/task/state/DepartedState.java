package by.n0x1.task.state;

import by.n0x1.task.entity.Plane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DepartedState implements PlaneState {
    private static final Logger logger = LogManager.getLogger(DepartedState.class);

    @Override
    public void handle(Plane plane) {
        logger.info("The plane {} successfully left the airport.", plane.getId());
        plane.setState(null);
    }
}