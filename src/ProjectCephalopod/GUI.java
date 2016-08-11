/******************************************************
***  Class Name: GUI
***  Class Author: Capri Sims
******************************************************
*** Purpose: Provides the GUI for the Neural Network
******************************************************
*** Date: June 21, 2016
******************************************************
*** TODO: Add Save & Load
***       Add interface to database
***       Clean interface
*******************************************************/
package ProjectCephalopod;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.filechooser.FileNameExtensionFilter;

public class GUI extends JFrame{
    
    private double[] input;
    private Network NN;
    private JTextArea text;
    private String enteredText;
    private boolean toTrain = false;
    private boolean created = false;
    private boolean successful = false;
    
    public GUI(){
        super("Biometric Security");
        setLayout(new BorderLayout());
        setSize(500, 700); 
        setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        
        JPanel top = new JPanel(), bottom = new JPanel(); 
        
        //TEXT AREA
        text = new JTextArea("Welcome! Please create a new network by clicking on 'New' above.", 35, 40);
        text.setEditable(true);
        text.setWrapStyleWord(true);
        JScrollPane scroller = new JScrollPane(text);
        top.add(scroller);
        add(top, BorderLayout.NORTH);
        
        //OK BUTTON
        JButton ok = new JButton("Enter");
        bottom.add(ok);
        bottom.setBorder(new EmptyBorder(20,20,20,20));
        add(bottom, BorderLayout.SOUTH);
        
        //MENU BAR
        JMenuBar bar = new JMenuBar();
        JMenu newMenu = new JMenu("New");
        JMenu trainMenu = new JMenu("Train");
        JMenu testMenu = new JMenu("Test");
        JMenu saveMenu = new JMenu("Save");
        JMenu loadMenu = new JMenu("Load");
        bar.add(newMenu);
        bar.add(trainMenu);
        bar.add(testMenu);
        bar.add(saveMenu);
        bar.add(loadMenu);
        setJMenuBar(bar);
        
        setVisible(true);
        
        //NEW CLICKED
        newMenu.addMenuListener(new MenuListener() { 
            public void menuSelected(MenuEvent e) {
                fileReader();
                if(successful){
                    NN = new Network(input);
                    created = true;
                    text.setText("Network Created!");
                    NN.setTextBox(text);
                }
                else{
                    text.setText("Try again!");
                }
            }
            public void menuDeselected(MenuEvent e) {}
            public void menuCanceled(MenuEvent e) {}
        });
        
        //TRAIN CLICKED
        trainMenu.addMenuListener(new MenuListener() {
            public void menuSelected(MenuEvent e) {
                
                if(created){
                    text.setText("Please enter the Expected Output here.");
                    toTrain = true;
                }
                else{
                    text.setText("Create a new network first!");
                }

            }
            public void menuDeselected(MenuEvent e) {}
            public void menuCanceled(MenuEvent e) {}
        });
        
        //TEST CLICKED
        testMenu.addMenuListener(new MenuListener() {
            public void actionPerformed(MenuEvent e) { 
                if(created){
                    text.setText("Enter new input values.");
                    fileReader(); 
                    NN.run();   
                }
                else{
                    text.setText("Create a new network first!");
                }
            }
            public void menuSelected(MenuEvent e) {}
            public void menuDeselected(MenuEvent e) {}
            public void menuCanceled(MenuEvent e) {}
        });
        
        //SAVE CLICKED
        saveMenu.addMenuListener(new MenuListener() {
            public void actionPerformed(MenuEvent e) { 
                if(created){
                    //TODO
                }
                else{
                    text.setText("Create a new network first!");
                }
            }
            public void menuSelected(MenuEvent e) {}
            public void menuDeselected(MenuEvent e) {}
            public void menuCanceled(MenuEvent e) {}
        });
        
        //LOAD CLICKED
        loadMenu.addMenuListener(new MenuListener() {
            public void actionPerformed(MenuEvent e) { 
                if(created){
                    //TODO
                }
                else{
                    text.setText("Create a new network first!");
                }
            }
            public void menuSelected(MenuEvent e) {}
            public void menuDeselected(MenuEvent e) {}
            public void menuCanceled(MenuEvent e) {}
        });
        
        //OK CLICKED
        ok.addActionListener((ActionEvent e) -> {
            enteredText = text.getText();
            if(toTrain){
                train();
            }
        });
        
        
    }
    
    /******************************************************
    ***  Method Name: fileReader
    ***  Method Author: Capri Sims
    ******************************************************
    *** Purpose: To read in a text file and populate array.
    *** Method Inputs: None
    *** Return value: None
    ******************************************************
    *** Date: July 21, 2016
    ******************************************************
    *** 
    *******************************************************/
    private void fileReader(){
        Path file = null;
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        fileChooser.setFileFilter(new FileNameExtensionFilter("Text", "txt"));
        int result = fileChooser.showOpenDialog(fileChooser);
        
        if(result != JFileChooser.CANCEL_OPTION)
            file = fileChooser.getSelectedFile().toPath();
        
        List<String> text = new ArrayList<>();
        try{ 
            text = Files.readAllLines(file);
        }
        catch(Exception ex){ System.err.println(ex); }
        
        int inputSize = 0;
        for(String line : text){
            inputSize += 2;
        }
        
        input = new double[inputSize];
        
        int i = 0;
        for(String line : text){ 
            //System.out.println(line); //DEBUGGER
            double first = Double.valueOf(line.substring(0, 2)); 
            //System.out.println(first); //DEBUGGER
            int index = line.indexOf('\t') + 1;
            double second = Double.valueOf(line.substring(index));
            input[i++] = first;
            input[i++] = second;
        }
        successful = true;
    }
    
    /******************************************************
    ***  Method Name: setText
    ***  Method Author: Capri Sims
    ******************************************************
    *** Purpose: Allows Network to change the text in GUI
    *** Method Inputs: String words
    *** Return value: None
    ******************************************************
    *** Date: July 21, 2016
    ******************************************************
    *** 
    *******************************************************/
    public void setText(String words){
        String original = text.getText();
        text.setText(original + "/n" + words);
    }
    
    /******************************************************
    ***  Method Name: train
    ***  Method Author: Capri Sims
    ******************************************************
    *** Purpose: Trains the network based on the given text.
    *** Method Inputs: None
    *** Return value: None
    ******************************************************
    *** Date: July 21, 2016
    ******************************************************
    *** Possibly change this to allow for multiple outputs
    ***     for flexibility
    *******************************************************/
    private void train(){
                        
        double num = 0;
        double[] output = new double[1];

        //Get the number from text box as a string and change to double
        try{
            num = Double.parseDouble(enteredText);
        }
        catch(Exception x){
            System.out.println(x);
            text.setText("Invalid!!! Enter the Expected Output as a single number.");
        }
      
        //Put the number into a size 1 array
        output[0] = num;

        //Train
        NN.train(output);
        
        toTrain = false;
    }
}
    
