package com.company;

import java.io.*;
import java.util.*;

public class Solution {
    private static final String fileName = "C:\\Users\\kirti\\IdeaProjects\\sber_hw\\src\\com\\company\\input.txt";
    private static String inputData = "";

    private static int sumCost100 = 0;
    private static int sumCost200 = 0;
    private static int sumCost300 = 0;
    private static int sumCost400 = 0;

    static {
        try (BufferedReader reader = new BufferedReader(new FileReader(new File(fileName)))) {
            inputData = reader.readLine();
        } catch (IOException e) {
            System.out.println("ошибка");
        }
    }

    public static void main(String[] args) {
        parseInput(inputData);
        List<Integer[]> infoList = parseInput(inputData);
        countTotalCost(infoList);

        //infoList = sortArrayDesc(infoList, 4);
        //printArray(infoList);

        printCertainTypeEl(infoList, 100, 0);
    }

    private static List<Integer[]> parseInput(String inputData) {
        inputData = inputData.replaceAll("\\s+|\\{|}","");
        inputData = inputData.substring(1, inputData.length()-1);
        List<String> inputList = Arrays.asList(inputData.split("\"(,\")*"));
        List<Integer[]> elemList = new ArrayList<>();
        Integer[] temp = new Integer[4];
        String parsedEl;
        for (String s : inputList) {
            // тип авто
            temp[0] = Integer.parseInt(s.substring(1, 4));
            // гос номер
            temp[1] = Integer.parseInt(s.substring(5, s.indexOf("-")));
            // пробег
            parsedEl = s.substring(s.indexOf("-") + 1);
            if (parsedEl.contains("-"))
                parsedEl = parsedEl.substring(0, parsedEl.indexOf("-"));
            temp[2] = Integer.parseInt(parsedEl);
            // доп параметр
            parsedEl = s.substring(s.indexOf("-") + 1);
            if (parsedEl.contains("-"))
                temp[3] = Integer.parseInt(s.substring(s.lastIndexOf("-") + 1));
            else temp[3] = -1;
            elemList.add(new Integer[] {temp[0], temp[1], temp[2], temp[3]});
        }
        return elemList;
    }

    private static void countTotalCost(List<Integer[]> list) {
        for (Integer[] el : list) {
            if (el[0] == 100)
                sumCost100 += getServiceCost(el);
            else if (el[0] == 200)
                sumCost200 += getServiceCost(el);
            else if (el[0] == 300)
                sumCost300 += getServiceCost(el);
            else
                sumCost400 += getServiceCost(el);
        }
        System.out.printf("Car code 100: %d\n", sumCost100);
        System.out.printf("Car code 200: %d\n", sumCost200);
        System.out.printf("Car code 300: %d\n", sumCost300);
        System.out.printf("Car code 400: %d\n", sumCost400);
        System.out.println("---------------------");
        System.out.printf("Total price: %d\n", sumCost100+sumCost200+sumCost300+sumCost400);
    }

    // вывод на экран всего массива
    private static void printArray(List<Integer[]> list) {
        for (Integer[] el : list) {
            // C(CODE_CAR)_гос номер-Пробег-(доп. параметр)
            System.out.printf("Тип машины: %d; Гос. номер: %d; Пробег: %d", el[0], el[1], el[2]);
            if (el[3] != -1)
                System.out.printf("; Доп. параметр: %d.\n", el[3]);
            else System.out.println(".");
        }
    }

    // сортировка массива по позрастанию
    // 1 - по типу
    // 2 - по госномеру
    // 3 - по пробегу
    // 4 - по доп. параметру
    // 5 - по стоимости обслуживания
    private static List<Integer[]> sortArrayAsc(List<Integer[]> list, int sortMode) {
        list.sort(new Comparator<Integer[]>() {
            @Override
            public int compare(Integer[] o1, Integer[] o2) {
                // сортировка по типу
                if (sortMode == 1) {
                    return o1[0].compareTo(o2[0]);
                }
                // сортировка по госномеру
                else if (sortMode == 2) {
                    return o1[1].compareTo(o2[1]);
                }
                else if (sortMode == 3) {
                    return o1[2].compareTo(o2[2]);
                }
                else if (sortMode == 4) {
                    if (o1[3].equals(o2[3])) return 0;
                    else if (o2[3] == -1 || o1[3] > o2[3]) return 1;
                    else return -1;
                }
                else {
                    return Double.compare(getServiceCost(o1), getServiceCost(o2));
                }
            }
        });
        return list;
    }

    // по убыванию
    private static List<Integer[]> sortArrayDesc(List<Integer[]> list, int sortMode) {
        list.sort(new Comparator<Integer[]>() {
            @Override
            public int compare(Integer[] o2, Integer[] o1) {
                // сортировка по типу
                if (sortMode == 1) {
                    return o1[0].compareTo(o2[0]);
                }
                // сортировка по госномеру
                else if (sortMode == 2) {
                    return o1[1].compareTo(o2[1]);
                }
                else if (sortMode == 3) {
                    return o1[2].compareTo(o2[2]);
                }
                else if (sortMode == 4) {
                    if (o1[3].equals(o2[3])) return 0;
                    else if (o2[3] == -1 || o1[3] > o2[3]) return 1;
                    else return -1;
                }
                else {
                    return Double.compare(getServiceCost(o1), getServiceCost(o2));
                }
            }
        });
        return list;
    }

    private static double getServiceCost(Integer[] el) {
        return switch (el[0]) {
            case 100 -> el[2] * 46.10 * 12.5 / 100;
            case 200 -> el[2] * 48.90 * 12 / 100;
            case 300 -> el[2] * 47.50 * 11.5 / 100;
            default -> el[2] * 48.90 * 20 / 100;
        };
    }

    // реализовать функции, которые в разрезе каждого типа авто выводят информацию о каждом авто
    // (тип, номер, пробег, доп. параметр), с сортировкой по пробегу и доп параметру
    // sortType = 0 - пробег
    // sortType = 1 - доп. параметр
    private static void printCertainTypeEl(List<Integer[]> list, int elType, int sortType) {
        list.removeIf(el -> el[0] != elType);
        if (sortType == 0)
            sortArrayAsc(list, 3);
        else if (sortType == 1)
            sortArrayAsc(list, 4);
        printArray(list);
    }

}
