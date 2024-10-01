public class Sub extends ComplexExpression {
    public Sub(Operation op, NumarComplex[] arg) {
        super(op, arg);
    }

    @Override
    public NumarComplex executeOneOperation() {
        args[0].Scadere(args[1]);
        return args[0];
    }
}