package ui;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Random;

import static ui.EscapeSequences.*;

public class TicTacToe {

    // Board dimensions.
    private static final int BOARD_SIZE_IN_SQUARES = 8;
    private static final int SQUARE_SIZE_IN_PADDED_CHARS = 1;
    private static final int LINE_WIDTH_IN_PADDED_CHARS = 1;

    // Padded characters.
    private static final String SPACE = " ";
    private static final String EMPTY = "   ";

    private static final ChessBoard board = new ChessBoard();


    public static void main(String[] args) {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);

        out.print(ERASE_SCREEN);

        board.resetBoard();

        drawHeaders(out);

        drawTicTacToeBoard(out);

        drawHeaders(out);

        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_WHITE);
    }

    private static void drawHeaders(PrintStream out) {

        setGrey(out);

//        String[] columns = { "TIC", "TAC", "TOE" };
        String[] columns = {"a", "b", "c", "d", "e", "f", "g", "h"};
        drawHeader(out, " ");
        for (int boardCol = 0; boardCol < BOARD_SIZE_IN_SQUARES; ++boardCol) {
            drawHeader(out, columns[boardCol]);

//            if (boardCol < BOARD_SIZE_IN_SQUARES - 1) {
//                out.print(EMPTY.repeat(LINE_WIDTH_IN_PADDED_CHARS));
//            }
        }
        drawHeader(out, " ");

        setBlack(out);
        out.println();
    }

    private static void drawHeader(PrintStream out, String rowColText) {
//        int prefixLength = SQUARE_SIZE_IN_PADDED_CHARS / 2;
        int prefixLength = 1;
//        int suffixLength = SQUARE_SIZE_IN_PADDED_CHARS - prefixLength - 1;
        int suffixLength = 1;

        out.print(SPACE.repeat(prefixLength));
        printRowColText(out, rowColText);
        out.print(SPACE.repeat(suffixLength));
    }

    private static void printRowColText(PrintStream out, String player) {
        out.print(SET_BG_COLOR_LIGHT_GREY);
        out.print(SET_TEXT_COLOR_BLACK);

        out.print(player);

        setGrey(out);
    }

    private static void drawTicTacToeBoard(PrintStream out) {

        String[] rows = {"8", "7", "6", "5", "4", "3", "2", "1"};
        for (int boardRow = 0; boardRow < BOARD_SIZE_IN_SQUARES; ++boardRow) {

            setGrey(out);
            drawHeader(out, rows[boardRow]);
            drawRowOfSquares(out, boardRow);
            drawHeader(out, rows[boardRow]);
            setBlack(out);
            out.println();

//            if (boardRow < BOARD_SIZE_IN_SQUARES - 1) {
//                // Draw horizontal row separator.
//                drawHorizontalLine(out);
//                setBlack(out);
//            }
        }
    }

    private static void drawRowOfSquares(PrintStream out, int boardRow) {

        for (int squareRow = 0; squareRow < SQUARE_SIZE_IN_PADDED_CHARS; ++squareRow) {
            for (int boardCol = 0; boardCol < BOARD_SIZE_IN_SQUARES; ++boardCol) {

                if (squareRow == SQUARE_SIZE_IN_PADDED_CHARS / 2) {
                    int prefixLength = SQUARE_SIZE_IN_PADDED_CHARS / 2;
                    int suffixLength = SQUARE_SIZE_IN_PADDED_CHARS - prefixLength - 1;

                    out.print(EMPTY.repeat(prefixLength));
                    ChessPosition position = new ChessPosition(boardRow+1, boardCol+1);
                    ChessPiece piece = board.getPiece(position);
                    printPlayer(out, (piece == null) ? " " : piece.toString(), (piece == null) ? null : piece.getTeamColor());
//                    printPlayer(out, rand.nextBoolean() ? X : O);
                    out.print(EMPTY.repeat(suffixLength));
                }
                else {
                    out.print(EMPTY.repeat(SQUARE_SIZE_IN_PADDED_CHARS));
                }

//                if (boardCol < BOARD_SIZE_IN_SQUARES - 1) {
//                    // Draw vertical column separator.
//                    setRed(out);
//                    out.print(EMPTY.repeat(LINE_WIDTH_IN_PADDED_CHARS));
//                }

                setGrey(out);
            }

//            out.println();
        }
    }


    private static void setWhite(PrintStream out) {
        out.print(SET_BG_COLOR_WHITE);
        out.print(SET_TEXT_COLOR_WHITE);
    }

    private static void setRed(PrintStream out) {
        out.print(SET_BG_COLOR_RED);
        out.print(SET_TEXT_COLOR_RED);
    }

    private static void setBlack(PrintStream out) {
        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_BLACK);
    }

    private static void setGrey(PrintStream out) {
        out.print(SET_BG_COLOR_LIGHT_GREY);
        out.print(SET_TEXT_COLOR_LIGHT_GREY);
    }

    private static void setGreen(PrintStream out) {
        out.print(SET_BG_COLOR_GOOD_GREEN);
        out.print(SET_TEXT_COLOR_GOOD_GREEN);
    }

    private static void printPlayer(PrintStream out, String player, ChessGame.TeamColor color) {
        out.print(SET_BG_COLOR_GOOD_GREEN);

        if (color == null || color == ChessGame.TeamColor.WHITE) {
            out.print(SET_TEXT_COLOR_RED);
        } else {
            out.print(SET_TEXT_COLOR_BLUE);
        }

        out.print(" ");
        out.print(player);
        out.print(" ");

        setGreen(out);
    }
}