package labirynth.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Container for a list of {@link GameResult} entries that can be displayed
 * in the in-game history dialog.
 * The class acts as a thin wrapper around a {@code List<GameResult>}.
 *
 **/
public class GameResults {
    private List<GameResult> adatok = new ArrayList<>();

    /**
     * Constructs a {@code GameResults} with the given list of entries.
     */
    public GameResults(List<GameResult> adatok) {
        this.adatok = adatok;
    }

    /**
     * {@return the list of game result entries held by this container.}
     */
    public List<GameResult> getAdatok() {
        return adatok;
    }

    /**
     * Replaces the list of game result entries held by this container.
     *
     * @param adatok the new list of {@link GameResult} records; must not be {@code null}
     */
    public void setAdatok(List<GameResult> adatok) {
        this.adatok = adatok;
    }

    /**
     * {@return a multi-line string containing all result entries}
     */
    @Override
    public String toString() {
        StringBuilder szoveg=new StringBuilder();
        szoveg.append("[\n");
        for( GameResult eredmeny: this.getAdatok())
        {   szoveg.append("{\n");
            szoveg.append(eredmeny.playerName()+"\n");
            szoveg.append(eredmeny.numberOfMoves()+"\n");
            szoveg.append(eredmeny.date()+"\n");
            szoveg.append((eredmeny.finished()?"igen":"nem")+"\n");
            szoveg.append("}\n");
        }
        szoveg.append("]");
        return szoveg.toString();
    }
}
