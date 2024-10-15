import java.util.*;
class Main {
  public static void main(String[] args) {
    Scanner input= new Scanner(System.in);
    int pivot[]={-1,-1};
    //asking the user for input//
    System.out.print("Input the number of equations ");
    int rows=input.nextInt();
    System.out.print("Input the number of variables ");
    int columns=input.nextInt();
    double matrix[][]=new double[rows][columns+1];
    //get all the variables//
    System.out.println("The equations will be in the form c1x1+c2x2+c3x3+...+cnxn=constant");
    matrix=askForVariables(matrix);
    output(matrix);
    int pivots=rowReduce(matrix, pivot);
    output(matrix);
    if(test(matrix, pivots)){
      output(backSolve(matrix, pivots));
    }
  }


  //output 2d array
  public static void output(double thematrix[][]){
    for(int i=0; i<thematrix.length; i++){
      for(int j=0; j<thematrix[i].length; j++){
        if(thematrix[i][j]==-0.0){
        System.out.print("0.0 ");  
        }
        else {
        System.out.print(thematrix[i][j]+" ");
        }
      }
      System.out.println();
    }
    System.out.println();
    return;
  }
  
  //output 1d array
  public static void output(double array[]){
    for(int i=0; i<array.length; i++){
      System.out.println("x"+(i+1)+"= "+array[i]);
    }
  }

  //get input matrix
  public static double[][] askForVariables(double thematrix[][]){
    Scanner input= new Scanner(System.in);
    double changedMatrix[][]=new double[thematrix.length][thematrix[0].length];
    for(int i=0; i<thematrix.length; i++){
      System.out.println("Equation "+(i+1)+":");
      for(int j=0; (j<thematrix[i].length-1); j++){
        System.out.print("c"+(j+1)+"= ");
        changedMatrix[i][j]=input.nextDouble();
        
      }
      System.out.print("constant= ");
      changedMatrix[i][thematrix[i].length-1]=input.nextDouble();
      System.out.println();
      
  }
   return changedMatrix;
  }

  
  //scales row
  public static void scaleRow(double thematrix[][], int row, double scale){
    for(int i=0; i<thematrix[row].length; i++){
     thematrix[row][i]=thematrix[row][i]*scale;
    }
  }
  //swaps rows
  public static void rowExchange(double thematrix[][], int row1, int row2){
    double temp[][]=thematrix;
    for(int i=0; i<thematrix[0].length; i++){
      thematrix[row1][i]=temp[row2][i];
    }
    for(int i=0; i<thematrix[0].length; i++){
      thematrix[row2][i]=temp[row1][i];
    }
  }

  //sums rows
  public static void rowSum(double thematrix[][], int row1, int row2){
    for(int i=0; i<thematrix[0].length; i++){
      thematrix[row2][i]=thematrix[row2][i]+thematrix[row1][i];
    }
  }

  //sets next pivot and returns true, or returns false if no next pivot exists
  public static boolean setNextPivot(double thematrix[][], int[]piv){
    if(piv[0]>=thematrix.length||piv[1]>=thematrix[0].length){
      return false;
    }
    for(int col=piv[1]+1; col<thematrix[0].length; col++){
      for(int row=piv[0]+1; row<thematrix.length; col++){
        if(thematrix[row][col]!=0){
          rowExchange(thematrix, row, piv[0]+1);
          piv[0]++;
          piv[1]=col;
          output(thematrix);
          
          return true;
        }
      }
      
    }
    return false;
  }

  //eliminates variables below current pivot
  public static void reduceBelowPivot(double thematrix[][], int[]piv){
    scaleRow(thematrix, piv[0], (1.0/(thematrix[piv[0]][piv[1]])));
    if(piv[0]==thematrix.length){
      return;
    }
    for(int row=piv[0]+1; row<thematrix.length; row++){
      if(thematrix[row][piv[1]]!=0){
        scaleRow(thematrix, piv[0], -thematrix[row][piv[1]]/thematrix[piv[0]][piv[1]]);
        rowSum(thematrix, piv[0], row);
        scaleRow(thematrix, piv[0], (1.0/(thematrix[piv[0]][piv[1]])));
        output(thematrix);
      }
    }
  }

  //continues to set new pivot and elimate variables until matrix is in Row Echelon Form
  public static int rowReduce(double thematrix[][], int piv[]){
    int pivotcount=0;
    while(setNextPivot(thematrix, piv)){
      pivotcount++;
      reduceBelowPivot(thematrix, piv);
      
    }
    return pivotcount;
  }

  //gives outputs based on type of solution
  public static boolean test(double thematrix[][], int pivots){
    if(pivots<(thematrix[0].length-1)){
      System.out.println("There are infinite solutions");
      return false;
    }
    if(pivots>(thematrix[0].length-1)){
      System.out.println("The system is not consistent");
      return false;
    }
    System.out.println("The solutions are below");
    return true;
  }

  //backsolves Row Echelon Matrix for solution, only runs if system is consistent and solution is unique
  public static double[] backSolve(double thematrix[][], int pivots){
    double subtract=0;
    double[] solution=new double[pivots];
    solution[pivots-1]=thematrix[pivots-1][pivots];
    for(int row=pivots-2; row>=0; row--){
      for(int entry=pivots-1; entry>row; entry--){
        subtract+=(solution[entry]*thematrix[row][entry]);
      }
      solution[row]=thematrix[row][pivots]-subtract;
      subtract=0;
    }
    return solution;
  }
}
