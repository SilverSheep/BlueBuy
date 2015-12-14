package pl.cba.marcinbaranowski.bluebuy.model;

/**
 * Created by flipflap on 13.12.15.
 */
public enum CategoryType {
    REGULAR(0),
    BASKET(1),
    OTHERS(2);

    private final int value;

    CategoryType(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }
}
