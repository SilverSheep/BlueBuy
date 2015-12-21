package pl.cba.marcinbaranowski.bluebuy;

import org.junit.Before;
import org.junit.Test;

import pl.cba.marcinbaranowski.bluebuy.model.Category;
import pl.cba.marcinbaranowski.bluebuy.model.CategoryType;
import pl.cba.marcinbaranowski.bluebuy.provider.CategoryProvider;

import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertEquals;

/**
 * Created by flipflap on 21.12.15.
 */
public class CategoryValidatorTest {

    CategoryValidator categoryValidator;
    CategoryProvider categoryProvider;

    @Before
    public void init() {
        categoryValidator = new CategoryValidator();
        categoryProvider = new CategoryProvider(null);
    }

    @Test
    public void invalidCategoryNames() {
        Category emptyNameCat = new Category("", CategoryType.REGULAR);
        assertTrue(categoryValidator.validate(emptyNameCat), false);

        // MAX NAME is categoryValidator.MAX_CATEGORY_NAME
        Category tooLong = new Category(
                "dhkjashfasdhf hakldfhg kahdskgsklgh ah jkghadksjgakdjshgakj hadgkhjadkl",
                CategoryType.REGULAR);

        assertTrue(categoryValidator.validate(tooLong), false);

        Category alreadyExisting = new Category(DbStub.EXISTING_CATEGORY_NAME,
                CategoryType.REGULAR);

        assertTrue(categoryValidator.validate(alreadyExisting), false);

        Category onlySpaces = new Category("            ",
                CategoryType.REGULAR);

        assertTrue(categoryValidator.validate(onlySpaces), false);
    }

    @Test
    public void validCategoryName() {

        Category almostTooLong = new Category("qwertyuiopasdfghjkl", CategoryType.REGULAR);
        assertTrue(categoryValidator.validate(almostTooLong), true);

        Category shortName = new Category("a", CategoryType.REGULAR);
        assertTrue(categoryValidator.validate(shortName), true);

        Category standard = new Category("Kategoria nowa", CategoryType.REGULAR);
        assertTrue(categoryValidator.validate(standard), true);

        Category spaced = new Category(" a a a a a", CategoryType.REGULAR);
        assertTrue(categoryValidator.validate(spaced), true);
    }

}
