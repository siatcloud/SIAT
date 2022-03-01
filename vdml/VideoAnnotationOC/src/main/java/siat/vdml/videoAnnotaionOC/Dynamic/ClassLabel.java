package siat.vdml.videoAnnotaionOC.Dynamic;


public class ClassLabel {

	public int getlabel(String rever, String dataName) {
		int levelid = 0;

		if (dataName.equals("KTH")) {
			if (Integer.valueOf(rever) >= 1 && Integer.valueOf(rever) <= 100)
				levelid = 0;
			else if (Integer.valueOf(rever) >= 101
					&& Integer.valueOf(rever) <= 200)
				levelid = 1;
			else if (Integer.valueOf(rever) >= 201
					&& Integer.valueOf(rever) <= 300)
				levelid = 2;
			else if (Integer.valueOf(rever) >= 301
					&& Integer.valueOf(rever) <= 400)
				levelid = 3;
			else if (Integer.valueOf(rever) >= 401
					&& Integer.valueOf(rever) <= 500)
				levelid = 4;
			else if (Integer.valueOf(rever) >= 501
					&& Integer.valueOf(rever) <= 600)
				levelid = 5;
		} else if (dataName.equals("UCF50")) {
			if (Integer.valueOf(rever) >= 1 && Integer.valueOf(rever) <= 150)
				levelid = 0; //150
			else if (Integer.valueOf(rever) >= 151
					&& Integer.valueOf(rever) <= 287)
				levelid = 1; //137
			else if (Integer.valueOf(rever) >= 288
					&& Integer.valueOf(rever) <= 447)
				levelid = 2; //160
			else if (Integer.valueOf(rever) >= 448
					&& Integer.valueOf(rever) <= 592)
				levelid = 3; //145
			else if (Integer.valueOf(rever) >= 593
					&& Integer.valueOf(rever) <= 742)
				levelid = 4; //150
			else if (Integer.valueOf(rever) >= 743
					&& Integer.valueOf(rever) <= 843)
				levelid = 5; //101
			else if (Integer.valueOf(rever) >= 844
					&& Integer.valueOf(rever) <= 955)
				levelid = 6; //112
			else if (Integer.valueOf(rever) >= 956
					&& Integer.valueOf(rever) <= 1108)
				levelid = 7; //153
			else if (Integer.valueOf(rever) >= 1109
					&& Integer.valueOf(rever) <= 1269)
				levelid = 8; //161
			else if (Integer.valueOf(rever) >= 1270
					&& Integer.valueOf(rever) <= 1380)
				levelid = 9;  //111
			else if (Integer.valueOf(rever) >= 1381
					&& Integer.valueOf(rever) <= 1522)
				levelid = 10;  //142
			else if (Integer.valueOf(rever) >= 1523
					&& Integer.valueOf(rever) <= 1645)
				levelid = 11; //123
			else if (Integer.valueOf(rever) >= 1646
					&& Integer.valueOf(rever) <= 1772)
				levelid = 12; //127
			else if (Integer.valueOf(rever) >= 1773
					&& Integer.valueOf(rever) <= 1969)
				levelid = 13;  //197
			else if (Integer.valueOf(rever) >= 1970
					&& Integer.valueOf(rever) <= 2094)
				levelid = 14; //125
			else if (Integer.valueOf(rever) >= 2095
					&& Integer.valueOf(rever) <= 2211)
				levelid = 15; //117
			else if (Integer.valueOf(rever) >= 2212
					&& Integer.valueOf(rever) <= 2333)
				levelid = 16; //122
			else if (Integer.valueOf(rever) >= 2334
					&& Integer.valueOf(rever) <= 2456)
				levelid = 17; //123
			else if (Integer.valueOf(rever) >= 2457
					&& Integer.valueOf(rever) <= 2604)
				levelid = 18; //148
			else if (Integer.valueOf(rever) >= 2605
					&& Integer.valueOf(rever) <= 2761)
				levelid = 19; //157
			else if (Integer.valueOf(rever) >= 2762
					&& Integer.valueOf(rever) <= 2902)
				levelid = 20; //141
			else if (Integer.valueOf(rever) >= 2903
					&& Integer.valueOf(rever) <= 3029)
				levelid = 21; //127
			else if (Integer.valueOf(rever) >= 3030
					&& Integer.valueOf(rever) <= 3170)
				levelid = 22; //141
			else if (Integer.valueOf(rever) >= 3171
					&& Integer.valueOf(rever) <= 3320)
				levelid = 23; //150
			else if (Integer.valueOf(rever) >= 3321
					&& Integer.valueOf(rever) <= 3434)
				levelid = 24; //114
			else if (Integer.valueOf(rever) >= 3435
					&& Integer.valueOf(rever) <= 3594)
				levelid = 25; //160
			else if (Integer.valueOf(rever) >= 3595
					&& Integer.valueOf(rever) <= 3699)
				levelid = 26; //105
			else if (Integer.valueOf(rever) >= 3700
					&& Integer.valueOf(rever) <= 3823)
				levelid = 27; //124
			else if (Integer.valueOf(rever) >= 3824
					&& Integer.valueOf(rever) <= 3923)
				levelid = 28; //100
			else if (Integer.valueOf(rever) >= 3924
					&& Integer.valueOf(rever) <= 4083)
				levelid = 29; //160
			else if (Integer.valueOf(rever) >= 4084
					&& Integer.valueOf(rever) <= 4206)
				levelid = 30; //159
			else if (Integer.valueOf(rever) >= 4207
					&& Integer.valueOf(rever) <= 4326)
				levelid = 31; //120
			else if (Integer.valueOf(rever) >= 4327
					&& Integer.valueOf(rever) <= 4486)
				levelid = 32; //160
			else if (Integer.valueOf(rever) >= 4487
					&& Integer.valueOf(rever) <= 4592)
				levelid = 33; //106
			else if (Integer.valueOf(rever) >= 4593
					&& Integer.valueOf(rever) <= 4740)
				levelid = 34;
			else if (Integer.valueOf(rever) >= 4741
					&& Integer.valueOf(rever) <= 4870)
				levelid = 35;
			else if (Integer.valueOf(rever) >= 4871
					&& Integer.valueOf(rever) <= 5007)
				levelid = 36;
			else if (Integer.valueOf(rever) >= 5008
					&& Integer.valueOf(rever) <= 5140)
				levelid = 37;
			else if (Integer.valueOf(rever) >= 5141
					&& Integer.valueOf(rever) <= 5260)
				levelid = 38;
			else if (Integer.valueOf(rever) >= 5261
					&& Integer.valueOf(rever) <= 5404)
				levelid = 39;
			else if (Integer.valueOf(rever) >= 5405
					&& Integer.valueOf(rever) <= 5504)
				levelid = 40;
			else if (Integer.valueOf(rever) >= 5505
					&& Integer.valueOf(rever) <= 5660)
				levelid = 41;
			else if (Integer.valueOf(rever) >= 5661
					&& Integer.valueOf(rever) <= 5797)
				levelid = 42;
			else if (Integer.valueOf(rever) >= 5798
					&& Integer.valueOf(rever) <= 5897)
				levelid = 43;
			else if (Integer.valueOf(rever) >= 5898
					&& Integer.valueOf(rever) <= 6064)
				levelid = 44;
			else if (Integer.valueOf(rever) >= 6065
					&& Integer.valueOf(rever) <= 6195)
				levelid = 45;
			else if (Integer.valueOf(rever) >= 6196
					&& Integer.valueOf(rever) <= 6314)
				levelid = 46;
			else if (Integer.valueOf(rever) >= 6315
					&& Integer.valueOf(rever) <= 6430)
				levelid = 47;
			else if (Integer.valueOf(rever) >= 6431
					&& Integer.valueOf(rever) <= 6553)
				levelid = 48;
			else if (Integer.valueOf(rever) >= 6554
					&& Integer.valueOf(rever) <= 6681)
				levelid = 49;

		} else if (dataName.equals("UCF101")) {
			if (Integer.valueOf(rever) <= 1)
				levelid = 1;
			else if (Integer.valueOf(rever) >= 2 && Integer.valueOf(rever) <= 3)
				levelid = 2;
			else if (Integer.valueOf(rever) >= 4 && Integer.valueOf(rever) <= 5)
				levelid = 3;
			else if (Integer.valueOf(rever) >= 6 && Integer.valueOf(rever) <= 9)
				levelid = 4;
			else if (Integer.valueOf(rever) >= 10
					&& Integer.valueOf(rever) <= 31)
				levelid = 5;
			else if (Integer.valueOf(rever) >= 32
					&& Integer.valueOf(rever) <= 33)
				levelid = 6;
			else if (Integer.valueOf(rever) <= 34)
				levelid = 7;
			else if (Integer.valueOf(rever) >= 35
					&& Integer.valueOf(rever) <= 36)
				levelid = 8;
			else if (Integer.valueOf(rever) >= 37
					&& Integer.valueOf(rever) <= 39)
				levelid = 9;
		}

		return levelid;
	}

	public int getlabel(String rever) {
		int levelid = 0;

		if (Integer.valueOf(rever) >= 1 && Integer.valueOf(rever) <= 100)
			levelid = 1;
		else if (Integer.valueOf(rever) >= 101 && Integer.valueOf(rever) <= 200)
			levelid = 2;
		else if (Integer.valueOf(rever) >= 201 && Integer.valueOf(rever) <= 300)
			levelid = 3;
		else if (Integer.valueOf(rever) >= 301 && Integer.valueOf(rever) <= 400)
			levelid = 4;
		else if (Integer.valueOf(rever) >= 401 && Integer.valueOf(rever) <= 500)
			levelid = 5;
		else if (Integer.valueOf(rever) >= 501 && Integer.valueOf(rever) <= 600)
			levelid = 6;

		return levelid;
	}

}
