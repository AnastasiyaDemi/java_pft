package ru.stqa.pft.sandbox;

public class Point {
    public double x;
    public double y;
    public double n;

    public static void main(String [] args) {
    Point p1 = new Point(2,3,1);
    Point p2 = new Point(1,2,2);
    distance(p1, p2);

    }

    public Point(double x, double y, int n ) {
        this.x=x;
        this.y=y;
        this.n=n;
        System.out.println("p" +n+ " это точка с координатами"+" "+ x + " и " + y);

    }

    public static double distance(Point p1, Point p2){
        System.out.println ("Расстояние между двумя заданными точками р1 и р2 = " + Math.sqrt((p1.x-p2.x)*(p1.x-p2.x)+(p1.y-p2.y)*(p1.y-p2.y)));
        return Math.sqrt((p1.x-p2.x)*(p1.x-p2.x)+(p1.y-p2.y)*(p1.y-p2.y));
    }

}
