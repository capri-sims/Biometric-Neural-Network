/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

/**
 *
 * @author Capri
 */
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
        setSize(500, 700); //larger size for presentation??
        setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        
        JPanel top = new JPanel(), bottom = new JPanel(); 
        
        text = new JTextArea("Welcome! Please create a new network by clicking on 'New' above.", 35, 40);
        text.setEditable(true);
        text.setWrapStyleWord(true);
        JScrollPane scroller = new JScrollPane(text);
        top.add(scroller);
        add(top, BorderLayout.NORTH);
        
        JButton ok = new JButton("Enter");
        bottom.add(ok);
        bottom.setBorder(new EmptyBorder(20,20,20,20));
        add(bottom, BorderLayout.SOUTH);
        
        JMenuBar bar = new JMenuBar();
        JMenu newMenu = new JMenu("New");
        JMenu trainMenu = new JMenu("Train");
        JMenu testMenu = new JMenu("Test");
        JMenu saveMenu = new JMenu("Save");
        bar.add(newMenu);
        bar.add(trainMenu);
        bar.add(testMenu);
        bar.add(saveMenu);
        setJMenuBar(bar);
        //NEEDS LOAD BUTTON
        
        setVisible(true);
        
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
                
        saveMenu.addMenuListener(new MenuListener() {
            public void actionPerformed(MenuEvent e) { 
                if(created){
                    
                }
                else{
                    text.setText("Create a new network first!");
                }
            }
            public void menuSelected(MenuEvent e) {}
            public void menuDeselected(MenuEvent e) {}
            public void menuCanceled(MenuEvent e) {}
        });
        
        ok.addActionListener((ActionEvent e) -> {
            enteredText = text.getText();
            if(toTrain){
                train();
            }
        });
        
        
    }
    
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
        //boolean firstChar = true; //because for some reason, the first char is always some kind of dot that resists removal
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
    
    public void setText(String words){
        String original = text.getText();
        text.setText(original + "/n" + words);
    }
    
    private void train(){
                        
        double num = 0;
        double[] output = new double[1];

        boolean bad = true;

        try{
            num = Double.parseDouble(enteredText);
        }
        catch(Exception x){
            System.out.println(x);
            text.setText("Invalid!!! Enter the Expected Output as a single number.");
        }
      
        output[0] = num;

        NN.train(output);
        
        toTrain = false;
    }
}
    

