package com.cenfotec.p3.neuralforge_api.exception.customTypes;

public class NeuralForgeGenericException extends Exception{

    public NeuralForgeGenericException(String message) {
        super(message);
    }

    public NeuralForgeGenericException(String message, Throwable cause) {
        super(message, cause);
    }

    public NeuralForgeGenericException(Throwable cause) {
        super(cause);
    }
}
