public class Mul extends ComplexExpression {
    public Mul(Operation op, NumarComplex[] arg) {
        super(op, arg);
    }

    @Override
    public NumarComplex executeOneOperation() {
        args[0].Inmultire(args[1]);
        return args[0];
    }
}
