/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProjectCephalopod;

import java.util.Random;

/**
 *
 * @author Capri
 */
public class Network {
    
    private int inputSize;
    private int outputSize = 1;
    private int hiddenSize; //experiment

    private double[] expectedOutput = new double[outputSize];
    private double learningRate = .2; //TODO: find learning rate
    
    private double[] input; 
    private double[] output = new double[outputSize];
    private double[] hidden; //1 hidden layer
    private double[][] synapse0;
    private double[][] synapse1;
    private double bias0;
    private double bias1;
    
    double[] delta1;
    double[] delta0;
    
    private int iterations = 5000;
    
    public Network(double[] input, double[] expectedOutput){
        
        this.input = input;
        inputSize = input.length;
        hiddenSize = (inputSize + outputSize) / 2;
        hidden = new double[hiddenSize];
        synapse0 = new double[inputSize][hiddenSize];
        synapse1 = new double[hiddenSize][outputSize];
        delta1 = new double[outputSize];
        delta0 = new double[hiddenSize];
        this.expectedOutput = expectedOutput;
        
        initWeights(); //initialize the weights in synapse0 and synapse1

        //TRAINING
        for(int i = 0; i < iterations; i++){ //or error reaches x?
            feedForward();
            //calcError();
            feedBackwards();
        }
        
        System.out.println(output[0]);
        
        //TEST
        
        //UPLOAD TO DATABASE 
        //synapse0, synapse1, bias0, bias1
        
    }
    
    private double tanhPrime(double n){
        return (1 - Math.pow(Math.tan(n), 2));
    }
    
    private void initWeights(){
        
        Random random = new Random(); //random number generator 

        //Initialize synapse0 with random weights
        for(int i = 0; i < inputSize; i++){
            for(int j = 0; j < hiddenSize; j++){
                synapse0[i][j] = random.nextDouble();
                //System.out.println(synapse0[i][j]); //DEBUGGER
            }
        }
       
        //Initialize synapse1 with random weights
        for(int i = 0; i < hiddenSize; i++){
            for(int j = 0; j < outputSize; j++){
                synapse1[i][j] = random.nextDouble();
            }
        }
        bias0 = 0; //for later
        bias1 = 0;
    }
    
    private void feedForward(){
        
        double num = 0;
        
        //HIDDEN LAYER 
        //hidden = input * synapse1 + bias1
        for(int i = 0; i < hiddenSize; i++){
            for(int j = 0; j < inputSize; j++){
                num += input[j] * synapse0[j][i]; //multiply matrices 
            }
            num += bias0; //add bias
            hidden[i] = Math.tanh(num); //squashed
            num = 0;
        }
        
        //OUTPUT LAYER
        //output = hidden * synapse 2 + bias2
        for(int j = 0; j < hiddenSize; j++){
            num += hidden[j] * synapse1[j][0]; //multiply matrices
        }
        num += bias1; //add bias
        output[0] = Math.tanh(num); //squashed
        
        //System.out.println(output[0] + " | Error: " + (expectedOutput[0] - output[0]));
    }
    
//    private void calcError(){
//        //error = output - calculated Output
//        double error = 0;
//        
//        for(int i = 0; i < outputSize; i++){
//            error += expectedOutput[i] - output[i];
//        }
//    }
    
    private void feedBackwards(){
        
        double error = 0;
        double change = 0;
        
        //FIND ERROR FOR OUTPUT
//        for(int i = 0; i < outputSize; i++){
//            error1 += expectedOutput[i] - output[i];
//        }
        //FIND DELTA FOR OUTPUT
        for(int i = 0; i < outputSize; i++){
            error = expectedOutput[i] - output[i];
            delta1[i] = tanhPrime(output[i]) * error; 
        }
        
        //FIND ERROR FOR HIDDEN
//        for(int i = 0; i < hiddenSize; i++){
//            for(int j = 0; j < outputSize; j++){ //E delta * weights
//                error0 += delta1[j] * synapse1[i][j]; //?
//            }
//        }
        //FIND DELTA FOR HIDDEN
        for(int i = 0; i < hiddenSize; i++){
            delta0[i] = tanhPrime(hidden[i]) * sumError(i); 
        }
        
        //UPDATE SYNAPSE1
        for(int i = 0; i < outputSize; i++){
            for(int j = 0; j < hiddenSize; j++){
                change = learningRate * delta1[i] * hidden[j];
                //System.out.println("Synapse1 : " + change); //DEBUGGER
                synapse1[j][i] += change;
                System.out.println("Synapse1[" + j + "][" + i + "] = " + synapse1[j][i]); //DEBUGGER
            }
        }
        
        //UPDATE SYNAPSE0
        for(int i = 0; i < hiddenSize; i++){
            for(int j = 0; j < inputSize; j++){
                change = learningRate * delta0[i] * input[j];
                //System.out.println("Synapse0 : " + change); //DEBUGGER
                synapse0[j][i] += change;
                System.out.println("Synapse0[" + j + "][" + i + "] = " + synapse0[j][i]); //DEBUGGER
            }
        }
        
        
    }
    
    private double sumError(int j){
        double sum = 0;
        
        for(int i = 0; i < outputSize; i++){
            sum += synapse1[j][i] * delta1[i]; //CHECK
        }
        
        return sum;
    }
    
    public void setExpectedOutput(double[] output){
        expectedOutput = output;
    }

    
}
