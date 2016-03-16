



#changes
#two import statements
# Added some missing colons

import copy
import random

boardSize=8


edges=list()
xDirs=[1,1,0,-1,-1,-1,0,1,2,0,-2,0]
yDirs=[0,1,1,1,0,-1,-1,-1,0,2,0,-2] 






def printBoard(board,rows,cols):
  for r in range(rows):
    for c in range(cols):
      index = r*cols + c
      print "%3d" % (board[index]),
    print


def fillRandomBoard():
  y=0
  b=list()
  while y<boardSize:
    x=0
    c=list()
    while x<boardSize:
      c.append(random.randint(1,2))
      x+=1
    b.append(c)
    y+=1
  return b
# -*- coding: iso-8859-1 -*-


def deleteCorners(board):
  board[0][0]=0
  board[0][boardSize-1]=0
  board[boardSize-1][0]=0
  board[boardSize-1][boardSize-1]
  return board


def deleteRandom(board):
  delCount=0
  while delCount<20:
    delX=random.randint(0,boardSize-1)
    delY=random.randint(0,boardSize-1)
    if board[delY][delX] !=0:
      delCount+=1
      board[delY][delX]=0
  return board
def addRandom(board):
  addCount=0
  while addCount<5:
    delX=random.randInt(0,boardSize-1)
    delY=random.randInt(0,boardSize-1)
    if board[delY][delX]==0:
      addCount+=1
      board[delY][delX]=random.randInt(1,2)
      
def isSatisfied(xPos,yPos,agent,board):
  if agent==0:
      return True
  neighbors=list()
  i=0
  while i<8:
    
    xIndex=xPos+xDirs[i]
    yIndex=yPos+yDirs[i]
    if onBoard(xIndex,yIndex,boardSize):
      neighbors.append(board[yIndex][xIndex])
    i=i+1
  happyCount=0
  print neighbors
  for item in neighbors:
      if item==agent:
        happyCount+=1
  

  if len(neighbors)>5:
    if happyCount >=3:
      return True
    
  elif len(neighbors)>2:
    if happyCount >=2:
      return True
  
  else: 
    if happyCount >=1:
      return True
  #print 'I am unhappy'
  #print str(xPos)+':'+str(yPos)
  return False
  
def move(agent,board,x,y):

  if agent==0:
      return
  xPots=list()
  yPots=list()
  if x==0:
                print 'jhio'
  xIndex=0
  while xIndex<boardSize:
                yIndex=0
                while yIndex<boardSize:
                        if isSatisfied(xIndex,yIndex,agent,board) and board[yIndex][xIndex]==0:
                                xPots.append(xIndex)
                                yPots.append(yIndex)
                            
                        yIndex=yIndex+1
                xIndex=xIndex+1
                


  if len(xPots)==0:
    return
  moveIndex=random.randint(0,len(xPots)-1)
  board[y][x]=0
  board[yPots[moveIndex]][xPots[moveIndex]]=agent
        



    



def onBoard(x,y,boardSize):
  
  if x>=boardSize:
    return False
  if x<0:
    return False
  if y<0:
    return False
  if y>=boardSize:
    return False
  return True
def drawBoard(board,boardSize):
  print '****************'
  for row in board:
    dispStr=''
    for item in row:
      dispStr=dispStr+' '+str(item)
    print dispStr



def main():

  index=0
  while index<boardSize:
  
    index+=1
  board=fillRandomBoard()
  board=deleteCorners(board)
  board=deleteRandom(board)
  iterations=0
  happyTrend=list()
  while True:
    x=0
    happyCount=0
    while x<boardSize:
      y=0
      while y<boardSize:
        agent=board[y][x]
  
        if isSatisfied(x,y,agent,board)==False:
          move(agent,board,x,y)
        else:
          happyCount+=1
        
        y=y+1
      x=x+1
    happyTrend.append(happyCount)
    drawBoard(board,boardSize)
    if iterations==20:
      print happyTrend
      break
    iterations+=1
    
    
  
  


  
      










      

  
      
if __name__=='__main__':
    main()
