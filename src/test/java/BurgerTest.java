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
    private Ingredient ingredient1;

    @Mock
    private Ingredient ingredient2;

    private final float bunPrice;
    private final float ingredient1Price;
    private final float ingredient2Price;

    public BurgerTest(float bunPrice, float ingredient1Price, float ingredient2Price) {
        this.bunPrice = bunPrice;
        this.ingredient1Price = ingredient1Price;
        this.ingredient2Price = ingredient2Price;
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
        burger.addIngredient(ingredient1);
        assertEquals(1, burger.ingredients.size());
        assertEquals(ingredient1, burger.ingredients.get(0));
    }

    @Test
    public void testRemoveIngredient() {
        burger.addIngredient(ingredient1);
        burger.removeIngredient(0);
        assertEquals(0, burger.ingredients.size());
    }

    @Test
    public void testMoveIngredient() {
        burger.addIngredient(ingredient1);
        burger.addIngredient(ingredient2);
        burger.moveIngredient(0, 1);
        assertEquals(ingredient2, burger.ingredients.get(0));
        assertEquals(ingredient1, burger.ingredients.get(1));
    }

    @Test
    public void testGetPrice() {
        when(bun.getPrice()).thenReturn(bunPrice);
        when(ingredient1.getPrice()).thenReturn(ingredient1Price);
        when(ingredient2.getPrice()).thenReturn(ingredient2Price);

        burger.addIngredient(ingredient1);
        burger.addIngredient(ingredient2);

        float expected = bunPrice * 2 + ingredient1Price + ingredient2Price;
        assertEquals(expected, burger.getPrice(), 0.001);
    }

    @Test
    public void testGetReceipt() {
        when(bun.getName()).thenReturn("Black bun");
        when(bun.getPrice()).thenReturn(bunPrice);

        when(ingredient1.getName()).thenReturn("Cheese");
        when(ingredient1.getType()).thenReturn(IngredientType.FILLING);
        when(ingredient1.getPrice()).thenReturn(ingredient1Price);

        burger.addIngredient(ingredient1);

        String expectedReceipt = String.format("(==== Black bun ====)%n" +
            "= filling Cheese =%n" +
            "(==== Black bun ====)%n" +
            "%nPrice: %f%n", burger.getPrice());

        assertEquals(expectedReceipt, burger.getReceipt());
    }
}