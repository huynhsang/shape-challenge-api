package com.sanght.shapechallenge.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "shape_category", schema="public")
public class ShapeCategory implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private ShapeCategoryKey id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("shapeId")
    private Shape shape;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("categoryId")
    private Category category;

    @NotNull
    private boolean isMain = false;

    public ShapeCategory() {}

    public ShapeCategory(Shape shape, Category category) {
        this.shape = shape;
        this.category = category;
        this.id = new ShapeCategoryKey(shape.getId(), category.getId());
    }

    public ShapeCategory(Shape shape, Category category, boolean isMain) {
        this(shape, category);
        this.isMain = isMain;
    }

    public ShapeCategoryKey getId() {
        return id;
    }

    public void setId(ShapeCategoryKey id) {
        this.id = id;
    }

    public Shape getShape() {
        return shape;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public boolean isMain() {
        return isMain;
    }

    public void setMain(boolean main) {
        isMain = main;
    }

    @Override
    public String toString() {
        return "ShapeCategory{" +
                ", shape='"+ shape +"'"+
                ", category='"+ category +"'"+
                ", isMain='"+ isMain +"'"+
                '}';
    }
}
