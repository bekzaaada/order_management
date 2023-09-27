package com.example.manageuser.Utils.validation;

import com.example.manageuser.bean.ValidationResult;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

public interface Validate {
    ValidationResult validate(Map<String, Object> params, HttpServletRequest request, Object other_param);
}
