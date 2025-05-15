
class Polynomial{

	double[] coeff;

	Polynomial(){coeff = new double[]{0};}

	Polynomial(double[] coeff){this.coeff = coeff;}

	Polynomial add(Polynomial p){
		double[] result = new double[Math.max(coeff.length, p.coeff.length)];
		for(int i=0; i<result.length; i++){
			if(i<coeff.length){result[i]+=coeff[i];}
			if(i<p.coeff.length){result[i]+=p.coeff[i];}
		}
		return new Polynomial(result);
	}

	double evaluate(double x){
		double result=0;
		for(int i=0; i<coeff.length; i++){
			result+=coeff[i]*Math.pow(x, i);
		}
		return result;

	}

	boolean hasRoot(double x){ return 0==evaluate(x);}
}