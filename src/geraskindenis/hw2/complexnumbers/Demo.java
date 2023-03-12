package geraskindenis.hw2.complexnumbers;

public class Demo {
    public static void main(String[] args) {

        // Creating objects ComplexNumber
        ComplexNumber z1 = new ComplexNumber(5d);
        ComplexNumber z2 = new ComplexNumber(2d, 4d);
        ComplexNumber z3 = new ComplexNumber(3d, -2.1d);

        // Demo toString()
        System.out.println("z1 = " + z1);
        System.out.println("z2 = " + z2);
        System.out.println("z3 = " + z3);

        // Demo add()
        System.out.println("\n---Demo add()---");
        System.out.println("z1 + z2 = " + z1.add(z2));

        // Demo subtract()
        System.out.println("\n---Demo subtract()---");
        System.out.println("z1 - z2 = " + z1.subtract(z2));

        // Demo multiply()
        System.out.println("\n---Demo multiply()---");
        System.out.println("z1 * z2 = " + z1.multiply(z2));

        // Demo multiply()
        System.out.println("\n---Demo mod()---");
        System.out.println("z3.mod() = " + z3.mod());

    }
}
