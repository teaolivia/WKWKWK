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
    
        Data D = new Data("src/data.txt");
        D.makeDataSet();
        //D.printInfo();
        //NaiveBayes NB = new NaiveBayes(D) ;
        //NB.doSchemaFullTraining(D);
        
        //NB.printInfo();
        KNN K = new KNN(2,D);
        //K.doSchemaFullTraining(D);
        K.do10crossFold(D);
//NB.do10crossFold(D);
    }
    
}
