import java.util.Arrays;

public abstract class ComplexExpression {
    protected Operation operation;
    protected NumarComplex[] args;

    public NumarComplex[] getArgs() {
        return args;
    }

    public ComplexExpression(Operation oper, NumarComplex[] nrs) {
        operation = oper;
        args = Arrays.copyOf(nrs, nrs.length);
    }

    public abstract NumarComplex executeOneOperation();

    public void execute() {
        executeOneOperation();
    }
}
