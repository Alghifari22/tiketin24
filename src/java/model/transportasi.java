package model;

/**
 *
 * @author Kelompok 1 12 RPL 2
 */
public class transportasi {
    private String id;
    private String nama_transportasi;
    private String jenis_transportasi;
    private String nama_perusahaan;
    private String kode_perusahaan;
    private Integer kapasitas_kursi;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNamaTransportasi() {
        return nama_transportasi;
    }

    public void setNamaTransportasi(String nama_transportasi) {
        this.nama_transportasi = nama_transportasi;
    }

    public String getJenisTransportasi() {
        return jenis_transportasi;
    }

    public void setJenisTransportasi(String jenis_transportasi) {
        this.jenis_transportasi = jenis_transportasi;
    }

    public String getNamaPerusahaan() {
        return nama_perusahaan;
    }

    public void setNamaPerusahaan(String nama_perusahaan) {
        this.nama_perusahaan = nama_perusahaan;
    }

    public String getKodePerusahaan() {
        return kode_perusahaan;
    }

    public void setKodePerusahaan(String kode_perusahaan) {
        this.kode_perusahaan = kode_perusahaan;
    }

    public Integer getKapasitasKursi() {
        return kapasitas_kursi;
    }

    public void setKapasitasKursi(Integer kapasitas_kursi) {
        this.kapasitas_kursi = kapasitas_kursi;
    }
}
