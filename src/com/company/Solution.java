package com.company;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public class Solution {
    private static final String fileName = "C:\\Users\\kirti\\IdeaProjects\\sber_hw\\src\\com\\company\\input.txt";
    private static String inputData = "";

    static {
        try (BufferedReader reader = new BufferedReader(new FileReader(new File(fileName)))) {
            inputData = reader.readLine();
        } catch (IOException e) {
            System.out.println("ошибка");
        }
    }

    public static void main(String[] args) {
        List<String> infoList = Arrays.asList(parseInput(inputData));

    }

    private static String[] parseInput(String inputData) {
        inputData = inputData.replaceAll("\\s+|\\{|}","");
        inputData = inputData.substring(1, inputData.length()-1);
        return inputData.split("\"(,\")*");
    }

    private static int countTotalCost(List<String> list) {

        return 0;
    }

}
