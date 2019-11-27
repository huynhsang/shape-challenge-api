package com.sanght.shapechallenge.web.controller;

import com.sanght.shapechallenge.common.exception.NotFoundException;
import com.sanght.shapechallenge.common.exception.PermissionDeniedException;
import com.sanght.shapechallenge.common.exception.ValidationException;
import com.sanght.shapechallenge.common.util.HeaderUtil;
import com.sanght.shapechallenge.common.util.PaginationUtil;
import com.sanght.shapechallenge.common.util.ResponseUtil;
import com.sanght.shapechallenge.domain.Shape;
import com.sanght.shapechallenge.service.ShapeService;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ShapeController {
    private final Logger log = LoggerFactory.getLogger(CategoryController.class);

    private static final String ENTITY_NAME = "shape";

    private final ShapeService shapeService;

    public ShapeController(ShapeService shapeService) {
        this.shapeService = shapeService;
    }

    @PostMapping(path = "/shape/submit", produces={MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE})
    public ResponseEntity<?> submit(@Valid @RequestBody Shape shape) {
        log.debug("Request to submit the shape {}", shape);
        HttpHeaders textPlainHeaders = new HttpHeaders();
        textPlainHeaders.setContentType(MediaType.TEXT_PLAIN);
        try {
            shape = shapeService.submit(shape);
            return ResponseUtil.createdOrNot(Optional.ofNullable(shape));
        } catch (ValidationException | NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), textPlainHeaders, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(path = "/shape", produces={MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE})
    public ResponseEntity<?> save(@Valid @RequestBody Shape shape) {
        log.debug("Request to save the shape {}", shape);
        HttpHeaders textPlainHeaders = new HttpHeaders();
        textPlainHeaders.setContentType(MediaType.TEXT_PLAIN);
        try {
            shape = shapeService.save(shape);
            return ResponseUtil.createdOrNot(Optional.ofNullable(shape));
        } catch (ValidationException | NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), textPlainHeaders, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(path = "/shape", produces={MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE})
    public ResponseEntity<?> update(@Valid @RequestBody Shape shape) {
        log.debug("Request to update the shape {}", shape);
        if (shape.getId() == null) {
            return save(shape);
        }
        try {
            shape = shapeService.save(shape);
            return ResponseEntity.ok()
                    .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, shape.getId().toString()))
                    .body(shape);
        } catch (ValidationException | NotFoundException e) {
            HttpHeaders textPlainHeaders = new HttpHeaders();
            textPlainHeaders.setContentType(MediaType.TEXT_PLAIN);
            return new ResponseEntity<>(e.getMessage(), textPlainHeaders, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/shapes")
    public ResponseEntity<List<Shape>> getAllShapes(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Shapes");
        try {
            Page<Shape> shapePage = shapeService.findAll(pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(shapePage, "/api/shapes");
            return new ResponseEntity<>(shapePage.getContent(), headers, HttpStatus.OK);
        } catch (NotFoundException e) {
            log.error(e.getMessage());
            return ResponseUtil.wrapOrNotFound(Optional.empty());
        }
    }

    @PostMapping(path = "/shape/{id}", produces={MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE})
    public ResponseEntity<?> create(@PathVariable Integer id, @Valid @RequestBody Shape shape) {
        log.debug("Request to create the shape {} for user id {}", shape, id);
        HttpHeaders textPlainHeaders = new HttpHeaders();
        textPlainHeaders.setContentType(MediaType.TEXT_PLAIN);
        try {
            shape = shapeService.createShape(shape, id);
            return ResponseUtil.createdOrNot(Optional.ofNullable(shape));
        } catch (ValidationException | NotFoundException | PermissionDeniedException e) {
            return new ResponseEntity<>(e.getMessage(), textPlainHeaders, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/shape/{id}")
    public ResponseEntity<Void> deleteViolation(@PathVariable Integer id) {
        log.debug("REST request to delete Shape : {}", id);
        shapeService.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
