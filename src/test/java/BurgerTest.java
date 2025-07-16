import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import praktikum.Bun;
import praktikum.Burger;
import praktikum.Ingredient;
import praktikum.IngredientType;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(Parameterized.class)
public class BurgerTest {

    private Burger burger;

    @Mock
    private Bun bun;

    @Mock
    private Ingredient sausceIngredient;

    @Mock
    private Ingredient fillingIngredient;

    private final float bunPrice;
    private final float sauceIingredientPrice;
    private final float fillingIngredientPrice;

    public BurgerTest(float bunPrice, float sauceIingredientPrice, float fillingIngredientPrice) {
        this.bunPrice = bunPrice;
        this.sauceIingredientPrice = sauceIingredientPrice;
        this.fillingIngredientPrice = fillingIngredientPrice;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testData() {
        return Arrays.asList(new Object[][]{
            {2.5f, 1.0f, 1.5f},
            {3.0f, 0.5f, 2.0f},
            {1.5f, 2.0f, 1.0f}
        });
    }

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        burger = new Burger();
        burger.setBuns(bun);
    }

    @Test
    public void testSetBuns() {
        Bun newBun = mock(Bun.class);
        burger.setBuns(newBun);
        assertEquals(newBun, burger.bun);
    }

    @Test
    public void testAddIngredient() {
        burger.addIngredient(sausceIngredient);
        assertEquals(1, burger.ingredients.size());
        assertEquals(sausceIngredient, burger.ingredients.get(0));
    }

    @Test
    public void testRemoveIngredient() {
        burger.addIngredient(sausceIngredient);
        burger.removeIngredient(0);
        assertEquals(0, burger.ingredients.size());
    }

    @Test
    public void testMoveIngredient() {
        burger.addIngredient(sausceIngredient);
        burger.addIngredient(fillingIngredient);
        burger.moveIngredient(0, 1);
        assertEquals(fillingIngredient, burger.ingredients.get(0));
        assertEquals(sausceIngredient, burger.ingredients.get(1));
    }

    @Test
    public void testGetPrice() {
        when(bun.getPrice()).thenReturn(bunPrice);
        when(sausceIngredient.getPrice()).thenReturn(sauceIingredientPrice);
        when(fillingIngredient.getPrice()).thenReturn(fillingIngredientPrice);

        burger.addIngredient(sausceIngredient);
        burger.addIngredient(fillingIngredient);

        float expected = bunPrice * 2 + sauceIingredientPrice + fillingIngredientPrice;
        assertEquals(expected, burger.getPrice(), 0.001);
    }

    @Test
    public void testGetReceipt() {
        when(bun.getName()).thenReturn("Black bun");
        when(bun.getPrice()).thenReturn(bunPrice);

        when(fillingIngredient.getName()).thenReturn("Cheese");
        when(fillingIngredient.getType()).thenReturn(IngredientType.FILLING);
        when(fillingIngredient.getPrice()).thenReturn(fillingIngredientPrice);

        burger.addIngredient(fillingIngredient);

        String expectedReceipt = String.format("(==== Black bun ====)%n" +
            "= filling Cheese =%n" +
            "(==== Black bun ====)%n" +
            "%nPrice: %f%n", burger.getPrice());

        assertEquals(expectedReceipt, burger.getReceipt());
    }
}