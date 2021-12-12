package com.geminibot.geminibot.entities.responses.restcontrollers;

public class ErrorResponse extends RestControllerResponse<String>{
    public ErrorResponse(String message, boolean error, String entity) {
        this.message = message;
        this.error = error;
        this.entity = entity;
    }
}
