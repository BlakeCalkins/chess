package chess;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {
    TeamColor teamColor;
    ChessBoard board;

    public ChessGame() {
        setTeamTurn(TeamColor.WHITE);
        this.board = new ChessBoard();
        board.resetBoard();
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return teamColor;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        this.teamColor = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        ChessPiece piece;
        if (board.getPiece(startPosition) == null) {
            return null;
        }
        else {
            piece = board.getPiece(startPosition);
        }
        Collection<ChessMove> valids = new ArrayList<>();
        Collection<ChessMove> moves = piece.pieceMoves(board, startPosition);
        for (ChessMove move: moves) {
            ChessBoard cloned = makeMoveOnClone(move);
            if (!isInCheck(piece.getTeamColor(), cloned)) {
                valids.add(move);
            }
        }
        return valids;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to perform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        if (board.getPiece(move.getStartPosition()) == null) {
            throw new InvalidMoveException("No piece here.");
        }
        if (board.getPiece(move.getStartPosition()).getTeamColor() != teamColor) {
            throw new InvalidMoveException("Not your turn");
        }
        if (!validMoves(move.getStartPosition()).contains(move)) {
            throw new InvalidMoveException("Illegal Move");
        }
        board.movePiece(move, board.getPiece(move.getStartPosition()));
        if (getTeamTurn() == TeamColor.WHITE) {
            setTeamTurn(TeamColor.BLACK);
        } else {
            setTeamTurn(TeamColor.WHITE);
        }
    }

    public ChessBoard makeMoveOnClone(ChessMove move) {
        ChessPiece piece = board.getPiece(move.getStartPosition());
        chess.ChessBoard cloned = board.clone();
        cloned.movePiece(move, piece);
        return cloned;
    }

    public ChessPosition findKing(TeamColor teamColor, ChessBoard board) {
        for (int row = 1; row < 9; row++) {
            for (int col = 1; col < 9; col++) {
                ChessPosition pos = new ChessPosition(row, col);
                if (board.getPiece(pos) != null &&
                        board.getPiece(pos).getTeamColor() == teamColor &&
                        board.getPiece(pos).getPieceType() == ChessPiece.PieceType.KING) {
                    return pos;
                }
            }
        }
        return null;
    }

    public Collection<ChessMove> findMoves(TeamColor color, ChessBoard board) {
        Collection<ChessMove> moves = new ArrayList<>();
        for (int row = 1; row < 9; row++) {
            for (int col = 1; col < 9; col++) {
                ChessPosition pos = new ChessPosition(row, col);
                if (board.getPiece(pos) != null && board.getPiece(pos).getTeamColor() == color) {
                    moves.addAll(board.getPiece(pos).pieceMoves(board, pos));
                }
            }
        }
        return moves;
    }

    public boolean noValids(TeamColor teamColor) {
        Collection<ChessMove> moves = findMoves(teamColor, board);
        for (ChessMove move: moves) {
            if (!validMoves(move.getStartPosition()).isEmpty()) {
                return false;
            }
        }
        return true;
    }


    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        TeamColor enemyColor = (teamColor == TeamColor.WHITE) ? TeamColor.BLACK : TeamColor.WHITE;
        ChessPosition kingPosition = findKing(teamColor, board);
        Collection<ChessMove> enemyMoves = findMoves(enemyColor, board);
        for (ChessMove move: enemyMoves) {
            if (move.getEndPosition().equals(kingPosition)) {
                return true;
            }
        }
        return false;
    }

    public boolean isInCheck(TeamColor teamColor, ChessBoard cloned) {
        TeamColor enemyColor = (teamColor == TeamColor.WHITE) ? TeamColor.BLACK : TeamColor.WHITE;
        ChessPosition kingPosition = findKing(teamColor, cloned);
        Collection<ChessMove> enemyMoves = findMoves(enemyColor, cloned);
        for (ChessMove move: enemyMoves) {
            if (move.getEndPosition().equals(kingPosition)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        return isInCheck(teamColor) && noValids(teamColor);
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves while not in check.
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        return !isInCheck(teamColor) && noValids(teamColor);
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.board = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return board;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessGame chessGame = (ChessGame) o;
        return teamColor == chessGame.teamColor && Objects.equals(board, chessGame.board);
    }

    @Override
    public int hashCode() {
        return Objects.hash(teamColor, board);
    }
}
