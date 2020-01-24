package fr.eni.parking.ExceptionPerso;

public class ExceptionBll extends Exception {

    public ExceptionBll(String message) {
        super(message);
    }

    public ExceptionBll(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getMessage() {
        StringBuilder message = new StringBuilder("Couche Bll -- ");
        message.append(super.getMessage());
        return message.toString();
    }
}
