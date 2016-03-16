


def calc(weightA,weightB,inputNum):
    y=weightA+inputNum*weightB
    z=1/(1+pow(2.71828183,-1*y))
    return z

def calcError(weightA,weightB):
    actual0=calc(weightA,weightB,0)
    actual1=calc(weightA,weightB,1)
    #print actual1
    return pow(actual0-1,2)+pow(actual1-0,2)
def main():

    weightA=0
    weightB=5
    error=99
    learningRate=0.8
    stepSize=0.1
    while error>0.000001:
          error=calcError(weightA,weightB)
          E0=calcError(weightA+stepSize,weightB)
          E1=calcError(weightA,weightB+stepSize)
          weightA=weightA-learningRate*(E0-error)/stepSize
          weightB=weightB-learningRate*(E1-error)/stepSize
          #print str(weightA)+':'+str(weightB)
          print error
    print weightA
    print weightB
        


if __name__=='__main__':
	main()
