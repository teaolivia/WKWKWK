/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ai;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
/**
 *
 * @author tama
 */
public class AI {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        ParseData P = new ParseData("D:/data.txt") ; //NAMA FILENYA SESUAIKAN SAMA LOKASI FILENYA YA
        P.hitungVariasiAtribut();
        P.hitungVariasiHasil();
        P.generateClassProbability();
        P.generateBayesTable();
        P.printInfo();
        P.doFullTraining();
        System.out.println("=== CONTOH ===");
        String contoh = "vhigh,vhigh,2,4,small,low";
        String ret[] = contoh.split(",");
        P.getClassification(ret,ret.length);
    }
    
}
