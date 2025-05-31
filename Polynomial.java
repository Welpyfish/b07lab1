import java.io.*;

class Polynomial{

	double[] coeff;
	int[] exp;

	Polynomial(){
		coeff = new double[]{};
		exp = new int[]{};
	}

	Polynomial(double[] coeff, int[] exp){
		this.coeff = coeff;
		this.exp = exp;
	}

	Polynomial(File file) throws Exception {
		this();
		BufferedReader input = new BufferedReader(new FileReader(file));
		String line = input.readLine();
		input.close();

		String[] p = line.split("(?=[+-])");

		coeff = new double[p.length];
		exp = new int[p.length];
		for(int i=0; i<p.length; i++){
			String[] t = p[i].split("x");

			coeff[i]=Double.parseDouble(t[0]);
			if(t.length==1){
				exp[i]=0;
			}else{
				exp[i]=Integer.parseInt(t[1]);
			}
		}

	}

	Polynomial add(Polynomial p){
		int l=coeff.length+ p.coeff.length;

		double[] newcoeff = new double[l];
		int[] newexp = new int[l];

		int i=0;
		int j=0;
		int a=0;

		while(i<exp.length || j<p.exp.length){

			if(j==p.exp.length || (i<exp.length && exp[i]<p.exp[j])){
				newexp[a]=exp[i];
				newcoeff[a]=coeff[i];
				i++;
				a++;
			}else if(i == exp.length || exp[i] > p.exp[j]){
				newexp[a]=p.exp[j];
				newcoeff[a]=p.coeff[j];
				j++;
				a++;
			}else if(exp[i] == p.exp[j]){
				if(coeff[i]+p.coeff[j]!=0){
					newexp[a]=exp[i];
					newcoeff[a]=coeff[i]+p.coeff[j];
					i++;
					j++;
					a++;
				}
			}else{
				break;
			}
		}

		double[] newcoeff2 = new double[a];
		int[] newexp2 = new int[a];
		for(int b=0; b<a; b++){
			newcoeff2[b]=newcoeff[b];
			newexp2[b]=newexp[b];
		}

		return new Polynomial(newcoeff2, newexp2);
	}

	double evaluate(double x){
		double result=0;
		for(int i=0; i<coeff.length; i++){
			result+=coeff[i]*Math.pow(x, exp[i]);
		}
		return result;

	}

	boolean hasRoot(double x){ return 0==evaluate(x);}

	Polynomial multiply(Polynomial p){

		Polynomial result = new Polynomial();

		for(int j=0; j<p.exp.length; j++){
			for(int i=0; i<exp.length; i++){
				result = result.add(new Polynomial(
						new double[]{coeff[i] * p.coeff[j]},
						new int[]{exp[i]+p.exp[j]}
						)
				);

			}
		}
		return result;
	}

	void saveToFile(String filename) throws Exception {
		PrintStream output = new PrintStream(filename);
		for(int i=0; i<exp.length; i++){
			if(coeff[i]>0){
				output.print("+");
			}
			output.print(coeff[i]);
			if(exp[i]!=0){
				output.print("x"+exp[i]);
			}
		}
		output.close();
	}
}