package fr.eni.parking.ExceptionPerso;

public class ExceptionDao extends Exception {


    private int id;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public ExceptionDao(String message) {
        super(message);
    }

    public ExceptionDao(String message, Throwable cause) {
        super(message, cause);
    }
    public ExceptionDao(String message, Throwable cause,int id) {
        super(message, cause);
        this.id = id;
    }
    @Override
    public String getMessage() {
        StringBuilder message = new StringBuilder("Couche Dao -- ");
        message.append(super.getMessage());
        return message.toString();
    }
}
