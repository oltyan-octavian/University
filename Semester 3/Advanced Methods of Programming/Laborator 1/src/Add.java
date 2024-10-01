import java.util.Arrays;

public class Add extends ComplexExpression {
    public Add(Operation op, NumarComplex[] arg) {
        super(op, arg);
    }

    @Override
    public NumarComplex executeOneOperation() {
        args[0].Adaugare(args[1]);
        return args[0];
    }
}