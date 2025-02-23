    package model;

    /**
     *
     * @author Kelompok 1 12 RPL 2
     */
    public class pembayaran {
        private String id;
        private Integer id_user;
        private Integer id_pemesanan;
        private String metode_pembayaran;
        private String tanggal_pembayaran;
        private String status_pembayaran;

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

        public Integer getIdPemesanan() {
            return id_pemesanan;
        }

        public void setIdPemesanan(Integer id_pemesanan) {
            this.id_pemesanan = id_pemesanan;
        }

        public String getMetodePembayaran() {
            return metode_pembayaran;
        }

        public void setMetodePembayaran(String metode_pembayaran) {
            this.metode_pembayaran = metode_pembayaran;
        }

        public String getTanggalPembayaran() {
            return tanggal_pembayaran;
        }

        public void setTanggalPembayaran(String tanggal_pembayaran) {
            this.tanggal_pembayaran = tanggal_pembayaran;
        }

        public String getStatusPembayaran() {
            return status_pembayaran;
        }

        public void setStatusPembayaran(String status_pembayaran) {
            this.status_pembayaran = status_pembayaran;
        }
    }
