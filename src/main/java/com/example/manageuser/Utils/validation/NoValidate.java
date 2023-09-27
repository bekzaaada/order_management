package com.example.manageuser.Utils.validation;

import com.example.manageuser.bean.ValidationResult;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

public class NoValidate implements Validate {
    @Override
    public ValidationResult validate(Map<String, Object> params, HttpServletRequest request, Object other_param) {
        ValidationResult validationResult = new ValidationResult();
        validationResult.setSuccess(true);
        return validationResult;
    }
}
