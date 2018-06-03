/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.afrikannerd.core;


import java.util.ArrayList;


/**
 *
 * @author afrikannerd <afrikannerd@gmail.com>
 */
public class Nerdsort {
    int k;
    static Object  nerd(int [] data)
    { 
       ArrayList<Integer> sorted = new ArrayList<>(data.length);
       int sortIndex = 1;
       for(int i = 0; i < data.length - 1; i++)
       {
           if ( i == data.length - 1)break;
           if( data[i] < data[i+1] || data[i] == data[i+1])
           {
               sorted.add(i, data[i]);
               sorted.add(i+1, data[i+1]);
               
           }
           else if( data[i] > data[i+1])
           {
               sorted.add(i, data[i+1]);
               sorted.add(i+1, data[i]);
           
           sortIndex++;
           if(sortIndex <= 2)continue;
           for(int x = 0;x < sortIndex - 1; x++)
           {
               if( sorted.get(x) < data[i] && sorted.get(x+1)  > data[i])
               {
                   sorted.add(i+1, data[i]);
               }
           }
           }
       }
        return (ArrayList)sorted;
    }
    public  Nerdsort outer(int x)
    {
       k = x;
        return this;
    }
    public void inner()
    {
        System.out.println(k);
        
    }
    public static void main(String [] args)
    {
        new Nerdsort().outer(0).inner();
        
        
//        String s1 = "lalalamama";
//        String s2 = "lalalagsss";
//        char [] st = s1.toCharArray();
//        char [] st2  = s2.toCharArray();
//        int k = 0;
//        for (int x = 0; x < 5;x++)
//        {
//            if(st[x] == st2[x])k++;
//        }
//        if (k == 5)
//            System.out.println("Strings are a match");
//        else
//            System.out.println("No match");
    }
}
