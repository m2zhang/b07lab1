import java.io.File;
import java.security.cert.PolicyQualifierInfo;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

import java.io.FileWriter;

public class Polynomial {
	double[] non_zero_coef; // new double[]
	int[] expon; // new int[]
	
	public Polynomial() {
		double[] x= {0};
		non_zero_coef = x;
		// new double[0]
		int[] y= {0};
		expon = y;
	}
	
	public Polynomial(double[]coef, int[]expo) {
		int coef_size = coef.length;
		int expo_size = expo.length;

		non_zero_coef = new double[coef_size];
		expon = new int[expo_size];
		
		for (int i=0; i<coef_size; i++) {
			non_zero_coef[i] = coef[i];
		}

		for (int i=0; i< expo_size; i++) {
			expon[i] = expo[i];
		}
	}

	// Constructor based on file input
	public Polynomial(File file){
		BufferedReader reader;

		try{
			reader = new BufferedReader(new FileReader(file));
			String line = reader.readLine();
			// need to split 
			String[] terms = line.split("(?=[+-])"); // split based on +/- but keep the signs

			int size = terms.length;
			System.out.println("Size is: " + size);
			
			non_zero_coef = new double[size]; 
			expon =  new int[size];

			int tracker = 0;
			for (String term: terms){
				String[] split = term.split("[x]"); // split based on the x to separate the coef + exponent
				if (split.length == 1) { // means there's only a coefficient
					non_zero_coef[tracker] = Double.parseDouble(split[0]);
					expon[tracker] = 0;
					tracker +=1;
				}
				else{
					non_zero_coef[tracker] = Double.parseDouble(split[0]);
					expon[tracker] = Integer.parseInt(split[1]);
					tracker +=1;
				}

			}

		} catch (IOException e){
			e.printStackTrace();
		}
	}

	public Polynomial add(Polynomial p) {
		// may need to get rid of the exponents with zero as the coefficient..
		// NOT the same size!!
		
		// Find the length of new array (count the distinct exponent values..)
		int final_size = Polynomial.this.expon.length;

		//System.out.println("Test1: " + final_size);

		// count the distinct exponent values in polynomial p
		for (int i=0; i< p.expon.length; i++) {
			//System.out.println("Expon is: " + p.expon[i]);
			boolean exists = false;
			for (int j=0; j< Polynomial.this.expon.length; j++){
				if (p.expon[i] == this.expon[j]) {
					exists = true;
				}
			}
			if (exists == false) {
				//System.out.println("ever get here??");
				final_size += 1;	
			}
		}// final size has now been updated
		//System.out.println("Test2: " + final_size);
		
		// polynomial size with distinct exponents
		double[] new_coef = new double[final_size];
		int[] new_expon = new int[final_size];

		int size1 = Polynomial.this.expon.length;
		int size2 = p.expon.length;
		
		boolean[] visited = new boolean[size2]; // all values default to false
		
		// first add all of the original polynomial's data over. If the expon matches with polynomial p, add + mark that spot
		// in polynomial p as visited.
		// keep counter to know where to pick up for the polynomial p
		int tracker = 0;


		for(int i=0;i<size1;i++) {
			boolean matched = false;
			for (int j=0; j<size2; j++){
				if (Polynomial.this.expon[i] == p.expon[j]) {
					new_expon[tracker] = Polynomial.this.expon[i];
					new_coef[tracker] = Polynomial.this.non_zero_coef[i] + p.non_zero_coef[j];
					visited[j] = true;
					tracker += 1;
					matched = true;
				}
			}
			if (matched == false){
				new_expon[tracker] = Polynomial.this.expon[i];
				new_coef[tracker] = Polynomial.this.non_zero_coef[i];
				tracker+=1;
			}
		}

		for (int j=0; j<size2; j++){
			if (visited[j] == false){
				new_expon[tracker] = p.expon[j];
				new_coef[tracker] = p.non_zero_coef[j];
				tracker +=1;
			}
		}

		// need to get of any coefficients of 0

		int z_count = 0;
		for (int i = 0; i< final_size; i++){
			if (new_coef[i] == 0 ) {
				z_count +=1;
			}
		}

		int f_size = final_size - z_count;
		
		double[] final_coef = new double[f_size];
		int[] final_expon = new int[f_size];

		int index = 0;
		for (int i=0; i<final_size; i++) {
			if (new_coef[i] != 0){
				final_coef[index] = new_coef[i];
				final_expon[index] = new_expon[i];
				index +=1;
			}
		}

		Polynomial newP = new Polynomial(final_coef, final_expon);
		return newP;
	}

	public double evaluate(double x) {
		int size = Polynomial.this.non_zero_coef.length;
		
		double output=0;
		for (int i=0;i<size;i++) {
			int expo = Polynomial.this.expon[i];
			output += Polynomial.this.non_zero_coef[i] * Math.pow(x, expo);
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

	public Polynomial multiply(Polynomial p) {

		int p_size = p.expon.length;
		int this_size = Polynomial.this.expon.length;

		int expa_size = this_size * p_size;

		double expa_coef[] = new double[expa_size];
		int expa_expon[] = new int[expa_size];

		int tracker = 0;
		int highest_coef = 0;
		
		for (int i=0; i<this_size; i++){
			for (int j=0; j< p_size; j++){
				expa_coef[tracker] = Polynomial.this.non_zero_coef[i] * p.non_zero_coef[j];
				int new_expo = Polynomial.this.expon[i] + p.expon[j];
				expa_expon[tracker] = new_expo;
				tracker += 1;

				if (new_expo > highest_coef ) {
					highest_coef = new_expo;			
				}
			}
		}
		// Get rid of duplicate exponents
		// Step 1: make array of highest coef. (don't forget to add one to length.)
		int temp_expon[] = new int[highest_coef +1];
		
		//initialize the exponents from 1 to highest_coef
		for (int i=0; i<highest_coef +1; i++){
			temp_expon[i] = i;
		}
		double temp_coef[] = new double[highest_coef +1]; // all initialized to 0 
		
		// Go through cross prod (expa_coef and expa_expon) and store all parts into the array..
		
		for (int i=0; i< expa_size; i++){
			temp_coef[expa_expon[i]] += expa_coef[i];
		}

		// Count the number of non-zero coefficients (in temp_coef) + make an array:
		int last_size = 0;
		for (int i=0; i< highest_coef+1; i++){
			if(temp_coef[i] != 0){
				last_size +=1;
			}
		}
		
		int l_expon[] = new int[last_size];
		double l_coef[] = new double[last_size];
		
		// Get rid of zero coefficients..
		int j= 0;
		for (int i=0; i<highest_coef +1; i++){
			if (temp_coef[i] != 0){
				l_coef[j] = temp_coef[i];
				l_expon[j] = temp_expon[i]; 
				j++; 
			}
		}

		Polynomial product = new Polynomial(l_coef, l_expon);
		return product;
	}
	
	public void saveToFile(String file){
		// we are saving the polynomial in this instance of the class into the file...
	try (FileWriter writer = new FileWriter(file)){
		double first_coef = non_zero_coef[0];
		writer.write(Double.toString(first_coef));
		
		int first_exp = expon[0];
		if (first_exp != 0) {
			writer.write("x" + Integer.toString(first_exp));
		}

		for (int i = 1; i < non_zero_coef.length; i++) {
			double coef = non_zero_coef[i];
			//System.out.println("The coef we're working with is: " + coef );
			int exp = expon[i];
			if (coef >= 0) {
				writer.write("+");
			}
			writer.write(Double.toString(coef));

			if (exp != 0) {
				writer.write("x" + Integer.toString(exp));
			}
		}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
