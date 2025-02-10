/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connection;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.sql.*;

/**
 *
 * @author Kelompok 1 12 RPL 2
 */
public class koneksi {
    static Connection koneksi;
    public static Connection getKoneksi(){
        if(koneksi==null){
            MysqlDataSource data = new MysqlDataSource();
            data.setDatabaseName("db_travel");
            data.setUser("root");
            data.setPassword("");
            try{
                koneksi = data.getConnection();
                System.out.println("Koneksi Sukses");
            }catch(SQLException se){
                System.out.println("Koneksi Gagal "+se);
            }
        }
        return koneksi;
    }
    
    public static void main(String[] args){
        getKoneksi();
    } 
}
