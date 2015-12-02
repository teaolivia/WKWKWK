/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ai;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

/**
 *
 * @author tama
 */
public class Data {
    String[][] data ;
    String[][] dataSet1 ;
    String[][] dataSet2 ;
    String[][] dataSet3 ;
    String[][] dataSet4 ;
    String[][] dataSet5 ;
    String[][] dataSet6 ;
    String[][] dataSet7 ;
    String[][] dataSet8 ;
    String[][] dataSet9 ;
    String[][] dataSet10 ;
    int[] totalDataperSet ; 
    int[] totalDataTraining; 
    int numAttr ;//numAttr = jumlah atribut
    int totalData; //totalData = jumlah data
    int numDataSetNormal ;
    int numDataSetOver ;
    String dataName;
     ArrayList<String> listAttr =new ArrayList<String>();
     
     Data() {}
     
     Data(String files) {
         totalDataperSet = new int[11];
         totalDataTraining = new int[11];
         try {
            File file = new File(files);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            do {
                line = bufferedReader.readLine();  
            } while (!line.contains("@relation"));
            //Ketika sudah menemukan yang berawalan @relation, maka pembacaan di lanjutkan 2x
            String[] aa=line.split(" ");
            dataName = aa[1];
            line = bufferedReader.readLine();
            line = bufferedReader.readLine();
            while (line.contains("@attribute")) {
                String[] attr = line.split(" ");
                listAttr.add(attr[1]);
                line = bufferedReader.readLine();
            }
            //Keluar dari proses di atas, pembacaan berada di line kosong
            line = bufferedReader.readLine(); //pembacaan berada di @data
            numAttr = listAttr.size(); //jumlah attributr = new int[numAttr];
            int count=0;
            //menghitung jumlah data
            while (bufferedReader.readLine()!=null) count++; //menghitung jumlah data
            totalData = count;
            data = new String[totalData][numAttr];
            fileReader.close();
        } catch (Exception e) {
            e.printStackTrace();
	}
        
        try {
            File file = new File(files);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            do {
                line = bufferedReader.readLine();            
            } while (!line.contains("@data"));
            int j=0;
            line=bufferedReader.readLine();
            //memasukkan data
            while (line!=null) {
                String[] attr = line.split(",");
                for (int i=0;i<numAttr;i++) data[j][i]=attr[i];
                line=bufferedReader.readLine();
                j++;
            }
            
            fileReader.close();
            System.out.println("Contents of file:");
        } catch (Exception e) {
            e.printStackTrace();
	}
     
     }
     
     public void makeDataSet() {
         int perData = totalData/10 ;
         System.out.println(perData);
         totalDataperSet[10] = perData + (totalData % 10);
         this.numDataSetNormal = perData ;
         this.numDataSetOver = perData + (totalData % 10);
         dataSet1 = new String[perData][numAttr];
         dataSet2 = new String[perData][numAttr];
         dataSet3 = new String[perData][numAttr];
         dataSet4 = new String[perData][numAttr];
         dataSet5 = new String[perData][numAttr];
         dataSet6 = new String[perData][numAttr];
         dataSet7 = new String[perData][numAttr];
         dataSet8 = new String[perData][numAttr];         
         dataSet9 = new String[perData][numAttr];
         dataSet10 = new String[perData+(totalData%10)][numAttr];

         for (int i=1;i<10;i++) totalDataperSet[i] = perData ;
         //Isi data Set 1-10
         int start;
         start=0;
         for (int i=0;i<perData;i++) dataSet1[i] = data[i+start];
         start = perData ;
         for (int i=0;i<perData;i++) dataSet2[i] = data[i+start];
         start = perData*2 ;
         for (int i=0;i<perData;i++) dataSet3[i] = data[i+start];
         start = perData*3 ;
         for (int i=0;i<perData;i++) dataSet4[i] = data[i+start];
         start = perData*4 ;
         for (int i=0;i<perData;i++) dataSet5[i] = data[i+start];
         start = perData*5 ;
         for (int i=0;i<perData;i++) dataSet6[i] = data[i+start];
         start = perData*6 ;
         for (int i=0;i<perData;i++) dataSet7[i] = data[i+start];
         start = perData*7 ;
         for (int i=0;i<perData;i++) dataSet8[i] = data[i+start];
         start = perData*8 ;
         for (int i=0;i<perData;i++) dataSet9[i] = data[i+start];
         start = perData*9 ;
         for (int i=0;i<(perData+(totalData%10));i++) dataSet10[i] = data[i+start];
     }
     
