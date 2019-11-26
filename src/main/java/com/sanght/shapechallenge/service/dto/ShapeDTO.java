package com.sanght.shapechallenge.service.dto;

import com.sanght.shapechallenge.domain.Category;
import com.sanght.shapechallenge.domain.Requirement;
import com.sanght.shapechallenge.domain.User;

import java.util.List;
import java.util.Map;

public class ShapeDTO {
    private Integer id;

    private String name;

    private double area;

    private Map<String, Double> dimensions;

    private Category category;

    private Requirement requirement;

    private List<Category> possibleCategories;

    private User user;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public Map<String, Double> getDimensions() {
        return dimensions;
    }

    public void setDimensions(Map<String, Double> dimensions) {
        this.dimensions = dimensions;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Requirement getRequirement() {
        return requirement;
    }

    public void setRequirement(Requirement requirement) {
        this.requirement = requirement;
    }

    public List<Category> getPossibleCategories() {
        return possibleCategories;
    }

    public void setPossibleCategories(List<Category> possibleCategories) {
        this.possibleCategories = possibleCategories;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "ShapeDTO{" +
                ", id='"+ id +"'"+
                ", name='"+ name +"'"+
                ", area='"+ area +"'"+
                ", dimensions='"+ dimensions +"'"+
                '}';
    }
}
