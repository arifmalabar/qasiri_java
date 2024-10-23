/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package qasiri;

import config.Koneksi;
import controller.diskon.Diskon;
import controller.kasir.Kasir;
import controller.transaksi.Transaksi;

/**
 *
 * @author hp
 */
public class Qasiri {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            System.out.println(new Transaksi().getKodeTransaksi());
        } catch (Exception e) {
        }
    }
    
}
