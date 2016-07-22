/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProjectCephalopod;

/**
 *
 * @author Capri
 */
public class Network {
    
    private int inputSize = 8;
    private int outputSize = 1;
    private int hiddenSize = (inputSize + outputSize) / 2; //experiment

    private double[] expectedOutput = new double[outputSize];
    
    private double[] input = new double[inputSize]; 
    private double[] output = new double[outputSize];
    private double[] hidden = new double[hiddenSize]; //1 hidden layer
    private double[][] synapse0 = new double[inputSize][hiddenSize];
    private double[][] synapse1 = new double[hiddenSize][outputSize];
    private double bias0;
    private double bias1;
    
    private int iterations = 100000;
    
    public Network(){
        
        initWeights(); //initialize the weights in synapse0 and synapse1
        
        //TRAINING
        for(int i = 0; i < iterations; i++){ //or error reaches x?
            feedForward();
            calcError();
            updateWeights();
        }
        
        //TEST
        
        //UPLOAD TO DATABASE 
        //synapse0, synapse1, bias0, bias1
        
    }
    
    private int squash(int n){
        return n*(1-n);
    }
    
    private void initWeights(){
        //random
        //synapse1 = random 
        //synapse2 = random
        bias0 = 1;
        bias1 = 1;
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
            num += hidden[j] * synapse1[0][j]; //multiply matrices
        }
        num += bias1; //add bias
        output[0] = Math.tanh(num); //squashed
    }
    
    private void calcError(){
        //error = output - calculated Output
        double error = 0;
        
        for(int i = 0; i < outputSize; i++){
            error += expectedOutput[i] - output[i];
        }
    }
    
    private void updateWeights(){
        //delta //error*squash(layer)
        squash(1);
    }

    
}
