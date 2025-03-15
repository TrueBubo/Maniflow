package com.truebubo.maniflow.Currency;

import java.io.IOException;

public class ConversionFailedException extends IOException {
    public ConversionFailedException() {super();}
    public ConversionFailedException(String message) {super(message);}
}
