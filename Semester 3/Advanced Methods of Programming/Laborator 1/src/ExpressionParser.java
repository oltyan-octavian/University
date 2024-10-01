import java.util.Arrays;

public class ExpressionParser {
    private String[] expression;
    public void setExpression(String[] args){
        expression = Arrays.copyOf(args, args.length);
    }

    public static boolean isValid(String input) {
        String regex = "^[-+]?[0-9]*\\.?[0-9]+\s*[-+]\s*[0-9]*\\.?[0-9]+i$";
        return input.matches(regex);
    }
    public static boolean verifyExpression(String[] args) {
        if (args.length % 2 == 0) {
            return false;
        }
        for(int i = 0; i < args.length; i = i + 2) {
            if(!isValid(args[i]))
                return false;
        }
        return true;
    }
    public NumarComplex parse(){
        ExpressionFactory factory = ExpressionFactory.getInstance();
        boolean negativ = false;
        if(expression[0].charAt(0) == '-'){
            negativ = true;
            expression[0]=expression[0].substring(1);
        }
        String[] numarComplex= expression[0].split("[+-]", 0);
        NumarComplex nr1 = new NumarComplex(), nr2 = new NumarComplex();
        float parteReala;
        float parteImaginara;
        parteReala = Float.parseFloat(numarComplex[0]);

        parteImaginara = Float.parseFloat(numarComplex[1].substring(0, numarComplex[1].length()-1));
        if(negativ) {
            parteReala = -parteReala;
        }
        if(expression[0].contains("-")){
            parteImaginara = -parteImaginara;
        }
        nr1.setRe(parteReala);
        nr1.setIm(parteImaginara);
        for(int i = 2; i < expression.length; i=i+2){
            String operator = expression[i-1];
            boolean negativ2 = false;
            if(expression[i].charAt(0) == '-'){
                negativ2 = true;
                expression[i]=expression[i].substring(1);
            }
            numarComplex = expression[i].split("[+-]");
            parteReala = Float.parseFloat(numarComplex[0]);
            parteImaginara = Float.parseFloat(numarComplex[1].substring(0, numarComplex[1].length()-1));
            if(negativ2) {
                parteReala = -parteReala;
            }
            if(expression[i].contains("-")){
                parteImaginara = -parteImaginara;
            }
            nr2.setIm(parteImaginara);
            nr2.setRe(parteReala);
            NumarComplex[] numere = new NumarComplex[]{nr1, nr2};
            ComplexExpression expresie = factory.createExpression(operator, numere);
            expresie.execute();
            nr1 = expresie.getArgs()[0];
        }
        return nr1;
    }

}
