package by.n0x1.task.exception;

public class CustomPlaneException extends Exception {
    public CustomPlaneException(String massage, Throwable cause){
        super(massage, cause);
    }

    public CustomPlaneException(String massage){
        super(massage);
    }

    public CustomPlaneException(){
        super();
    }
}
