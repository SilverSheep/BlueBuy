package pl.cba.marcinbaranowski.bluebuy.provider;

import java.util.ArrayList;
import java.util.List;

import pl.cba.marcinbaranowski.bluebuy.model.Unit;

public class UnitProvider {

    private static final List<Unit> UNITS = new ArrayList<>();

    //TODO: Sort by categories alphabetically
    public UnitProvider() {
        UNITS.clear();
        UNITS.add(new Unit("szt."));
        UNITS.add(new Unit("kg"));
        UNITS.add(new Unit("l"));
    }

    public Unit getUnit(int position) {
        return UNITS.get(position);
    }

    public int getUnitsSize() {
        return UNITS.size();
    }

}
