/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller.kasir;

import config.Koneksi;
import controller.transaksi.Transaksi;
import data_interface.DataInterface;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;

/**
 *
 * @author hp
 */
public class Kasir implements DataInterface {
    private int id;
    private String kode_transaksi;
    private String nama_barang;
    private int total;
    private int harga_transaksi;
    private double total_diskon;
    private int jmlBayar;
    private Transaksi transaksi;

    public Kasir() {
        this.total_diskon = 0.0;
    }
    
    public void setJmlBayar(int jmlBayar) {
        this.jmlBayar = jmlBayar;
    }
    
    public void setTotal_diskon(double total_diskon) {
        this.total_diskon = total_diskon;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setKode_transaksi(String kode_transaksi) {
        this.kode_transaksi = kode_transaksi;
    }

    public void setNama_barang(String nama_barang) {
        this.nama_barang = nama_barang;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void setHarga_transaksi(int harga_transaksi) {
        this.harga_transaksi = harga_transaksi;
    }
    private String toString(Object obj)
    {
        return String.valueOf(obj);
    }
    public int kalkulasiHarga() {
        int total = 0;
        for (int i = 0; i < getData().size(); i++) {
            Map<String, String> item = getData().get(i);
            total = total + toInt(item.get("harga_transaksi"));
        }
        return total;
    }
    public int hitDiskon()
    {
        int totalSetelahDiskon = 0;
        if(total_diskon != 0.0){
            totalSetelahDiskon = (int) Math.round(total_diskon * kalkulasiHarga());
        } 
        return totalSetelahDiskon;
    }
    public int getTotalTransaksi()
    {
        if(hitDiskon() == 0.0)
        {
            return kalkulasiHarga();
        } else {
            return hitDiskon();
        }
    }
    private int toInt(String data)
    {
        return Integer.parseInt(data);
    }
    @Override
    public List<Map<String, String>> getData() {
        List<Map<String, String>> data = new ArrayList<Map<String, String>>();
        try {
            String sql = "SELECT * FROM `tb_detail_transaksi` WHERE status = 'belum lunas'";
            PreparedStatement ps = null;
            ps = Koneksi.getKoneksi().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                Map<String, String> item_data = new HashMap<String, String>();
                item_data.put("id", toString(rs.getInt("id")));
                item_data.put("nama_barang", rs.getString("nama_barang"));
                item_data.put("total", toString(rs.getString("total")));
                item_data.put("harga_transaksi", toString(rs.getString("harga_transaksi")));
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
            String sql = "INSERT INTO `tb_detail_transaksi`(`nama_barang`, `total`, `harga_transaksi`, `status`) VALUES (?,?,?,?)";
            PreparedStatement ps = null;
            ps = Koneksi.getKoneksi().prepareCall(sql);
            int harga_item = total * harga_transaksi;
            ps.setString(1, nama_barang);
            ps.setInt(2, total);
            ps.setInt(3, harga_item);
            ps.setString(4, "belum lunas");
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Berhasil menambah data");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    @Override
    public void updateData(int id) {
        String sql = "";
        try {
            if(id != 0){
                sql = "UPDATE `tb_detail_transaksi` SET `nama_barang`=?,`total`=?,`harga_transaksi`=?,`status`=? WHERE id = ?";
            } else {
                sql = "UPDATE `tb_detail_transaksi` SET `nama_barang`=?,`total`=?,`harga_transaksi`=?,`status`=? WHERE status = 'belum lunas'";
            }
            PreparedStatement ps = null;
            ps = Koneksi.getKoneksi().prepareCall(sql);
            int harga_item = total * harga_transaksi;
            ps.setString(1, nama_barang);
            ps.setInt(2, total);
            ps.setInt(3, harga_item);
            ps.setString(4, "belum lunas");
            ps.setInt(5, id);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Berhasil mengubah data");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    @Override
    public void hapusData(int id) {
        try {
            String sql = "DELETE FROM `tb_detail_transaksi` WHERE id = ?";
            PreparedStatement ps = null;
            ps = Koneksi.getKoneksi().prepareCall(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Berhasil menghapus data");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    public void batalTransaksi()
    {
        try {
            String sql = "DELETE FROM tb_detail_transaksi WHERE status = 'belum lunas'";
            Koneksi.getKoneksi().prepareCall(sql).executeUpdate();
            JOptionPane.showMessageDialog(null, "Berhasil membatalkan transaksi");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    public int bayarTransaksi()
    {
        if(jmlBayar <= getTotalTransaksi())
        {
            JOptionPane.showMessageDialog(null, "Maaf nominal bayar masih kuran");
            return 0;
        } else {
            setTransaksi();
            transaksi.tambahData();
            updateData(0);
            return getKembalian();
        }
    }
    private int getKembalian()
    {
        return jmlBayar - getTotalTransaksi();
    }

    private void setTransaksi() {
        transaksi.setTotal_transaksi(getTotalTransaksi());
        transaksi.setKembalian(getKembalian());
        transaksi.setJml_bayar(jmlBayar);
        transaksi.setStatus("lunas");
    }
    
}
