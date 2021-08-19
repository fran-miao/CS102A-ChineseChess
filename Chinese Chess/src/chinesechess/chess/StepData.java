package xyz.chengzi.cs102a.chinesechess.chess;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class StepData {
    public static ArrayList<NearestSteps> nearestSteps = new ArrayList<>();
    public static ArrayList<NearestSteps> eatenChess = new ArrayList<>();

//    public static void storageOfStepData() throws IOException {
//        File file = new File("C:\\Users\\G3\\Desktop\\Project Tchess\\Tchess5\\stepstorageTest\\storage.txt");
//        FileWriter fileWriter = new FileWriter(file.getPath(), true);
//        BufferedWriter bufferedReader = new BufferedWriter(fileWriter);
////      bufferedReader.write((nearestSteps.get(nearestSteps.size() - 1)).getChessSteps()[0]+(nearestSteps.get(nearestSteps.size() - 1)).getChessSteps()[1]);
//        System.out.printf("this is stepData\"%d %d\n\"",nearestSteps.get(nearestSteps.size()-1).getChessSteps()[0],nearestSteps.get(nearestSteps.size() - 1).getChessSteps()[1]);
//        bufferedReader.close();
//        fileWriter.close();
//    }


}
