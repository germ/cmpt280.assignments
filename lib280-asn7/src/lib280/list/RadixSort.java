package lib280.list;

import lib280.list.ArrayedList280;
import lib280.list.LinkedList280;


public class RadixSort {
	
	/**  prints a list in order
	 * @param items[] the list of ints to be radix sorted */
	public static void LSDRadixListBuckets(int items[]) {
		ArrayedList280<LinkedList280<Integer>> buckets = new ArrayedList280<LinkedList280<Integer>>(10);
		int digit = 10;
		for (int i = 0; i<10; i++){
			buckets.goBefore();
			buckets.clear();
			for (int k = 0; k<10;k++){
				buckets.insert(new LinkedList280<Integer>());
			}
			for (int j = 0; j<items.length;j++){
				int kDig = items[j];
				kDig = kDig%digit;
				kDig = kDig/(digit/10);
				buckets.getItemAtIndex(kDig).insertLast(items[j]);
			}
			digit*=10;
			int count = 0;
			for (int k = 0; k<buckets.capacity();k++){
				if (!buckets.getItemAtIndex(k).isEmpty())
					buckets.getItemAtIndex(k).goFirst();
				while (buckets.getItemAtIndex(k).itemExists()){
					items[count] = buckets.getItemAtIndex(k).item();
					count++;
					buckets.getItemAtIndex(k).goForth();
				}
			}
		}
		System.out.println("Sorted numbers");
		for(int i =0; i < items.length; i++){
			System.out.print(items[i] +", ");
		}
	}

	public static void LSDRadixArrayBuckets(int items[]) {
		int[][] buckets = new int[10][items.length];
		
		int digit = 10;
		System.out.println("0: " + items[0]);
		for (int k = 0; k<10;k++){
			for (int l = 0;l<10;l++){
				buckets[k][l] = -1;
			}
		}
		for (int i = 0; i<10; i++){
			//buckets.goBefore();
			//buckets.clear();
			for (int j = 0; j<items.length;j++){
				int kDig = items[j];
				kDig = kDig%digit;
				kDig = kDig/(digit/10);
				int findNeg = 0;
				while (buckets[kDig][findNeg] != -1 && findNeg <items.length-1){
					findNeg++;
				}
				buckets[kDig][findNeg] = (items[j]);
			}
			digit*=10;
			int count = 0;
			int findNeg = 0;
			for (int k = 0; k<10;k++){
				while (buckets[k][findNeg] != -1 && findNeg <items.length-1 && count <items.length){
					items[count] = buckets[k][findNeg];
					findNeg++;
					count++;
				}
				findNeg = 0;
			}
		}
		System.out.println("Sorted numbers");
		for(int i =0; i < items.length; i++){
			System.out.print(items[i] +", ");
		}
		
	}

	
	
	public static void main(String args[]) {
		// TODO -- Write your test program here.  
		int[] array = new int[10];
		
		for (int i=0;i<10;i++){
			array[i] =(int) (Math.random() * ( 100 - 0 )); // (max - min)
			//System.out.println(array[i]);
		}
		int[] myray = new int[10];
		myray[0] = 5;
		myray[1] = 2;
		myray[2] = 3;
		myray[3] = 4;
		myray[4] = 40;
		myray[5] = 30;
		myray[6] = 20;
		myray[7] = 100;
		myray[8] = 70;
		myray[9] = 10;
		
		LSDRadixListBuckets(myray);
		LSDRadixArrayBuckets(myray);
		
	}
}
