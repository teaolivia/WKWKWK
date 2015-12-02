/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Code;

/**
 *
 * @author tama
 */
public class test {
    public static void main (String[] args) {
        System.out.print("Input file name : ");
        java.util.Scanner S = new java.util.Scanner(System.in);
        String file = S.nextLine();
        Data D = new Data(file);
        D.makeDataSet();
        D.checkNumericAttr();
        knn2 K = new knn2(5,D);
        //K.doFullTraining();
    }
}
