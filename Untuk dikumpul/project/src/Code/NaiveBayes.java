package Code;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
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
     public String data[][] ;
     String dataTest[][];
     String dataName;
     String sTemp,sTemp2;
     ArrayList<String> variasiClass=new ArrayList<String>() ; //List yang menyimpan nilai berbeda untuk klasifikasi
     int variasiKelas; //  nilai yang menyimpan jumlah nilai unik untuk klasifikasi
     int variasiAttr[] ; //array yang menyimpan jumlah nilai unik untuk tiap attribut
     int numAttr ;//numAttr = jumlah atribut
     public int totalData; //totalData = jumlah data
     public int totalDataUji;
     int totalMatch ;
     int classResult[][];
     double meanTable[];
     double varTable[];
     double accuracy ;
     int isNumericValue[];
     Map<String,Double> bayesTable = new HashMap<String,Double> ();
     Map<String,Double> classProbability = new HashMap<String,Double> ();
     Map<String,Integer> classFrekuensi = new HashMap<String,Integer>();
     Map<String,Integer> finalClass = new HashMap<String,Integer>();
     Map<String,Double> attrInfoD = new HashMap<String,Double>();
     Map<String,Integer> distribusiClass = new HashMap<String,Integer>();
     Map<String,Integer> distinctAttr = new HashMap<String,Integer>(); //Menyimpan jumlah nilai unik untuk satu attribute
    List sortedKeys ;
     //Map<String,classFrekuensi> = 
     
     NaiveBayes() {
         numAttr = 5;
         totalData = 5;
         data = new String[totalData][numAttr];
         for (int i=0;i<numAttr;i++) {
             for (int j=0;j<totalData;j++) data[j][i]="";
         }
     }
     
     public NaiveBayes(Data D) {
         this.variasiClass = D.variasiClass;
         this.isNumericValue = D.isNumericValue;
         for (int i=0;i<D.listAttr.size();i++) {
             this.listAttr.add(D.listAttr.get(i));
         }
         for (int i=0;i<D.variasiClass.size();i++) {
            // System.out.println(i+"|"+variasiClass.get(i));
             this.finalClass.put(D.variasiClass.get(i), i);
         }
         variasiKelas = variasiClass.size();
         this.numAttr = D.listAttr.size();
         this.dataName = D.dataName;
         this.variasiAttr = new int[numAttr];
         classResult = new int[numAttr][numAttr];
         this.distribusiClass = D.distribusiClass;
         this.distinctAttr = D.distinctAttr;
     }
     
     public static double round(double value,int places) {
        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
     }
     
     public void printInfo() {
         System.out.println("> Nama data : "+dataName);
         System.out.println("> Jumlah atribut : "+numAttr);
         for (int i=0;i<numAttr;i++) System.out.println("    "+(i+1)+". "+listAttr.get(i)+"\t | Nilai berbeda : "+variasiAttr[i]);
         System.out.println("> Jumlah data : "+totalData);
         System.out.println("> Variasi hasil akhir (klasifikasi) : "+variasiKelas);
         /*for (int i=0;i<totalData;i++) {
             for (int j=0;j<numAttr-1;j++) {
                 System.out.print(data[i][j]+",");
             }
             System.out.print(data[i][numAttr-1]+"\n");
         }*/
          System.out.println("> Frekuensi tiap Kelas");     
         for (Map.Entry<String,Integer> entry : classFrekuensi.entrySet()) {                
                System.out.println("     " + entry.getKey() + "\t : " + entry.getValue());
        }
         System.out.println("> Probabilitas tiap Kelas");;         
         for (Map.Entry<String, Double> entry : classProbability.entrySet()) {
                System.out.println("      " + entry.getKey() + "\t : " + entry.getValue());
        }
        
        System.out.println("===================================\n"); 
     }
    /* 
     public String printBayesTable() {
         //System.out.println("=== Probability Table");
         //System.out.println(" Klasifikasi | Nama Atribut | Nilai Atribut : Probability");
         String ret="";
         String curclass="",curattr="";   
         for (int i=0;i<sortedKeys.size();i++) {               
               String key = String.valueOf(sortedKeys.get(i));
               double val = bayesTable.get(key);
               String[] aa=key.split(",");
               String tempclass = aa[0];
               //System.out.println(tempclass);
               if (!curclass.equals(tempclass)) {
                   curclass = tempclass ;
                   System.out.println("\n * Class : "+curclass);
               } 
               String tempattr = aa[1];
               if (!curattr.equals(tempattr)) {
                 curattr = tempattr ;
                 System.out.println("   - "+curattr);
               }
               System.out.println("       "+aa[2]+"\t : "+val);
        }
         return ret;
     }*/
     
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
             if (classFrekuensi.containsKey(key)) {
                 int current2 = classFrekuensi.get(key);
                 classFrekuensi.put(key,current2+1);
             }
             else {
                 classFrekuensi.put(key,1);
             }
         }
         for (Map.Entry<String,Integer> entry : classFrekuensi.entrySet()) {
            String key = entry.getKey();
            int d = entry.getValue();
            double dd = (double) d / totalData ;  
            //System.out.println(key+" : "+d+" : "+dd);
            classProbability.put(key, dd);
        }
     }
     
     
     
     public void generateBayesTable() {
         Map<String,Double> temp = new HashMap<String,Double>();
         countMeanAttr();
         countVarAttr();
         for (int j=0;j<numAttr;j++) {             
            if (this.isNumericValue[j]!=1) {
                for (int i=0;i<totalData;i++) {
                    String key = data[i][numAttr-1]+","+listAttr.get(j)+","+data[i][j];
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
            else {
                String key = listAttr.get(j)+",mean";
                String key2 = listAttr.get(j)+",var";
                bayesTable.put(key,meanTable[j]);
                bayesTable.put(key2,varTable[j]);
            }
         }
         
         sortedKeys= new ArrayList(bayesTable.keySet());
         Collections.sort(sortedKeys);
        
     }
     public void countMeanAttr() {
        
                meanTable = new double[numAttr];
        Map<String,Double> tempM = new HashMap<String,Double>();
         for (int x=0;x<listAttr.size();x++) {
            if (isNumericValue[x]==1) {
                double sum=0.0 ;
                double sum2[]=new double[variasiClass.size()];
                tempM.clear();
                for (int i=0;i<numAttr;i++) meanTable[i]=0.0;
                for (int i=0;i<totalData;i++) {
                    double temp = Double.valueOf(data[i][x]);
                    //String c = variasiClass.get(i);
                    String c = data[i][numAttr-1];
                    if (tempM.containsKey(c)) {
                        tempM.put(c,tempM.get(c)+temp);
                    } else tempM.put(c,temp);
                    sum+=temp;
                }
                double mean = (double)sum/totalData ;
                meanTable[x]=mean;
                for (int i=0;i<variasiClass.size();i++) {
                    String c = variasiClass.get(i);
                    int tc = distribusiClass.get(c);
                    String ss = listAttr.get(x)+",mean,"+c;
                    double meant = (double)tempM.get(c)/tc ;
                    //System.out.println(meant+"|"+ss);
                    attrInfoD.put(ss,meant);
                }
            }
         }
     }
     
     public void countVarAttr() {
             
                varTable = new double[numAttr];
        Map<String,Double> tempM = new HashMap<String,Double>();
         for (int x=0;x<listAttr.size();x++) {
            if (isNumericValue[x]==1) {
                double sum=0.0 ;
                double sum2[]=new double[variasiClass.size()];
                for (int i=0;i<totalData;i++) {
                    double temp = Double.valueOf(data[i][x]);
                    double mean1 = meanTable[x] ;
                    String cc = data[i][numAttr-1];
                    String ss = listAttr.get(x)+",mean,"+cc;
                   // System.out.println("s->"+ss);
                    double mean2 = attrInfoD.get(ss);
                    String c = data[i][numAttr-1];
                    if (tempM.containsKey(c)) {
                        double cur =tempM.get(c)+temp;
                        double add = (temp-mean2)*(temp-mean2);
                        tempM.put(c,cur+add);
                    } else tempM.put(c,temp);
                    sum+=((temp-mean1)*(temp-mean1));
                }
                double var = (double)sum/totalData ;
                varTable[x]=var;
                for (int i=0;i<variasiClass.size();i++) {
                    String c = variasiClass.get(i);
                    int tc = distribusiClass.get(c);
                    String ss = listAttr.get(x)+",var,"+c;
                    double vart = (double)tempM.get(c)/tc ;
                    attrInfoD.put(ss,vart);
                }
            }
         }
     }
     
     
     
     public double countGaussian (double x,double m,double v) {
         double ret =1.0;
         double phi = 3.14285714286;
         double d1 = (double)Math.sqrt(2*phi*v);
         double ex = (double)Math.exp(-1*(x-m)*(x-m)/(2*v));
         ret = (double) (1/d1);
         ret*=ex ;
         
         return (ret) ;
     }
     
     public String getClassification(String s[],int k) {
         //Asumsi masukan (value1,value2,....,valueN)
         double def = (double) 1/totalData ;
         double max=0,prob_temp;
         String kelas="";
         for (int i=0;i<variasiKelas;i++) {
             String nullvalue ="";
             String kelasuji = variasiClass.get(i);
            //  System.out.print("P("+kelasuji+")");
              prob_temp = classProbability.get(kelasuji);
             // System.out.println("|"+prob_temp);
              for (int j=0;j<k;j++) {
                  if (this.isNumericValue[j]!=1) {
                    String aa = kelasuji+","+listAttr.get(j)+","+s[j];                  
                    double multiplier = def;
                    if (bayesTable.get(aa)!=null) {
                        multiplier = bayesTable.get(aa);     
                        //System.out.println("    aa : "+aa+"|get : "+bayesTable.get(aa));
                    }
                    else {
                       // System.out.println("    getnull : "+aa);
                        nullvalue=nullvalue+aa+" - " ;
                    }
                    prob_temp = prob_temp*multiplier ;

                   // System.out.println("|probtemp"+prob_temp);
                    //System.out.print("*P("+aa+")");
                  }
                  else {
                       String key = listAttr.get(j)+",mean,"+kelasuji;
                       String key2 = listAttr.get(j)+",var,"+kelasuji; 
                       //System.out.println(key+"|"+key2);
                       double m = attrInfoD.get(key);
                       double v = attrInfoD.get(key2);
                       double x = Double.valueOf(s[j]);
                       prob_temp = prob_temp*this.countGaussian(x, m, v);
                       //System.out.println(key+"|"+m+"|"+key2+"|"+v);
                  }
              }
              //System.out.println("    =====================");
              //System.out.println(" = "+prob_temp);
              //System.out.println("Null value : "+nullvalue);
              if (max<prob_temp) {max=prob_temp; kelas=kelasuji;}
         }
         String datauji="";
         for (int i=0;i<k;i++) datauji=datauji+s[i]+",";
         //System.out.println(datauji+" => "+kelas+" \n   |Probability : "+max);
         return kelas;
     }
     
     public double doFullTraining() {
       //  System.out.println("=============== FULL TRAINING CLASSIFICATION DATA ===============");
         totalMatch=0;
         sTemp="";
         for (int i=0;i<totalDataUji;i++) {
             String datauji[] = dataTest[i] ;
             String hasil =getClassification(datauji,numAttr-1);
            // System.out.println("Class from data : "+dataTest[i][numAttr-1]);
            // System.out.println("Hasil: "+hasil);
               if (hasil.length()>0) {
                int aa = finalClass.get(hasil);
               int bb = finalClass.get(dataTest[i][numAttr-1]);
               classResult[aa][bb]+=1;
               }
             if (hasil.equals(dataTest[i][numAttr-1])) {
                 totalMatch++;
           //      System.out.println(" ===> MATCH !!");
             }
            // else System.out.println(" ===> NOT MATCH !!");
         }
         sTemp+="=> Jumlah data yang match : "+totalMatch+"\n";
         sTemp+="=> Jumlah yang data tidak match : "+(totalDataUji-totalMatch)+"\n";
         accuracy = ((double) totalMatch/totalDataUji)*100 ;
         sTemp+="=> Akurasi : "+accuracy+"%\n";
         //System.out.println("=====================================================================");
         return this.accuracy;
     }
     
     public void printDataUji(int x){
         System.out.println("Print data uji : "+x);
         for (int i=0;i<x;i++) {
             for (int j=0;j<numAttr;j++) {
                 System.out.print(this.dataTest[i][j]+",");
             }
             System.out.println();
         }
     }
     
     public void printDataTrain(int x) {
         System.out.println("Print data train : "+x);
          for (int i=0;i<x;i++) {
             for (int j=0;j<numAttr;j++) {
                 System.out.print(this.data[i][j]+",");
             }
             System.out.println();
         }
     }
     
     public double doClassification(String[][] dataTraining,int totalDTrain,String[][] dataUji,int totalDUji) {
         
         this.totalData = totalDTrain ;
         //data = new String[totalDTrain][numAttr];
         this.data = dataTraining ;
         this.totalDataUji = totalDUji ;
         //dataUji = new String[totalDUji][numAttr];
         this.dataTest = dataUji;
         
            //System.out.println("doClassification | totalData : "+totalData+"| tDataUji : "+totalDUji);
         //printDataTrain(30);
         //System.out.println("==========================");
         //printDataUji(10);
         this.bayesTable.clear();
         this.classProbability.clear();
         this.classFrekuensi.clear();
         hitungVariasiAtribut();
         hitungVariasiHasil();
         generateClassProbability();
         generateBayesTable();
         //printBayesTable();
         doFullTraining();
         
         return this.accuracy;
         //printInfo();
     }
     
     public String printClassResult() {
         String ret="";
         ret+="\n === Matriks Klasifikasi === \n";
         for (int i=0;i<variasiKelas;i++) {
             String s = "C"+String.valueOf(i+1);
             ret+="\t"+s;
         }
         ret+="\n";
         for (int i=0;i<variasiKelas;i++) {
             String s = "C"+String.valueOf(i+1);
             ret+=s+"\t";
             for (int j=0;j<variasiKelas;j++) {
                ret+=classResult[i][j]+"\t";
            }
         ret+="\n";
        }
        ret+="\nInfo : \n";
         for (int i=0;i<variasiKelas;i++) {
             String s = "C"+String.valueOf(i+1);
             ret+=s+" => "+this.variasiClass.get(i)+"\n";
         }
         return ret ;
     }
     
     public String do10crossFold(Data D) {
         double accuracy[];
         String ret="" ;
         accuracy = new double[11];         
         variasiKelas = D.variasiClass.size();
         classResult = new int[variasiKelas][variasiKelas];
         ret+=" ========== 10 Cross Fold =========== \n";
         D.acakData();
         accuracy[1] = doClassification(D.generateDataTraining(1),D.totalDataTraining[1],D.generateDataSet(1),D.totalDataperSet[1]);
         //System.out.println("==============================================");
         accuracy[2] = doClassification(D.generateDataTraining(2),D.totalDataTraining[2],D.generateDataSet(2),D.totalDataperSet[2]);
         //System.out.println("==============================================");
         accuracy[3] = doClassification(D.generateDataTraining(3),D.totalDataTraining[3],D.generateDataSet(3),D.totalDataperSet[3]);
         //System.out.println("==============================================");
         accuracy[4] = doClassification(D.generateDataTraining(4),D.totalDataTraining[4],D.generateDataSet(4),D.totalDataperSet[4]);
         //System.out.println("==============================================");
         accuracy[5] = doClassification(D.generateDataTraining(5),D.totalDataTraining[5],D.generateDataSet(5),D.totalDataperSet[5]);
        // System.out.println("==============================================");
         accuracy[6] = doClassification(D.generateDataTraining(6),D.totalDataTraining[6],D.generateDataSet(6),D.totalDataperSet[6]);
        // System.out.println("==============================================");
         accuracy[7] = doClassification(D.generateDataTraining(7),D.totalDataTraining[7],D.generateDataSet(7),D.totalDataperSet[7]);
        // System.out.println("==============================================");
         accuracy[8] = doClassification(D.generateDataTraining(8),D.totalDataTraining[8],D.generateDataSet(8),D.totalDataperSet[8]);
         //System.out.println("==============================================");
         accuracy[9] = doClassification(D.generateDataTraining(9),D.totalDataTraining[9],D.generateDataSet(9),D.totalDataperSet[9]);
        // System.out.println("==============================================");
         accuracy[10] = doClassification(D.generateDataTraining(10),D.totalDataTraining[10],D.generateDataSet(10),D.totalDataperSet[10]);
         sTemp2="";
         double sum=0, acc ;
         System.out.println("");
        
         for (int i=1;i<=10;i++) {
             sTemp2+="Accuracy uji ke -"+i+" : "+accuracy[i]+"%\n";
             sum+=accuracy[i];
         }
         acc = (double)sum/10;
         double dT = (double) acc/100;
         double dM = (double)dT*D.totalData ;
         dM=round(dM,0);
         double nM = D.totalData - dM;
         sTemp2+="\n=> Jumlah data yang match : "+dM+"\n";
         sTemp2+="=> Jumlah data yang tidak match : "+nM+"\n";
         sTemp2+="Accuracy rata rata : "+acc+"%\n\n";
         ret+=sTemp2;
         return ret;
     }
     
     public String doSchemaFullTraining(Data D) {
         String ret="" ; 
         double ret2;
         ret+="\n =========== FULL TRAINING ==========\n";
         
         classResult = new int[variasiKelas][variasiKelas];
         ret2 = doClassification(D.data,D.totalData,D.data,D.totalData);
         ret+=sTemp;
         return ret ;
     }
}
     
     
     

