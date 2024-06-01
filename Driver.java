import java.io.File;

public class Driver {
public static void main(String [] args) {
Polynomial p = new Polynomial();
System.out.println(p.evaluate(3));

double [] c1 = {5,-3,7};
int [] e1 = {0, 2, 8};
Polynomial p1 = new Polynomial(c1, e1);

double [] c2 = {-2, -3, 1};
int [] e2 = {2, 0, 1};
Polynomial p2 = new Polynomial(c2, e2);
Polynomial s = p1.add(p2);

// adding to 4 + x^2 + 3x^3 [4, 1, 3] [0, 2, 3]
double [] a1 = {4, 1, 3};
int [] a2 = {0, 2, 3};
Polynomial a = new Polynomial( a1 ,a2 );

double [] b1 = {2, 2, 2, 2};
int [] b2 = {0, 1, 2, 3};
Polynomial b = new Polynomial(b1, b2);

Polynomial c = a.add(b);

System.out.println("The coeffs are: " + c.non_zero_coef[0] + ", " + c.non_zero_coef[1] + ", " + c.non_zero_coef[2] + ", " + c.non_zero_coef[3]);
System.out.println("The expos are: " + c.expon[0] + ", " + c.expon[1] + ", " + c.expon[2] + ", " + c.expon[3]);

// could add: [2, 2, 2] [1, 2, 3] 2x + 2x^2 + 2x^3
// could add: [2, 1] [2, 1] 2x^2 + x
// could add [2, 2, 2, 2] [0, 1, 2, 3] 2 + 2x + 2x^2 + 2x^3

// can we assume the exponent values inside will be unique?
double [] c3 = {1, 2};
int [] e3 = {1, 0};
Polynomial p3 = new Polynomial(c3, e3);

double [] c4 = {1, -2};
int [] e4 = {1, 0};
Polynomial p4 = new Polynomial(c4, e4);

Polynomial p5 = p3.multiply(p4);

if (p5.hasRoot(2) && p5.hasRoot(-2)){
    System.err.println("Multiplication worked");
}
else {
    System.err.println("Multiplication did not work breh.");
}

System.out.println(p5.expon[0] + "," + p5.expon[1] + "," + p5.non_zero_coef[0] + "," + p5.non_zero_coef[1]);

System.out.println("s(0) hopefully is 2: " + s.evaluate(0));

File file = new File("file.txt");

Polynomial p6 = new Polynomial(file);

if (p6.evaluate(0) == 5) {
    System.out.println("Constuctor via file worked!");
}

else{
    System.out.println("UHoh, constructor via file didn't work");
}

p5.saveToFile("empty.txt");


}
}