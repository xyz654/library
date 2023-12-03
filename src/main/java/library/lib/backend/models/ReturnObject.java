package library.lib.backend.models;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface ReturnObject {
    String toJson() throws JsonProcessingException;
}
