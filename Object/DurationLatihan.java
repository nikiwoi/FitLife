package Object;

public class DurationLatihan extends Latihan implements KaloriTracker {
    private int duration;

    public DurationLatihan(String namaLatihan, String deskripsiLatihan,String manfaatLatihan, String kategoriLatihan,
            int kaloriPerLatihan, int duration) {
        super(namaLatihan, deskripsiLatihan, manfaatLatihan, "duration", kategoriLatihan, kaloriPerLatihan);
        this.duration = duration;
    }
    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
    @Override
    public int hitungKalori() {
        return duration * getKaloriPerLatihan();
    }
}
