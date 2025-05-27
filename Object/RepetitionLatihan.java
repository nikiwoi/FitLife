package Object;

public class RepetitionLatihan extends Latihan {
    private int repetition;

    public RepetitionLatihan(String namaLatihan, String deskripsiLatihan, String tipeLatihan, String kategoriLatihan,
            int kaloriPerLatihan, int repetition) {
        super(namaLatihan, deskripsiLatihan, tipeLatihan, kategoriLatihan, kaloriPerLatihan);
        this.repetition = repetition;
    }

    
}
