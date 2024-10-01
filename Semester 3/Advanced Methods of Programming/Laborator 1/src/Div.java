public class Div extends ComplexExpression {
    public Div(Operation op, NumarComplex[] arg) {
        super(op, arg);
    }

    @Override
    public NumarComplex executeOneOperation() {
        args[0].Impartire(args[1]);
        return args[0];
    }
}
