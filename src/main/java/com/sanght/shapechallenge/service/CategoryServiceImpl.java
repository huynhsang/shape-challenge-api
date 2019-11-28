package com.sanght.shapechallenge.service;

import com.sanght.shapechallenge.common.exception.NotFoundException;
import com.sanght.shapechallenge.domain.Category;
import com.sanght.shapechallenge.repository.CategoryDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final Logger log = LoggerFactory.getLogger(CategoryServiceImpl.class);

    private final CategoryDAO categoryDAO;

    private final MessageSource messageSource;

    public CategoryServiceImpl(CategoryDAO categoryDAO, MessageSource messageSource) {
        this.categoryDAO = categoryDAO;
        this.messageSource = messageSource;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Category> findAll(Pageable pageable) {
        log.debug("Request to get all categories");
        return categoryDAO.findAll(pageable);
    }

    @Override
    public Category save(Category category) {
        category = categoryDAO.save(category);
        log.debug("Saved category {}", category);
        return category;
    }

    @Override
    public Category update(Category category) throws NotFoundException {
        Optional<Category> currentC = categoryDAO.findById(category.getId());
        if (currentC.isPresent()) {
            category.setId(currentC.get().getId());
            return categoryDAO.save(category);
        }
        String errMsg = messageSource.getMessage("err.shape.notExists", null, LocaleContextHolder.getLocale());
        throw new NotFoundException(errMsg);
    }

    @Override
    public void deleteById(Integer id) {
        categoryDAO.deleteById(id);
        log.debug("Deleted shape by id {}", id);
    }
}
