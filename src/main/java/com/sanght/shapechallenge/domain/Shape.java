package com.sanght.shapechallenge.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

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
    private double area;

    @Column(name = "dimensions")
    private String dimensionJSON;

    @OneToMany(mappedBy = "shape")
    List<ShapeCategory> shapeCategories;

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

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public String getDimensionJSON() {
        return dimensionJSON;
    }

    public void setDimensionJSON(String dimensionJSON) {
        this.dimensionJSON = dimensionJSON;
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

    public List<ShapeCategory> getShapeCategories() {
        return shapeCategories;
    }

    public void setShapeCategories(List<ShapeCategory> shapeCategories) {
        this.shapeCategories = shapeCategories;
    }

    @Override
    public String toString() {
        return "Shape{" +
                ", id='"+ id +"'"+
                ", name='"+ name +"'"+
                ", area='"+ area +"'"+
                ", dimensionJSON='"+ dimensionJSON +"'"+
                '}';
    }
}