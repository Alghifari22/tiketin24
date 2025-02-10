package model;

/**
 *
 * @author Kelompok 1 12 RPL 2
 */
public class passenger {
    private String id;
    private String nama;
    private Integer NIK;
    private Integer no_pasport;
    private Integer no_telepon;
    private String email;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public Integer getNIK() {
        return NIK;
    }

    public void setNIK(Integer NIK) {
        this.NIK = NIK;
    }

    public Integer getNoPasport() {
        return no_pasport;
    }

    public void setNoPasport(Integer no_pasport) {
        this.no_pasport = no_pasport;
    }

    public Integer getNoTelepon() {
        return no_telepon;
    }

    public void setNoTelepon(Integer no_telepon) {
        this.no_telepon = no_telepon;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
