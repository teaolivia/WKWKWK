/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Code;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.lang.Number;
/**
 *
 * @author tama
 */
public class Data {
    public String[][] data ;
    public int[] totalDataperSet ; 
    public int[] totalDataTraining; 
    public int numAttr ;//numAttr = jumlah atribut
    public int totalData; //totalData = jumlah data
    public int numDataSetNormal ;
    public int[] isNumericValue ;
    public double[] meanTable ;
    public double[] varTable ;
    public String fileName;
    public String dataName;
    public ArrayList<String> listAttr =new ArrayList<String>(); //List menyimpan nama nama atribut
    public ArrayList<String> variasiClass = new ArrayList<String>(); //List menyimpan nilai klasifikasi
    public Map<String,Integer> distribusiClass = new HashMap<String,Integer>() ; //Tabel menyimpan jumlah klasifikasi tiap kelas
    public Map<String,Integer> distinctAttr = new HashMap<String,Integer>(); //Menyimpan jumlah nilai unik untuk satu attribute
    public Map<String,Integer> attrInfo = new HashMap<String,Integer>(); //Menyimpan info jumlah atribut untuk setiap nilai atribut, misalnya doors,4,vgood = 5 
    public Map<String,Double> attrInfoD = new HashMap<String,Double>(); //Menyimpan info jumlah atribut untuk setiap nilai atribut, misalnya doors,4,vgood = 5 
    public Map<String,ArrayList<String>> attrListVal = new HashMap<String,ArrayList<String>>();
    /*
        Menyimpan seluruh value untuk attribute. Contoh ;
        buying : <yes,maybe yes, maybe no,no>
    */
    
    Data() {}
     
    public Data(String files) {
         fileName = files;
         totalDataperSet = new int[11];
         totalDataTraining = new int[11];
         try {
            File file = new File(files);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            do {
                line = bufferedReader.readLine();
                
            } while (!line.contains("Number of Instances"));
            String[] bb=line.split(" ");
            totalData = Integer.valueOf(bb[bb.length-1]);
             do {
                line = bufferedReader.readLine();
                
            } while (!line.contains("@relation"));
            //Ketika sudah menemukan yang berawalan @relation, maka pembacaan di lanjutkan 2x
            String[] aa=line.split(" ");
            dataName = aa[1];
            do {
                line = bufferedReader.readLine();  
            } while (!line.contains("@attribute"));
            
            do {
                String[] attr = line.split(" ");
                listAttr.add(attr[1]);
                //System.out.println(attr[1]);
                line = bufferedReader.readLine();
            } while (line.contains("@attribute"));
            
            if (!line.contains("@data")) {
                while (!line.contains("@data")) {               
                    line = bufferedReader.readLine();
                }
//               System.out.println(line);
            }
            //Keluar dari proses di atas, pembacaan berada di line kosong
            numAttr = listAttr.size(); //jumlah attributr = new int[numAttr];
            //System.out.println(line);
            if (line.length() < numAttr) {
              do {
                line = bufferedReader.readLine();  
              } while (line.length()<numAttr);
            }
            
            /*int count=0;
            //menghitung jumlah data
            String ss = line ;
            do {
                count++;
                ss = bufferedReader.readLine();
                System.out.println(count);
            } while (ss.length()>6 && ss!=null);
            
            totalData = count;*/
            data = new String[totalData+1][numAttr];
            fileReader.close();
        } catch (Exception e) {
            e.printStackTrace();
	}
        //System.out.println(numAttr+"|"+totalData);
        try {
            File file = new File(files);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            do {
                line = bufferedReader.readLine();            
            } while (!line.contains("@data"));
            int j=0;
           
            //System.out.println("=="+line);
            //memasukkan data
            do {
               line=bufferedReader.readLine();
            } while (line.length()<(numAttr*4));
            while (j<totalData) {
                //System.out.println((j+1)+" . "+line);
                String[] attr = line.split(",");
                //System.out.println("=> "+attr.length+"|"+line);
                String s = attr[numAttr-1];
                
                if (!variasiClass.contains(s)) {
                    variasiClass.add(s);
                    distribusiClass.put(s,1);
                }
                else distribusiClass.put(s,distribusiClass.get(s)+1);
               
                for (int i=0;i<numAttr;i++) {
                    ArrayList<String> art1 = new ArrayList<String>(); 
                    String att = listAttr.get(i),val = attr[i],cls = attr[numAttr-1];   
                    //System.out.println(att+"|"+val+"|"+cls);
                    String ss = att+","+val+","+cls;
                    String ss2  = att+","+val;
                    if (attrInfo.containsKey(ss)) {
                        int cur = attrInfo.get(ss);
                        attrInfo.put(ss,cur+1);
                    } else  attrInfo.put(ss,1);
                     if (attrInfo.containsKey(ss2)) {
                        int cur = attrInfo.get(ss2);
                        attrInfo.put(ss2,cur+1);
                    } else  attrInfo.put(ss2,1);
                    if (attrListVal.containsKey(att)) {
                        art1 = (ArrayList<String>)attrListVal.get(att);
                        if (!art1.contains(val)) {
                            art1.add(val);
                           attrListVal.put(att, art1);
                        } 
                    } else {
                        art1.add(val);
                        attrListVal.put(att, art1);
                    }
                    data[j][i]=attr[i];
                }
                line=bufferedReader.readLine();
                j++;
            }
            
            fileReader.close();
           // System.out.println("Contents of file:");
        } catch (Exception e) {
            e.printStackTrace();
	}
     }
     
