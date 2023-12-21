import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimpleMinds extends JFrame implements ActionListener {

    // 2 Buttons, 1 Textfield is defined globally
    // So, that ActionListener can detect their activity.
    JButton b1, b2;
    JTextField t1,t2;

    SimpleMinds() { //Everything is defined in default constructor, so user don't have to trigger anything to open the game.

        //2 Colors selected. So, I don't have to pick them again and again.
        Color MainColor = new Color(30, 144, 254);
        Color PrimaryColor = new Color(160, 32, 240);

        //Main Window Coordinates on x,y axis and size of the window.
        setBounds(400, 200, 1200, 500);
        getContentPane().setBackground(Color.white);
        setDefaultCloseOperation(SimpleMinds.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(null); //JFrame's Default Layout disabled.

        //Image is fetched from icons folder and added to label to actually show on JFrame.
        ImageIcon il = new ImageIcon(ClassLoader.getSystemResource("icons/72gi.gif"));
        JLabel l1 = new JLabel(il);
        l1.setBounds(0, 0, 600, 500);
        add(l1);

        //Name of the Game. Main Heading.
        JLabel l2 = new JLabel("Simple Minds");
        l2.setFont(new Font("Mongolian Baiti", Font.BOLD, 40)); // setting Font & FontStyle.
        l2.setBounds(780, 40, 300, 45);
        l2.setForeground(PrimaryColor); //Picked color is passed here.
        add(l2);

        //Enter your Name Text.
        JLabel l3 = new JLabel("Enter your Name");
        l3.setFont(new Font("Mongolian Baiti", Font.BOLD, 18));
        l3.setBounds(810, 150, 300, 20);
        l3.setForeground(MainColor);
        add(l3);

        //Input Field where user can enter his name which will be fetched by the program to use later on other Screens.
        t1 = new JTextField();
        t1.setBounds(735, 180, 300, 25);
        t1.setFont(new Font("Times New Roman", Font.BOLD, 20));
        add(t1);

        //Enter your Password Text.
        JLabel l4 = new JLabel("Enter your Password");
        l4.setFont(new Font("Mongolian Baiti", Font.BOLD, 18));
        l4.setBounds(810, 250, 300, 20);
        l4.setForeground(MainColor);
        add(l4);

        //Input Field where user can enter his password which will be fetched by the program to use later on other Screens.
        t2 = new JTextField();
        t2.setBounds(735, 280, 300, 25);
        t2.setFont(new Font("Times New Roman", Font.BOLD, 20));
        add(t2);

        //Rules Button which will close the Main Frame and opens another window with rules.
        b1 = new JButton("Rules");
        b1.setBounds(735, 370, 120, 25);
        b1.addActionListener(this); //connecting button to the event Listener.
        b1.setBackground(PrimaryColor);
        b1.setForeground(Color.white);
        add(b1);

        //Exit Button which will Close the Program.
        b2 = new JButton("Exit");
        b2.setBounds(915, 370, 120, 25);
        b2.addActionListener(this); //connecting button to the event Listener.
        b2.setBackground(MainColor);
        b2.setForeground(Color.white);
        add(b2);

        //Main Frame is set to visible by default.
        setVisible(true);
    }

    //Action Listener Interface's Abstract function overRided. which actually listens to the events.
    @Override
    public void actionPerformed(ActionEvent e) {

        //To check which Button was pressed by the user [Rules or Exit].
        if (e.getSource() == b1) {
            String name = t1.getText();
            String pass = t2.getText();
            this.dispose();  //Main Frame will dispose and Rules Frame will show up.
            new Rules(name,pass); //Name of the person entered in textfield is being passed to Rules Frame via name argument.
        } else {
            System.exit(0); // exits the program.
        }

    }

    public static void main(String[] args) {
        //Simply Create the class and Default Constructor will handle everything.
        new SimpleMinds();
    }

}