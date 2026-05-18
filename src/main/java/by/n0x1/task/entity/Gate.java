package by.n0x1.task.entity;

public class Gate {
    private final int id;
    private boolean isFree;

    public Gate(int id) {
        this.id = id;
        this.isFree = true;
    }

    public int getId() { return id; }
    public boolean isFree() { return isFree; }
    public void setFree(boolean free) { isFree = free; }
}