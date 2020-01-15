package com.rc.model.crm;

public class test {
	
	
	public static void main(String[] args) {
		String total = "999111000";
		int totalLength = total.length();

		if(totalLength > 3) {
			//int count = Math.floorDiv(totalLength, 3);
			if(totalLength>=4 && totalLength<=6) {
				total = total.substring(0, totalLength-3) + "," + total.substring(totalLength - 3, totalLength);
			}
			if(totalLength>=6 && totalLength<=9) {
				String s1 =  "," + total.substring(totalLength - 3, totalLength);
				String s2 = "," + total.substring(totalLength - 6, totalLength - 3);
				total = total.substring(0, totalLength-6) + s2 + s1;
			}
		}
		System.out.println(total);
	}
}
