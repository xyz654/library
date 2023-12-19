package library.lib.backend.models;

public class ReturnModel {
    public ReturnObject object;
    public String message;
    public ReturnCodes code;

    public ReturnModel(ReturnObject object, String message, ReturnCodes code) {
        this.object = object;
        this.message = message;
        this.code = code;
    }
}
