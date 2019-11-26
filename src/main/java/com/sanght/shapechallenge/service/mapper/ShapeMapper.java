package com.sanght.shapechallenge.service.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sanght.shapechallenge.common.exception.ValidationException;
import com.sanght.shapechallenge.domain.Shape;
import com.sanght.shapechallenge.service.dto.ShapeDTO;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ShapeMapper {
    public Map<String, Double> parseDimensions(String dimensions) throws ValidationException {
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

    public Shape convertDTOToEntity(ShapeDTO shapeDTO) throws ValidationException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Shape shape = new Shape();
            shape.setName(shapeDTO.getName());
            shape.setDimensions(mapper.writeValueAsString(shapeDTO.getDimensions()));
            shape.setArea(shapeDTO.getArea());
            String possibleCategories = shapeDTO.getPossibleCategories().stream()
                    .map(c -> String.valueOf(c.getId()))
                    .collect(Collectors.joining(", "));
            shape.setPossibleCategories(possibleCategories);
            shape.setUser(shapeDTO.getUser());
            shape.setCategory(shapeDTO.getCategory());
            shape.setRequirement(shapeDTO.getRequirement());
            return shape;
        } catch (JsonProcessingException e) {
            throw new ValidationException(e.getMessage());
        }
    }

    public ShapeDTO convertEntityToDTO(Shape shape) throws ValidationException {
        ShapeDTO shapeDTO = new ShapeDTO();
        shapeDTO.setName(shape.getName());
        shapeDTO.setDimensions(parseDimensions(shape.getDimensions()));
        shapeDTO.setArea(shape.getArea());
        shapeDTO.setUser(shape.getUser());
        shapeDTO.setCategory(shape.getCategory());
        shapeDTO.setRequirement(shape.getRequirement());
        return shapeDTO;
    }
}
