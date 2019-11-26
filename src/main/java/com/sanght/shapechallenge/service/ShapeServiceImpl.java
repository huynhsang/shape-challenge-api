package com.sanght.shapechallenge.service;

import com.sanght.shapechallenge.common.exception.NotFoundException;
import com.sanght.shapechallenge.common.exception.ValidationException;
import com.sanght.shapechallenge.domain.Category;
import com.sanght.shapechallenge.domain.Requirement;
import com.sanght.shapechallenge.domain.Shape;
import com.sanght.shapechallenge.domain.User;
import com.sanght.shapechallenge.provider.ShapeProvider;
import com.sanght.shapechallenge.repository.ShapeDAO;
import com.sanght.shapechallenge.service.dto.ShapeDTO;
import com.sanght.shapechallenge.service.mapper.RequirementMapper;
import com.sanght.shapechallenge.service.mapper.ShapeMapper;
import com.sanght.shapechallenge.web.vmodel.ShapeVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ShapeServiceImpl implements ShapeService {
    private final Logger log = LoggerFactory.getLogger(ShapeServiceImpl.class);

    private final ShapeDAO shapeDAO;

    private final ShapeProvider shapeProvider;

    private final RequirementService requirementService;

    private final RequirementMapper requirementMapper;

    private final ShapeMapper shapeMapper;

    private final MessageSource messageSource;

    private final UserService userService;

    public ShapeServiceImpl(ShapeDAO shapeDAO, ShapeProvider shapeProvider, RequirementService requirementService, RequirementMapper requirementMapper, ShapeMapper shapeMapper, MessageSource messageSource, UserService userService) {
        this.shapeDAO = shapeDAO;
        this.shapeProvider = shapeProvider;
        this.requirementService = requirementService;
        this.requirementMapper = requirementMapper;
        this.shapeMapper = shapeMapper;
        this.messageSource = messageSource;
        this.userService = userService;
    }

    @Override
    public ShapeDTO submit(ShapeVM shapeVM) throws NotFoundException, ValidationException {
        Requirement requirement = requirementService.findOneById(shapeVM.getRequirementId());
        Map<String, Double> dimensions = shapeMapper.parseDimensions(shapeVM.getDimensions());
        Map<String, Boolean> requirementSets = requirementMapper.parseSet(requirement.getSet());
        verifyDimensions(dimensions, requirementSets);
        Category category = requirement.getCategory();
        ShapeDTO shape = new ShapeDTO();
        shape.setName(shapeVM.getName());
        shape.setDimensions(dimensions);
        shape.setCategory(category);
        shape.setRequirement(requirement);
        shape.setArea(shapeProvider.calculateArea(category.getFormulas(), dimensions));
        shape.setPossibleCategories(shapeProvider.getPossibleCategories(category.getConditions(), dimensions));
        return shape;
    }

    @Override
    public Shape save(ShapeVM shapeVM) throws NotFoundException, ValidationException {
        User user = userService.getCurrentUser();
        ShapeDTO shapeDTO = submit(shapeVM);
        Shape shape = shapeMapper.convertDTOToEntity(shapeDTO);
        shape.setUser(user);
        shape = shapeDAO.save(shape);
        log.debug("Saved shape: {}", shape);
        return shape;
    }

    private void verifyDimensions(Map<String, Double> dimensions, Map<String, Boolean> requirementSets) throws ValidationException {
        for (Map.Entry<String, Boolean> entry : requirementSets.entrySet()) {
            if (entry.getValue() && dimensions.get(entry.getKey()) == null) {
                String errMsg = messageSource.getMessage("err.shape.dimensions.notMatch", null, LocaleContextHolder.getLocale());
                throw new ValidationException(errMsg);
            }
        }
    }
}
