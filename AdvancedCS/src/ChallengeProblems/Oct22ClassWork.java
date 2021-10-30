package ChallengeProblems;

public class Oct22ClassWork {
	
	//int a[][] new int[n][n];
		public Integer sumArrays(int a[][], int b[][]) {
			int sum = 0;
			int[][]total = new int[a.length][a[0].length];
	 		for(int i = 0; i < a.length; i++) {
				for(int j = 0; j < b[0].length; j++) {
					total[i][j] = a[i][j] + b[i][j];
				}
			}
	 		
	 		for(int i = 0; i < total.length; i++) {
	 			if(equalzero(total[i])) {
	 				sum++;
	 			}
	 		}
	 		
	 		for(int i = 0; i < total[0].length; i++) {
	 			int s = 0;
		 		for(int j = 0; j < total.length; j++) {
		 			s += total[j][i];
		 		}
		 		if(s == 0) {
		 			System.out.println("ye");
		 			sum++;
		 		}
	 		}
	 		return sum;
		}
		
		public boolean equalzero(int z[]) {
			int s = 0;
			for(int i = 0; i < z.length; i++) {
				s += z[i];
			}
			if(s == 0) {
				return true;
			}
			return false;
		}
		
		public Integer contagious(boolean[] b) {
			int front = 0;
			int longest = 0;
			int temp_len = 0;
			boolean first = true;
			for(boolean bol: b) {
				// checks for the beginning case
				if(bol && first) {
					front++;
				}
				else {
					first = false;
				}
				// checks for the longest string of true
				if(bol) {
					temp_len++;
				}
				else {
					if(temp_len > longest) {
						longest = temp_len;
					}
					temp_len = 0;
				}
			}
			int end = 0;
			for(int i = b.length-1; i >= 0; i--) {
				if(b[i]) {
					end++;
				}
				else {
					break;
				}
			}
			if(end == b.length) {
				return -1;
			}
			else {
				return Math.max(Math.max(front, temp_len/2+1), end);
			}
		}
		
		public static void main(String[] args) {
			Oct22ClassWork run = new Oct22ClassWork();
			//int [][]arr= {{5, 3, -1},{-2,4,0},{-2,4,0}, {-2,4,0}, {-2,4,0}, {-2,4,0}, {-2,4,0}, {-2,4,0}, {5, 3, -1}, {5, 3, -1},{-2,4,0},{-2,4,0}, {-2,4,0}, {-2,4,0}, {-2,4,0}, {-2,4,0}, {-2,4,0}, {5, 3, -1}, {5, 3, -1},{-2,4,0},{-2,4,0}, {-2,4,0}, {-2,4,0}, {-2,4,0}, {-2,4,0}, {-2,4,0}, {5, 3, -1}, {5, 3, -1},{-2,4,0},{-2,4,0}, {-2,4,0}, {-2,4,0}, {-2,4,0}, {-2,4,0}, {-2,4,0}, {5, 3, -1} };
			//int [][]arr2= {{3, -3, 6},{2, -4, 0}, {-2,4,0}, {-2,4,0}, {-2,4,0}, {-2,4,0}, {-2,4,0}, {3, -3, 6}, {3, -3, 6}, {5, 3, -1},{-2,4,0},{-2,4,0}, {-2,4,0}, {-2,4,0}, {-2,4,0}, {-2,4,0}, {-2,4,0}, {5, 3, -1}, {5, 3, -1},{-2,4,0},{-2,4,0}, {-2,4,0}, {-2,4,0}, {-2,4,0}, {-2,4,0}, {-2,4,0}, {5, 3, -1}, {5, 3, -1},{-2,4,0},{-2,4,0}, {-2,4,0}, {-2,4,0}, {-2,4,0}, {-2,4,0}, {-2,4,0}, {5, 3, -1}};
			//int [][]arr={{5, 3, -1},{-2,4,0}};
			//int [][]arr2={{3, -3, 6},{2, -4, 0}};
			int[][] arr = new int[10000][10000];
			int[][]arr2 = new int[10000][10000];
//			int [][]arr= {{5, 3, -1},{-2,4,0},{2,-4,0}, {2,-4,0}, {2,-4,0}, {2,-4,0}, {2,-4,0}, {2,-4,0}, {5, 3, -1}, {5, 3, -1},{-2,4,0},{-2,4,0}, {-2,4,0}, {-2,4,0}, {-2,4,0}, {-2,4,0}, {-2,4,0}, {5, 3, -1}, {5, 3, -1},{-2,4,0},{-2,4,0}, {-2,4,0}, {-2,4,0}, {-2,4,0}, {-2,4,0}, {-2,4,0}, {5, 3, -1}, {5, 3, -1},{-2,4,0},{-2,4,0}, {-2,4,0}, {-2,4,0}, {-2,4,0}, {-2,4,0}, {-2,4,0}, {5, 3, -1} };
//	        int [][]arr2= {{3, -3, 6},{2, -4, 0}, {-2,4,0}, {-2,4,0}, {-2,4,0}, {-2,4,0}, {-2,4,0}, {3, -3, 6}, {3, -3, 6}, {5, 3, -1},{-2,4,0},{-2,4,0}, {-2,4,0}, {-2,4,0}, {-2,4,0}, {-2,4,0}, {-2,4,0}, {5, 3, -1}, {5, 3, -1},{-2,4,0},{-2,4,0}, {-2,4,0}, {-2,4,0}, {-2,4,0}, {-2,4,0}, {-2,4,0}, {5, 3, -1}, {5, 3, -1},{-2,4,0},{-2,4,0}, {-2,4,0}, {-2,4,0}, {-2,4,0}, {-2,4,0}, {-2,4,0}, {5, 3, -1}};
//			//System.out.println(run.sumArrays(arr, arr2));
	        System.out.println(run.sumArrays(arr, arr2));
			boolean []b = {true, true, false, true, false, false, true, true, true, true};
			System.out.println(run.contagious(b));
		}
}