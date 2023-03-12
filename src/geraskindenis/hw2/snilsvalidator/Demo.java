package geraskindenis.hw2.snilsvalidator;

public class Demo {
    public static void main(String[] args) {
        SnilsValidatorImpl validator = new SnilsValidatorImpl();
        System.out.println(validator.validate("01468870570")); // false
        System.out.println(validator.validate("00000000000")); // true
        System.out.println(validator.validate("90114404441")); // true
    }
}
