package com.sanght.shapechallenge.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "shape", schema="public")
public class Shape implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String name;

    @NotNull
    private float area;

    @Column(name = "possible_categories")
    private String possibleCategories;

    private String dimensions;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "requirement_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Requirement requirement;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
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

    public float getArea() {
        return area;
    }

    public void setArea(float area) {
        this.area = area;
    }

    public String getPossibleCategories() {
        return possibleCategories;
    }

    public void setPossibleCategories(String possibleCategories) {
        this.possibleCategories = possibleCategories;
    }

    public String getDimensions() {
        return dimensions;
    }

    public void setDimensions(String dimensions) {
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Shape{" +
                ", id='"+ id +"'"+
                ", name='"+ name +"'"+
                ", area='"+ area +"'"+
                ", dimensions='"+ dimensions +"'"+
                '}';
    }
}