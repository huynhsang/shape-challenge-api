package com.sanght.shapechallenge.service;

import com.sanght.shapechallenge.common.exception.NotFoundException;
import com.sanght.shapechallenge.common.exception.ValidationException;
import com.sanght.shapechallenge.common.util.RequirementUtil;
import com.sanght.shapechallenge.common.util.SecurityUtil;
import com.sanght.shapechallenge.common.util.ShapeUtil;
import com.sanght.shapechallenge.domain.*;
import com.sanght.shapechallenge.provider.ShapeProvider;
import com.sanght.shapechallenge.repository.ShapeDAO;
import com.sanght.shapechallenge.security.jwt.AuthorityConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ShapeServiceImpl implements ShapeService {
    private final Logger log = LoggerFactory.getLogger(ShapeServiceImpl.class);

    private final ShapeDAO shapeDAO;

    private final ShapeProvider shapeProvider;

    private final RequirementService requirementService;

    private final MessageSource messageSource;

    private final UserService userService;

    public ShapeServiceImpl(ShapeDAO shapeDAO, ShapeProvider shapeProvider, RequirementService requirementService, MessageSource messageSource, UserService userService) {
        this.shapeDAO = shapeDAO;
        this.shapeProvider = shapeProvider;
        this.requirementService = requirementService;
        this.messageSource = messageSource;
        this.userService = userService;
    }

    @Override
    public Shape submit(Shape shape) throws NotFoundException, ValidationException {
        Requirement requirement = requirementService.findOneById(shape.getRequirement().getId());
        Map<String, Double> dimensions = ShapeUtil.parseDimensions(shape.getDimensionJSON());
        Map<String, Boolean> requirementSets = RequirementUtil.parseSet(requirement.getSet());
        verifyDimensions(dimensions, requirementSets);

        List<ShapeCategory> shapeCategories = new ArrayList<>();
        Category category = requirement.getCategory();
        shapeCategories.add(new ShapeCategory(shape, category, true));
        List<Category> possibleCategories = shapeProvider.getPossibleCategories(category.getConditions(), dimensions);
        possibleCategories.forEach(c -> shapeCategories.add(new ShapeCategory(shape, c, false)));
        shape.setShapeCategories(shapeCategories);
        shape.setRequirement(requirement);
        shape.setArea(shapeProvider.calculateArea(category.getFormulas(), dimensions));
        return shape;
    }

    @Override
    public Shape save(Shape shape) throws NotFoundException, ValidationException {
        shape = submit(shape);
        if (shape.getUser() == null || !SecurityUtil.isCurrentUserInRole(AuthorityConstant.ROLE_ADMIN)) {
            shape.setUser(userService.getCurrentUser());
        }
        shape = shapeDAO.save(submit(shape));
        log.debug("Saved shape: {}", shape);
        return shape;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Shape> findAll(Pageable pageable) throws NotFoundException {
        if (SecurityUtil.isCurrentUserInRole(AuthorityConstant.ROLE_ADMIN)) {
            return shapeDAO.findAll(pageable);
        }
        User user = userService.getCurrentUser();
        return shapeDAO.findAllByUserId(user.getId(), pageable);
    }

    @Override
    public void deleteById(Integer id) {
        shapeDAO.deleteById(id);
        log.debug("Deleted shape by id {}", id);
    }

    @Override
    public Shape findById(Integer id) throws NotFoundException {
        Optional<Shape> shape = shapeDAO.findById(id);
        if (shape.isPresent()) {
            return shape.get();
        }
        String errMsg = messageSource.getMessage("err.shape.notExists", null, LocaleContextHolder.getLocale());
        throw new NotFoundException(errMsg);
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
