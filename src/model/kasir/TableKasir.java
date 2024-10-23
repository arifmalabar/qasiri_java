/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model.kasir;

import java.util.List;
import java.util.Map;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author hp
 */
public class TableKasir extends AbstractTableModel {
    List<Map<String, String>> data;
    String[] colname = {"nama_barang", "total", "harga_transaksi"};

    public TableKasir(List<Map<String, String>> data) {
        this.data = data;
    }
    
    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return colname.length;
    }

    @Override
    public String getColumnName(int column) {
        return colname[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Map<String, String> item_data = data.get(rowIndex);
        String cname = colname[columnIndex];
        return item_data.get(cname);
    }
    
}
