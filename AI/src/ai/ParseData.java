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
public class ParseData {
     String name[] ;
     String data[][] ;
     ArrayList<String> variasiClass=new ArrayList<String>() ; //List yang menyimpan nilai berbeda untuk klasifikasi
     int variasihasil; //  nilai yang menyimpan nilai perbedaan hasil akhir (klasifikasi)
     int variasidata[] ; //array yang menyimpan nilai perbedaan data
     int iname ;//iname = jumlah atribut
     int rdata; //rdata = jumlah data
     int totalMatch ;
     double accuracy ;
     Map<String,Double> bayesTable = new HashMap<String,Double> ();
     Map<String,Double> classProbability = new HashMap<String,Double> ();
     Map<String,Integer> classFrekuensi = new HashMap<String,Integer>();
     
     ParseData() {
         iname = 5;
         rdata = 5;
         name = new String[iname] ;
         data = new String[rdata][iname];
         for (int i=0;i<iname;i++) {
             name[i]="x";
             for (int j=0;j<rdata;j++) data[j][i]="";
         }
     }
     
     ParseData(String files) {
        try {
            File file = new File(files);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            line = bufferedReader.readLine();      
            String[] attr = line.split(",");
            iname = attr.length; //menghitung jumlah attribut
            name = new String[iname];
            variasidata = new int[iname];
            for (int i=0;i<iname;i++) {
                name[i] = attr[i];
            }
            int count=0;
            //menghitung jumlah data
            while (bufferedReader.readLine()!=null) count++; //menghitung jumlah data
            rdata = count;
            data = new String[rdata][iname];
            fileReader.close();
            System.out.println("Contents of file:");
        } catch (Exception e) {
            e.printStackTrace();
	}
        
        try {
            File file = new File(files);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            line = bufferedReader.readLine();
            int j=0;
            line=bufferedReader.readLine();
            //memasukkan data
            while (line!=null) {
                String[] attr = line.split(",");
                for (int i=0;i<iname;i++) data[j][i]=attr[i];
                line=bufferedReader.readLine();
                j++;
            }
            
            fileReader.close();
            System.out.println("Contents of file:");
        } catch (Exception e) {
            e.printStackTrace();
	}
        
        
     }
     
     public void printInfo() {
         System.out.println("===================================");
         System.out.println("======== DATA DATA TERKAIT ========");
         System.out.println("Jumlah atribut : "+iname);
         for (int i=0;i<iname;i++) System.out.println("  "+(i+1)+". "+name[i]+" | Nilai berbeda (variasi) : "+variasidata[i]);
         System.out.println("Jumlah data : "+rdata);
         System.out.println("Variasi hasil akhir (klasifikasi) : "+variasihasil);
         /*for (int i=0;i<rdata;i++) {
             for (int j=0;j<iname-1;j++) {
                 System.out.print(data[i][j]+",");
             }
             System.out.print(data[i][iname-1]+"\n");
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
         for (int i=0;i<rdata;i++) {
             String temp = data[i][iname-1];
             if (!variasiClass.contains(temp)) variasiClass.add(temp);
         }
         variasihasil = variasiClass.size();
     }
     
     public void hitungVariasiAtribut() {
         
         for (int j=0;j<iname;j++) {
            ArrayList<String> item = new ArrayList<String>();
            for (int i=0;i<rdata;i++) {
                String temp = data[i][j];
                if (!item.contains(temp)) item.add(temp);
            }
            variasidata[j] = item.size();
         }
     }
     
     public void generateClassProbability() {
         for (int i=0;i<rdata;i++) {
             String key = data[i][iname-1];
             if (classProbability.containsKey(key)) {
                 double current = (double) classProbability.get(key);
                 current+=(double)1/rdata ;
                 classProbability.put(key,current);
                 int current2 = classFrekuensi.get(key);
                 classFrekuensi.put(key,current2+1);
             }
             else {
                 classProbability.put(key,(double)1/rdata);
                 classFrekuensi.put(key,1);
             }
         }
     }
     
     public void generateBayesTable() {        
         for (int j=0;j<iname;j++) {
            for (int i=0;i<rdata;i++) {
                String key = name[j]+"|"+data[i][j]+"|"+data[i][iname-1];
                if (bayesTable.containsKey(key)) {
                    double current = bayesTable.get(key);
                    int frekuensiKelas = classFrekuensi.get(data[i][iname-1]);                    
                    double addition =(double) 1/frekuensiKelas ;
                    bayesTable.put(key,(double) current+addition);
                }
                else {
                    int frekuensiKelas = classFrekuensi.get(data[i][iname-1]);                    
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
         for (int i=0;i<variasihasil;i++) {
             String nullvalue ="";
             String kelasuji = variasiClass.get(i);
              //System.out.print("P("+kelasuji+")");
              prob_temp = classProbability.get(kelasuji);
              for (int j=0;j<k;j++) {
                  String aa = name[j]+"|"+s[j]+"|"+kelasuji ;
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
     
     public void doFullTraining() {
         System.out.println("=============== FULL TRAINING CLASSIFICATION DATA ===============");
         for (int i=0;i<rdata;i++) {
             String datauji[] = data[i] ;
             String hasil =getClassification(datauji,iname-1);
             System.out.print("Class from data : "+data[i][iname-1]);
             if (hasil.equals(data[i][iname-1])) {
                 totalMatch++;
                 System.out.println(" ===> MATCH !!");
             }
             else System.out.println(" ===> NOT MATCH !!");
         }
         System.out.println("=> Jumlah data yang match : "+totalMatch);
         System.out.println("=> Jumlah yang data tidak match : "+(rdata-totalMatch));
         accuracy = ((double) totalMatch/rdata)*100 ;
         System.out.println("=> Akurasi : "+accuracy);
         System.out.println("=====================================================================");
     }
}
