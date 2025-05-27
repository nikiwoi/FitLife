package Object;

public class DurationLatihan extends Latihan implements KaloriTracker {
    private int duration;

    public DurationLatihan(String namaLatihan, String deskripsiLatihan, String tipeLatihan, String kategoriLatihan,
            int kaloriPerLatihan, int duration) {
        super(namaLatihan, deskripsiLatihan, "duration", kategoriLatihan, kaloriPerLatihan);
        this.duration = duration;
    }
    @Override
    public int hitungKalori() {
        return duration * getKaloriPerLatihan();
    }
}
