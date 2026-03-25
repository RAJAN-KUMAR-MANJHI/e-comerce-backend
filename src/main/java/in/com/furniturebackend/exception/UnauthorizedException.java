package in.com.furniturebackend.exception;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
    	super(message);
    }
}
