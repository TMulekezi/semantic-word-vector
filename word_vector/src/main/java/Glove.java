public class Glove {
    private String strVocabulary;
    private Vector vecVector;

    public Glove(String _vocabulary, Vector _vector) {
        //TODO Task 2.1
        strVocabulary = _vocabulary;
        vecVector = _vector;
    }

    public String getVocabulary() {
        //TODO Task 2.2
        return strVocabulary;
    }

    public Vector getVector() {
        //TODO Task 2.3
        return vecVector;
    }

    public void setVocabulary(String _vocabulary) {
        //TODO Task 2.4
        this.strVocabulary = _vocabulary;
    }

    public void setVector(Vector _vector) {
        //TODO Task 2.5
        this.vecVector = _vector;
    }
}
