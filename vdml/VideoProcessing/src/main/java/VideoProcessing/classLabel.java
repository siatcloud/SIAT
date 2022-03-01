package VideoProcessing;

public class classLabel {
	public int getlabel(String rever, String dataName) {
		int levelid = 0;

		if (dataName.equals("KTH")) {
			if (Integer.valueOf(rever) >= 1 && Integer.valueOf(rever) <= 100)
				levelid = 0;
			else if (Integer.valueOf(rever) >= 101 && Integer.valueOf(rever) <= 200)
				levelid = 1;
			else if (Integer.valueOf(rever) >= 201 && Integer.valueOf(rever) <= 300)
				levelid = 2;
			else if (Integer.valueOf(rever) >= 301 && Integer.valueOf(rever) <= 400)
				levelid = 3;
			else if (Integer.valueOf(rever) >= 401 && Integer.valueOf(rever) <= 500)
				levelid = 4;
			else if (Integer.valueOf(rever) >= 501 && Integer.valueOf(rever) <= 600)
				levelid = 5;
		} else if (dataName.equals("gender")) {
			if (Integer.valueOf(rever) >= 1 && Integer.valueOf(rever) <= 15)
				levelid = 0; // Female
			else if (Integer.valueOf(rever) >= 16 && Integer.valueOf(rever) <= 30)
				levelid = 1; // Male
		}

		return levelid;
	}
}
