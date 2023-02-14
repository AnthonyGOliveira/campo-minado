import br.com.glivers.model.Board;
import br.com.glivers.view.ViewBoardConsole;

public class App {
    public static void main(String[] args) throws Exception {
        Board board;
        int columns = 10;
        int rows = 10;
        int mines = 6;
        board = new Board(columns, rows, mines);
        ViewBoardConsole game = new ViewBoardConsole(board);
    }
}
