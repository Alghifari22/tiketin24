package model;

/**
 *
 * @author Kelompok 1 12 RPL 2
 */
public class pemesanan {
    private String id;
    private Integer id_jadwal;
    private Double total_harga;
    private String status_pembayaran;
    private Integer jarak_perjalanan;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getIdJadwal() {
        return id_jadwal;
    }

    public void setIdJadwal(Integer id_jadwal) {
        this.id_jadwal = id_jadwal;
    }

    public Double getTotalHarga() {
        return total_harga;
    }

    public void setTotalHarga(Double total_harga) {
        this.total_harga = total_harga;
    }

    public String getStatusPembayaran() {
        return status_pembayaran;
    }

    public void setStatusPembayaran(String status_pembayaran) {
        this.status_pembayaran = status_pembayaran;
    }

    public Integer getJarakPerjalanan() {
        return jarak_perjalanan;
    }

    public void setJarakPerjalanan(Integer jarak_perjalanan) {
        this.jarak_perjalanan = jarak_perjalanan;
    }
}