       public static double round(double value,int places) {
        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
     }
     
       public void checkNumericAttr() {
           boolean y = false; 
           isNumericValue = new int[numAttr];
           for (int i=0 ; i<listAttr.size();i++) {
               String a = listAttr.get(i);
               ArrayList<String> t = attrListVal.get(a);
               boolean x=true ;
               int j=0;
               while (x&&j<t.size()) {
                   String a2 = t.get(j);
                   x =  a2.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
                   j++;
               }
               if (x&&distinctAttr.get(a)>10) {
                   isNumericValue[i] = 1;
                   y=true;
               }
               else {
                   isNumericValue[i] = 0;  
               }
           }
       }
     
     public void doNumericAttr() {
         checkNumericAttr();
         countMeanAttr();
         countVarAttr();
     }
     
     public void countMeanAttr() {
        
                
        Map<String,Double> tempM = new HashMap<String,Double>();
         Map<String,Double> tempC = new HashMap<String,Double>();
         for (int x=0;x<listAttr.size();x++) {
            if (isNumericValue[x]==1) {
                double sum=0.0 ;
                //double sum2[]=new double[variasiClass.size()];
                int countt[] = new int[variasiClass.size()];
                tempM.clear();
                tempC.clear();
                for (int i=0;i<numAttr;i++) meanTable[i]=0.0;
                for (int i=0;i<totalData;i++) {
                    double temp = Double.valueOf(data[i][x]);
                    //String c = variasiClass.get(i);
                    String c = data[i][numAttr-1];
                    if (tempM.containsKey(c)) {
                        tempM.put(c,tempM.get(c)+temp);
                        tempC.put(c,tempC.get(c)+1);
                    } else {
                        tempM.put(c,temp);
                        tempC.put(c,1.0);
                    }
                    sum+=temp;
                }
                double mean = (double)sum/totalData ;
                String ca = listAttr.get(x);
                this.meanTable[x]=mean;
                String ss2 = listAttr.get(x)+",mean";
                attrInfoD.put(ss2,mean);
                //System.out.println("m ->"+ss2+"|"+mean);
                //System.out.println(ca+"|"+sum+"|"+totalData+"|"+meanTable[x]);
                for (int i=0;i<variasiClass.size();i++) {
                    String c = variasiClass.get(i);
                    int tc = distribusiClass.get(c);
                    String ss = listAttr.get(x)+",mean,"+c;
                    double meant = (double)tempM.get(c)/(tc+1) ;
                    //System.out.println(meant+"|"+ss+"|tc : "+tempC.get(c)+"|tm : "+tempM.get(c));
                    //System.out.println("m ->"+ss+"|"+meant+"|"+tc+"|"+tempM.get(c));
                    attrInfoD.put(ss,meant);
                }
            }
         }        
     }
     
