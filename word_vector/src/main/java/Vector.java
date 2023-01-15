public class Vector {
    private double[] doubElements;

    public Vector(double[] _elements) {
        //TODO Task 1.1
        doubElements = _elements;
    }

    public double getElementatIndex(int _index) {
        //TODO Task 1.2
        if (_index < 0 || _index > doubElements.length-1) {
            return -1;
        }
        return doubElements[_index];
    }

    public void setElementatIndex(double _value, int _index) {
        //TODO Task 1.3
        if (_index < 0 || _index > doubElements.length - 1) {
            doubElements[doubElements.length - 1] = _value;
        } else {
            doubElements[_index] = _value;
        }
    }

    public double[] getAllElements() {
        //TODO Task 1.4
        return doubElements;
    }

    public int getVectorSize() {
        //TODO Task 1.5
        return doubElements.length;
    }

    public Vector reSize(int _size) {
        //TODO Task 1.6
        double[] newArray;
        if (_size == getVectorSize() || _size <= 0) {
            return this;
        } else if (_size < getVectorSize()) {
            newArray = new double[_size];
            for (int i = 0; i < _size; i++) {
                newArray[i] = doubElements[i];
            }
        } else {
            newArray = new double[_size];
            for (int i = 0; i < _size; i++) {
                if (i > getVectorSize() - 1) {
                    newArray[i] = -1.0;
                } else {
                    newArray[i] = doubElements[i];
                }
            }

        }
        Vector newVector = new Vector(newArray);
        return newVector;
    }

    public Vector add(Vector _v) {
        //TODO Task 1.7
        if (this.getVectorSize() > _v.getVectorSize()) {
            _v = _v.reSize(this.getVectorSize());
        } else if (this.getVectorSize() < _v.getVectorSize()) {
            doubElements = this.reSize(_v.getVectorSize()).getAllElements();
        }

        for (int i = 0; i < this.getVectorSize(); i++) {

            doubElements[i] = doubElements[i] + _v.getAllElements()[i];
        }
        return this;
    }

    public Vector subtraction(Vector _v) {
        //TODO Task 1.8
        if (this.getVectorSize() > _v.getVectorSize()) {
            _v = _v.reSize(this.getVectorSize());
        } else if (this.getVectorSize() < _v.getVectorSize()) {
            doubElements = this.reSize(_v.getVectorSize()).getAllElements();
        }

        for (int i = 0; i < this.getVectorSize(); i++) {

            doubElements[i] = doubElements[i] - _v.getAllElements()[i];
        }
        return this;
    }

    public double dotProduct(Vector _v) {
        //TODO Task 1.9
        if (this.getVectorSize() > _v.getVectorSize()) {
            _v = _v.reSize(this.getVectorSize());
        } else if (this.getVectorSize() < _v.getVectorSize()) {
            doubElements = this.reSize(_v.getVectorSize()).getAllElements();
        }
        double result = 0;

        for (int i = 0; i < this.getVectorSize(); i ++) {
            result += doubElements[i] * _v.getAllElements()[i];
        }

        return result;
    }

    public double cosineSimilarity(Vector _v) {
        //TODO Task 1.10
        if (this.getVectorSize() > _v.getVectorSize()) {
            _v = _v.reSize(this.getVectorSize());
        } else if (this.getVectorSize() < _v.getVectorSize()) {
            doubElements = this.reSize(_v.getVectorSize()).getAllElements();
        }

        double dotProduct = this.dotProduct(_v);

        double sumSquareThis = 0;
        double sumSquare_V = 0;
        for (int i = 0; i < this.getVectorSize(); i ++ ) {
            sumSquareThis += doubElements[i] * doubElements[i];
            sumSquare_V += _v.getAllElements()[i] * _v.getAllElements()[i];
        }

        double cs = dotProduct/(Math.sqrt(sumSquareThis) * Math.sqrt(sumSquare_V));
        return cs;
    }

    @Override
    public boolean equals(Object _obj) {
        Vector v = (Vector) _obj;
        boolean boolEquals = true;
        //TODO Task 1.11
        if (this.getVectorSize() != v.getVectorSize()) {
            boolEquals = false;
        } else {
            int i = 0;
            while (i < this.getVectorSize() && boolEquals) {
                if (doubElements[i] != v.getAllElements()[i]) {
                    boolEquals = false;
                }

                i++;
            }
        }
        return boolEquals;
    }

    @Override
    public String toString() {
        StringBuilder mySB = new StringBuilder();
        for (int i = 0; i < this.getVectorSize(); i++) {
            mySB.append(String.format("%.5f", doubElements[i])).append(",");
        }
        mySB.delete(mySB.length() - 1, mySB.length());
        return mySB.toString();
    }
}
