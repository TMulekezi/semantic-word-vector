import org.apache.commons.lang3.time.StopWatch;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SemanticMain {
    public List<String> listVocabulary = new ArrayList<>();  //List that contains all the vocabularies loaded from the csv file.
    public List<double[]> listVectors = new ArrayList<>(); //Associated vectors from the csv file.
    public List<Glove> listGlove = new ArrayList<>();
    public final List<String> STOPWORDS;

    public SemanticMain() throws IOException {
        STOPWORDS = Toolkit.loadStopWords();
        Toolkit.loadGLOVE();
    }


    public static void main(String[] args) throws IOException {
        StopWatch mySW = new StopWatch();
        mySW.start();
        SemanticMain mySM = new SemanticMain();
        mySM.listVocabulary = Toolkit.getListVocabulary();
        mySM.listVectors = Toolkit.getlistVectors();
        mySM.listGlove = mySM.CreateGloveList();

        List<CosSimilarityPair> listWN = mySM.WordsNearest("computer");
        Toolkit.PrintSemantic(listWN, 5);

        listWN = mySM.WordsNearest("phd");
        Toolkit.PrintSemantic(listWN, 5);

        List<CosSimilarityPair> listLA = mySM.LogicalAnalogies("china", "uk", "london", 5);
        Toolkit.PrintSemantic("china", "uk", "london", listLA);

        listLA = mySM.LogicalAnalogies("woman", "man", "king", 5);
        Toolkit.PrintSemantic("woman", "man", "king", listLA);

        listLA = mySM.LogicalAnalogies("banana", "apple", "red", 3);
        Toolkit.PrintSemantic("banana", "apple", "red", listLA);
        mySW.stop();

        if (mySW.getTime() > 2000)
            System.out.println("It takes too long to execute your code!\nIt should take less than 2 second to run.");
        else
            System.out.println("Well done!\nElapsed time in milliseconds: " + mySW.getTime());
    }

    public List<Glove> CreateGloveList() {
        List<Glove> listResult = new ArrayList<>();
        //TODO Task 6.1
        for (int i = 0; i < Toolkit.getListVocabulary().size(); i++) {

            if (!(STOPWORDS.contains(Toolkit.getListVocabulary().get(i)))) {
                listResult.add(new Glove(Toolkit.getListVocabulary().get(i), new Vector(Toolkit.getlistVectors().get(i))));
            }

        }
        listGlove = listResult;
        return listResult;
    }

    public List<CosSimilarityPair> WordsNearest(String _word) {
        List<CosSimilarityPair> listCosineSimilarity = new ArrayList<>();
        //TODO Task 6.2
        Vector v;
        boolean foundInList = false;
        int indexOfError = 0;
        int indexOfWord = 0;
        for (int i = 0; i < listGlove.size(); i++) {
            if (listGlove.get(i).getVocabulary().equals("error")) {
                indexOfError = i;
            }
            if (listGlove.get(i).getVocabulary().equals(_word)) {
                foundInList = true;
                indexOfWord = i;
            }
        }

            if (!foundInList) {
                _word = "error";
                v = listGlove.get(indexOfError).getVector();
            } else {
                v = listGlove.get(indexOfWord).getVector();
            }

        for (int i = 0; i < listGlove.size(); i++) {
            if (!(listGlove.get(i).getVocabulary().equals(_word))){
                listCosineSimilarity.add(new CosSimilarityPair(_word, listGlove.get(i).getVocabulary(), v.cosineSimilarity(listGlove.get(i).getVector())));
            }
        }
        HeapSort.doHeapSort(listCosineSimilarity);
        return listCosineSimilarity;
    }

    public List<CosSimilarityPair> WordsNearest(Vector _vector) {
        List<CosSimilarityPair> listCosineSimilarity = new ArrayList<>();
        //TODO Task 6.3

        for (int i = 0; i < listGlove.size(); i++) {
            if (!(_vector.equals(listGlove.get(i).getVector()))){
                listCosineSimilarity.add(new CosSimilarityPair(_vector, listGlove.get(i).getVocabulary(), _vector.cosineSimilarity(listGlove.get(i).getVector())));
            }
        }

        HeapSort.doHeapSort(listCosineSimilarity);


        return listCosineSimilarity;
    }

    /**
     * Method to calculate the logical analogies by using references.
     * <p>
     * Example: uk is to london as china is to XXXX.
     *       _firISRef  _firTORef _secISRef
     * In the above example, "uk" is the first IS reference; "london" is the first TO reference
     * and "china" is the second IS reference. Moreover, "XXXX" is the vocabulary(ies) we'd like
     * to get from this method.
     * <p>
     * If _top <= 0, then returns an empty listResult.
     * If the vocabulary list does not include _secISRef or _firISRef or _firTORef, then returns an empty listResult.
     *
     * @param _secISRef The second IS reference
     * @param _firISRef The first IS reference
     * @param _firTORef The first TO reference
     * @param _top      How many vocabularies to include.
     */
    public List<CosSimilarityPair> LogicalAnalogies(String _secISRef, String _firISRef, String _firTORef, int _top) {
        List<CosSimilarityPair> listResult = new ArrayList<>();
        //TODO Task 6.4
        if (_top <= 0) {
            return listResult;
        }


        int _firIsIndex = -1;
        int _firToIndex = -1;
        int _secIsIndex = -1;

        for (int i = 0; i < listGlove.size(); i++) {
            if (listGlove.get(i).getVocabulary().equals(_firISRef)) {
                _firIsIndex = i;
            }
            if (listGlove.get(i).getVocabulary().equals(_firTORef) ){
                _firToIndex = i;
            }
            if (listGlove.get(i).getVocabulary().equals(_secISRef)) {
                _secIsIndex = i;
            }

        }

        if (_secIsIndex == -1 || _firToIndex == -1 || _firIsIndex == -1) {
            return listResult;
        }

        Vector temp = listGlove.get(_secIsIndex).getVector().subtraction(listGlove.get(_firIsIndex).getVector());
        Vector result = temp.add(listGlove.get(_firToIndex).getVector());

        List<CosSimilarityPair> tempList = WordsNearest(result);
        int added = 0;
        int i = 0;
        while (added < _top) {
            String word = tempList.get(i).getWord2();
            if (!(word.equals(_firISRef) || word.equals(_firTORef) || word.equals(_secISRef))) {
                listResult.add(tempList.get(i));
                added += 1;
            }
            i++;

        }

        return listResult;
    }
}