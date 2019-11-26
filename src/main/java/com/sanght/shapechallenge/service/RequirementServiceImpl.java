package com.sanght.shapechallenge.service;

import com.sanght.shapechallenge.common.exception.NotFoundException;
import com.sanght.shapechallenge.domain.Requirement;
import com.sanght.shapechallenge.repository.RequirementDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RequirementServiceImpl implements RequirementService {
    private final Logger log = LoggerFactory.getLogger(ShapeServiceImpl.class);

    private final RequirementDAO requirementDAO;

    private final MessageSource messageSource;

    public RequirementServiceImpl(RequirementDAO requirementDAO, MessageSource messageSource) {
        this.requirementDAO = requirementDAO;
        this.messageSource = messageSource;
    }

    @Override
    public Requirement findOneById(Integer id) throws NotFoundException {
        Optional<Requirement> requirement = requirementDAO.findById(id);
        if (requirement.isPresent()) {
            return requirement.get();
        }
        String errMsg = messageSource.getMessage("err.requirement.notExists", null, LocaleContextHolder.getLocale());
        throw new NotFoundException(errMsg);
    }
}
