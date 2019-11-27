package com.sanght.shapechallenge.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sanght.shapechallenge.common.exception.ValidationException;

import java.util.HashMap;
import java.util.Map;

public class RequirementUtil {
    public static Map<String, Boolean> parseSet(String set) throws ValidationException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Boolean> output = new HashMap<>();
        try {
            Map<String, Object> maps = mapper.readValue(set, Map.class);
            maps.forEach((key, value) -> {
                output.put(key, Boolean.parseBoolean(value.toString()));
            });
        } catch (JsonProcessingException e) {
            throw new ValidationException(e.getMessage());
        }
        return output;
    }
}
