package Object;

public class RepetitionLatihan extends Latihan implements KaloriTracker {
    private int repetition;

    public RepetitionLatihan(String namaLatihan, String deskripsiLatihan, String manfaatLatihan, String kategoriLatihan,
            int kaloriPerLatihan, int repetition) {
        super(namaLatihan, deskripsiLatihan, manfaatLatihan, "repetition", kategoriLatihan, kaloriPerLatihan);
        this.repetition = repetition;
    }

    @Override
    public int hitungKalori() {
        return repetition * getKaloriPerLatihan();
    }
}