     public void countVarAttr() {
            Map<String,Double> tempM = new HashMap<String,Double>();
         for (int x=0;x<listAttr.size();x++) {
            if (isNumericValue[x]==1) {
                double sum=0.0 ;
                double sum2[]=new double[variasiClass.size()];
                for (int i=0;i<totalData;i++) {
                    double temp = Double.valueOf(data[i][x]);
                    String ss3 = listAttr.get(x)+",mean";
                    //System.out.print("ss3 _>"+ss3);
                    double mean1 = attrInfoD.get(ss3);
                   // System.out.println(" | Mean1 / "+mean1);
                    String cc = data[i][numAttr-1];
                    String ss = listAttr.get(x)+",mean,"+cc;
                   // System.out.println("s->"+ss);
                    double mean2 = attrInfoD.get(ss);
                    String c = data[i][numAttr-1];
                    if (tempM.containsKey(c)) {
                        double cur =tempM.get(c);
                        double add = (temp-mean2)*(temp-mean2);
                        tempM.put(c,cur+add);
                    } else {
                        double add = (temp-mean2) * (temp-mean2);
                        tempM.put(c,add);
                    }
                    sum+=((temp-mean1)*(temp-mean1));
                }
                double var = (double)sum/(totalData-1) ;
                varTable[x]=var;
                String ss2 = listAttr.get(x)+",var";
                var = Math.sqrt(var);
                 //System.out.println("v ->"+ss2+"|"+var);
                attrInfoD.put(ss2,var);
                for (int i=0;i<variasiClass.size();i++) {
                    String c = variasiClass.get(i);
                    int tc = distribusiClass.get(c);
                    String ss = listAttr.get(x)+",var,"+c;
                   // System.out.println(tempM.get(c)+"|"+tc);
                    double vart = (double)tempM.get(c)/(tc-1) ;
                     vart = Math.sqrt(vart);                    
                    
                   // System.out.println("vv ->"+ss+"|"+vart);
                    
                    attrInfoD.put(ss,vart);
                }
            }
         }
     }
     
     
     public String infoNumericAttr() {
         String ret=""; 
         for (int i=0;i<listAttr.size();i++) {
             ret+=listAttr.get(i);
             if (isNumericValue[i]==0) ret+="\t Not numeric\n";
             else ret+="\t Numeric\n";
         }
         return ret;
     }
       
     public void makeDataSet() {
               varTable = new double[numAttr];
               meanTable = new double[numAttr];
         int perData = totalData / 10 ;
         int sisa = totalData % 10 ;
         totalDataperSet = new int[11];
         totalDataTraining = new int[11];         
        countDistinct();
         for (int i=1;i<=10;i++) {
             if (i<=sisa) totalDataperSet[i] = perData+1;
             else totalDataperSet[i] = perData ;
         }
         doNumericAttr();
         //for (int i=0;i<listAttr.size();i++)System.out.println("->"+meanTable[i]+"|"+varTable[i]);
     }
     
     public void printData(int x) {
         for (int i=0;i<x;i++) {
             for (int j=0;j<numAttr;j++) {
                 System.out.print(data[i][j]+",");
             }
             System.out.println();
         }
     }
     
     public  String[][] generateDataSet(int i) {
         int td = totalDataperSet[i] ;
        // System.out.println(i+". td = "+td+" (Generate Data Set)");
         String ret[][] = new String[td][numAttr];
         if (i==1) {
             int start = 0;
             int end = totalDataperSet[i] ;
          //   System.out.println("Start : "+start+" | end : "+end);
             int j=0;
             for (int k=start;k<end;k++) {
                 ret[j]=data[k] ;
                 j++;
             }
         }
         else if (i==10) {
             int start = totalData - totalDataperSet[i] ;
             int end = totalData ;
            // System.out.println("Start : "+start+" | end : "+end);
             int j = 0;
             for (int k=start;k<end;k++) {
                 ret[j]=data[k] ;
                 j++;
             }
         }
         else {
             int start = 0;
             for (int k=0;k<i;k++) {
                 start+=totalDataperSet[k];
             }
             int end = start+totalDataperSet[i];
             int j=0;
             //System.out.println("Start : "+start+" | end : "+end);
             for (int k=start;k<end;k++) {
                 ret[j] = data[k];
                 j++;
             }             
         }
         return ret ;
     }
     
