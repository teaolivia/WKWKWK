/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ai;

/**
 *
 * @author LUCKY
 */
public class kNNTest {
    public static void main (String[] args){
        Data D = new Data("src/data.txt");
        D.makeDataSet();
        kNN k = new kNN(D);
        k.doClassification(D, 1);
    }
}
