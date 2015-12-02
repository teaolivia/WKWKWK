/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Code;


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
    
       // Data D = new Data("src/data1.txt");
        
        System.out.print("Input file name : ");
        java.util.Scanner S = new java.util.Scanner(System.in);
        String file = S.nextLine();
        Data D = new Data(file);
        D.makeDataSet();
        D.checkNumericAttr();
        System.out.print("1. Naive Bayes\n2.KNN\nYour choice : ");
        int pil = S.nextInt();
        if (pil==1) {
            NaiveBayes NB = new NaiveBayes(D) ;
            String res="";
            System.out.print("1.Full Training\n2.10 Cross Fold\nYour choice : ");
            int pil2 = S.nextInt();
            if (pil2==1) {
                String ss = "full|bayes";
                 System.out.println(D.printInfo(ss));
                 res = NB.doSchemaFullTraining(D);
            }
             else {
                String ss = "10fold|bayes";
                 System.out.println(D.printInfo(ss));
                 res = NB.do10crossFold(D);
            }
             System.out.println(res);
             System.out.println(NB.printClassResult());
        } else {
            System.out.print("k-NN -> Input k : ");
            int k = S.nextInt();
            String res="";
            knn2 K = new knn2(k,D);
             System.out.print("1.Full Training\n2.10 Cross Fold\nYour choice : ");
            int pil2 = S.nextInt();
            if (pil2==1) {
                String ss = "full|knn,"+k;
                 System.out.println(D.printInfo(ss));
                 res = K.schemaFullTraining();
            }
             else {
                String ss = "10fold|knn,"+k;
                 System.out.println(D.printInfo(ss));
                 res = K.schema10Fold(D);
            }
            System.out.println(res);
            System.out.println(K.printClassResult());
        }
        
        D.makeDataSet();
        D.checkNumericAttr();
        //System.out.println(D.infoNumericAttr());
       
      //  
        
      
        //D.printData(20);
        //D.acakData();
        //System.out.println("+================+");
        //D.printData(20);
        //D.printInfo();
      //
        
      // 
     //  String 
//NB.doFullTraining();
        //NB.printBayesTable();
      //System.out.println(res);
     // System.out.println(NB.printClassResult());
      // System.out.println(NB.countGaussian(6.0,5.855,((double)3.5033/100)));
      
//        K.doSchemaFullTraining(D);
        //K.do10crossFold(D);
        //NB.do10crossFold(D);
        //NB.printInfo();
        //NB.printBayesTable();
        //NB.printClassResult();
        
/*
        NB.doClassification(D.generateDataTraining(6),D.totalDataTraining[6],D.generateDataSet(6), D.totalDataperSet[6]);
        NB.doClassification(D.generateDataTraining(7),D.totalDataTraining[7],D.generateDataSet(7), D.totalDataperSet[7]);
        NB.doClassification(D.generateDataTraining(8),D.totalDataTraining[8],D.generateDataSet(8), D.totalDataperSet[8]);
  */
        //for (int i=1;i<=10;i++) D.generateDataTraining(i);
    }
    
}
