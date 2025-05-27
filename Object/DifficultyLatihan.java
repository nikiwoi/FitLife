package Object;

import java.util.ArrayList;

public class DifficultyLatihan {
    private String difficultyLevel;
    private ArrayList<Latihan> latihanList;

    public DifficultyLatihan(String difficultyLevel, ArrayList<Latihan> latihanList) {
        this.difficultyLevel = difficultyLevel;
        this.latihanList = latihanList;
    }

    public String getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(String difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public ArrayList<Latihan> getLatihanList() {
        return latihanList;
    }

    public void setLatihanList(ArrayList<Latihan> latihanList) {
        this.latihanList = latihanList;
    }

}
