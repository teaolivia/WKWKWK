package ai;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

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
     int iname ;
     int rdata;
     
     
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
         System.out.println("Jumlah atribut : "+iname);
         for (int i=0;i<iname;i++) System.out.println("  "+(i+1)+". "+name[i]);
         System.out.println("Jumlah data : "+rdata);
         for (int i=0;i<rdata;i++) {
             for (int j=0;j<iname-1;j++) {
                 System.out.print(data[i][j]+",");
             }
             System.out.print(data[i][iname-1]+"\n");
         }
     }
}
