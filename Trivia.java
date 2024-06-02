//Lincon
//shivansh

//imports
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

class Trivia {

    private String[][] questionsAndInfo;
    private boolean[] usedQuestions;
    private final String[] answerTable = new String[]{"A", "B", "C", "D"};

    public Trivia(){

        JSONParser parser = new JSONParser();

        try{
            JSONObject a = (JSONObject) parser.parse(new FileReader("TriviaQuestions.json"));
            interpretFile((JSONArray) a.get("Questions"));
        } catch (IOException io) {
            io.printStackTrace();
        } catch (ParseException p) {
            p.printStackTrace();
        }
    }

    public void interpretFile(JSONArray questions){
        JSONObject currentQuestion;
        JSONArray tempArray;
        questionsAndInfo = new String[questions.size()][7]; // 7 for hint
        for (int i = 0; i < questions.size(); i++){
            currentQuestion = (JSONObject) questions.get(i);
            questionsAndInfo[i][0] = (String) currentQuestion.get("Question");
            tempArray = (JSONArray) currentQuestion.get("Answers");
            for (int j = 0; j < 4; j++){
                questionsAndInfo[i][j + 1] = (String) tempArray.get(j);
            }
            questionsAndInfo[i][5] = (String) currentQuestion.get("Key");
            questionsAndInfo[i][6] = (String) currentQuestion.get("Hint");
        }

        usedQuestions = new boolean[questions.size()];
    }

    public String[] getQandAandK(){
        return questionsAndInfo[uniqueRandomQuestion()];
    }

    public int uniqueRandomQuestion(){
        ArrayList<Integer> tempIndexes = new ArrayList<Integer>();
        for (int i = 0; i < usedQuestions.length; i++){
            if (!usedQuestions[i]){
                tempIndexes.add(i);
            }
        }
        if (tempIndexes.size() == usedQuestions.length){
            usedQuestions = new boolean[questionsAndInfo.length];
        }
        int indexToReturn = (int) (Math.random() * tempIndexes.size());
        usedQuestions[tempIndexes.get(indexToReturn)] = true;
        return indexToReturn;
    }
}