package com.sanght.shapechallenge.web.vmodel;

import javax.validation.constraints.NotNull;

public class ShapeVM {
    @NotNull
    private String name;

    @NotNull
    private String dimensions;

    @NotNull
    private Integer categoryId;

    @NotNull
    private Integer requirementId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDimensions() {
        return dimensions;
    }

    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getRequirementId() {
        return requirementId;
    }

    public void setRequirementId(Integer requirementId) {
        this.requirementId = requirementId;
    }

    @Override
    public String toString() {
        return "ShapeVM{" +
                "name='" + name + '\'' +
                ", dimensions=" + dimensions +
                ", categoryId=" + categoryId +
                ", requirementId=" + requirementId +
                '}';
    }
}
