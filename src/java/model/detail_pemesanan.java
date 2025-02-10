package model;

/**
 *
 * @author Kelompok 1 12 RPL 2
 */
public class detail_pemesanan {
    private String id;
    private Integer id_passenger;
    private Integer id_pemesanan;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getIdPassenger() {
        return id_passenger;
    }

    public void setIdPassenger(Integer id_passenger) {
        this.id_passenger = id_passenger;
    }

    public Integer getIdPemesanan() {
        return id_pemesanan;
    }

    public void setIdPemesanan(Integer id_pemesanan) {
        this.id_pemesanan = id_pemesanan;
    }
}
