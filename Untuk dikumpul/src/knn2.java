/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Code;

/**
 *
 * @author tama
 */
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
public class knn2 {
    String name[] ;
    String data[][] ;
    String dataTraining[][];
    String dataUji[][];
    int numDTraining; 
    int numDUji ;
    ArrayList<String> variasiClass=new ArrayList<String>() ; //List yang menyimpan nilai berbeda untuk klasifikasi
    int variasihasil; //  nilai yang menyimpan nilai perbedaan hasil akhir (klasifikasi)
    int variasidata[] ; //array yang menyimpan nilai perbedaan data
    int iname ;//iname = jumlah atribut
    int rdata; //rdata = jumlah data
    int variasiKelas;
    int totalMatch ;
    int match ;
    int matchF[];
    double accF[];
    double accuracy ;
    int numKNN;
    int classResult[][];
    Data D ;
     Map<String,Integer> finalClass = new HashMap<String,Integer>();
    
    knn2() {
        iname = 5;
        rdata = 5;
        name = new String[iname] ;
        data = new String[rdata][iname];
        for (int i=0;i<iname;i++) {
            name[i]="x";
            for (int j=0;j<rdata;j++) data[j][i]="";
        }
    }
     
    
    public knn2(int k,Data D_) {
        this.D = D_;
        this.numKNN = k;
        this.data = D_.data ;
        this.rdata = D_.totalData ;
        this.iname = D_.numAttr;
        this.variasiClass = D_.variasiClass;
        name = new String[iname];
        variasidata = new int[iname];
        this.variasiKelas = D_.variasiClass.size();
        for (int i=0;i<iname;i++) {
               name[i] = D.listAttr.get(i);
         }
        for (int i=0;i<D_.variasiClass.size();i++) {
            // System.out.println(i+"|"+variasiClass.get(i));
             this.finalClass.put(D_.variasiClass.get(i), i);
        }
    }
    
    public void printInfo(){
        System.out.println("===================================");
        System.out.println("======== DATA DATA TERKAIT ========");
        System.out.println("Jumlah atribut : "+iname);
        
        for (int i=0;i<iname;i++)
            System.out.println("  "+(i+1)+". "+name[i]);
        
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
        
        Integer temp[][] =new Integer [numDTraining][2];
        //test KNN for every dataset
        for (int i=0;i<numDTraining;i++){
            temp[i][0] = i;
            temp[i][1] = Integer.valueOf(check(dataUji[cur],dataTraining[i]));
        }
        /*for (int i=0;i<rdata;i++) {
            System.out.println("     ->+"+temp[i][0]+"|"+temp[i][1]+"|"+data[i][iname-1]);
        }*/
        Arrays.sort(temp, new Comparator<Integer[]>()
        {
            @Override
            public int compare(Integer[] int1, Integer[] int2)
            {
                Integer numOfKeys1 = int1[1];
                Integer numOfKeys2 = int2[1];
                return numOfKeys1.compareTo(numOfKeys2);
            }
        });

        //System.out.println("====");
        //for (int i=0;i<10;i++) {
        //    int ix = temp[i][0];
         //   System.out.println("     ->+"+temp[i][0]+"|"+temp[i][1]+"|"+data[ix][iname-1]);
       // }
        // Get the clasification
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        String tempStr;
        for (int i = 0; i < k; i++)
        {
            int idx = temp[i][0];
            tempStr = dataTraining[idx][iname-1];
            //System.out.println("    -"+temp[i][iname-1]+" : "+temp[i][iname]);
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
        //System.out.println("Res: " + res+"\n");
        
        return res;
    }
    
    public void doTraining(String[][] dataTraining_,int totalDTrain,String[][] dataUji_,int totalDUji){
        int same=0;
        this.match = 0;
        this.accuracy = 0;
        this.dataTraining=dataTraining_ ;
        this.numDTraining = totalDTrain ;
        this.dataUji = dataUji_ ;
        this.numDUji = totalDUji ;
        double acc;
            
       for(int i=0;i<numDUji;i++){
           
           String class_ = doKNN(i,numKNN);
           //System.out.println(data[i][iname-1]+" : "+match[i]+"\n");
           if (dataUji[i][iname-1].equals(class_)) same++;
           int aa = finalClass.get(class_);
           int bb = finalClass.get(dataUji[i][iname-1]);
           classResult[aa][bb]+=1;
        }
       this.match = same ;
       acc = ((double)same / this.numDUji)*100;
       this.accuracy = acc;
        // System.out.println("Accuracy: "+ String.format( "%.2f", acc) + "%");
    }
    
    public String schemaFullTraining() {
        String ret="";
        ret+="=== "+numKNN+"-NN Full Training ===\n";
        int tc= this.D.variasiClass.size();
        classResult = new int[tc][tc];
        doTraining(D.data,rdata,D.data,rdata);
        ret+="=> Jumlah data yang match : "+match+"\n";
        ret+="=> Jumlah yang data tidak match : "+(numDUji-match)+"\n";
        accuracy = ((double) match/numDUji)*100 ;
        accuracy = round(accuracy,2);
        ret+="=> Akurasi : "+accuracy+"%\n";
        return ret ;
    }
    
     public static double round(double value,int places) {
        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
     }
    public String schema10Fold(Data D_) {
        String ret="";
        classResult = new int[variasiKelas][variasiKelas];
        accF = new double[11];
        matchF = new int[11];
         ret+=" ========== 10 Cross Fold =========== \n";
         D_.acakData();
         for (int i=1;i<=10;i++) {
              doTraining(D_.generateDataTraining(i),D_.totalDataTraining[i],D_.generateDataSet(i),D_.totalDataperSet[i]);
              matchF[i] = this.match ;
              accF[i] = this.accuracy; 
              System.out.println(i+"match : "+matchF[i]+"| acc: "+accF[i]+"|dT : "+this.numDTraining+"|du :"+this.numDUji);
       }
         double sum=0;
       for (int i=1;i<=10;i++) {
             ret+="Accuracy uji ke -"+i+" : "+accF[i]+"%\n";
             sum+=accF[i];
         }
         double acc = (double)sum/10;
         double dT = (double) acc/100;
         double dM = (double)dT*D.totalData ;
         dM=round(dM,0);
         double nM = D.totalData - dM;
         ret+="\n=> Jumlah data yang match : "+dM+"\n";
         ret+="=> Jumlah data yang tidak match : "+nM+"\n";
         ret+="Accuracy rata rata : "+acc+"%\n\n";
        return ret ;
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
     
    
}