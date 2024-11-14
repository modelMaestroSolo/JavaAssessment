package exceptions;

public class ProductAlreadyRegisteredException extends RuntimeException{
    public ProductAlreadyRegisteredException() {
    }

    public ProductAlreadyRegisteredException(String message) {
        super(message);
    }
}
