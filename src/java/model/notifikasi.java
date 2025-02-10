package model;

/**
 *
 * @author Kelompok 1 12 RPL 2
 */
public class notifikasi {
    private String id;
    private Integer id_user;
    private String pesan;
    private String created_at;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getIdUser() {
        return id_user;
    }

    public void setIdUser(Integer id_user) {
        this.id_user = id_user;
    }

    public String getPesan() {
        return pesan;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }

    public String getCreatedAt() {
        return created_at;
    }

    public void setCreatedAt(String created_at) {
        this.created_at = created_at;
    }
}
