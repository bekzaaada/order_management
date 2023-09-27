package com.example.manageuser.Utils;

import java.util.Map;

public interface Filter
{
    boolean operation(Map<String,Object> item);
}
