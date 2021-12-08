package com.geminibot.geminibot.entities.responses.restcontrollers;

public abstract class RestControllerResponse<T> {
    protected String message;
    protected boolean error;
    protected T entity;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public T getEntity() {
        return entity;
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }

    @Override
    public String toString() {
        return "RestControllerResponse{" +
                "message='" + message + '\'' +
                ", error=" + error +
                ", entity=" + entity +
                '}';
    }
}
