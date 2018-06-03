/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.afrikannerd.core;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author afrikannerd <afrikannerd@gmail.com>
 */
public class Sorting {
    
     // A function to implement bubble sort
    static void bubbleSort(int arr[], int n)
    {
        // Base case
        if (n == 1)
            return;
      
        // One pass of bubble sort. After
        // this pass, the largest element
        // is moved (or bubbled) to end.
        for (int i=0; i<n-1; i++)
            if (arr[i] > arr[i+1])
            {
                // swap arr[i], arr[i+1]
                int temp = arr[i];
                arr[i] = arr[i+1];
                arr[i+1] = temp;
            }
      
        // Largest element is fixed,
        // recur for remaining array
        bubbleSort(arr, n-1);
    }
     private static Double Exponential(double x){ return Math.pow(x,12)+Math.pow(x,8)+Math.pow(x,5)+Math.pow(x,2);}
    // Driver Method
    public static void main(String[] args) 
    {
        System.out.println(Exponential(3.99999999999999999999999999999));
        
            int arr[] = {64, 34, 25, 12, 22, 11, 90, 1,0,345,6434,3};
            
            bubbleSort(arr, arr.length);
            
            System.out.println("Sorted array : ");
            System.out.println(Arrays.toString(arr));
         
    }
}
