package com.example.login;
import Jama.Matrix;
import Jama.QRDecomposition;
public class PolynomialRegression implements  Comparable<PolynomialRegression> {
   private int year;
    // private final String variableName;  // name of the predictor variable
    private int degree;                 // degree of the polynomial regression
    private Matrix beta;                // the polynomial regression coefficients
    private double sse;                 // sum of squares due to error
    private double sst;

   // public PolynomialRegression(double[] x, double[] y, int degree) {
     //   this(x, y, degree, "n");
    //}


    public PolynomialRegression(double[] x, double[] y, int degree, int yr) {
        this.degree = degree;
        this.year=yr;

        int n = x.length;
        QRDecomposition qr = null;
        Matrix matrixX = null;


        while (true) {


            double[][] vandermonde = new double[n][this.degree+1];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j <= this.degree; j++) {
                    vandermonde[i][j] = Math.pow(x[i], j);
                }
            }
            matrixX = new Matrix(vandermonde);


            qr = new QRDecomposition(matrixX);
            if (qr.isFullRank()) break;


            this.degree--;
        }


        Matrix matrixY = new Matrix(y, n);


        beta = qr.solve(matrixY);


        double sum = 0.0;
        for (int i = 0; i < n; i++)
            sum += y[i];
        double mean = sum / n;


        for (int i = 0; i < n; i++) {
            double dev = y[i] - mean;
            sst += dev*dev;
        }


        Matrix residuals = matrixX.times(beta).minus(matrixY);
        sse = residuals.norm2() * residuals.norm2();
    }
    @Override
    public int compareTo(PolynomialRegression that) {
        double EPSILON = 1E-5;
        int maxDegree = Math.max(this.degree(), that.degree());
        for (int j = maxDegree; j >= 0; j--) {
            double term1 = 0.0;
            double term2 = 0.0;
            if (this.degree() >= j) term1 = this.beta(j);
            if (that.degree() >= j) term2 = that.beta(j);
            if (Math.abs(term1) < EPSILON) term1 = 0.0;
            if (Math.abs(term2) < EPSILON) term2 = 0.0;
            if      (term1 < term2) return -1;
            else if (term1 > term2) return +1;
        }
        return 0;
    }

    private double beta(int j) {
        if (Math.abs(beta.get(j, 0)) < 1E-4) return 0.0;
        return beta.get(j, 0);
    }

    private int degree() {

        return degree;
    }

    public double method() {
        StringBuilder s = new StringBuilder();
        int j = degree;

        // ignoring leading zero coefficients
        while (j >= 0 && Math.abs(beta(j)) < 1E-5)
            j--;

        // create remaining terms
/*
        while (j >= 0) {
            if      (j == 0) s.append(String.format("%.2f ", beta(j)));
            else if (j == 1) s.append(String.format("%.2f %s + ", beta(j), variableName));
            else             s.append(String.format("%.2f %s^%d + ", beta(j), variableName, j));
            j--;
        }
        */
        double m=0.0;
        while(j>=0){
            if(j==0){
                m=m+beta(j);
            }
            else if(j==1){
                m=m+(beta(j)*(year-2000));
            }
            else{
                m=m+(beta(j)*Math.pow((year-2000),j));

            }
            j--;

        }

        //-0.2836
       // s = s.append("  (R^2 = " + String.format("%.3f", R2()) + ")");

        // replace "+ -2n" with "- 2n"
       // return s.toString().replace("+ -", "- ");
        return m;
    }




    public double predict(double x) {
        // horner's method
        double y = 0.0;
        for (int j = degree; j >= 0; j--)
            y = beta(j) + (x * y);
        return y;
    }


    public double R2() {
        if (sst == 0.0) return 1.0;   // constant function
        return 1.0 - sse/sst;
    }




}

