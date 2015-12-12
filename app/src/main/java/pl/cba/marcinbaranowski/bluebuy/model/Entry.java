package pl.cba.marcinbaranowski.bluebuy.model;

import java.io.Serializable;

/**
 * Created by flipflap on 14.11.15.
 */
public class Entry implements Serializable {
    private int id;
    private Category category;
    private Category recentCategory;
    private String name;

    private int quantity;
    private String unit;
    private String comment;

    public Entry(Category category, String name) {
        this.category = category;
        this.name = name;
    }

    public Entry(Category category, String name, int quantity, String unit, String comment) {
        this(category, name);
        this.quantity = quantity;
        this.unit = unit;
        this.comment = comment;
    }

    public Entry(int id, Category category, String name, int quantity, String unit, String comment) {
        this(category, name, quantity, unit, comment);
        this.id = id;
    }

    public Entry(int id, Category category, Category recentCategory, String name, int quantity, String unit, String comment) {
        this(id, category, name, quantity, unit, comment);
        this.recentCategory = recentCategory;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Category getRecentCategory() {
        return recentCategory;
    }

    public void setRecentCategory(Category recentCategory) {
        this.recentCategory = recentCategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