     public void printInfo() {
         System.out.println("Nama data : "+dataName);
         System.out.println("Jumlah attribut : "+numAttr);
         System.out.println("Jumlah data : "+totalData);
         System.out.println("Data set 1 : (2 pertama)");
         for (int i=0;i<2;i++) {
             for (int j=0;j<numAttr;j++) System.out.print("|"+dataSet1[i][j]);
             System.out.println();
         }
         System.out.println("\nData set 2 : (2 pertama)");
         for (int i=0;i<2;i++) {
             for (int j=0;j<numAttr;j++) System.out.print("|"+dataSet2[i][j]);
            System.out.println();
         }
         System.out.println("\nData set 3 : (2 pertama)");
         for (int i=0;i<2;i++) {
             for (int j=0;j<numAttr;j++) System.out.print("|"+dataSet3[i][j]);
            System.out.println();
         }
         System.out.println("\nData set 4 : (2 pertama)");
         for (int i=0;i<2;i++) {
             for (int j=0;j<numAttr;j++) System.out.print("|"+dataSet4[i][j]);
            System.out.println();
         }
         System.out.println("\nData set 5 : (2 pertama)");
         for (int i=0;i<2;i++) {
             for (int j=0;j<numAttr;j++) System.out.print("|"+dataSet5[i][j]);
            System.out.println();
         }
         System.out.println("\nData set 6 : (2 pertama)");
         for (int i=0;i<2;i++) {
             for (int j=0;j<numAttr;j++) System.out.print("|"+dataSet6[i][j]);
            System.out.println();
         }
         System.out.println("\nData set 7 : (2 pertama)");
         for (int i=0;i<2;i++) {
             for (int j=0;j<numAttr;j++) System.out.print("|"+dataSet7[i][j]);
            System.out.println();
         }
         System.out.println("\nData set 8 : (2 pertama)");
         for (int i=0;i<2;i++) {
             for (int j=0;j<numAttr;j++) System.out.print("|"+dataSet8[i][j]);
            System.out.println();
         }
         System.out.println("\nData set 9 : (2 pertama)");
         for (int i=0;i<2;i++) {
             for (int j=0;j<numAttr;j++) System.out.print("|"+dataSet9[i][j]);
            System.out.println();
         }
         System.out.println("\nData set 10 : (2 pertama)");
         for (int i=0;i<2;i++) {
             for (int j=0;j<numAttr;j++) System.out.print("|"+dataSet10[i][j]);
            System.out.println();
         }
     }
     
     public String[][] generateDataTraining(int i) {
         String ret[][] = new String[5][5];
         
         if (i==10) ret = new String[numDataSetNormal*9][numAttr] ;
         else ret = new String[(numDataSetNormal*8)+numDataSetOver][numAttr];
         for (int q=1;q<10;q++)totalDataTraining[q] = (numDataSetNormal*8)+numDataSetOver;
         totalDataTraining[10] = numDataSetNormal*9;
         if (i==10) {
             int start = 0;
             int end = numDataSetNormal*8 ;
             int j=0;
             for (int k=start;k<end;k++) {
                 ret[j] = data[k];
                 j++;
             }
         }
         else if (i==1) {
             int start = numDataSetNormal ;
             int end = totalData ;
             int j=0;
             for (int k=start;k<end;k++) {
                 ret[j] = data[k];
                 j++;
             }
         }
         else {
             int start1= 0,start2 = (numDataSetNormal*i) ;
             int end1=(numDataSetNormal*(i-1)),end2=totalData ;
             int j=0;
             for (int k=start1;k<end1;k++) {
                 ret[j] = data[k];
                 j++;
             }
             for (int k=start2;k<end2;k++) {
                 ret[j]=data[k];
                 j++;
             }
         }         
         return ret ;
     }
}
