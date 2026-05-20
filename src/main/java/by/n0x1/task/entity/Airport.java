package by.n0x1.task.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Airport {
    private static final Logger logger = LogManager.getLogger(Airport.class);

    private List<Gate> gates;
    private Terminal terminal;
    private Semaphore gateSemaphore;

    private final Lock lock = new ReentrantLock();
    private boolean isInitialized = false;

    private Airport() {}

    private static class Holder {
        private static final Airport INSTANCE = new Airport();
    }

    public static Airport getInstance(int gatesCount, int terminalCapacity) {
        Airport instance = Holder.INSTANCE;

        instance.lock.lock();
        try {
            if (!instance.isInitialized) {
                instance.gates = new ArrayList<>();
                for (int i = 1; i <= gatesCount; i++) {
                    instance.gates.add(new Gate(i));
                }
                instance.terminal = new Terminal(terminalCapacity);
                instance.gateSemaphore = new Semaphore(gatesCount, true);
                instance.isInitialized = true;
                logger.info("Airport initialized: {} gates, terminal capacity {}", gatesCount, terminalCapacity);
            }
        } finally {
            instance.lock.unlock();
        }

        return instance;
    }

    public static Airport getInstance() {
        return Holder.INSTANCE;
    }

    public Gate acquireGate() {
        try {
            gateSemaphore.acquire();
            lock.lock();
            try {
                for (Gate gate : gates) {
                    if (gate.isFree()) {
                        gate.setFree(false);
                        return gate;
                    }
                }
            } finally {
                lock.unlock();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("Error receiving the gate", e);
        }
        return null;
    }

    public void releaseGate(Gate gate) {
        lock.lock();
        try {
            gate.setFree(true);
        } finally {
            lock.unlock();
        }
        gateSemaphore.release();
    }

    public Terminal getTerminal() {
        return terminal;
    }
}