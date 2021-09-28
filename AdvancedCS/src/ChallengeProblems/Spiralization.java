package ChallengeProblems;

import java.util.ArrayList;
import java.util.Arrays;

public class Spiralization {

	public void spiral(int[] og) {
		int len = (int) Math.sqrt(og.length);
		int[][] spiral = new int[len][len];
		
		for (int i=0; i<spiral.length; i++) {
			for(int j=0; j<spiral[i].length; j++) {
				
				
				
			}
		}
		
	}
		
		
	public static ArrayList<Integer> findPrimes(int num) {
		ArrayList<Integer> prime = new ArrayList<>();
		for(int i=2; i<num; i++) {
			for(int j=2; j<=i; j++) {
				if(i%j==0&&j!=i)
					break;
				else {
					if(j==i) {
						prime.add(i);
						System.out.println(i);
					}
				}
			}
		}
		return prime;
	}
	
	
	public static void primeFactor(int num) {
		
		ArrayList<Integer> prime = findPrimes(num);
		ArrayList<int[]> record = new ArrayList<>();

		
		
		System.out.println();
		
		int countexp = 0;
		int countbase = prime.get(0);
		
		while(num!=1) {
			
			for(Integer i : prime) {
				
				if(num%i==0) {
					
					if(countbase!=i) {
						
						record.add(new int[] {countbase, countexp});
						countexp = 0;
						
					}
						
					countbase=i;
					countexp++;
					num/=i;
					break;
					
				}
				
			}
			
		}
		
		
		for(int[] i : record) {
			System.out.println(Arrays.toString(i));
		}
//		
//		int[][] array = new int [2][divided.size()];
//		for(int i=0; i<array.length; i++) {
//			array[i][0]=record.get(i);
//			array[i][1]=divided.get(i);
//		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	/*
	 * Turn 1D array into spiral 2D and back into 1D
	 */
		primeFactor(72);
	}

}
