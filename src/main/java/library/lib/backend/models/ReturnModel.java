package library.lib.backend.models;

public class ReturnModel {
    public ReturnObject object;
    public String message;
    public int code;

    public ReturnModel(ReturnObject object, String message, int code) {
        this.object = object;
        this.message = message;
        this.code = code;
    }
}
