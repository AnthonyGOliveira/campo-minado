package br.com.glivers.view;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

import br.com.glivers.exception.LeaveException;
import br.com.glivers.model.Board;

public class ViewBoardConsole {
    private Board board;
    private Scanner scanner;
    private boolean continueGame = true;
    private String input;

    public ViewBoardConsole(Board board) {
        this.board = board;
        this.scanner = new Scanner(System.in);
        this.handleGame();
    }

    private void handleGame() {
        try {
            while (continueGame) {
                this.cicleGame();
                this.resetGame();
            }
        } catch (Exception e) {
            System.out.println(this.board);
            System.out.println("Sorry, you lose. End game!!!");
        } finally {
            this.scanner.close();

        }
    }

    private void cicleGame() {
        while (!this.board.challengeConclued()) {
            System.out.println(this.board);
            System.out.println("Get the coordenates (x,y): ");
            this.getInput();
            this.nextAction();
        }
        System.out.println("Well done! You did it!!!");
    }

    private void getInput() {
        this.input = this.scanner.nextLine();
        this.checkCloseGame();
    }

    private void nextAction() {
        Iterator<Integer> coordenates = Arrays
                .stream(this.input.split(","))
                .map(element -> Integer
                        .parseInt(element.trim()))
                .iterator();
        if (this.markOrOpen()) {
            this.board.markField(coordenates.next(), coordenates.next());
        } else {
            this.board.openField(coordenates.next(), coordenates.next());
        }
    }

    private void checkCloseGame() {
        if (this.input.equalsIgnoreCase("close") || this.input.equalsIgnoreCase("sair")) {
            this.continueGame = false;
            throw new LeaveException();
        }
    }

    private void resetGame() {
        System.out.println("Would you like to play again? (Yes - y or Not n): ");
        this.input = this.scanner.nextLine();
        if (this.input.equalsIgnoreCase("n")) {
            throw new LeaveException();
        }
    }

    private boolean markOrOpen() {
        System.out.println("1 - Mark or  2 -Open: ");
        String inputChoice = this.scanner.nextLine();
        int choice = Integer.parseInt(inputChoice);
        return choice == 1;
    }
}