     public String printInfo(String model) {
         
         
         System.out.println("=========");
         String ret = "";
         if (model.contains("bayes")) ret+="- Schema = Naive Bayes\n";
         else {
             String aa[]=model.split(",");
             ret+="- Schema = "+aa[aa.length-1]+"-NN\n";
         }
         if (model.contains("full")) ret+="- Test Mode = Full Training \n";
         else ret+="- Test Mode : 10 Cross Fold \n";
         ret+="- Nama data : "+dataName+"\n";
         ret+="- Jumlah attribut : "+numAttr+"\n";
         for (int i=0;i<listAttr.size();i++) {
             ret+="\t- "+listAttr.get(i)+"\n";
         }
         if (model.contains("bayes")) {
         ret+="=== Classifier Model ===\n";
         for (int i=0;i<listAttr.size()-1;i++) {
             String a = listAttr.get(i);
             double u = (double) distinctAttr.get(a)*100/totalData ;
             u = round(u,2);
             String s = "Nilai beda ("+distinctAttr.get(a)+") Keunikan ("+u+"%)";
             ret+="    "+(i+1)+". "+a;
             
             if (isNumericValue[i]!=1) {
                 ret+="\t -> Not numeric\n       "+s+"\n      \t        ";
                for (int j=0;j<variasiClass.size();j++) {
                    String ts = variasiClass.get(j);
                    if (ts.length()>6) ts = ts.substring(0,5)+"..";
                    ret+=ts+"\t";
                }
                ret+="Total\n";
                ArrayList<String> tVal = attrListVal.get(a);
                for (int j=0;j<tVal.size();j++) {
                    String s2 = tVal.get(j);
                    String s3 = a+","+s2;
                    String ts = s2 ;
                    if (ts.length()>6) ts = ts.substring(0,5)+"..";
                    int v1 = attrInfo.get(s3);
                    ret+="       "+ts+"\t";                
                    for (int k=0;k<variasiClass.size();k++) {
                       String s4 = a+","+s2+","+variasiClass.get(k);
                       //System.out.println(s4);
                       int v2=0;
                       if (attrInfo.containsKey(s4))  v2 = attrInfo.get(s4);
                       ret+=v2+"\t";
                    }
                    ret+=v1+"\n";
                }
                ret+="\n";
             } else {
                  ret+="\t -> Numeric\n       "+s+"\n      \t        ";
                for (int j=0;j<variasiClass.size();j++) {
                    String ts = variasiClass.get(j);
                    if (ts.length()>6) ts = ts.substring(0,5)+"..";
                    ret+=ts+"\t";
                }
                 ret+="Keseluruhan\n";
                 ret+="\tRata2\t:";
                 for (int l=0;l<variasiClass.size();l++) {
                     String aa =  a+",mean,"+variasiClass.get(l);
                     double m_ = attrInfoD.get(aa);
                     m_ = round(m_,2);
                     ret+=m_+"\t";
                 }
                 String mm = a+",mean";
                 ret+=attrInfoD.get(mm)+"\n";
                 ret+="\n\tVarian\t:";
                 for (int l=0;l<variasiClass.size();l++) {
                     String aa =  a+",var,"+variasiClass.get(l);
                     double m_ = attrInfoD.get(aa);
                     m_ = round(m_,2);
                     ret+=m_+"\t";
                 }
                  String mm2 = a+",var";
                 ret+=attrInfoD.get(mm2)+"\n";
             }
         }
         ret+="\t Klasifikasi : \n";
         for (int i=0;i<variasiClass.size();i++) {
             String c = variasiClass.get(i);
             int aa = distribusiClass.get(c);
             double dd = (double) aa*100/totalData ;
             dd = round(dd,2);
             if (c.length()>6)c = c.substring(0,5)+"..";
             ret+="\t"+c+"\t";
             ret+=aa+"\t"+dd+"%\n";
         }
         }
         ret+="- Jumlah data : "+totalData+"\n";
       
         return ret ; 
     }
     
     public int[][] getDetailAttr(String s) {
         int[][] ret ;
         ArrayList<String> ar = new ArrayList<String>();
         ret = new int[distinctAttr.get(s)][variasiClass.size()];
         for (Map.Entry<String,Integer> entry : attrInfo.entrySet()) {
           ar.clear();
           String key = entry.getKey();
           int val = entry.getValue();
           String temp[] = key.split(",");
           
           if (temp[0].equals(s)) {
            //   System.out.println(key);            
            int i=0;
            if (ar.contains(temp[1])) i = ar.indexOf(temp[1]);
            else {
                ar.add(temp[1]);
                i = ar.size()-1;
            }
            //System.out.println(temp[2]);
            int j = variasiClass.indexOf(temp[2]);
            //System.out.println(i+","+j);
            HashMap<ArrayList<String>,ArrayList<Integer>> az = new HashMap<ArrayList<String>,ArrayList<Integer>>();
            ret[i][j]+=1;
           }
        }
         return ret;
     }
     
