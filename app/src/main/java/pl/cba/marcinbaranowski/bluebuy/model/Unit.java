package pl.cba.marcinbaranowski.bluebuy.model;

/**
 * Created by flipflap on 14.11.15.
 */
public class Unit {
    private int id;
    private String name;

    public Unit(String name) {
        this.name = name;
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
}
