package com.sanght.shapechallenge.service.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sanght.shapechallenge.common.exception.ValidationException;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class RequirementMapper {
    public Map<String, Boolean> parseSet(String set) throws ValidationException {
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
