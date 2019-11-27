package com.sanght.shapechallenge.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sanght.shapechallenge.common.exception.ValidationException;

import java.util.HashMap;
import java.util.Map;

public class ShapeUtil {
    public static Map<String, Double> parseDimensions(String dimensions) throws ValidationException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Double> output = new HashMap<>();
        try {
            Map<String, Object> maps = mapper.readValue(dimensions, Map.class);
            maps.forEach((key, value) -> {
                output.put(key, Double.parseDouble(value.toString()));
            });
        } catch (JsonProcessingException | NumberFormatException e) {
            throw new ValidationException(e.getMessage());
        }
        return output;
    }
}
