package ru.stqa.pft.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PointTests {

    @Test
    public void testDistance () {
        Point p1 = new Point(0, 0);
        Point p2 = new Point(3, 4);
        Point p3 = new Point(0, 0);
        Point p4 = new Point(-3, -4);

        Assert.assertEquals(p1.distance(p2),5.0);
        Assert.assertEquals(p1.distance(p3),0.0);
        Assert.assertEquals(p4.distance(p2),10.0);

    }
}
