package chess;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
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
        if (board.getPiece(startPosition) == null)
            return null;
        else
            piece = board.getPiece(startPosition);
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
        if (validMoves(move.getEndPosition()).contains(move)) {
            board.movePiece(move, board.getPiece(move.getStartPosition()));
            if (getTeamTurn() == TeamColor.WHITE) {
                setTeamTurn(TeamColor.BLACK);
            } else {
                setTeamTurn(TeamColor.WHITE);
            }
        } else
            throw new InvalidMoveException("Illegal Move");
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
                if (board.getPiece(pos) != null && board.getPiece(pos).getTeamColor() == teamColor && board.getPiece(pos).getPieceType() == ChessPiece.PieceType.KING) {
                    return pos;
                }
            }
        }
        return null;
    }

    public Collection<ChessMove> findMoves(TeamColor teamColor, ChessBoard board) {
        Collection<ChessMove> moves = new ArrayList<>();
        for (int row = 1; row < 9; row++) {
            for (int col = 1; col < 9; col++) {
                ChessPosition pos = new ChessPosition(row, col);
                if (board.getPiece(pos) != null && board.getPiece(pos).getTeamColor() != teamColor) {
                    moves.addAll(board.getPiece(pos).pieceMoves(board, pos));
                }
            }
        }
        return moves;
    }

    public boolean kingCantMove(TeamColor teamColor) {
        ChessPosition kingPosition = findKing(teamColor, board);
//        int kRow = kingPosition.getRow();
//        int kCol = kingPosition.getColumn();
        Collection<ChessMove> enemyMoves = findMoves(teamColor, board);
        Collection<ChessMove> kingMoves = board.getPiece(kingPosition).pieceMoves(board, kingPosition);
        Iterator<ChessMove> it = kingMoves.iterator();
        while (it.hasNext()) {
            ChessMove kMove = it.next();
            for (ChessMove eMove : enemyMoves) {
                if (eMove.getEndPosition().equals(kMove.getEndPosition())) {
                    it.remove();
                    break;
                }
            }
        }
//        for (ChessMove kMove: kingMoves) {
//            for (ChessMove eMove: enemyMoves) {
//                if (eMove.getEndPosition().equals(kMove.getEndPosition()))
//                    kingMoves.remove(kMove);
//            }
//        }
//        for (int row = kRow-1; row <= kRow+1; row++) {
//            for (int col = kCol-1; col <= kCol+1; col++) {
//                if (row < 1 || col < 1 || row > 8 || col > 8) {
//                    continue;
//                }
//                ChessPosition pos = new ChessPosition(row, col);
//                if (kingMoves.contains(new ChessMove(kingPosition, pos, null))){
//                    for (ChessMove move: enemyMoves) {
//                        if (move.getEndPosition().equals(pos)) {
//                            kingMoves.removeIf(m -> m.getEndPosition().equals(pos));
//                        }
//                    }
//                }
//            }
//        }
        System.out.println("King moves after pruning: " + kingMoves);
        return kingMoves.isEmpty();
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        ChessPosition kingPosition = findKing(teamColor, board);
        Collection<ChessMove> enemyMoves = findMoves(teamColor, board);
        for (ChessMove move: enemyMoves) {
            if (move.getEndPosition().equals(kingPosition)) {
                return true;
            }
        }
        return false;
    }

    public boolean isInCheck(TeamColor teamColor, ChessBoard cloned) {
        ChessPosition kingPosition = findKing(teamColor, cloned);
        Collection<ChessMove> enemyMoves = findMoves(teamColor, cloned);
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
        return isInCheck(teamColor) && kingCantMove(teamColor);
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves while not in check.
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
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
