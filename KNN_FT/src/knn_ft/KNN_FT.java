/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package knn_ft;

/**
 *
 * @author moel
 */
public class KNN_FT {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        parse K= new parse("D:/data.txt") ; //NAMA FILENYA SESUAIKAN SAMA LOKASI FILENYA YA
        K.printInfo();
        
        System.out.println("Masukan nilai K");
        int nk=5;
        K.doFullTraining(nk);
        System.out.println("=== CONTOH ===");
        String contoh = "vhigh,vhigh,2,4,small,low";
        String ret[] = contoh.split(",");
        K.getClassification(ret,ret.length);
    }
    
}
