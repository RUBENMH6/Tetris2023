/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.ieselcaminas.tetris;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Scores {
    
    public static final int NUM_HIGH_SCORES = 5;
    private List<Score>[] lists;
    
    public Scores() {
        lists = new ArrayList[3];
        for (int i = 0; i < lists.length ; i++ ) {
            lists[i] = new ArrayList<>();
        }
    }
    
    public void addScore(Score score){
        int level = socre.getLevel();
        List<Score> list = lists[level];
        if (list.size() > NUM_HIGH_SCORES) {
           if (score.getScore() > getMinScore(level)) {
               list.add(score);
               Collections.sort(list);
               list.remove(list.size() - 1); 
           }
        } else {
            list.add(score); 
        }
        writeListsToFile();
    }
    
    public void writeListsToFile()  {
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream( new FileOutputStream("scores.dat") );
            for (List<Score> list : lists) {
                out.writeObject(list);  
            }
        } catch (IOException ex) {
            
        } finally {
            if (out != null) {
                try {
                  out.close();  
                } catch (IOException ex) {
                    ex.printStackTrace();
                }  
            }       
        } 
    }
    
    public void readListFromFile() {
        ObjectInputStream in = null;
        try {
            in = new ObjectInputStream( new FileInputStream("scores.dat") );
            int i = 0;
            while (true) {
                lists[i] = (List<Score>) in.readObject();
                i++;
            }
        } catch (EOFException ex) {
        
        } catch (IOException ex) {
            
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            if (in != null) {
                try {
                  in.close();  
                } catch (IOException ex) {
                    ex.printStackTrace();
                }  
            }       
        } 
        
    }
    
    private int getMinScore(int level) {
        int min = Integer.MAX_VALUE;
        for (Score s : lists[level]) {
            if (s.getScore() <= min) {
                min = s.getScore();
            }
        }
        return min;
    }
}
