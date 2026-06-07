package labirynth.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a single entry in the leaderboard.
 *
 * @param playerName    the name of the player
 * @param numberOfMoves the number of moves taken to solve the puzzle
 * @param date          the date the puzzle was solved
 * @param finished      whether the attempt was successful
 */
public record GameResult(
        @JsonProperty("playerName")    String playerName,
        @JsonProperty("numberOfMoves") int    numberOfMoves,
        @JsonProperty("date")          String date,
        @JsonProperty("finished")      boolean finished
) {}
