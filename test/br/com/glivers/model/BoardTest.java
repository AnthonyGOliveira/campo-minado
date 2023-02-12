import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import br.com.glivers.model.Board;
import br.com.glivers.model.Field;

public class BoardTest {
    private Board board;
    private int columns;
    private int rows;
    private int mines;

    @Test
    void testInitializeBoard() {
        columns = 3;
        rows = 3;
        mines = 1;
        board = new Board(columns, rows, mines);
        int quantityOfFields = 9;
        assertTrue(board.getColumns() == columns);
        assertTrue(board.getRows() == rows);
        assertTrue(board.getMines() == mines);
        assertTrue(board.getFields().size() == quantityOfFields);    }
}
