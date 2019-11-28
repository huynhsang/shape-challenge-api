package com.sanght.shapechallenge.web.controller;

import com.sanght.shapechallenge.common.exception.NotFoundException;
import com.sanght.shapechallenge.common.util.HeaderUtil;
import com.sanght.shapechallenge.common.util.PaginationUtil;
import com.sanght.shapechallenge.common.util.ResponseUtil;
import com.sanght.shapechallenge.domain.Category;
import com.sanght.shapechallenge.security.jwt.AuthorityConstant;
import com.sanght.shapechallenge.service.CategoryService;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CategoryController {
    private final Logger log = LoggerFactory.getLogger(CategoryController.class);

    private static final String ENTITY_NAME = "category";

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * GET  /categories : get all the categories.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of categories in body
     */
    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getAllCategories(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Categories");
        Page<Category> page = categoryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/categories");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @PostMapping(path = "/category", produces={MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE})
    @Secured(AuthorityConstant.ROLE_ADMIN)
    public ResponseEntity<?> save(@Valid @RequestBody Category category) {
        log.debug("Request to save the category {}", category);
        category = categoryService.save(category);
        return ResponseUtil.createdOrNot(Optional.ofNullable(category));
    }

    @PutMapping(path = "/category", produces={MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE})
    @Secured(AuthorityConstant.ROLE_ADMIN)
    public ResponseEntity<?> update(@Valid @RequestBody Category category) {
        log.debug("Request to update the category {}", category);
        if (category.getId() == null) {
            return save(category);
        }
        try {
            category = categoryService.update(category);
            return ResponseEntity.ok()
                    .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, category.getId().toString()))
                    .body(category);
        } catch (NotFoundException e) {
            HttpHeaders textPlainHeaders = new HttpHeaders();
            textPlainHeaders.setContentType(MediaType.TEXT_PLAIN);
            return new ResponseEntity<>(e.getMessage(), textPlainHeaders, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/category/{id}")
    @Secured(AuthorityConstant.ROLE_ADMIN)
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        log.debug("REST request to delete category : {}", id);
        categoryService.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
