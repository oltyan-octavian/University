public class NumarComplex {
    private float re;
    private float im;

    public NumarComplex() {
    }
    public NumarComplex(float real, float imaginar) {
        re = real;
        im = imaginar;
    }
    public float getRe() {
        return re;
    }

    public void setRe(float re) {
        this.re = re;
    }

    public float getIm() {
        return im;
    }

    public void setIm(float im) {
        this.im = im;
    }

    public void Adaugare(NumarComplex n1) {
        im = n1.im + im;
        re = n1.re + re;
    }

    public void Scadere(NumarComplex n1) {
        im = im - n1.im;
        re = re - n1.re;
    }

    public void Inmultire(NumarComplex n1) {
        NumarComplex n3 = new NumarComplex();
        n3.im = re * n1.im + im * n1.re;
        n3.re = re * n1.re - im * n1.im;
        im = n3.im;
        re = n3.re;
    }

    public void Impartire(NumarComplex n1) {
        NumarComplex n3 = new NumarComplex();
        n3.im = (im * n1.re - re * n1.im) / (n1.re * n1.re + n1.im * n1.im);
        n3.re = (re * n1.re + im * n1.im) / (n1.re * n1.re + n1.im * n1.im);
        im = n3.im;
        re = n3.re;
    }

    public void Conjugatul() {
        im = -im;
    }

    @Override
    public String toString() {
        if(getIm() < 0){
            return getRe() + " - " + Math.abs(getIm()) + "i";
        }
        else {
            return getRe() + " + " + getIm() + "i";
        }
    }
}