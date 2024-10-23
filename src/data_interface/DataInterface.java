/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package data_interface;

import java.util.List;
import java.util.Map;

/**
 *
 * @author hp
 */
public interface DataInterface {
    public List<Map<String, String>> getData();
    public void tambahData();
    public void updateData(int id);
    public void hapusData(int id);
}
