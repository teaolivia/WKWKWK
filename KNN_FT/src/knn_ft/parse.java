/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package knn_ft;

import java.io.BufferedReader;
import java.util.HashMap;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Arrays;
import java.util.Comparator;

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
    
    public void printInfo(){
        System.out.println("===================================");
        System.out.println("======== DATA DATA TERKAIT ========");
        System.out.println("Jumlah atribut : "+iname);
        
        for (int i=0;i<iname;i++)
            System.out.println("  "+(i+1)+". +name[i]");
        
        System.out.println("Jumlah data : "+rdata);
        
        // To show dataset
        /*
        for (int i=1;i<rdata;i++){
            for(int j=0;j<iname;j++){
                System.out.print(data[i][j]);
                System.out.print(" ");
            }
            System.out.println("");
        }
        */
        System.out.println("===================================\n"); 
        
    }
    
    public String check (String s[], String p[]){
        int dif;
        dif = iname;
        
        for(int i=0;i<iname;i++){
            
            if(s[i].equals(p[i])) dif--;
            //System.out.println(s[i] + " " + p[i] + " " + dif);
        }
        
        String res = Integer.toString(dif);
        return res;
        
    }
    
    public String doKNN (int cur, int k){
        //inisialisasi temp dataset;
        
        String temp[][];
        temp = new String[rdata][iname+1];
        String atr[];
        atr =  new String[iname+1];

        for (int i=0;i<iname;i++){
            atr[i] = name[i];
        }

        atr[iname] = "Jarak";

        for(int i=0;i<rdata;i++){
            for (int j=0;j<iname;j++){
                
                temp[i][j] = data[i][j];
            }
        }

        //test KNN for every dataset
        for (int i=0;i<rdata;i++){
            
            temp[i][iname] = check(data[cur],temp[i]);
        }
        
        Arrays.sort(temp, new Comparator<String[]>() {
            @Override
            public int compare(final String[] entry1, final String[] entry2) {
                final String j1 = entry1[iname];
                final String j2 = entry2[iname];
                return j1.compareTo(j2);
            }
        });
        
        // Get the clasification
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        String tempStr;
        for (int i = 0; i < k; i++)
        {
            tempStr = temp[i][iname-1];
            if(map.containsKey(tempStr))
            {
                map.put(tempStr, map.get(tempStr) + 1);
            }
            else
            {
                map.put(tempStr,1);
            }
        }
        Map.Entry<String,Integer> entry=map.entrySet().iterator().next();
        String res= entry.getKey();
        //System.out.println("Res: " + res);
        
        return res;
    }
    
    public void doFullTraining(){
        System.out.println("Masukan nilai k");
        
        try{
            BufferedReader cin = new BufferedReader(new InputStreamReader(System.in));
            String se = cin.readLine();
            int k = Integer.parseInt(se);
            int same=0;
            double acc;
            String match[];
            match = new String[rdata];
            
            for(int i=0;i<rdata;i++){
                match[i] = doKNN(i,k);
                if (data[i][iname-1].equals(match[i])) same++;
            }
            
            
            acc = ((double)same / rdata)*100;
            
            System.out.println("Accuracy: "+ String.format( "%.2f", acc) + "%");
            
            
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        
    }
    
    
}
