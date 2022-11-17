package ru.netology.stats;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StatsServiceTest {

    int[] d = {8, 15, 13, 15, 17, 20, 19, 20, 7, 14, 14, 18};

    @Test
    public void sumTest() {
        StatsService service = new StatsService();
        int expected = 180;
        int actual = service.sum(d);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void averageTest() {
        StatsService service = new StatsService();
        double expected = 15.0;
        double actual = service.average(d);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void monthOfMaxTest() {
        StatsService service = new StatsService();
        int expected = 8;
        int actual = service.monthOfMax(d);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void monthOfMinTest() {
        StatsService service = new StatsService();
        int expected = 9;
        int actual = service.monthOfMin(d);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void belowAverageCountTest() {
        StatsService service = new StatsService();
        int expected = 5;
        int actual = service.belowAverageCount(d);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void aboveAverageCountTest() {
        StatsService service = new StatsService();
        int expected = 5;
        int actual = service.aboveAverageCount(d);

        Assertions.assertEquals(expected, actual);
    }

}
