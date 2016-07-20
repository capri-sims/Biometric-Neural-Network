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
    
    private int inputSize;
    private int hiddenSize;
    private int outputSize = 1;
    
    private int[][] input; 
    private int[][] inputLayer; 
    private int[] output = new int[2];
    private int[] outputLayer;
    private int[][] hiddenLayer; //1 for now
    private int[][] synapse1;
    private int[][] synapse2;
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
        //synapse1 = random 
        //synapse2 = random
    }
    
    private void feedForward(){
        
    }
    
    private void calcError(){
        //error = output - calculated Output
    }
    
    private void updateWeights(){
        //delta //error*squash(layer)
        squash(1);
    }
    
}
