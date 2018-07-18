package ru.stqa.pft.sandbox;

public class Point {
    public double x;
    public double y;

    public static void main(String[] args) {
        Point p1 = new Point(0, 0);
        Point p2 = new Point(3, 4);
        double d = p1.distance(p2);
        System.out.println("Расстояние между двумя заданными точками р1 и р2 = " + d);

    }

    public Point(double x, double y) {
        this.x = x;
        this.y = y;

    }

    public double distance(Point p2) {
        double result = Math.sqrt((this.x - p2.x) * (this.x - p2.x) + (this.y - p2.y) * (this.y - p2.y));
        return result;
    }

}
