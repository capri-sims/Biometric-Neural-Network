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
    private int hiddenSize = (inputSize + outputSize) / 2; //experiment
    private int outputSize = 1;
    
    private int[][] input = new int[1][inputSize]; 
    private int[][] output = new int[1][outputSize];
    private int[][] hiddenLayer = new int[hiddenSize][1]; //1 hidden layer
    private int[][] synapse0 = new int[inputSize][hiddenSize];
    private int[][] synapse1 = new int[hiddenSize][outputSize];
    private int iterations = 100000;
    
    public Network(){
        
        initWeights();
        for(int i = 0; i < iterations; i++){ //or error reaches x?
            feedForward();
            calcError();
            updateWeights();
        }
    }
    
    private int squash(int n){
        return n*(1-n);
    }
    
    private void initWeights(){
        //random
        //synapse1 = random 
        //synapse2 = random
    }
    
    private void feedForward(){
        //hidden = input * synapse1 + bias1
        //hidden = squash hidden
        //output = hidden * synapse 2 + bias2
        //output = squash output
    }
    
    private void calcError(){
        //error = output - calculated Output
    }
    
    private void updateWeights(){
        //delta //error*squash(layer)
        squash(1);
    }
    
    private void multiply(int size1, int size2){
        
        for(int i = 0; i < size1; i++){
            for(int j = 0; j < size2; j++){
                //stuff
            }
        }
    }
    
}
