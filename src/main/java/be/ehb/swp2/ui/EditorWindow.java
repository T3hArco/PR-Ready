package be.ehb.swp2.ui;

import be.ehb.swp2.entity.*;
import be.ehb.swp2.entity.question.QuestionAnswer;
import be.ehb.swp2.exception.DuplicateQuestionException;
import be.ehb.swp2.exception.QuizNotFoundException;
import be.ehb.swp2.exception.UserNoPermissionException;
import be.ehb.swp2.manager.*;
import be.ehb.swp2.util.PermissionHandler;
import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.BrowserFunction;
import com.teamdev.jxbrowser.chromium.JSValue;
import com.teamdev.jxbrowser.chromium.dom.By;
import com.teamdev.jxbrowser.chromium.dom.DOMDocument;
import com.teamdev.jxbrowser.chromium.dom.DOMElement;
import com.teamdev.jxbrowser.chromium.dom.DOMNode;
import com.teamdev.jxbrowser.chromium.events.FinishLoadingEvent;
import com.teamdev.jxbrowser.chromium.events.LoadAdapter;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;
import org.hibernate.SessionFactory;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by domienhennion on 10/12/15.
 */
public class EditorWindow extends JFrame implements Window {
    //answers
    static List<Answer> newAnswers = new ArrayList<Answer>();
    //question answers
    static List<QuestionAnswer> newQuestionAnswers = new ArrayList<QuestionAnswer>();
    private SessionFactory factory;
    private QuizManager quizManager;
    private Integer quizId;
    private List<Question> newQuestions = new ArrayList<Question>();
    private List<MediaURL> newVideoURLs = new ArrayList<MediaURL>();
    private List<MediaURL> newAudioURLs = new ArrayList<MediaURL>();
    private List<MediaURL> newImgURLs = new ArrayList<MediaURL>();
    private AnswerType currentAnswerType = null;
    private AnswerMediaType currenMediaType = null;

    private ArrayList<Integer> questionDatabaseIDs = new ArrayList<Integer>();
    private ArrayList<Integer> answerDatabaseIDs = new ArrayList<Integer>();

    /**
     * Constructor for Editor Window
     *
     * @param factory Session factory singleton
     * @param quizId  the id for the parent quiz
     * @throws UserNoPermissionException
     */
    public EditorWindow(SessionFactory factory, Integer quizId) throws UserNoPermissionException {
        this.factory = factory;
        this.quizManager = new QuizManager(factory);
        this.quizId = quizId;
        if (!PermissionHandler.currentUserHasPermission(factory, UserRole.ADMINISTRATOR))
            throw new UserNoPermissionException();

        this.initComponents();
    }

