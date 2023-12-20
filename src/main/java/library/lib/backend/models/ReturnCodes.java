package library.lib.backend.models;

public enum ReturnCodes {
    OK(200),
    NOT_FOUND(404),
    USER_ERROR(202),
    ERROR(500)
    ;

    ReturnCodes(int i) {

    }
}
