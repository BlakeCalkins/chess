package ui;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import static ui.EscapeSequences.*;

public class TicTacToe {

    // Board dimensions.
    private static final int BOARD_SIZE_IN_SQUARES = 8;
    private static final int SQUARE_SIZE_IN_PADDED_CHARS = 1;

    // Padded characters.
    private static final String SPACE = " ";
    private static final String EMPTY = "   ";

    private static final ChessBoard board = new ChessBoard();


    public static void main(String[] args) {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);

        out.print(ERASE_SCREEN);

        board.resetBoard();

        drawBorders(out);

        drawChessBoard(out);

        drawBorders(out);

        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_WHITE);
    }

    private static void drawBorders(PrintStream out) {

        setGrey(out);

        String[] columns = {"a", "b", "c", "d", "e", "f", "g", "h"};
        drawBorder(out, " ");
        for (int boardCol = 0; boardCol < BOARD_SIZE_IN_SQUARES; ++boardCol) {
            drawBorder(out, columns[boardCol]);
        }
        drawBorder(out, " ");

        setBlack(out);
        out.println();
    }

    private static void drawBorder(PrintStream out, String rowColText) {
        int prefixLength = 1;
        int suffixLength = 1;

        out.print(SPACE.repeat(prefixLength));
        printRowColText(out, rowColText);
        out.print(SPACE.repeat(suffixLength));
    }

    private static void printRowColText(PrintStream out, String player) {
        out.print(SET_BG_COLOR_DARK_GREY);
        out.print(SET_TEXT_COLOR_WHITE);

        out.print(player);

        setGrey(out);
    }

    private static void drawChessBoard(PrintStream out) {

        String[] rows = {"8", "7", "6", "5", "4", "3", "2", "1"};
        String squareColor;
        for (int boardRow = 0; boardRow < BOARD_SIZE_IN_SQUARES; ++boardRow) {

            setGrey(out);
            drawBorder(out, rows[boardRow]);
            squareColor = (boardRow%2 == 0) ? "dark" : "light";
            drawRowOfSquares(out, boardRow, squareColor);
            drawBorder(out, rows[boardRow]);
            setBlack(out);
            out.println();
        }
    }

    private static void drawRowOfSquares(PrintStream out, int boardRow, String squareColor) {

            for (int boardCol = 0; boardCol < BOARD_SIZE_IN_SQUARES; ++boardCol) {
                int prefixLength = SQUARE_SIZE_IN_PADDED_CHARS / 2;
                int suffixLength = 0;

                out.print(EMPTY.repeat(prefixLength));
                ChessPosition position = new ChessPosition(boardRow+1, boardCol+1);
                ChessPiece piece = board.getPiece(position);
                squareColor = switchColor(squareColor);
                printPlayer(out, (piece == null) ? " " : piece.toString(), (piece == null) ? null : piece.getTeamColor(), squareColor);
                out.print(EMPTY.repeat(suffixLength));

                setGrey(out);
            }
    }


    private static void setWhite(PrintStream out) {
        out.print(SET_BG_COLOR_WHITE);
        out.print(SET_TEXT_COLOR_WHITE);
    }

    private static void setBlack(PrintStream out) {
        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_BLACK);
    }

    private static void setGrey(PrintStream out) {
        out.print(SET_BG_COLOR_DARK_GREY);
        out.print(SET_TEXT_COLOR_DARK_GREY);
    }

    private static void setGreen(PrintStream out) {
        out.print(SET_BG_COLOR_GOOD_GREEN);
        out.print(SET_TEXT_COLOR_GOOD_GREEN);
    }

    private static String switchColor(String squareColor) {
        if (Objects.equals(squareColor, "dark")) {
            return "light";
        } else {
            return "dark";
        }
    }

    private static void printPlayer(PrintStream out, String player, ChessGame.TeamColor pieceColor, String squareColor) {
        if ((Objects.equals(squareColor, "dark"))) {
            out.print(SET_BG_COLOR_GOOD_GREEN);
        } else {
            out.print(SET_BG_COLOR_WHITE);
        }

        if (pieceColor == null || pieceColor == ChessGame.TeamColor.WHITE) {
            out.print(SET_TEXT_COLOR_RED);
        } else {
            out.print(SET_TEXT_COLOR_BLUE);
        }

        out.print(" ");
        out.print(player);
        out.print(" ");


        if ((Objects.equals(squareColor, "dark"))) {
            setGreen(out);
        } else {
            setWhite(out);
        }

    }
}