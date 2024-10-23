/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package config;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.sql.Connection;

/**
 *
 * @author hp
 */
public class Koneksi {
    public static Connection getKoneksi() throws Exception{
        MysqlDataSource dt = new MysqlDataSource();
        dt.setUrl("jdbc:mysql://localhost:3306/qasir");
        dt.setUser("root");
        dt.setPassword("");
        return dt.getConnection();
    }
}
