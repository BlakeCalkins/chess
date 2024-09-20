package chess;

import java.util.Arrays;
import java.util.Objects;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {

    private ChessPiece[][] board;

    public ChessBoard() {
        this.board = new ChessPiece[8][8];
    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        int row = position.getRow() -1;
        int col = position.getColumn()-1;
        board[row][col] = piece;
    }

    public void removePiece(ChessPosition position) {
        int row = position.getRow() - 1;
        int col = position.getColumn() - 1;
        board[row][col] = null;
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        int row = position.getRow() - 1;
        int col = position.getColumn() - 1;
        return board[row][col];
    }

    public void clearBoard() {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                removePiece(new ChessPosition(row + 1, col + 1));
            }
        }
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        clearBoard();
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                ChessPosition position = new ChessPosition(row + 1, col + 1);
                ChessPiece piece = null;
                if (row == 0) {
                    piece = switch (col) {
                        case 0, 7 -> new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK);
                        case 1, 6 -> new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT);
                        case 2, 5 -> new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP);
                        case 3 -> new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.QUEEN);
                        case 4 -> new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KING);
                        default -> piece;
                        };
                } else if (row == 1) {
                    piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
                } else if (row == 6) {
                    piece = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
                } else if (row == 7) {
                    piece = switch (col) {
                        case 0, 7 -> new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK);
                        case 1, 6 -> new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT);
                        case 2, 5 -> new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP);
                        case 3 -> new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.QUEEN);
                        case 4 -> new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KING);
                        default -> piece;
                    };
                }
                if (piece != null) {
                    addPiece(position, piece);
                }
            }
        }
    }

    public static void main(String[] args) {
        ChessBoard board1 = new ChessBoard();
        board1.resetBoard();
        System.out.print(board1);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int row = 1; row < board.length; row++) {
            for (int col = 1; col < board[row].length; col++) {
                ChessPosition pos = new ChessPosition(row, col);
                ChessPiece pie = getPiece(pos);
                if (pie != null) {
                    builder.append(pie.toString());
                    builder.append(pos.toString());
                }
            }
        }
        return "ChessBoard{" +
                "board=" + builder +
                '}';
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(board);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessBoard that = (ChessBoard) o;
        return Objects.deepEquals(board, that.board);
    }
}
