/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ai;

import java.util.Arrays;
import java.util.Comparator;

/**
 *
 * @author LUCKY
 */
public class kNN {
    private int match;
    private int totalData;
    private int row;
    private int collumn;
    private double accuracy;
    private int[][] sametemp;
    
    
    public kNN(Data D) {
        this.totalData = D.totalData;
        row = D.data.length;
        collumn = D.data[1].length;
        sametemp = new int [row][2];
    }
    
    public int compareSet(String[] dataset ,String datates[]){
        int same = 0;
        for (int i = 0; i < dataset.length-1 ; i++ ){
            if(dataset[i].equals(datates[i])) same++;
        }
        return same;
    }
    
    public void initsametemp(int row){
       for(int i = 0 ; i < row ; i++){
            sametemp[i][0]=i;
            sametemp[i][1]=0;
       }
    }
    public void doClassification(Data D,int k){
        int start = 0 ;
        initsametemp(row);
        for (int i = 0 ; i < D.dataSet1.length;i++){            
            for (int j = 0 ; j < D.totalData ; j++){
                if(j>=start && j <=start+D.dataSet1.length ){

                }else{
                    sametemp[j][1] = compareSet(D.data[j],D.dataSet1[i]);
                }
            }
        Arrays.sort(sametemp, new Comparator<int[]>() {
            @Override
            public int compare(int[] int1, int[] int2)
            {
                Integer number1 = int1[1];
                Integer number2 = int2[1];
                return number2.compareTo(number1);
            }    
        });
        for(int a=0;a<row;a++){
            for(int b = 0;b <2;b++){
                System.out.print(sametemp[a][b]);            
            }
            System.out.println();
        }
        }
    }
}
