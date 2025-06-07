package Model;

import java.util.ArrayList;

public class DifficultyLatihan {
    private String difficultyLevel;
    private ArrayList<Latihan> latihanList;

    public DifficultyLatihan(String difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
        this.latihanList = new ArrayList<>();
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

    public void addLatihan(Latihan latihan) {
        latihanList.add(latihan);
    }
}
