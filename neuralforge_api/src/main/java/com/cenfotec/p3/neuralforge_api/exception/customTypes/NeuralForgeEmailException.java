package com.cenfotec.p3.neuralforge_api.exception.customTypes;

public class NeuralForgeEmailException extends NeuralForgeGenericException{

    public NeuralForgeEmailException(String message) {
        super(message);
    }

    public NeuralForgeEmailException(String message, Throwable cause) {
        super(message, cause);
    }

    public NeuralForgeEmailException(Throwable cause) {
        super(cause);
    }
}
