import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import praktikum.Bun;
import praktikum.Burger;
import praktikum.Ingredient;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class BurgerTest {

    private Burger burger;

    @Mock
    private Bun bun;

    @Mock
    private Ingredient cheese;

    @Mock
    private Ingredient cutlet;

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
        burger.addIngredient(cheese);
        assertEquals(1, burger.ingredients.size());
        assertEquals(cheese, burger.ingredients.get(0));
    }

    @Test
    public void testRemoveIngredient() {
        burger.addIngredient(cheese);
        burger.removeIngredient(0);
        assertEquals(0, burger.ingredients.size());
    }

    @Test
    public void testMoveIngredient() {
        burger.addIngredient(cheese);
        burger.addIngredient(cutlet);
        burger.moveIngredient(0, 1);
        assertEquals(cutlet, burger.ingredients.get(0));
        assertEquals(cheese, burger.ingredients.get(1));
    }
}