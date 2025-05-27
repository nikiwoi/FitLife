package Object;

public class RepetitionLatihan extends Latihan implements KaloriTracker {
    private int repetition;

    public RepetitionLatihan(String namaLatihan, String deskripsiLatihan, String tipeLatihan, String kategoriLatihan,
            int kaloriPerLatihan, int repetition) {
        super(namaLatihan, deskripsiLatihan, "repetition", kategoriLatihan, kaloriPerLatihan);
        this.repetition = repetition;
    }
    @Override
    public int hitungKalori() {
        return repetition * getKaloriPerLatihan();
    }
}
