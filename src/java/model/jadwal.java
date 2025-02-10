package model;

/**
 *
 * @author Kelompok 1 12 RPL 2
 */
public class jadwal {
    private Integer id;
    private Integer id_rute;
    private String waktu_keberangkatan;
    private String waktu_kedatangan;
    private Double harga_tiket;
    private Integer kapasitas;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdRute() {
        return id_rute;
    }

    public void setIdRute(Integer id_rute) {
        this.id_rute = id_rute;
    }

    public String getWaktuKeberangkatan() {
        return waktu_keberangkatan;
    }

    public void setWaktuKeberangkatan(String waktu_keberangkatan) {
        this.waktu_keberangkatan = waktu_keberangkatan;
    }

    public String getWaktuKedatangan() {
        return waktu_kedatangan;
    }

    public void setWaktuKedatangan(String waktu_kedatangan) {
        this.waktu_kedatangan = waktu_kedatangan;
    }

    public Double getHargaTiket() {
        return harga_tiket;
    }

    public void setHargaTiket(Double harga_tiket) {
        this.harga_tiket = harga_tiket;
    }

    public Integer getKapasitas() {
        return kapasitas;
    }

    public void setKapasitas(Integer kapasitas) {
        this.kapasitas = kapasitas;
    }
}
