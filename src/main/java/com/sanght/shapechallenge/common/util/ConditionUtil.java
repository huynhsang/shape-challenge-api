package com.sanght.shapechallenge.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sanght.shapechallenge.domain.Category;
import com.sanght.shapechallenge.domain.Condition;
import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

public class ConditionUtil {
    public static Category matchCategory(Condition condition, Map<String, Double> dimensions) {
        JSONArray operands = new JSONArray(condition.getOperands());
        for (int i = 0, length = operands.length(); i < length - 1; i++) {
            try {
                String operandA = operands.getString(i);
                String operandB = operands.getString(i + 1);
                Double valueA = dimensions.get(operandA);
                Double valueB = dimensions.get(operandB);
                if (valueA == null) {
                    valueA = Double.parseDouble(operandA);
                }
                if (valueB == null) {
                    valueB = Double.parseDouble(operandB);
                }
                boolean isMatch = compare(condition.getComparator(), valueA, valueB);
                if (!isMatch) {
                    return null;
                }
            } catch (Exception e) {
                return null;
            }
        }
        return condition.getCategory();
    }

    private static boolean compare(String comparator, Double a, Double b) {
        switch (comparator) {
            case "=":
                return a.equals(b);
            case ">=":
                return a >= b;
            case "<=":
                return a <= b;
            case "!=":
                return !a.equals(b);
            case ">":
                return a > b;
            case "<":
                return a < b;
            default:
                return false;
        }
    }

    public static void main(String [] args) {
        ObjectMapper mapper = new ObjectMapper();
        String data = "{\"age\":37, \"c\":\"12\"}";

        Map<String, Double> output = new HashMap<>();
        try {
            Map<String, Object> maps = mapper.readValue(data, Map.class);
            maps.forEach((key, value) -> {
                output.put(key, Double.parseDouble(value.toString()));
                System.out.println(output.get(key) instanceof Double);
                System.out.println(output.get(key));

            });
        } catch (JsonProcessingException | NumberFormatException e) {
            e.printStackTrace();
        }
    }
}
