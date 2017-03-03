package com.rodrigoftw.myworkouttracker.myworkouttracker.exception;

import android.widget.EditText;

public class InvalidFormException extends Exception {
    /**
     * Input
     */
    private EditText etElem;

    /**
     * Constructors
     */
    public InvalidFormException() {}
    public InvalidFormException(String message, EditText formElement) {
        super(message);
        this.etElem = formElement;
    }

    public EditText getElem() {
        return etElem;
    }
}
