package com.sanght.shapechallenge.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Map;

@Entity
@Table(name = "condition", schema="public")
public class Condition implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String name;

    @NotNull
    private String comparator;

    @NotNull
    @Column(name = "operands")
    private String operandJSON;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Category category;

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

    public String getComparator() {
        return comparator;
    }

    public void setComparator(String comparator) {
        this.comparator = comparator;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getOperandJSON() {
        return operandJSON;
    }

    public void setOperandJSON(String operandJSON) {
        this.operandJSON = operandJSON;
    }

    @Override
    public String toString() {
        return "Category{" +
                ", id='"+ id +"'"+
                ", name='"+ name +"'"+
                '}';
    }
}
