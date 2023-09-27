package com.example.manageuser.bean;

import java.util.ArrayList;
import java.util.List;

public class ValidationResult {

    private boolean success;

    private List<String> errors = new ArrayList<>();

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}

