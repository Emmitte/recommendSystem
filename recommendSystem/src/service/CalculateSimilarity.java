package service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import entity.Score;
import util.FileTool;

public class CalculateSimilarity {

	public static double EuclidDist(Map<String, Double> userMap1,
			Map<String, Double> userMap2, Set<String> userSet,
			Set<String> itemSet) {
		double sum = 0;
		for (String itemId : itemSet) {
			double score1 = 0.0;
			double score2 = 0.0;
			if (userMap1.containsKey(itemId) && userMap2.containsKey(itemId)) {
				score1 = userMap1.get(itemId);
				score2 = userMap2.get(itemId);
			} else if (userMap1.containsKey(itemId)) {
				score1 = userMap1.get(itemId);
			} else if (userMap2.containsKey(itemId)) {
				score2 = userMap2.get(itemId);
			}
			double temp = Math.pow((score1 - score2), 2);
			sum += temp;
		}
		sum = Math.sqrt(sum);
		return sum;
	}

	public static double CosineDist(Map<String, Double> userMap1,
			Map<String, Double> userMap2, Set<String> userSet,
			Set<String> itemSet) {
		double dist = 0;
		double numerator = 0; // 分子
		double denominator1 = 0; // 分母
		double denominator2 = 0; // 分母
		for (String itemId : itemSet) {
			double score1 = 0.0;
			double score2 = 0.0;
			if (userMap1.containsKey(itemId) && userMap2.containsKey(itemId)) {
				numerator++;
				score1 = userMap1.get(itemId);
				score2 = userMap2.get(itemId);
			} else if (userMap1.containsKey(itemId)) {
				score1 = userMap1.get(itemId);
			} else if (userMap2.containsKey(itemId)) {
				score2 = userMap2.get(itemId);
			}
			denominator1 += Math.pow(score1, 2);
			denominator2 += Math.pow(score2, 2);
		}
		dist = ((1.0 * numerator) / (Math.sqrt(denominator1) * Math
				.sqrt(denominator2)));
		return dist;
	}
	public static double execute(Map<String, Double> userMap1,Map<String, Double> userMap2,Set<String> userSet,Set<String> itemSet) {
		double dist = EuclidDist(userMap1, userMap2, userSet, itemSet);
		double userScore = 1.0 / (1.0 + dist);
		// double userScore = CosineDist(userMap1, userMap2, userSet, itemSet);
		return userScore;
	}

	public static void execute(String userId,Map<String, Map<String, Double>> scoreTable,
			Set<String> userSet, Set<String> itemSet) {
		for (Entry<String, Map<String, Double>> userEntry : scoreTable.entrySet()) {
			String userId2 = userEntry.getKey();
			Map<String, Double> userMap2 = userEntry.getValue();
			double dist = EuclidDist(scoreTable.get(userId), userMap2, userSet, itemSet);
			double userScore = 1.0 / (1.0 + dist);
			// double userScore = CosineDist(userMap1, userMap2, userSet, itemSet);
			FileTool.ps1.println(userId + "\t" + userId2 + "\t" + userScore);
		}
	}

	public static void execute(Map<String, Map<String, Double>> scoreTable,
			Set<String> userSet, Set<String> itemSet) {
		List<Score> similarList = new ArrayList<Score>();
		for (Entry<String, Map<String, Double>> userEntry1 : scoreTable.entrySet()) {
			String userId = userEntry1.getKey();
			execute(userId, scoreTable, userSet, itemSet);
		}
	}

}
