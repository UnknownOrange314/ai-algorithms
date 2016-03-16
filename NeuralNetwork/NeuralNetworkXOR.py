import copy
import random
class Node:
    def __init__(self,layerWidth):
        self.weights=list()
        index=0
        while index<layerWidth:
            self.weights.append(random.random()*3)
            index=index+1
        self.value=0
    def setValue(self,newVal):
        self.value=newVal
    def getValue(self):
        return self.value
    def getWeights(self):
        return self.weights
    def setWeight(self,index,newWeight):
        self.weights[index]=newWeight
    def setWeights(self,newWeights):
        self.weights=newWeights

def calc(layers,level,node):

    nodeA=layers[level-1][0]
    nodeB=layers[level-1][1]
    weights=node.getWeights()
    y=weights[0]*nodeA.getValue()+weights[1]*nodeB.getValue()
    z=1/(1+pow(2.71828183,-1*y))
    return z

def testNetwork(layers,solutions,epochs):
    eTot=0
    for inputs in solutions:
        layers[0][0].setValue(inputs[0])
        layers[0][1].setValue(inputs[1])
        for node in layers[1]:
            node.setValue(calc(layers,1,node))
        layers[2][0].setValue(calc(layers,2,node))
        finalVal=layers[2][0].getValue()
        if epochs>995:
            print(finalVal-solutions[inputs],2)
        eTot=eTot+pow(finalVal-solutions[inputs],2)
        
    return eTot

def main():

    numLayers=3
    layerWidth=2
    stepSize=0.1
    learningRate=0.8
    solutions=dict()
    layers=list()
    index=0
    while index<numLayers:
        nodes=list()
        indexA=0
        while indexA<layerWidth:
            newNode=Node(layerWidth)
            nodes.append(newNode)
            indexA=indexA+1
        layers.append(nodes)
        index=index+1
    solutions[0,1]=1
    solutions[1,0]=1
    solutions[0,0]=0
    solutions[1,1]=0
    error=99


    while error>0.0001:
        
        eTot=testNetwork(layers,solutions,0)
        error=eTot
        print error
        for layerNodes in layers:
            for node in layerNodes:
                nodeWeights=copy.deepcopy(node.getWeights())
                index=0
                errors=list()
                while index<len(nodeWeights):
                    newWeight=nodeWeights[index]+stepSize
                    node.setWeight(index,newWeight)
                    errors.append(testNetwork(layers,solutions,1))
                    index=index+1
                    
                index=0
                while index<len(nodeWeights):
                    nodeWeights[index]=nodeWeights[index]-learningRate*(errors[index]-eTot)/stepSize
                    index=index+1
                node.setWeights(nodeWeights)
    testNetwork(layers,solutions,1000)
        
            
            
                
    
    

        


if __name__=='__main__':
	main()