    /**
     * Initialize the GUI components
     */
    public void initComponents() {
        final Browser browser = new Browser();
        final JFrame parent = this;
        final JDialog dialog = new JDialog(parent, "Editor", true);

        browser.loadURL("http://dtprojecten.ehb.be/~PR-Ready/editor/editor.html?1459713469871463");

        //register error message function from web
        browser.registerFunction("jxBrowserAlert", new BrowserFunction() {
            public JSValue invoke(JSValue... args) {
                String message = args[0].getString();
                JOptionPane.showMessageDialog(parent, message);
                return JSValue.create("jxBrowserAlert!");
            }
        });


        browser.registerFunction("saveQuestionToJava", new BrowserFunction() {
            public JSValue invoke(JSValue... args) {
                //read web info current question
                JSValue questionNumber = args[0];
                JSValue questionText = args[1];
                JSValue answerType = args[2];
                JSValue mediaType = args[3];
                JSValue mediaURL = args[4];

                //convert info
                getAnswerTypeFromWeb(answerType.getString());
                getMediaTypeFromWeb(mediaType.getString());

                //add new question in java
                Question currentQuestion = new Question();

                currentQuestion.setId((int) questionNumber.getNumber());
                currentQuestion.setText(questionText.getString());
                currentQuestion.setAnswerType(currentAnswerType);
                currentQuestion.setAnswerMediaType(currenMediaType);

                if (mediaURL.isNull()) {
                    System.out.println("no url needed for question " + (int) questionNumber.getNumber());
                } else if (mediaURL.toString() != null) {
                    //dit is de locale id nog
                    //straks bij het uploaden naar de database moet dit worden aangepast !
                    getUrlFromWeb((int) questionNumber.getNumber(), mediaURL.getString());
                } else {
                    System.out.println("Error: url for question " + (int) questionNumber.getNumber() + " is empty.");
                }

                //add the new question to the "new questions" list
                newQuestions.add(currentQuestion);

                return JSValue.createUndefined();
            }
        });

        browser.registerFunction("saveFillInAnswerToJava", new BrowserFunction() {
            public JSValue invoke(JSValue... args) {
                System.out.println("saveAnswerToJava Function is invoked!");

                //get vars
                JSValue questionNumber = args[0];
                JSValue answerNumber = args[1];
                JSValue answertext = args[2];

                //fill in answer
                Answer newAnswer = new Answer();
                newAnswer.setAnswerId((int) answerNumber.getNumber());
                newAnswer.setText(answertext.getString());


                QuestionAnswer newQuestionAnswer = new QuestionAnswer((int) questionNumber.getNumber(), (int) answerNumber.getNumber());

                newAnswers.add(newAnswer);
                newQuestionAnswers.add(newQuestionAnswer);


                return JSValue.create("saveAnswerToJava!");
            }
        });


        browser.registerFunction("saveChoiceAnswerToJava", new BrowserFunction() {
            public JSValue invoke(JSValue... args) {
                System.out.println();
                System.out.println("saveAnswerToJava Function is invoked!");

                JSValue questionNumber = args[0];
                JSValue answerNumber = args[1];
                JSValue answertext = args[2];
                JSValue iscorret = args[3];

                Answer newAnswer = new Answer();
                newAnswer.setAnswerId((int) answerNumber.getNumber());
                newAnswer.setText(answertext.getString());

                System.out.println("with boolean");
                boolean bIscorret = iscorret.getBoolean();
                QuestionAnswer newQuestionAnswer = new QuestionAnswer((int) questionNumber.getNumber(), (int) answerNumber.getNumber(), bIscorret);

                newAnswers.add(newAnswer);
                newQuestionAnswers.add(newQuestionAnswer);

                return JSValue.create("saveAnswerToJava!");
            }
        });


        browser.registerFunction("SavingDone", new BrowserFunction() {
            public JSValue invoke(JSValue... args) {
                System.out.println("SavingDone");

                JOptionPane.showMessageDialog(parent, "Saving Done!");
                System.out.println("newQuestions.toString() = " + newQuestions.toString());
                System.out.println("newAnswers.toString() = " + newAnswers.toString());
                System.out.println("newQuestionAnswers.toString() = " + newQuestionAnswers.toString());

                int newDatabaseID =0;
                int OriginalQuestionIDinEditor = 0;
                //add questions to the database
                QuestionManager quizManager = new QuestionManager(factory);
                for (Question q : newQuestions) {
                    try {
                        q.setTitle("Quiz");
                        q.setQuizId(quizId);
                        q.setParentId(quizId);

                        System.out.println("Original ID was: " + q.getId());
                        OriginalQuestionIDinEditor = q.getId();

                        newDatabaseID =  quizManager.addQuestion(q);
                        System.out.println("Question added to the database, it's ID is now: " + newDatabaseID);
                        System.out.println("Original ID is now: " + q.getId());
                        questionDatabaseIDs.add(newDatabaseID);

                        //update reference  ID's for the  media ons
                        // so they match the ID's in the Database
                        //Audio
                        for(MediaURL audio : newAudioURLs){
                            System.out.println("updating audio");
                            if(audio.getId() == OriginalQuestionIDinEditor)
                            {
                                System.out.println("audio updated");
                                audio.setId(newDatabaseID);
                            }
                        }
                        //Video
                        for(MediaURL video : newVideoURLs){
                            System.out.println("updating video");
                            if(video.getId() == OriginalQuestionIDinEditor)
                            {
                                System.out.println("video updated");
                                video.setId(newDatabaseID);
                            }
                        }
                        //IMG
                        for(MediaURL img : newImgURLs){
                            System.out.println("updating img");
                            if(img.getId() == OriginalQuestionIDinEditor)
                            {
                                System.out.println("image updated");
                                img.setId(newDatabaseID);
                            }
                        }

                        System.out.println("newAudioURLs is now = " + newAudioURLs.toString());
                        System.out.println("newVideoURLs is now = " + newVideoURLs.toString());
                        System.out.println("newImgURLs is now = " +   newImgURLs.toString());

                    } catch (DuplicateQuestionException e) {
                        e.printStackTrace();
                    }
                }

                //add answers to the database
                AnswerManager answerManager = new AnswerManager(factory);
                for (Answer a : newAnswers) {
                    newDatabaseID = answerManager.addAnswer(a);

                    System.out.println("Answer added to the database, it's ID is now: " + newDatabaseID);
                    answerDatabaseIDs.add(newDatabaseID);
                }


                //this is where the magic happens
                //the id's inside each question_answer have to be updated befor being pushed inside the database.
                //Let's do this!

                int correspondingQuestion = 0;
                int currentQuestionAnswer = 0;
                for (QuestionAnswer qa : newQuestionAnswers) {
                    //Update QuestonID
                    //-----------------
                    //to what question does this question_answer belong
                    // - 1 !!! QuestionId 1 is 0 in the arrayList
                    correspondingQuestion = qa.getQuestionId()- 1;
                    //update the ID
                    qa.setQuestionId(questionDatabaseIDs.get(correspondingQuestion));

                    //Update AnswerID
                    //-----------------
                    //for each question_answer there is a question so no need to check corresponding, just change the id's
                    //to what question does this question_answer belong

                    qa.setAnswerId(answerDatabaseIDs.get(currentQuestionAnswer));
                    ++currentQuestionAnswer;

                    System.out.println("converting question answer " + currentQuestionAnswer);
                }

                System.out.println("conversion done");

                //add question_questions to the database
                QuestionAnswerManager questionAnswerManager = new QuestionAnswerManager(factory);
                for (QuestionAnswer qa : newQuestionAnswers) {

                    System.out.println("before");
                    questionAnswerManager.addQuestionAnswer(qa.getQuestionId(), qa.getAnswerId(), qa.isCorrect());
                    System.out.println("after");
                    System.out.println("QuestionAnswer added to the database, it's ID is now: " + newDatabaseID);
                }

                VideoQuestionManager videoQuestionManager = new VideoQuestionManager(factory);
                for (MediaURL mu : newVideoURLs) {
                    videoQuestionManager.addVideoQuestion(mu.getId(), mu.getUrl());
                }

                AudioQuestionManager audioQuestionManager = new AudioQuestionManager(factory);
                for (MediaURL mu : newAudioURLs) {
                    audioQuestionManager.addAudioQuestion(mu.getId(), mu.getUrl());
                }

                ImageQuestionManager imageQuestionManager = new ImageQuestionManager(factory);
                for (MediaURL mu : newImgURLs) {
                    imageQuestionManager.addImageQuestion(mu.getId(), mu.getUrl());
                }

                browser.dispose();
                dialog.setVisible(false);
                dialog.dispose();

                new OverviewWindow(factory);

                return JSValue.create("SavingDone!");
            }
        });

        browser.addLoadListener(new LoadAdapter() {
            @Override
            public void onFinishLoadingFrame(FinishLoadingEvent event) {
                String base64 = null;

                try {
                    base64 = quizManager.getQuizById(quizId).getLogo();
                } catch (QuizNotFoundException e) {
                    e.printStackTrace();
                }

                DOMDocument document = event.getBrowser().getDocument();
                DOMNode logoDiv = document.findElement(By.id("demo"));


                DOMElement logo = document.createElement("img");
                logo.setAttribute("src", "data:image/png;base64," + base64);
                logoDiv.appendChild(logo);
            }
        });

        dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        dialog.add(new BrowserView(browser), BorderLayout.CENTER);
        dialog.setResizable(false);
        dialog.setUndecorated(true);
        dialog.setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds());
        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true);
        dialog.setAlwaysOnTop(true);
    }

    public void getAnswerTypeFromWeb(String stringFromWeb) {
        if (stringFromWeb.equals("Choice")) {
            currentAnswerType = AnswerType.MULTIPLE_CHOICE;
        }
        if (stringFromWeb.equals("String")) {
            currentAnswerType = AnswerType.KEYWORD;
        }
    }

    public void getMediaTypeFromWeb(String stringFromWeb) {
        if (stringFromWeb.equals("Audio")) {
            currenMediaType = AnswerMediaType.AUDIO;
        }
        if (stringFromWeb.equals("Video")) {
            currenMediaType = AnswerMediaType.VIDEO;
        }
        if (stringFromWeb.equals("IMG")) {
            currenMediaType = AnswerMediaType.IMAGE;
        }
        if (stringFromWeb.equals("None")) {
            currenMediaType = AnswerMediaType.EMPTY;
        }
    }

    public void getUrlFromWeb(int id, String url) {
        MediaURL newMediaUrl = new MediaURL(id, url);

        switch (currenMediaType) {
            case AUDIO:
                newAudioURLs.add(newMediaUrl);
                break;

            case VIDEO:
                newVideoURLs.add(newMediaUrl);
                break;

            case IMAGE:
                newImgURLs.add(newMediaUrl);
                break;

            case EMPTY:
                break;

            default:
                System.out.println("default");
                break;
        }

    }
}