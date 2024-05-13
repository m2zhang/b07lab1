public class Polynomial {
	double[] coefficients; // new double[]
	
	public Polynomial() {
		double[] x= {0};
		coefficients = x;
		// new double[0]
	}
	
	public Polynomial(double[] arr) {
		int size = arr.length;
		coefficients = new double[size];
		
		for (int i=0; i<size; i++) {
			coefficients[i] = arr[i];
		}
	}
	
	public Polynomial add(Polynomial p) {
		// NOT the same size!!
		int size1 = Polynomial.this.coefficients.length;
		int size2 = p.coefficients.length;
		//System.out.println("Size1 and size 2 respectively: " + size1 + " " + size2);
		
		
		if (size1 >= size2) {
			double[] arr = new double[size1];
			
			for(int i=0;i<size1;i++) {
				arr[i] = Polynomial.this.coefficients[i]; 
			}
			
			Polynomial newP = new Polynomial(arr);
			
			
			for (int i=0;i<size2;i++) {
				newP.coefficients[i] += p.coefficients[i];
			}
			
			return newP;
		}
		
		else {
			double[] arr = new double[size2];
			
			for(int i=0;i<size2;i++) {
				//System.out.println(i);
				arr[i] = p.coefficients[i]; 
			}
			
			Polynomial newP = new Polynomial(arr);
			
			
			for (int i=0;i<size1;i++) {
				newP.coefficients[i] += Polynomial.this.coefficients[i];
			}
			
			return newP;
		}
			
		}
	
	
	// bruh it's all double, NOT int lmfao
	public double evaluate(double x) {
		int size = Polynomial.this.coefficients.length;
		
		double output=0;
		for (int i=0;i<size;i++) {
			output += Polynomial.this.coefficients[i] * Math.pow(x, i);
		}
		//System.out.println("Val is: " + output);
		return output;
	}
	
	public boolean hasRoot(double root) {
		if (evaluate(root)==0) {
			return true;
		}
		return false;

	}
	
		
}