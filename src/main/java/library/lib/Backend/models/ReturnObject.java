package library.lib.Backend.models;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface ReturnObject {
    String toJson() throws JsonProcessingException;
}
