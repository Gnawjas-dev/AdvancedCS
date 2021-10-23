package ChallengeProblems;

public class Oct22ClassWork {
	
	public void problem1(int[][]arr, int [][]arr2) {
		int [][]arr3=new int[arr.length][arr.length+1];
		int total=0;
		
		for(int i=0; i<arr.length; i++) {
			for(int j=0; j<arr[i].length; j++ ) {

				arr3[i][j]=arr[i][j]+arr2[i][j];
								
			}
		}
		

		int horizontalsum=0;
		for(int i=0; i<arr3.length; i++) {
			for(int j=0; j<arr3[i].length; j++) {
				horizontalsum+=arr3[i][j];
			}
			if(horizontalsum==0)
				total++;
			horizontalsum=0;
		}
		
		System.out.println(total);
	}

public static void main(String[] args) {
		Oct22ClassWork run = new Oct22ClassWork();
		

		//int [][]arr= {{5, 3, -1},{-2,4,0},{2,-4,0}, {2,-4,0}, {2,-4,0}, {2,-4,0}, {2,-4,0}, {2,-4,0}, {5, 3, -1}, {5, 3, -1},{-2,4,0},{-2,4,0}, {-2,4,0}, {-2,4,0}, {-2,4,0}, {-2,4,0}, {-2,4,0}, {5, 3, -1}, {5, 3, -1},{-2,4,0},{-2,4,0}, {-2,4,0}, {-2,4,0}, {-2,4,0}, {-2,4,0}, {-2,4,0}, {5, 3, -1}, {5, 3, -1},{-2,4,0},{-2,4,0}, {-2,4,0}, {-2,4,0}, {-2,4,0}, {-2,4,0}, {-2,4,0}, {5, 3, -1} };

        //int [][]arr2= {{3, -3, 6},{2, -4, 0}, {-2,4,0}, {-2,4,0}, {-2,4,0}, {-2,4,0}, {-2,4,0}, {3, -3, 6}, {3, -3, 6}, {5, 3, -1},{-2,4,0},{-2,4,0}, {-2,4,0}, {-2,4,0}, {-2,4,0}, {-2,4,0}, {-2,4,0}, {5, 3, -1}, {5, 3, -1},{-2,4,0},{-2,4,0}, {-2,4,0}, {-2,4,0}, {-2,4,0}, {-2,4,0}, {-2,4,0}, {5, 3, -1}, {5, 3, -1},{-2,4,0},{-2,4,0}, {-2,4,0}, {-2,4,0}, {-2,4,0}, {-2,4,0}, {-2,4,0}, {5, 3, -1}};

		int [][]arr={{5, 3, -1},{-2,4,0}};
		int [][]arr2={{3, -3, 6},{2, -4, 0}};
		run.problem1(arr, arr2);

	}
}