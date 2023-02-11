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

    @Test
    void testChallengeConcluedonFieldLessMine() {
        field.openField();
        assertTrue(field.challengeConclued());
    }

    @Test
    void testChallengeConcluedonFieldLessMineButFieldIsClosed() {
        assertFalse(field.challengeConclued());
    }

    @Test
    void testChallengeConcluedonFieldWithMine() {
        field.addMine();
        assertFalse(field.challengeConclued());
    }

    @Test
    void testChallengeConcluedonFieldWithMineButFlaged() {
        field.addMine();
        field.toggleFlag();
        field.openField();
        assertTrue(field.challengeConclued());
    }

    @Test
    void testQuantityOfMinesInNeighborhoodWithMines() {
        Field neighbor1 = new Field(2, 3);
        Field neighbor2 = new Field(2, 2);
        neighbor1.addMine();
        neighbor2.addMine();
        field.addNeighborhood(neighbor1);
        field.addNeighborhood(neighbor2);
        assertTrue(field.checkNeighborhood() == 2);
    }

    @Test
    void testQuantityOfMinesInNeighborhoodWithNoMines() {
        Field neighbor1 = new Field(2, 3);
        Field neighbor2 = new Field(2, 2);
        field.addNeighborhood(neighbor1);
        field.addNeighborhood(neighbor2);
        assertTrue(field.checkNeighborhood() == 0);
    }

    @Test
    void testToResetField() {
        field.addMine();
        field.toggleFlag();
        field.reset();
        assertTrue(!field.isOpen() && !field.isFlag() && !field.isMine());
    }

    @Test
    void testToStringWithFieldFlaged() {
        field.toggleFlag();
        assertTrue(field.toString() == "!");
    }

    @Test
    void testToStringWithFieldMined() {
        field.addMine();
        assertThrows(ExplosionException.class, () -> {
            field.openField();
        });
        assertTrue(field.toString() == "*");
    }

    @Test
    void testToStringWithFieldOpenedWithNoMine() {
        field.openField();
        assertTrue(field.toString() == " ");
    }

    @Test
    void testToStringWithFieldOpenedAndNeighborhoodHaveMines() {
        Field neighbor1 = new Field(2, 3);
        Field neighbor2 = new Field(2, 2);
        neighbor2.addMine();
        neighbor1.addNeighborhood(neighbor2);
        field.addNeighborhood(neighbor1);
        field.openField();
        assertTrue(neighbor1.toString().equals("1") && neighbor1.isOpen() && neighbor1.checkNeighborhood() == 1);
    }

    @Test
    void testToStringWithFieldNotOpened() {
        assertTrue(field.toString() == "?");
    }
}
