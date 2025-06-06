package Model;

public abstract class Latihan {
    protected String namaLatihan;
    protected String deskripsiLatihan;
    protected String manfaatLatihan;
    protected String tipeLatihan;
    protected String kategoriLatihan;
    protected int kaloriPerLatihan;

    public Latihan(String namaLatihan, String deskripsiLatihan, String manfaatLatihan, String tipeLatihan, String kategoriLatihan,
            int kaloriPerLatihan) {
        this.namaLatihan = namaLatihan;
        this.deskripsiLatihan = deskripsiLatihan;
        this.manfaatLatihan = manfaatLatihan;
        this.tipeLatihan = tipeLatihan;
        this.kategoriLatihan = kategoriLatihan;
        this.kaloriPerLatihan = kaloriPerLatihan;
    }

    public String getNamaLatihan() {
        return namaLatihan;
    }

    public void setNamaLatihan(String namaLatihan) {
        this.namaLatihan = namaLatihan;
    }

    public String getDeskripsiLatihan() {
        return deskripsiLatihan;
    }

    public void setDeskripsiLatihan(String deskripsiLatihan) {
        this.deskripsiLatihan = deskripsiLatihan;
    }

    public String getManfaatLatihan() {
        return manfaatLatihan;
    }

    public void setManfaatLatihan(String manfaatLatihan) {
        this.manfaatLatihan = manfaatLatihan;
    }

    public String getTipeLatihan() {
        return tipeLatihan;
    }

    public void setTipeLatihan(String tipeLatihan) {
        this.tipeLatihan = tipeLatihan;
    }

    public String getKategoriLatihan() {
        return kategoriLatihan;
    }

    public void setKategoriLatihan(String kategoriLatihan) {
        this.kategoriLatihan = kategoriLatihan;
    }

    public int getKaloriPerLatihan() {
        return kaloriPerLatihan;
    }

    public void setKaloriPerLatihan(int kaloriPerLatihan) {
        this.kaloriPerLatihan = kaloriPerLatihan;
    }
}
