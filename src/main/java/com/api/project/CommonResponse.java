package com.api.project;

import lombok.extern.log4j.Log4j2;
import java.util.HashMap;
import java.util.Map;

@Log4j2
public record CommonResponse(boolean result, String message, Object content) {
    public static CommonResponse responseSuccess() {
        return responseSuccess(null);
    }

    public static CommonResponse responseSuccess(Object body) {
        if (body == null) {
            body = new HashMap<>();
        }

        return new CommonResponse(true, "success", body);
    }

    public static CommonResponse responseSuccessWithList(Object list, String listName) {
        Map<String, Object> content = new HashMap<>();

        if (list != null) {
            content.put(listName, list);
        }

        return new CommonResponse(true, "success", content);
    }

    public static CommonResponse responseFail(String message) {
        return new CommonResponse(false, message, new HashMap<>());
    }
}