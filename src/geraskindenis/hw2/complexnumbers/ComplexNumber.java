package geraskindenis.hw2.complexnumbers;

import java.util.Objects;

public class ComplexNumber {
    private final double real;
    private final double imaginary;

    public ComplexNumber(double real) {
        this(real, 0.0d);
    }

    public ComplexNumber(double real, double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    public ComplexNumber add(ComplexNumber z) {
        return new ComplexNumber(this.real + z.real, this.imaginary + z.imaginary);
    }

    public ComplexNumber subtract(ComplexNumber z) {
        return new ComplexNumber(this.real - z.real, this.imaginary - z.imaginary);
    }

    public ComplexNumber multiply(ComplexNumber z) {
        double newReal = this.real * z.real - this.imaginary * z.imaginary;
        double newImaginary = this.real * z.imaginary + this.imaginary * z.real;
        return new ComplexNumber(newReal, newImaginary);
    }

    public double mod() {
        return Math.sqrt(Math.pow(this.real, 2) + Math.pow(this.imaginary, 2));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ComplexNumber that = (ComplexNumber) o;

        if (Double.compare(that.real, real) != 0) return false;
        return Double.compare(that.imaginary, imaginary) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(real, imaginary);
    }

    @Override
    public String toString() {
        String result = String.valueOf(real);
        if (imaginary < 0) {
            result += " - " + Math.abs(imaginary) + "i";
        } else if (imaginary > 0) {
            result += " + " + imaginary + "i";
        }
        return result;
    }
}
