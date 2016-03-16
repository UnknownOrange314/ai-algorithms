   import java.util.*;
    public class Board {
   
      ArrayList<ArrayList<ArrayList<Integer>>> myData;
   
       public Board()
      {
         myData=new ArrayList<ArrayList<ArrayList<Integer>>>();
         for(int x=0;x<9;x++)
         {
            ArrayList<ArrayList<Integer>> xA=new ArrayList<ArrayList<Integer>>();
            for(int y=0;y<9;y++)
            {
               ArrayList<Integer> c=new ArrayList<Integer>();
               for(int z=1;z<10;z++)
               {
                  c.add(z);
               }
               xA.add(c);
            }
            myData.add(xA);
         }
      
      }
       public void addData(String s)
      {
       //  System.out.println("***********************");
      
      
         for(int x=0;x<s.length()-1;x++)
         {
            String data=s.substring(x,x+1);
         
            if(data.equals(".")==false)
            {
            
               int xPos=x-(x/9)*9;
               int yPos=x/9;
            
            //System.out.println(xPos+":"+yPos);
               int replaceNum=Integer.parseInt(data);
               replaceData(xPos,yPos,replaceNum);
            }
         }
         String d=s.substring(s.length()-1);
         if(d.equals(".")==false)
         {
            replaceData(8,8,Integer.parseInt(d));
         }
       // print();
      
      
      }
       public void replaceData(int x,int y,int num)
      {
         myData.get(x).get(y).clear();
         ArrayList<Integer> c=new ArrayList<Integer>();
         c.add(num);
         myData.get(x).set(y,c);
      
      }
       public void removeLegalNum(int i,int y,int num)
      {
			ArrayList c=myData.get(i).get(y);
		
			if(c.contains(num))
			{
			c.remove(c.indexOf(num));
			}

      }
       public void pickRandomSolution(int x,int y)
      {
         int size=myData.get(x).get(y).size();
         int selIndex=(int)(Math.random()*size);
         int newNum=myData.get(x).get(y).get(selIndex);
         myData.get(x).get(y).clear();
         myData.get(x).get(y).add(newNum);
      
      }
       public void print()
      {
         System.out.println(" ");
         for(int x=0;x<9;x++)
         {
            for(int y=0;y<9;y++)
            {
               ArrayList<Integer> c=myData.get(x).get(y);
               if(c.size()<2)
               {
                  System.out.print(myData.get(x).get(y));
               }
               else
               {
                  System.out.print("___");
               }
            
            }
            System.out.println();
         }
      
      }
       public ArrayList<Integer> getLegalNums(int x,int y)
      {
      
      	return myData.get(x).get(y);
      
      }
       public void rowScan()
      {
         for(int x=0;x<9;x++)
         {
            for(int y=0;y<9;y++)
            {
               if(myData.get(x).get(y).size()==1)
               {
                  int num=myData.get(x).get(y).get(0);
               	
                  for(int i=0;i<9;i++)
                  {
                  	
                  	
                     if(i !=x)
                     {
							
                        removeLegalNum(i,y,num);
                     }
                  }
                  for(int i=0;i<9;i++)
                  {
                     if(i !=y)
                     {
                        removeLegalNum(x,i,num);
                     } 
                  
                  }
               }
            }
         }
      }
       public boolean isSolved()
      {
         for(int x=0;x<9;x++)
         {
            for(int y=0;y<9;y++)
            {
               if(myData.get(x).get(y).size()>1)
               {
                  return false;
               
               }
            
            }
         }
			return true;
      
      
      
      }
       public void squareScan()//something is wrong here.
      {
         for(int x=0;x<3;x++)
         {
            for(int y=0;y<3;y++)
            {
					
               int xPos=(x)*3;
               int yPos=(y)*3;
               HashSet<Integer> c=new HashSet<Integer>();
               for(int i=0;i<3;i++)
               {
                  for(int j=0;j<3;j++)
                  {
                     Iterator legal=myData.get(xPos+i).get(yPos+j).iterator();
                     while(legal.hasNext())
                     {
                        Object o=legal.next();
                        Integer num=(Integer)o;
                        if(c.contains(num))
                        {
                          legal.remove();
                        }
                     
                     }
                     if(myData.get(xPos+i).get(yPos+j).size()==1)
                     {
                        c.add(myData.get(xPos+i).get(yPos+j).get(0));
                     
                     
                     }
                  
                  
                  }
               
               }
            
            }
         
         
         }
      }
   }
