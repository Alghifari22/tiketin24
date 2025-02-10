package model;

/**
 *
 * @author Kelompok 1 12 RPL 2
 */
public class riwayat {
    private String id;
    private Integer id_pemesanan;
    private String status_perjalanan;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getIdPemesanan() {
        return id_pemesanan;
    }

    public void setIdPemesanan(Integer id_pemesanan) {
        this.id_pemesanan = id_pemesanan;
    }

    public String getStatusPerjalanan() {
        return status_perjalanan;
    }

    public void setStatusPerjalanan(String status_perjalanan) {
        this.status_perjalanan = status_perjalanan;
    }
}
