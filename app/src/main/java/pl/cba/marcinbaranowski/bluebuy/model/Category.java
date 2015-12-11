package pl.cba.marcinbaranowski.bluebuy.model;

import java.io.Serializable;


public class Category implements Serializable {

    private int id;
    private String name;
    private Boolean isBasket;

    public Category(String name, Boolean isBasket) {
        this.name = name;
        this.isBasket = isBasket;
    }

    public Category(int id, String name, Boolean isBasket) {
        this.id = id;
        this.name = name;
        this.isBasket = isBasket;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isBasket() {
        return isBasket;
    }

    public void setIsBasket(Boolean isBasket) {
        this.isBasket = isBasket;
    }
}
