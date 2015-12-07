package pl.cba.marcinbaranowski.bluebuy.model;

/**
 * Created by flipflap on 14.11.15.
 */
public class Entry {
    private Long id;
    private Category category;
    private String name;

    private int quantity;
    private Unit unit;
    private String comment;

    public Entry(Category category, String name) {
        this.category = category;
        this.name = name;
    }

    public Entry(Category category, String name, int quantity, Unit unit, String comment) {
        this.category = category;
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
        this.comment = comment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
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

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
