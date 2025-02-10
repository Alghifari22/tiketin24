package model;

/**
 *
 * @author Kelompok 1 12 RPL 2
 */
public class rute {
    private String id;
    private Integer id_transportasi;
    private String kota_asal;
    private String kota_tujuan;
    private Integer durasi_perjalanan;
    private Integer jarak_perjalanan;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public Integer getIdTransportasi() {
        return id_transportasi;
    }

    public void setIdTransportasi(Integer id_transportasi) {
        this.id_transportasi = id_transportasi;
    }

    public String getKotaAsal() {
        return kota_asal;
    }

    public void setKotaAsal(String kota_asal) {
        this.kota_asal = kota_asal;
    }

    public String getKotaTujuan() {
        return kota_tujuan;
    }

    public void setKotaTujuan(String kota_tujuan) {
        this.kota_tujuan = kota_tujuan;
    }

    public Integer getDurasiPerjalanan() {
        return durasi_perjalanan;
    }

    public void setDurasiPerjalanan(Integer durasi_perjalanan) {
        this.durasi_perjalanan = durasi_perjalanan;
    }

    public Integer getJarakPerjalanan() {
        return jarak_perjalanan;
    }

    public void setJarakPerjalanan(Integer jarak_perjalanan) {
        this.jarak_perjalanan = jarak_perjalanan;
    }
}
