/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller.diskon;

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
public class Diskon implements DataInterface {

    private String kode_diskon;
    
    public void setKode_diskon(String kode_diskon) {
        this.kode_diskon = kode_diskon;
    }
    @Override
    public List<Map<String, String>> getData() {
        List<Map<String, String>> data = new ArrayList<Map<String, String>>();
        try {
            String sql = "SELECT * FROM tb_diskon WHERE kode_diskon = ? LIMIT 1";
            PreparedStatement st = null;
            st = Koneksi.getKoneksi().prepareCall(sql);
            st.setString(1, kode_diskon);
            ResultSet rs = st.executeQuery();
            while(rs.next())
            {
                Map<String, String> item_data = new HashMap<String, String>();
                item_data.put("id", String.valueOf(rs.getInt("id")));
                item_data.put("nama_diskon", rs.getString("nama_diskon"));
                item_data.put("kode_diskon", rs.getString("kode_diskon"));
                item_data.put("total_diskon", String.valueOf(rs.getFloat("total_diskon")));
                data.add(item_data);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return data;
    }

    @Override
    public void tambahData() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
