package Object;

public class User {
    private String username;
    private String password;
    private int usia;
    private double beratBadan;
    private double tinggiBadan;
    private String level;
    private int targetWeight;

    public User(String username, String password, int usia, double beratBadan, double tinggiBadan, String level, int targetWeight) {
        this.username = username;
        this.password = password;
        this.usia = usia;
        this.beratBadan = beratBadan;
        this.tinggiBadan = tinggiBadan;
        this.level = level;
        this.targetWeight = targetWeight;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUsia() {
        return usia;
    }

    public void setUsia(int usia) {
        this.usia = usia;
    }

    public double getBeratBadan() {
        return beratBadan;
    }

    public void setBeratBadan(double beratBadan) {
        this.beratBadan = beratBadan;
    }

    public double getTinggiBadan() {
        return tinggiBadan;
    }

    public void setTinggiBadan(double tinggiBadan) {
        this.tinggiBadan = tinggiBadan;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getTargetWeight() {
        return targetWeight;
    }

    public void setTargetWeight(int targetWeight) {
        this.targetWeight = targetWeight;
    }

}
