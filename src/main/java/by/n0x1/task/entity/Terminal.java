package by.n0x1.task.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Terminal {
    private static final Logger logger = LogManager.getLogger(Terminal.class);
    private int currentPassengers;
    private final int capacity;

    private final Lock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();

    public Terminal(int capacity) {
        this.capacity = capacity;
        this.currentPassengers = 0;
    }

    public void unboardPassengers(int count, int planeId) throws InterruptedException {
        lock.lock();
        try {
            while (currentPassengers + count > capacity) {
                logger.info("The plane {} is waiting for a seat in the terminal for passengers to disembark {}", planeId, count);
                notFull.await();
            }
            currentPassengers += count;
            logger.info("The plane {} dropped off {} passengers. In the terminal: {}/{}", planeId, count, currentPassengers, capacity);
            notEmpty.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public void boardPassengers(int count, int planeId) throws InterruptedException {
        lock.lock();
        try {
            while (currentPassengers - count < 0) {
                logger.info("The plane {} is waiting {} for passengers to board", planeId, count);
                notEmpty.await();
            }
            currentPassengers -= count;
            logger.info("The plane {} picked up {} passengers. In the terminal: {}/{}", planeId, count, currentPassengers, capacity);
            notFull.signalAll();
        } finally {
            lock.unlock();
        }
    }
}
