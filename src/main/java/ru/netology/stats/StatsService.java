package ru.netology.stats;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class StatsService {

    public int sum(int[] data) {
        int sum = 0;
        for (int d : data) {
            sum = sum + d;
        }
        return sum;
    }

    public double average(int[] data) {
        double average = 0.0;
        int sum = sum(data);    //сумму считаем в готовой функции
        average = 1.0 * sum / (data.length);    //среднее считаем с точкой
        average = BigDecimal.valueOf(average).setScale(2, RoundingMode.HALF_UP).doubleValue();  //округляем до 2х цифр после запятой для красоты
        return average;
    }

    public int monthOfMax(int[] data) {
        int max = 0;
        for (int i = 1; i < data.length; i++) {   //начинаем с 1, т.к. 0 уже max
            if (data[i] >= data[max]) {    //>= чтобы находился последныий подходящий месяц
                max = i;
            }
        }
        return max + 1;   //+1, потому что нормальные люди считают с 1
    }

    public int monthOfMin(int[] data) {
        int min = 0;
        for (int i = 1; i < data.length; i++) {   //начинаем с 1, т.к. 0 уже min
            if (data[i] <= data[min]) {    //<= чтобы находился последныий подходящий месяц
                min = i;
            }
        }
        return min + 1;   //+1, потому что нормальные люди считают с 1
    }

    public int belowAverageCount(int[] data) {
        int count = 0;
        double average = average(data); //average считаем в готовой функции
        for (int d : data) {
            if (d < average) {   //волевое решение, в условии именно ниже среднего
                count++;
            }
        }
        return count;
    }

    public int aboveAverageCount(int[] data) {
        int count = 0;
        double average = average(data); //average считаем в функции
        for (int d : data) {
            if (d > average) {   //волевое решение, в условии именно выше среднего
                count++;
            }
        }
        return count;
    }
}
