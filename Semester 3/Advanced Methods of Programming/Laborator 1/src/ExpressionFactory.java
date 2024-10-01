import java.util.Objects;

public class ExpressionFactory {
    private static final ExpressionFactory instance = new ExpressionFactory();
    private ExpressionFactory(){}
    public static ExpressionFactory getInstance(){
        return instance;
    }
    public ComplexExpression createExpression(String operation, NumarComplex[] args) {
        if (Objects.equals(operation, "+")){
            return new Add(Operation.ADDITION, args);
        }
        if (Objects.equals(operation, "-")){
            return new Sub(Operation.SUBTRACTION, args);
        }
        if (Objects.equals(operation, "*")){
            return new Mul(Operation.MULTIPLICATION, args);
        }
        if (Objects.equals(operation, "/")){
            return new Div(Operation.DIVISION, args);
        }
        return null;
    }
}