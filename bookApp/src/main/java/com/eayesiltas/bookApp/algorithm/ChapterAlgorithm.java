package com.eayesiltas.bookApp.algorithm;

import lombok.*;

import java.util.ArrayList;

@Data
public class ChapterAlgorithm {

    private ArrayList<String> lastResult = new ArrayList<String>();
    public ChapterAlgorithm(String text, ArrayList<String> chapters){
        mergeSentences(separateSentences(text,chapters));
    }

    public ArrayList<String> separateSentences(String text, ArrayList<String> chapters){

        int counter = 1;
        String part = "";

        String threePoint = "...";
        String questMarkParan = "(?)";
        String shockMarkParan = "(!)";

        for(int i = 0; i < text.length(); i++){

            part += text.charAt(i);

            if(text.charAt(i) == ' '){
                counter++;
            }

            if(text.charAt(i) == '.' || text.charAt(i) == '?' || text.charAt(i) == '!'){
                chapters.add(part);
                part = "";
            }
        }

        return chapters;
    }

    public void mergeSentences(ArrayList<String> chapters){


        ArrayList<Integer> wordCounters = new ArrayList<Integer>();
        ArrayList<String> results = new ArrayList<String>();
        String part = "";

        for(int i = 0; i<chapters.size(); i++){

            if(i == 0){
                wordCounters.add(1);
            }else{
                wordCounters.add(0);
            }


            for(int j = 0; j<chapters.get(i).length(); j++){

                if(chapters.get(i).charAt(j) == ' '){
                    wordCounters.set(i, wordCounters.get(i) + 1);
                }

            }
        }

        int addition = 0;

        for(int i = 0; i<chapters.size(); i++){

            addition += wordCounters.get(i);

            if(addition <= 50){
                part += chapters.get(i);
            }else{
                results.add(part);
                part = "";
                addition = 0;
                i--;
            }
        }
        results.add(part);

        for(int i = 0; i<results.size(); i++){

            String segment = "";

            if(results.get(i).startsWith(" ")){
                segment = results.get(i).substring(1,results.get(i).length());
                results.set(i,segment);
            }

        }

        lastResult = results;

    }

}
