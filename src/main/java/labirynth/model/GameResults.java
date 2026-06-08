package labirynth.model;

import java.util.ArrayList;
import java.util.List;

public class GameResults {
    private List<GameResult> adatok = new ArrayList<>();

    public GameResults(List<GameResult> adatok) {
        this.adatok = adatok;
    }

    public List<GameResult> getAdatok() {
        return adatok;
    }

    public void setAdatok(List<GameResult> adatok) {
        this.adatok = adatok;
    }

    @Override
    public String toString() {
        StringBuilder szoveg=new StringBuilder();
        szoveg.append("{\n");
        for( GameResult eredmeny: this.getAdatok())
        {   szoveg.append("[\n");
            szoveg.append(eredmeny.playerName()+"\n");
            szoveg.append(eredmeny.numberOfMoves()+"\n");
            szoveg.append(eredmeny.date()+"\n");
            szoveg.append(eredmeny.finished()?"igen":"nem"+"\n");
            szoveg.append("]\n");
        }
        szoveg.append("}");
        return szoveg.toString();
    }
}
