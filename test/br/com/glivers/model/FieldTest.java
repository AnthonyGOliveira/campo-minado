import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.glivers.exception.ExplosionException;
import br.com.glivers.model.Field;

public class FieldTest {
    private Field field;

    @BeforeEach
    void initField() {
        field = new Field(3, 3);
    }

    @Test
    void testNeighborhoodWithOneOfDistance() {
        Field neighborhood = new Field(3, 2);
        boolean result = field.addNeighborhood(neighborhood);

        assertTrue(result);
    }

    @Test
    void testNeighborhoodWithTwoOfDistance() {
        Field neighborhood = new Field(2, 2);
        boolean result = field.addNeighborhood(neighborhood);

        assertTrue(result);
    }

    @Test
    void testNeighborhoodWithDistanceNotValid() {
        Field neighborhood = new Field(1, 2);
        boolean result = field.addNeighborhood(neighborhood);

        assertFalse(result);
    }

    @Test
    void testAlternateFlagFieldToTrue() {
        field.toggleFlag();
        boolean result = field.isFlag();

        assertTrue(result);
    }

    @Test
    void testAlternateFlagFieldToFalse() {
        field.toggleFlag();
        field.toggleFlag();
        boolean result = field.isFlag();

        assertFalse(result);
    }

    @Test
    void testOpenFieldWithSuccess() {
        assertTrue(field.openField());
    }

    @Test
    void testOpenFieldSuccessWithNeighborhooh() {
        Field neighbor1 = new Field(2, 3);
        Field neighbor2 = new Field(2, 2);
        neighbor1.addNeighborhood(neighbor2);
        field.addNeighborhood(neighbor1);
        assertTrue(field.openField() && neighbor1.isOpen() && neighbor2.isOpen());
    }

    @Test
    void testTryOpenFieldWithFlag() {
        field.toggleFlag();
        assertFalse(field.openField());
    }

    @Test
    void testOpenFieldWithMine() {
        field.addMine();
        assertThrows(ExplosionException.class, () -> {
            field.openField();
        });
    }
 
    @Test
    void testOpenFieldWithMineAndNeighborhooh() {
        Field neighbor1 = new Field(2, 3);
        Field neighbor2 = new Field(2, 2);
        neighbor2.addMine();
        neighbor1.addNeighborhood(neighbor2);
        field.addNeighborhood(neighbor1);
        assertTrue(field.openField() && neighbor1.isOpen() && !neighbor2.isOpen());
    }
}
