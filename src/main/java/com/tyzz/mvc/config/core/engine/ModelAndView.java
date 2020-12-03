package com.tyzz.mvc.config.core.engine;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ModelAndView {
    private String view;

    private Map<String, Object> data = new HashMap<>();

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void addAttribute(String key, Object value) {
        this.data.put(key, value);
    }

    public void addAttribute(Map map) {
        data.putAll(map);
    }

    public ModelAndView() {
    }

    public ModelAndView(String view) {
        this.view = view;
    }

    public ModelAndView(String view, Map<String, Object> data) {
        this.view = view;
        this.data = data;
    }
}
