package com.sanght.shapechallenge.provider;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sanght.shapechallenge.common.constant.AppConstant;
import com.sanght.shapechallenge.common.exception.ValidationException;
import com.sanght.shapechallenge.common.util.ConditionUtil;
import com.sanght.shapechallenge.common.util.FormulaUtil;
import com.sanght.shapechallenge.domain.Category;
import com.sanght.shapechallenge.domain.Condition;
import com.sanght.shapechallenge.domain.Formula;
import com.sanght.shapechallenge.domain.Shape;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class ShapeProvider implements Serializable {
    private static final long serialVersionUID = 1L;
    private final Logger log = LoggerFactory.getLogger(ShapeProvider.class);

    private final MessageSource messageSource;

    public ShapeProvider(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public double calculateArea(List<Formula> formulaList, Map<String, Double> dimensions) throws ValidationException {
        Optional<Formula> areaFormula = formulaList.stream()
                .filter(formula -> formula.getName().toLowerCase().equals(AppConstant.AREA_FIELD))
                .findFirst();
        if (!areaFormula.isPresent()) {
            String errMsg = messageSource.getMessage("err.formula.notProvided", null, LocaleContextHolder.getLocale());
            throw new ValidationException(errMsg);
        }
        return FormulaUtil.calculate(areaFormula.get(), dimensions);
    }

    public List<Category> getPossibleCategories(List<Condition> conditionList, Map<String, Double> dimensions) throws ValidationException {
        List<Category> output = new ArrayList<>();
        conditionList.forEach(condition -> {
            Category matched = ConditionUtil.matchCategory(condition, dimensions);
            if (matched != null) {
                output.add(matched);
            }
        });
        return output;
    }

}
