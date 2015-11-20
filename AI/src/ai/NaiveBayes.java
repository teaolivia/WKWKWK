package ai;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author tama
 */
public class NaiveBayes {
     ArrayList<String> listAttr =new ArrayList<String>();
     String data[][] ;
     String dataTest[][];
     String dataName;
     ArrayList<String> variasiClass=new ArrayList<String>() ; //List yang menyimpan nilai berbeda untuk klasifikasi
     int variasiKelas; //  nilai yang menyimpan jumlah nilai unik untuk klasifikasi
     int variasiAttr[] ; //array yang menyimpan jumlah nilai unik untuk tiap attribut
     int numAttr ;//numAttr = jumlah atribut
     int totalData; //totalData = jumlah data
     int totalDataUji;
     int totalMatch ;
     double accuracy ;
     Map<String,Double> bayesTable = new HashMap<String,Double> ();
     Map<String,Double> classProbability = new HashMap<String,Double> ();
     Map<String,Integer> classFrekuensi = new HashMap<String,Integer>();
     
     NaiveBayes() {
         numAttr = 5;
         totalData = 5;
         data = new String[totalData][numAttr];
         for (int i=0;i<numAttr;i++) {
             for (int j=0;j<totalData;j++) data[j][i]="";
         }
     }
     
     NaiveBayes(Data D) {
         for (int i=0;i<D.listAttr.size();i++) {
             this.listAttr.add(D.listAttr.get(i));
         }
         this.numAttr = D.listAttr.size();
         this.dataName = D.dataName;
         this.variasiAttr = new int[numAttr];
     }
     
     
     public void printInfo() {
         System.out.println("=============================================");
         System.out.println("======== DATA DATA PENTING '"+dataName+"' ========");
         System.out.println("Jumlah atribut : "+numAttr);
         for (int i=0;i<numAttr;i++) System.out.println("  "+(i+1)+". "+listAttr.get(i)+" | Nilai berbeda (variasi) : "+variasiAttr[i]);
         System.out.println("Jumlah data : "+totalData);
         System.out.println("Variasi hasil akhir (klasifikasi) : "+variasiKelas);
         /*for (int i=0;i<totalData;i++) {
             for (int j=0;j<numAttr-1;j++) {
                 System.out.print(data[i][j]+",");
             }
             System.out.print(data[i][numAttr-1]+"\n");
         }*/
          System.out.println("==== Frekuensi tiap Kelas ====");
         System.out.println("Kelas -> Frekuensi");         
         for (Map.Entry<String,Integer> entry : classFrekuensi.entrySet()) {                
                System.out.println(" " + entry.getKey() + " -> " + entry.getValue());
        }
         System.out.println("==== Probabilitas tiap Kelas ====");
         System.out.println("Kelas -> Probabilitas ");         
         for (Map.Entry<String, Double> entry : classProbability.entrySet()) {
                System.out.println(" " + entry.getKey() + " -> " + entry.getValue());
        }
       /*  System.out.println("=== Probability Table");
         System.out.println("Nama Atribut | Nilai Atribut | Klasifikasi -> Probability");
           for (Map.Entry<String, Double> entry : bayesTable.entrySet()) {
                System.out.println(" " + entry.getKey() + " -> " + entry.getValue());
        }*/
        System.out.println("===================================\n"); 
     }
     
     public void hitungVariasiHasil() {         
         for (int i=0;i<totalData;i++) {
             String temp = data[i][numAttr-1];
             if (!variasiClass.contains(temp)) variasiClass.add(temp);
         }
         variasiKelas = variasiClass.size();
     }
     
     public void hitungVariasiAtribut() {
         
         for (int j=0;j<numAttr;j++) {
            ArrayList<String> item = new ArrayList<String>();
            for (int i=0;i<totalData;i++) {
                String temp = data[i][j];
                if (!item.contains(temp)) item.add(temp);
            }
            variasiAttr[j] = item.size();
         }
     }
     
     public void generateClassProbability() {
         for (int i=0;i<totalData;i++) {
             String key = data[i][numAttr-1];
             if (classProbability.containsKey(key)) {
                 double current = (double) classProbability.get(key);
                 current+=(double)1/totalData ;
                 classProbability.put(key,current);
                 int current2 = classFrekuensi.get(key);
                 classFrekuensi.put(key,current2+1);
             }
             else {
                 classProbability.put(key,(double)1/totalData);
                 classFrekuensi.put(key,1);
             }
         }
     }
     
     public void generateBayesTable() {        
         for (int j=0;j<numAttr;j++) {
            for (int i=0;i<totalData;i++) {
                String key = listAttr.get(j)+"|"+data[i][j]+"|"+data[i][numAttr-1];
                if (bayesTable.containsKey(key)) {
                    double current = bayesTable.get(key);
                    int frekuensiKelas = classFrekuensi.get(data[i][numAttr-1]);                    
                    double addition =(double) 1/frekuensiKelas ;
                    bayesTable.put(key,(double) current+addition);
                }
                else {
                    int frekuensiKelas = classFrekuensi.get(data[i][numAttr-1]);                    
                    double addition =(double) 1/frekuensiKelas ;
                    bayesTable.put(key,addition);
                }
            }
         }     
     }
     
     public String getClassification(String s[],int k) {
         //Asumsi masukan (value1,value2,....,valueN)
         
         double max=0,prob_temp;
         String kelas="";
         for (int i=0;i<variasiKelas;i++) {
             String nullvalue ="";
             String kelasuji = variasiClass.get(i);
              //System.out.print("P("+kelasuji+")");
              prob_temp = classProbability.get(kelasuji);
              for (int j=0;j<k;j++) {
                  String aa = listAttr.get(j)+"|"+s[j]+"|"+kelasuji ;
                  double multiplier = 0;
                  if (bayesTable.get(aa)!=null) {
                      multiplier = bayesTable.get(aa);                      
                  }
                  else nullvalue=nullvalue+aa+" - " ;
                  prob_temp = prob_temp*multiplier ;
                  //System.out.print("*P("+aa+")");
              }
              //System.out.println(" = "+prob_temp);
              //System.out.println("Null value : "+nullvalue);
              if (max<prob_temp) {max=prob_temp; kelas=kelasuji;}
         }
         String datauji="";
         for (int i=0;i<k;i++) datauji=datauji+s[i]+",";
         System.out.println(datauji+" => "+kelas+" |Probability : "+max);
         return kelas;
     }
     
     public double doFullTraining() {
         System.out.println("=============== FULL TRAINING CLASSIFICATION DATA ===============");
         totalMatch=0;
         for (int i=0;i<totalDataUji;i++) {
             String datauji[] = dataTest[i] ;
             String hasil =getClassification(datauji,numAttr-1);
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
         Map<String,Double> bayesTable = new HashMap<String,Double> ();
         Map<String,Double> classProbability = new HashMap<String,Double> ();
         Map<String,Integer> classFrekuensi = new HashMap<String,Integer>();
         ArrayList<String> variasiClass=new ArrayList<String>() ; //List yang menyimpan nilai berbeda untuk klasifikasi
         hitungVariasiAtribut();
         hitungVariasiHasil();
         generateClassProbability();
         generateBayesTable();
         doFullTraining(); 
         return this.accuracy;
         //printInfo();
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
     
     
     

