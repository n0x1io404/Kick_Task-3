package by.n0x1.task.state;

import by.n0x1.task.entity.Plane;

public interface PlaneState {
    void handle(Plane plane);
}