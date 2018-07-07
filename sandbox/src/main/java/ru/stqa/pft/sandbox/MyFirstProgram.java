package ru.stqa.pft.sandbox;

public class MyFirstProgram {
	
	public static void main(String [] args) {
		hello ("world");
		hello ("Nik");
		hello ("everybody");

		scuare s = new scuare()
		s.l = 5;
		System.out.println ("Пощадь квадрата со стороной "+s.l+" = "+area(s));

		rectangle r = new rectangle()
        r.a = 2;
        r.b = 6;
        System.out.println ("Пощадь прямоугольника со сторонами "+r.a+ " и "+r.b+" = "+area(r));
	}

	public static void hello (String somebody) {
		System.out.println("Hello,"+ somebody + "!");
	}

	public static double area (scuare s) {
        return s.l*s.l;
    }

    public static double area (rectangle r) {
        return r.a*r.b;
    }
}