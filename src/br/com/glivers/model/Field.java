package br.com.glivers.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.glivers.exception.ExplosionException;

public class Field {
    private boolean open = false;
    private boolean mine = false;
    private boolean flag = false;
    private final int col;
    private final int row;
    private List<Field> neighborhood = new ArrayList<>();

    public Field(int col, int row) {
        this.col = col;
        this.row = row;
    }

    public boolean addNeighborhood(Field neighborhood) {
        boolean columnIsDifferent = neighborhood.getCol() != this.col;
        boolean rowIsDifferent = neighborhood.getRow() != this.row;
        boolean isDiagonal = columnIsDifferent && rowIsDifferent;

        int colResult = Math.abs(this.col - neighborhood.getCol());
        int rowResult = Math.abs(this.row - neighborhood.getRow());
        int finalResult = colResult + rowResult;

        if ((finalResult == 1 && !isDiagonal) || (finalResult == 2 && isDiagonal)) {
            return this.neighborhood.add(neighborhood);
        }
        return false;
    }
    

    public boolean isOpen() {
        return open;
    }

    public boolean isMine() {
        return mine;
    }

    public boolean isFlag() {
        return flag;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    public List<Field> getNeighborhood() {
        return Collections.unmodifiableList(neighborhood);
    }

    public void toggleFlag() {
        if (!this.open) {
            this.flag = !this.flag;
        }
    }

    public boolean openField() {
        if (!this.open && !this.flag) {
            this.open = true;
            if (this.mine) {
                throw new ExplosionException();
            }
            if (this.neighborhoodIsSafety()) {
                this.neighborhood.forEach(Field::openField);
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean neighborhoodIsSafety() {
        return this.neighborhood.stream().noneMatch(Field::isMine);
    }

    public void addMine() {
        this.mine = true;
    }

    public boolean challengeConclued() {
        boolean opened = this.open && !this.mine;
        boolean marked = this.flag && this.mine;
        return opened || marked;
    }

    public Long checkNeighborhood() {
        return this.neighborhood.stream().filter(Field::isMine).count();
    }

    public void reset() {
        this.mine = false;
        this.open = false;
        this.flag = false;
    }

    public String toString() {
        if (this.flag && !this.open) {
            return "!";
        } else if (this.open && this.mine) {
            return "*";
        } else if (this.open && this.checkNeighborhood() > 0) {
            return Long.toString(this.checkNeighborhood());
        } else if (this.open) {
            return " ";
        } else {
            return "?";
        }
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public void setNeighborhood(List<Field> neighborhood) {
        this.neighborhood = neighborhood;
    }
}
