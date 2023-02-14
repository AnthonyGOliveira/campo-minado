package br.com.glivers.model;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private int columns;
    private int rows;
    private int mines;
    private List<Field> fields = new ArrayList<>();

    public Board(int columns, int rows, int mines) {
        this.columns = columns;
        this.rows = rows;
        this.mines = mines;

        this.generateFields();
        this.associateNeighbors();
        this.addMines();
    }

    public boolean challengeConclued() {
        return fields.stream().allMatch(Field::challengeConclued);
    }

    public void openField(int columnSearch, int rowSearch) {
        try {
            fields
                    .parallelStream()
                    .filter(field -> field.getCol() == columnSearch && field.getRow() == rowSearch)
                    .findFirst().ifPresent(Field::openField);

        } catch (Exception e) {
            fields.forEach(field -> field.setOpen(true));
            throw e;
        }
    }

    public void markField(int columnSearch, int rowSearch) {
        fields
                .parallelStream()
                .filter(field -> field.getCol() == columnSearch && field.getRow() == rowSearch)
                .findFirst().ifPresent(Field::toggleFlag);
    }

    @Override
    public String toString() {
        StringBuilder board = new StringBuilder();
        int element = 0;
        board.append("   ");
        for (int column = 0; column < columns; column++) {
            board.append("[");
            board.append(column);
            board.append("]");
        }
        ;
        board.append("\n");
        for (int column = 0; column < columns; column++) {
            board.append("[");
            board.append(column);
            board.append("]");
            for (int row = 0; row < rows; row++) {
                board.append("[");
                board.append(fields.get(element));
                board.append("]");
                element++;
            }
            board.append("\n");
        }
        return board.toString();
    }

    private void addMines() {
        int numberOfMines = 0;
        do {
            int ramdom = (int) (Math.random() * fields.size());
            fields.get(ramdom).addMine();
            numberOfMines = (int) fields.stream().filter(Field::isMine).count();
        } while (numberOfMines < mines);
    }

    private void associateNeighbors() {
        for (Field field1 : fields) {
            for (Field field2 : fields) {
                field1.addNeighborhood(field2);
            }
        }
    }

    private void generateFields() {
        for (int column = 0; column < columns; column++) {
            for (int row = 0; row < rows; row++) {
                fields.add(new Field(column, row));
            }
        }
    }

    public int getColumns() {
        return columns;
    }

    public int getRows() {
        return rows;
    }

    public int getMines() {
        return mines;
    }

    public List<Field> getFields() {
        return fields;
    }

}
