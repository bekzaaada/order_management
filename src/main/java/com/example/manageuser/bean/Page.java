package com.example.manageuser.bean;

import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Setter

public class Page {
    private int record_count = 0;
    private Object customInfo = null;
    private List<Map<String, Object>> rows = new ArrayList<>();

}
