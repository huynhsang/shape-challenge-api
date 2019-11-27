package com.sanght.shapechallenge.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class ShapeCategoryKey implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "shape_id", nullable = false)
    Integer shapeId;

    @Column(name = "category_id", nullable = false)
    Integer categoryId;

    public ShapeCategoryKey() {}

    public ShapeCategoryKey(Integer shapeId, Integer categoryId) {
        this.shapeId = shapeId;
        this.categoryId = categoryId;
    }

    public Integer getShapeId() {
        return shapeId;
    }

    public void setShapeId(Integer shapeId) {
        this.shapeId = shapeId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
}