     public String[][] generateDataTraining(int i) {
         String ret[][]=new String[5][5];
         numDataSetNormal = totalData / 10 ;
         for (int q=1;q<=10;q++)
             totalDataTraining[q] = totalData - totalDataperSet[q];
         
         int td = totalDataTraining[i] ;
         //System.out.println(i+". td = "+td+" (Generate Data Training");
         ret = new String[td][numAttr];
         if (i==10) {
             int start = 0;
             int end = totalDataTraining[10];
             //System.out.println("Start : "+start+" | end : "+end);
             
             int j=0;
             for (int k=start;k<end;k++) {
                 ret[j] = data[k];
                 j++;
             }
         }
         else if (i==1) {
             int start = totalDataperSet[i] ;
             int end = totalData ;
             //System.out.println("Start : "+start+" | end : "+end);
             
             int j=0;
             for (int k=start;k<end;k++) {
                 ret[j] = data[k];
                 j++;
             }
         }
         else {
             int start1= 0,start2 = 0 ;
             int end1=0,end2=totalData ;
             int j=0,te = 0;
             for (int k=0;k<i;k++) te=te+totalDataperSet[k];
             end1 = te;
             start2 = te+totalDataperSet[i] ;
             //System.out.println("Start : "+start1+" | end : "+end1);
             
             //System.out.println("Start : "+start2+" | end : "+end2);
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
     
     public void countDistinct() {
         ArrayList<String> temp = new ArrayList<String>();
         
         for (int i=0;i<numAttr;i++) {
             String a = listAttr.get(i);
             temp.clear();
             for (int j=0;j<totalData;j++) {
                 String s = data[j][i] ;
                 if (!temp.contains(s)) temp.add(s);
             }
             distinctAttr.put(a,temp.size());
         }
     }
     
     public void acakData() {
         String temp[] ;
         
         for (int i=0;i<totalData;i++) {
             Random R = new Random();
             int x = R.nextInt(totalData);
             temp = new String[numAttr];
             temp = data[x];
             data[x] = data[i] ;
             data[i] = temp;
         }
     }
     
     public String getInfoAttr(String attr) {
         int dis = this.distinctAttr.get(attr);
         int ix=0;
         boolean b=false ;
         int i=0;
         while (i<this.listAttr.size()&&!b) {
             if (listAttr.get(i).equals(attr)) {
                 b=true;
                 ix=i;
             } i++;
         }
         String ret="";
         ret+=" Info detail attribut "+attr+"\n";
         ret+="- Nilai beda : "+dis+"\n";
         double dd = (double)dis/totalData ;
         dd=dd*100;
         dd = round(dd,2);
         ret+="- Persentase keunikan : "+dd+" %\n";
         ret+="- Tipe data : ";
         if (isNumericValue[ix]!=1) {
             ret+="not numeric \n";
             ret+=" List value attribut : \n";
              ArrayList<String> tVal = attrListVal.get(attr);
                for (int j=0;j<tVal.size();j++) {
                    String s2 = tVal.get(j);
                     String s3 = attr+","+s2;
                    int v1 = attrInfo.get(s3);
                    double d1 = (double) v1*100/totalData;
                    d1 = round(d1,2);
                    ret+="  > "+s2+" : "+v1+"    =>   "+d1+" %\n";
                    for (int k=0;k<variasiClass.size();k++) {
                       String s4 = attr+","+s2+","+variasiClass.get(k);
                       //System.out.println(s4);
                       ret+="    - "+variasiClass.get(k)+" : ";
                       int v2=0;
                       if (attrInfo.containsKey(s4))  v2 = attrInfo.get(s4);
                       ret+=v2+"\n";
                    }
                }
         }
         else {
             ret+="numeric \n";
              String mm = attr+",mean";
              ret+="Rata-rata : "+attrInfoD.get(mm);
              String mm2 = attr+",var";
              ret+="  |  Varianasi : "+attrInfoD.get(mm2)+"\n";
              for (int l=0;l<variasiClass.size();l++) {
                String cl = variasiClass.get(l);
                String aa =  attr+",mean,"+variasiClass.get(l);
                String aa2 =  attr+",var,"+variasiClass.get(l);
                double m_ = attrInfoD.get(aa);
                double m_2 = attrInfoD.get(aa2);
                m_ = round(m_,2);
                m_2 = round(m_2,2);
                if (cl.length()>6) cl=cl.substring(0,5)+"..";
                ret+="- "+cl+" : \tRata-rata : "+m_+" | Variansi : "+m_2+"\n";  
               }
         }
         return ret ;
     }
}
