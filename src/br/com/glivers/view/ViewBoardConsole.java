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
            System.out.println("Sorry, you lose. End game!!!");
        } finally {
            this.scanner.close();

        }
    }

    private void cicleGame() {
        while (!this.board.challengeConclued()) {
            System.out.println(this.board);
            String input;
            System.out.println("Get the coordenates: ");
            input = this.getInput();
            this.nextAction(input);
        }
        System.out.println("Well done! You did it!!!");
    }

    private String getInput() {
        String input = this.scanner.nextLine();
        this.checkCloseGame(input);
        return input;
    }

    private void nextAction(String input) {
        Iterator<Integer> coordenates = Arrays
                .stream(input.split(","))
                .map(element -> Integer
                        .parseInt(element.trim()))
                .iterator();
        this.board.openField(coordenates.next(), coordenates.next());
    }

    private void checkCloseGame(String input) {
        if (input.equalsIgnoreCase("close") || input.equalsIgnoreCase("sair")) {
            this.continueGame = false;
            throw new LeaveException();
        }
    }

    private void resetGame() {
        System.out.println("Would you like to play again? (Yes - y or Not n): ");
        String input = this.scanner.nextLine();
        if(input.equalsIgnoreCase("n")){
            throw new LeaveException();
        }
    }
}
