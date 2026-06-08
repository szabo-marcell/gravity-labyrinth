package labirynth.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Immutable record representing a single game attempt stored in the result history.
 *
 * @param playerName    the display name of the player who played the attempt
 * @param numberOfMoves the total number of moves made during the attempt
 * @param date          the calendar date of the attempt in {@code yyyy-MM-dd} format
 * @param finished      {@code true} if the player reached the goal cell, {@code false} if
 *                      the game was abandoned (reset or exited before solving).
 */
public record GameResult(
        @JsonProperty("playerName")    String playerName,
        @JsonProperty("numberOfMoves") int    numberOfMoves,
        @JsonProperty("date")          String date,
        @JsonProperty("finished")      boolean finished
) {}
