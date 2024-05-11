package dartimfont.code;

public class Main {

    public static void main(String[] args) {

        try {
            Calculator calculator = new Calculator();

            calculator.setLine("I + III");
            calculator.run();

            calculator.setLine("VI / III");
            calculator.run();

            calculator.setLine("10 * 6");
            calculator.run();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}