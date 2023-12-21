import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Quiz extends JFrame implements ActionListener {

    public static int count = 0; //counter to keep track of No. of Question currently displayed on screen.
    public static int timer = 15; //15 seconds of time to solve each Question.
    public static boolean ans_given = false; //keep track whether the user has answerd or not.
    public static int score = 0; //Stores total Score of the user.

    Dimension screenSize;

    JButton next, lifeline, submit; //Buttons [Next, LifeLine, Submit]
    JLabel qno, question; //Question No. Text, Actual Quesion Text.
    JRadioButton opt1, opt2, opt3, opt4; //4 options given to the user.
    ButtonGroup options; //4 options are grouped as 1 input.

    String q[][] = new String[10][5]; //Array Stores Question and it's 4 options.
    String qa[][] = new String[10][2]; //Array Stores Correct Answers. 10 questions - 1 correct answer. (2 to use lifeline).
    String pa[][] = new String[10][1]; //Answers choosen by the user - 10 questions - 1 option chosen.

    String    username; //username to send to Score screen.
    String    password; //password to send to Score screen.

    Quiz(String username, String password) { //username is being accepted as a parameter.

        this.username = username; //Name teken from previous Screen to stored to pass to Score Screen.
        this.password = password; //Name teken from previous Screen to stored to pass to Score Screen.
        Color MainColor = new Color(30, 144, 254);
        Color PrimaryColor = new Color(160, 32, 240);

        //sets the Position and Size of Quiz Screen.
        // Get the screen size, and perform some calculation for ideal size
        screenSize = Toolkit.getDefaultToolkit().getScreenSize(); // Get Users Screen Size
        if (screenSize.getWidth() > 800 && screenSize.getHeight() > 600) {
            screenSize.setSize(screenSize.getWidth() / 1.2, screenSize.getHeight() / 1.2);
        }
//        System.out.println(screenSize.getWidth());
//        System.out.println(screenSize.getHeight());
//        setBounds(250, 50, 1440, 950);
        setSize(screenSize);
        setResizable(false);
        setDefaultCloseOperation(Quiz.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.white);
        setLayout(null);

        //Image Icons takes image from icons folder and adds it to label to show on screen.
        ImageIcon I1 = new ImageIcon(ClassLoader.getSystemResource("icons/quiz.jpg"));
        JLabel l1 = new JLabel(I1);
        l1.setVerticalAlignment(JLabel.TOP);
        l1.setBounds(0, 0, (int) screenSize.getWidth(), (int) screenSize.getHeight());
        add(l1);

        // Questions and thier Options
        q[0][0] = "Which is used to find and fix bugs in the Java programs?";
        q[0][1] = "JVM";
        q[0][2] = "JDB";                    //1
        q[0][3] = "JDK";
        q[0][4] = "JRE";

        q[1][0] = "What is the return type of the hashCode() method in the Object class?";
        q[1][1] = "Object";
        q[1][2] = "int";                 //2
        q[1][3] = "long";
        q[1][4] = "void";

        q[2][0] = "Which package contains the Random class?";
        q[2][1] = "java.util package";
        q[2][2] = "java.lang package";      //3
        q[2][3] = "java.awt package";
        q[2][4] = "java.io package";

        q[3][0] = "An interface with no fields or methods is known as?";
        q[3][1] = "Runnable Interface";
        q[3][2] = "Abstract Interface";     //4
        q[3][3] = "Marker Interface";
        q[3][4] = "CharSequence Interface";

        q[4][0] = "In which memory a String is stored, when we create a string using new operator?";
        q[4][1] = "Stack";
        q[4][2] = "String memory";          //5
        q[4][3] = "Random storage space";
        q[4][4] = "Heap memory";

        q[5][0] = "Which of the following is a marker interface?";
        q[5][1] = "Runnable interface";
        q[5][2] = "Remote interface";       //6
        q[5][3] = "Readable interface";
        q[5][4] = "Result interface";

        q[6][0] = "Which keyword is used for accessing the features of a package?";
        q[6][1] = "import";
        q[6][2] = "package";                //7
        q[6][3] = "extends";
        q[6][4] = "export";

        q[7][0] = "In java, jar stands for?";
        q[7][1] = "Java Archive Runner";
        q[7][2] = "Java Archive";           //8
        q[7][3] = "Java Application Resource";
        q[7][4] = "Java Application Runner";

        q[8][0] = "Which of the following is a mutable class in java?";
        q[8][1] = "java.lang.StringBuilder";
        q[8][2] = "java.lang.Short";        //9
        q[8][3] = "java.lang.Byte";
        q[8][4] = "java.lang.String";

        q[9][0] = "Which of the following option leads to the portability and security of Java?";
        q[9][1] = "Bytecode is executed by JVM";
        q[9][2] = "The applet makes the Java code secure and portable";     //10
        q[9][3] = "Use of exception handling";
        q[9][4] = "Dynamic binding between objects";
        // Questions and thier Options

        // Correct Answers
        qa[0][1] = "JDB";                               //1
        qa[1][1] = "int";                               //2
        qa[2][1] = "java.util package";                 //3
        qa[3][1] = "Marker Interface";                  //4
        qa[4][1] = "Heap memory";                       //5
        qa[5][1] = "Remote interface";                  //6
        qa[6][1] = "import";                            //7
        qa[7][1] = "Java Archive";                      //8
        qa[8][1] = "java.lang.StringBuilder";           //9
        qa[9][1] = "Bytecode is executed by JVM";       //10
        // Correct Answers

        //Question Number Text.
        qno = new JLabel();
        qno.setBounds(50, 400, 50, 30);
        qno.setFont(new Font("Tahoma", Font.PLAIN, 24));
        add(qno);

        //Actual Question Text.
        question = new JLabel();
        question.setBounds(100, 400, 900, 30);
        question.setFont(new Font("Tahoma", Font.PLAIN, 24));
        add(question);

        //Option 1.
        opt1 = new JRadioButton();
        opt1.setFont(new Font("Dialog", Font.PLAIN, 20));
        opt1.setBackground(Color.white);
        opt1.setBounds(100, 480, 300, 30);
        add(opt1);

        //Option 2.
        opt2 = new JRadioButton();
        opt2.setFont(new Font("Dialog", Font.PLAIN, 20));
        opt2.setBackground(Color.white);
        opt2.setBounds(100, 520, 300, 30);
        add(opt2);

        //Option 3.
        opt3 = new JRadioButton();
        opt3.setFont(new Font("Dialog", Font.PLAIN, 20));
        opt3.setBackground(Color.white);
        opt3.setBounds(100, 560, 300, 30);
        add(opt3);

        //Option 4.
        opt4 = new JRadioButton();
        opt4.setFont(new Font("Dialog", Font.PLAIN, 20));
        opt4.setBackground(Color.white);
        opt4.setBounds(100, 600, 300, 30);
        add(opt4);

        //4 options grouped as 1 selectable Button.
        options = new ButtonGroup();
        options.add(opt1);
        options.add(opt2);
        options.add(opt3);
        options.add(opt4);

        //Next Button to Go to Next Question.
        next = new JButton("Next");
        next.setFont(new Font("Tahoma", Font.PLAIN, 22));
        next.addActionListener(this);
        next.setBackground(PrimaryColor);
        next.setForeground(Color.white);
        next.setBounds((int) screenSize.getWidth() - 280, 550, 200, 40);
        add(next);

        //LifeLine Button to limit the options from 4 to 2.(1 wrong, 1 correct)
        lifeline = new JButton("50 - 50 Lifeline");
        lifeline.setFont(new Font("Tahoma", Font.PLAIN, 22));
        lifeline.addActionListener(this);
        lifeline.setBackground(PrimaryColor);
        lifeline.setForeground(Color.white);
        lifeline.setBounds((int) screenSize.getWidth() - 280, 630, 200, 40);
        add(lifeline);

        //Submit Button to End the Quiz on last Quesion.
        submit = new JButton("Submit");
        submit.setFont(new Font("Tahoma", Font.PLAIN, 22));
        submit.addActionListener(this);
        submit.setEnabled(false);
        submit.setBackground(MainColor);
        submit.setForeground(Color.white);
        submit.setBounds((int) screenSize.getWidth() - 280, 710, 200, 40);
        add(submit);

        start(0);

    }

    //actionPerformed() Overrided from ActionListener Interface.
    @Override
    public void actionPerformed(ActionEvent e) {

        //check which button is pressed. [Next, Submit, Lifeline]
        if (e.getSource() == next) {
            repaint(); //calls Paint() function to change values on same screen.
            opt1.setEnabled(true); //will enable all options if lifeline is used in the previos question.
            opt2.setEnabled(true);
            opt3.setEnabled(true);
            opt4.setEnabled(true);
            
            ans_given = true; //User has chosen some Option on the Click of Next Button.

            if (options.getSelection() == null) {
                //if user does not chose any option within 15 sec, Empty String will be saved in the Answer Array.
                pa[count][0] = ""; 
            } else {
                //Value of choosen answer will be fetched and stored in the 1st memory block of the Answer Array.
                pa[count][0] = options.getSelection().getActionCommand();
            }

            if (count == 8) {
                //Submit Button will be Disabled until last Question.
                //Next Button will be Enabled until last Question.
                next.setEnabled(false);
                submit.setEnabled(true);
            }

            count++; //Next Question will show up on the click of Next Button.
            start(count); //calls start() function and passes real question count as argument to update initial values.

        } else if (e.getSource() == submit) {

            //User has chosen some Option on the Click of Submit Button.
            ans_given = true;

            if (options.getSelection() == null) {
                 //if user does not chose any option within 15 sec, Empty String will be saved in the Answer Array.
                pa[count][0] = "";
            } else {
                //Value of choosen answer will be fetched and stored in the 1st memory block of the Answer Array.
                pa[count][0] = options.getSelection().getActionCommand();
            }

            //on Click on Submit Button, It matches all the Answers Choosen saved in pa[][] Array to the Array with correct Answers qa[][].
            for (int i = 0; i < pa.length; i++) {
                if (pa[i][0].equals(qa[i][1])) {
                    //if answers right, Total Score will increment by 10.
                    score += 10;
                } else {
                    //if answers wrong, Total Score will increment by 0.
                    score += 0;
                }
            }
            Add(); //call Add function, which will add Name & Score to the DataBase file.
            count = 0;
            
            this.dispose(); //Quiz Screen will dispose on click of Submit Button.
            
            //Score Screen will open and Name & Score & Password will be passed as an argument.
            new Score(username, score, password).setVisible(true);
            score = 0;


            
        } else if (e.getSource() == lifeline) {
            
            //LifeLine Functionality Trick.. only Options 1 or 4 are correct in Question 2, 4, 6, 8, 9.
            //if Lifeline is used on these questions Only Option 2 & 3 will disable.
            if(count == 2 || count == 4 || count == 6 || count == 8 || count == 9) {
                opt2.setEnabled(false);
                opt3.setEnabled(false);
            } else {
                //if Lifeline is used on 0,1,3,5,7 questions Only Option 1 & 4 will disable.
                //Because only Option 2 or 3 is Correct.
                opt1.setEnabled(false);
                opt4.setEnabled(false);
            }
            lifeline.setEnabled(false);

        }
    }

    //same paint() function called by Next Button.
    public void paint(Graphics g) {
        super.paint(g); //Parent Only rebuilds assets that were changed in childs.

        //Timer Text counting from 15 - 0.
        String time = "Time Left = " + timer + " seconds"; // 15
        g.setColor(Color.RED);
        g.setFont(new Font("Tahoma", Font.BOLD, 20));

        //checks if the timer has reached 0 and changes text to Times Up.
        if (timer > 0) {
            g.drawString(time, (int) screenSize.getWidth() - 280, 520);
        } else {
            g.drawString("Times Up!!", (int) screenSize.getWidth() - 280, 520);
        }

        timer--; //decrements timer by 1 on every repaint.

        try {
            //Repaints Screen after every 1 second.
            Thread.sleep(1000);
            repaint();

        } catch (Exception e) {
            e.printStackTrace(); //Catched any Error in Runtime.
        }

        if (ans_given) {
            //check if timer reaches 0, it resets back to 15.
            ans_given = false;
            timer = 15;
        } else if (timer < 0) {
            //checks if user has pressed next button and resets timer back to 15.
            timer = 15;

            //will enable all options if lifeline is used in the previos question.
            opt1.setEnabled(true);
            opt2.setEnabled(true);
            opt3.setEnabled(true);
            opt4.setEnabled(true);

            if (count == 8) {
                //Submit Button will be Disabled until last Question.
                //Next Button will be Enabled until last Question.
                next.setEnabled(false);
                submit.setEnabled(true);
            }
            if (count == 9) {
                if (options.getSelection() == null) {
                    //if user does not chose any option within 15 sec, Empty String will be saved in the Answer Array.
                    pa[count][0] = "";
                } else {
                    //Value of choosen answer will be fetched and stored in the 1st memory block of the Answer Array.
                    pa[count][0] = options.getSelection().getActionCommand();
                }

                //if Time ends, It matches all the Answers Choosen saved in pa[][] Array to the Array with correct Answers qa[][].
                for (int i = 0; i < pa.length; i++) {
                    if (pa[i][0].equals(qa[i][1])) {
                        score += 10; //if answers right, Total Score will increment by 10.
                    } else {
                        score += 0; //if answers wrong, Total Score will increment by 0.
                    }
                }

                Add();
                count = 0;
                
                this.dispose(); // Closes Quiz Screen.
                new Score(username, score, password).setVisible(true);
                score = 0;
                //Opens Score Screen and passes username, Calculated Score, Password as arguments.

            } else {
                if (options.getSelection() == null) {
                    //if user does not chose any option within 15 sec, Empty String will be saved in the Answer Array.
                    pa[count][0] = "";
                } else {
                    //Value of choosen answer will be fetched and stored in the 1st memory block of the Answer Array.
                    pa[count][0] = options.getSelection().getActionCommand();
                }
                count++; //Next Question will show up on Time end.
                start(count); //calls start() function and passes real question count as argument to update initial values.
            }
        }
    }

    //Start() takes count as parameter and Calculates Dynamically
    //No. of Question, Title of Question, Options of Question, Correct Answer, Choosed Answer.
    public void start(int count) {
        qno.setText(count + 1 + ". "); //Question Number.
        question.setText(q[count][0]); //Question Title.

        opt1.setText(q[count][1]);          //Takes the Values of the chosen option.
        opt1.setActionCommand(q[count][1]); //and stores that value in Option 1.

        opt2.setText(q[count][2]);
        opt2.setActionCommand(q[count][2]);

        opt3.setText(q[count][3]);
        opt3.setActionCommand(q[count][3]);

        opt4.setText(q[count][4]);
        opt4.setActionCommand(q[count][4]);

        options.clearSelection(); //let's user to to choose any option.
    }

    public void Add() {
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            String path = "jdbc:ucanaccess://C:\\Quiz_App\\src\\database\\data.accdb";
            Connection connection = DriverManager.getConnection(path);
            java.sql.Statement statement = connection.createStatement();
            String qry = "INSERT INTO ScoreBoard (Name,Score) VALUES ('" + username + "'," + score + ")";
            statement.executeUpdate(qry);
            System.out.println(username + " has been successfully Stored in DataBase having " + score + " Points");

        } catch (SQLException | ClassNotFoundException e1) {
            e1.printStackTrace();
        }
    }

    
    public static void main(String[] args) {
        //empty String is passed on startup because the real Name & Password will be passed from the Rules Screen.
        new Quiz("","").setVisible(true);
    }

}
