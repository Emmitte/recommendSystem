package util;

import entity.Item;
import entity.Score;
import entity.User;

public class ParseTool {
	public static boolean isNumber(String str) {
		int i,n;
		n = str.length();
		for(i = 0;i < n;i++){
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}
	
	public static Item parseItem(String[] contents) {
		Item item = new Item();
		if (contents[0] != null && !contents[0].isEmpty()) {
			item.setItemId(contents[0].trim());
		}
		if (contents[1] != null && !contents[1].isEmpty()) {
			item.setItemGeoHash(contents[1].trim());
		}
		if (contents[2] != null && !contents[2].isEmpty()) {
			item.setItemCategory(contents[2].trim());
		}
		return item;
	}
	public static User parseUser(String[] contents) {
		User user = new User();
		int n = contents.length;
		if (contents[0] != null && !contents[0].isEmpty()) {
			user.setUserId(contents[0].trim());
		}
		if (contents[1] != null && !contents[1].isEmpty()) {
			user.setItemId(contents[1].trim());
		}
		/*
		// 2.调用CountFileTest需放开，其它需注释
		if (contents[2] != null && !contents[2].isEmpty()) {
			user.setBehaviorType(Integer.valueOf(contents[2].trim()));
		}
		
		// 2.调用CountFileTest需放开，其它需注释
		if (contents[n-1] != null && !contents[n-1].isEmpty()) {
			user.setCount(Integer.valueOf(contents[n-1].trim()));
		}
		*/
		
		// 3.调用PredictTest需放开，其它需注释
		if (contents[n-1] != null && !contents[n-1].isEmpty()) {
			user.setWeight(Double.valueOf(contents[n-1].trim()));
		}
		
		/*
		// 1.调用SpliteFileAndMakeScoreTable需放开，其它需注释
		if (contents[3] != null && !contents[3].isEmpty()) {
			user.setUserGeoHash(contents[3].trim());
		}
		if (contents[4] != null && !contents[4].isEmpty()) {
			user.setItemCategory(contents[4].trim());
		}
		if (contents[5] != null && !contents[5].isEmpty()) {
			user.setTime(contents[5].trim());
		}
		*/
		return user;
	}
	public static Score parseScore(String[] contents) {
		Score score = new Score();
		if (contents[0] != null && !contents[0].isEmpty()) {
			score.setUserId(contents[0].trim());
		}
		if (contents[1] != null && !contents[1].isEmpty()) {
			score.setItemId(contents[1].trim());
		}
		if (contents[2] != null && !contents[2].isEmpty()) {
			score.setScore(Double.parseDouble(contents[2].trim()));
		}
		return score;
	}
}
