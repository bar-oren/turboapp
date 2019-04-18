package services;

public class AbstractService {
    private StringBuilder error = new StringBuilder();

    public boolean hasErrors() {
        return error.length() > 0;
    }

    public  String getError() {
        return error.toString();
    }

    protected void appendError(String error) {
        this.error.append(error);
    }
}
