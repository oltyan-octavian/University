public class Main {
    public static void main(String[] args) {
        ExpressionParser parser = new ExpressionParser();
        parser.setExpression(args);
        if(ExpressionParser.verifyExpression(args)){
            NumarComplex rezultat = parser.parse();
            System.out.println("Raspunsul este " + rezultat.toString());
        }
        else {
            System.out.println("Expresia introdusa nu este corecta!");
        }
    }
}