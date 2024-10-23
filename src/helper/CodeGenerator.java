/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package helper;

/**
 *
 * @author hp
 */
public class CodeGenerator {
    private int counter = 1;
    public String getKode(char position)
    {
        String num = String.format("%03d", counter);
        counter++;
        return position + num;
    }
}
