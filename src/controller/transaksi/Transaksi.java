/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller.transaksi;

import config.Koneksi;
import data_interface.DataInterface;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;

/**
 *
 * @author hp
 */
public class Transaksi implements DataInterface {
    private int total_transaksi;
    private int jml_bayar;
    private int kembalian;
    private String status;

    public void setTotal_transaksi(int total_transaksi) {
        this.total_transaksi = total_transaksi;
    }

    public void setJml_bayar(int jml_bayar) {
        this.jml_bayar = jml_bayar;
    }

    public void setKembalian(int kembalian) {
        this.kembalian = kembalian;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getKodeTransaksi()
    {
        String newCode = "";
        if(!(getData().isEmpty()))
        {
            String lastCode = getData().get(0).get("kode_transaksi");
            int number = Integer.parseInt(lastCode.substring(1)) + 1;
            newCode = String.format("T%03d", number);
        } else {
            newCode = "T001";
        }
        return newCode;
    }
    private String toString(Object obj)
    {
        return String.valueOf(obj);
    }

    @Override
    public List<Map<String, String>> getData() {
        List<Map<String, String>> data = new ArrayList<Map<String, String>>();
        try {
            String sql = "SELECT * FROM tb_transaksi";
            String kode = sql + " ORDER BY kode_transaksi DESC";
            ResultSet rs = Koneksi.getKoneksi().prepareCall(sql).executeQuery();
            while(rs.next())
            {
                Map<String, String> item_data = new HashMap<String, String>();
                item_data.put("kode_transaksi", rs.getString("kode_transaksi"));
                item_data.put("total_transaksi", toString(rs.getInt("total_transaksi")));
                item_data.put("jml_bayar", toString(rs.getInt("jml_bayar")));
                item_data.put("kembalian", toString(rs.getInt("kembalian")));
                item_data.put("status", rs.getString("status"));
                data.add(item_data);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return data;
    }

    @Override
    public void tambahData() {
        try {
            String sql = "INSERT INTO `tb_transaksi`(`kode_transaksi`, `total_transaksi`, `jml_bayar`, `kembalian`, `status`) VALUES (?,?,?,?,?)";
            PreparedStatement ps = Koneksi.getKoneksi().prepareCall(sql);
            ps.setString(1, getKodeTransaksi());
            ps.setInt(2, total_transaksi);
            ps.setInt(3, jml_bayar);
            ps.setInt(4, kembalian);
            ps.setString(5, status);
            ps.executeUpdate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    @Override
    public void updateData(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void hapusData(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
