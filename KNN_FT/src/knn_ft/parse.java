/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package knn_ft;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Alriana Moel
 */
public class parse {
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
    
    parse() {
        iname = 5;
        rdata = 5;
        name = new String[iname] ;
        data = new String[rdata][iname];
        for (int i=0;i<iname;i++) {
            name[i]="x";
            for (int j=0;j<rdata;j++) data[j][i]="";
        }
    }
     
    parse(String files) {
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
    
    public printInfo(){
        System.out.println("===================================");
        System.out.println("======== DATA DATA TERKAIT ========");
        System.out.println("Jumlah atribut : "+iname);
        
        for (int i=0;i<iname;i++)
            System.out.println("  "+(i+1)+". "+name[i]");
        
        System.out.println("Jumlah data : "+rdata);
        
        System.out.println("===================================\n"); 
        
    }
    
    public string getClassificaion (String s[], int k){
        // implements later   
    }
    
    public void doFullTraining(int k){
        //implements later
    }
    
    
}
