/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ai;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class KNN {
    String[] kelasTemp;
    int[] differTemp ;
    String[][] data ;
    String[][] dataTest ;
    int numKNN,totalData,totalDataUji,numAttr,totalMatch ;
    Data D ;
    double accuracy ;
    Map<String,Integer> sortKNN ;
    KNN() {}
    
    KNN(int numKNN_,Data D_) {
        this.numKNN = numKNN_ ;
        this.D = D_ ;
        this.numAttr = D_.numAttr;
    }
    
    public int findDiffer(String[] ref,String[] test) {
        int ret = 0;
        for (int i=0;i<((D.numAttr)-1);i++) {
            if (!ref[i].equals(test[i])) ret++;
        }
        return ret ;
    }
    
    public void doKNN() {
        
        
    }
    
    public int getIndex(int p) {
        int i=0 ;
        while (i<numKNN && p>differTemp[i]&&!kelasTemp[i].equals("")) {
            i++;
        }
        if (i<numKNN) return i ;
        else return -1;
    }
    
     public String getClassification(String s[]) {
         //Asumsi masukan (value1,value2,....,valueN)
         kelasTemp = new String[numKNN];
         for (int i=0;i<numKNN;i++) kelasTemp[i]="";
         differTemp = new int[numKNN];
         for (int i=0;i<totalData;i++) {
             int result = findDiffer(data[i],s);
             String kelas = data[i][(D.numAttr)-1];
             int idx = getIndex(result);
             if (idx!=-1) {
                 for (int j=numKNN-2;j>idx;j--) {
                     kelasTemp[j+1] = kelasTemp[j];
                     differTemp[j+1] = differTemp[j] ;
                 }
                 kelasTemp[idx] = kelas ;
                 differTemp[idx] = result ;
             }
         }
         sortKNN = new HashMap<String,Integer>();
         for (int i=0;i<numKNN;i++) {
             if (sortKNN.containsKey(kelasTemp[i])) {
                 int current = sortKNN.get(kelasTemp[i]) ;
                 sortKNN.put(kelasTemp[i],(current+1));
             }
             else sortKNN.put(kelasTemp[i],1);
         }
         int max=0;
         String kelasFinal="";
          for (Map.Entry<String,Integer> entry : sortKNN.entrySet()) {                
             int cc = entry.getValue() ;
             String kk = entry.getKey();
             if (cc>max) {
                 max = cc;
                 kelasFinal = kk ;
             }
        }
        return kelasFinal;
     }
     
     public double doFullTraining() {
         System.out.println("=============== FULL TRAINING CLASSIFICATION DATA ===============");
         totalMatch=0;
         for (int i=0;i<totalDataUji;i++) {
             String datauji[] = dataTest[i] ;
             String hasil =getClassification(datauji);
             System.out.print("Class from data : "+dataTest[i][numAttr-1]);
             if (hasil.equals(dataTest[i][numAttr-1])) {
                 totalMatch++;
                 System.out.println(" ===> MATCH !!");
             }
             else System.out.println(" ===> NOT MATCH !!");
         }
         System.out.println("=> Jumlah data yang match : "+totalMatch);
         System.out.println("=> Jumlah yang data tidak match : "+(totalDataUji-totalMatch));
         accuracy = ((double) totalMatch/totalDataUji)*100 ;
         System.out.println("=> Akurasi : "+accuracy);
         System.out.println("=====================================================================");
         return this.accuracy;
     }
     
     
     public double doClassification(String[][] dataTraining,int totalDTrain,String[][] dataUji,int totalDUji) {
         this.data = dataTraining ;
         this.totalData = totalDTrain ;
         this.dataTest = dataUji;
         this.totalDataUji = totalDUji ;
         doFullTraining() ;
         return this.accuracy;
     }
     
     
     public double do10crossFold(Data D) {
         double accuracy[];
         accuracy = new double[11];
         accuracy[1] = doClassification(D.generateDataTraining(1),D.totalDataTraining[1],D.dataSet1,D.totalDataperSet[1]);
         System.out.println("==============================================");
         accuracy[2] = doClassification(D.generateDataTraining(2),D.totalDataTraining[2],D.dataSet2,D.totalDataperSet[2]);
         System.out.println("==============================================");
         accuracy[3] = doClassification(D.generateDataTraining(3),D.totalDataTraining[3],D.dataSet3,D.totalDataperSet[3]);
         System.out.println("==============================================");
         accuracy[4] = doClassification(D.generateDataTraining(4),D.totalDataTraining[4],D.dataSet4,D.totalDataperSet[4]);
         System.out.println("==============================================");
         accuracy[5] = doClassification(D.generateDataTraining(5),D.totalDataTraining[5],D.dataSet5,D.totalDataperSet[5]);
         System.out.println("==============================================");
         accuracy[6] = doClassification(D.generateDataTraining(6),D.totalDataTraining[6],D.dataSet6,D.totalDataperSet[6]);
         System.out.println("==============================================");
         accuracy[7] = doClassification(D.generateDataTraining(7),D.totalDataTraining[7],D.dataSet7,D.totalDataperSet[7]);
         System.out.println("==============================================");
         accuracy[8] = doClassification(D.generateDataTraining(8),D.totalDataTraining[8],D.dataSet8,D.totalDataperSet[8]);
         System.out.println("==============================================");
         accuracy[9] = doClassification(D.generateDataTraining(9),D.totalDataTraining[9],D.dataSet9,D.totalDataperSet[9]);
         System.out.println("==============================================");
         accuracy[10] = doClassification(D.generateDataTraining(10),D.totalDataTraining[10],D.dataSet10,D.totalDataperSet[10]);
         
         double sum=0, acc ;
         for (int i=1;i<=10;i++) {
             System.out.println("Accuracy uji ke -"+i+" : "+accuracy[i]);
             sum+=accuracy[i];
         }
         acc = (double)sum/10;
         System.out.println("Accuracy rata rata : "+acc);
         return acc;
     }
         
     public double doSchemaFullTraining(Data D) {
         double ret ;
         ret = doClassification(D.data,D.totalData,D.data,D.totalData);
         return ret ;
     }
    
}
