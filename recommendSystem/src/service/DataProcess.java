package service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import util.FileTool;
import entity.Item;
import entity.Score;
import entity.User;

public class DataProcess {
	
	public static final double[] w = {0,10,20,30}; 
	
	public static void output(Map<String, Map<String, List<User>>> userMap,String outputPath) {
		for(Entry<String, Map<String, List<User>>> entry : userMap.entrySet()){
			FileTool.initWriter1(outputPath + entry.getKey());
			Map<String, List<User>> temp = entry.getValue();
			for(Entry<String, List<User>> tempEntry : temp.entrySet()){
				List<User> users = tempEntry.getValue();
				int count = users.size();
				for(User user : users){
					FileTool.ps1.print(user.getUserId() + "\t");
					FileTool.ps1.print(user.getItemId() + "\t");
					FileTool.ps1.print(user.getBehaviorType() + "\t");
					//FileTool.ps1.print(user.getUserGeoHash() + "\t");
					//FileTool.ps1.print(user.getItemCategory() + "\t");
					//FileTool.ps1.print(user.getTime() + "\t");
					FileTool.ps1.print(count + "\n");
				}
			}
		}
		FileTool.closeWriter1();
	}
	
	public static void output(Map<String, Map<String, Double>> scoreTable, String outputPath, Set<String> userSet, Set<String> itemSet, String token) {
		FileTool.initWriter1(outputPath);
		
		for(String itemId: itemSet){
			FileTool.ps1.print(token + itemId);
		}
		FileTool.ps1.println();
		for(String userId : userSet){
			FileTool.ps1.print(userId + token);
			Map<String, Double> itemMap = scoreTable.get(userId);
			for(String itemId: itemSet){
				if(itemMap.containsKey(itemId)){
					FileTool.ps1.print(itemMap.get(itemId));
				}else {
					//FileTool.ps1.print(0);
				}
				FileTool.ps1.print(token);
			}
			FileTool.ps1.print("\n");
		}
	}
	
	public static void outputUser(List<User> userList) {
		for(User user : userList){
			FileTool.ps1.println(user.getUserId() + "\t" + user.getItemId() + "\t" + user.getWeight());
		}
	}
	
	public static void outputScore(List<Score> scoreList) {
		for(Score score : scoreList){
			FileTool.ps1.println(score.getUserId() + "\t" + score.getItemId() + "\t" + score.getScore());
		}
	}
	
	public static void outputRecommendList(Map<String, Set<String>> map) {
		for(Entry<String, Set<String>> entry : map.entrySet()){
			String userId = entry.getKey();
			Set<String> itemSet = entry.getValue();
			for(String itemId : itemSet){
				FileTool.ps1.println(userId + "," + itemId);
			}
		}
	}
	
	public static void output(Map<String, Set<String>> map) {
		for(Entry<String, Set<String>> entry : map.entrySet()){
			String userId = entry.getKey();
			Set<String> set = entry.getValue();
			for(String itemId : set){
				FileTool.ps1.println(userId + "\t" + itemId);
			}
		}
	}
	
	public static Map<String, Map<String, List<User>>> mapByUser(List<User> userList,Set<String> userSet,Set<String> itemSet) {
		Map<String, Map<String, List<User>>> userMap = new HashMap<>();
		for(User user: userList){
			Map<String, List<User>> tempMap = new HashMap<String, List<User>>();
			List<User> tempList = new ArrayList<User>();
			if (!userMap.containsKey(user.getUserId())) {
			}else {
				tempMap = userMap.get(user.getUserId());
				if (!tempMap.containsKey(user.getItemId())) {
				}else {
					tempList = tempMap.get(user.getItemId());
				}
			}
			tempList.add(user);
			tempMap.put(user.getItemId(), tempList);
			userMap.put(user.getUserId(), tempMap);
			userSet.add(user.getUserId());
			itemSet.add(user.getItemId());
			
		}
		return userMap;
	}
	
	public static Map<String, Map<String, Double>> makeScoreTable(Map<String, Map<String, List<User>>> userMap) {
		Map<String, Map<String, Double>> scoreTable = new HashMap<String, Map<String,Double>>();
		for(Entry<String, Map<String, List<User>>> userEntry : userMap.entrySet()){
			
			Map<String, List<User>> itemMap = userEntry.getValue();
			String userId = userEntry.getKey();
			
			Map<String, Double> itemScoreMap = new HashMap<String, Double>();
			
			for(Entry<String, List<User>> itemEntry : itemMap.entrySet()){
				String itemId = itemEntry.getKey();
				List<User> users = itemEntry.getValue();
				double weight = 0.0;
				
				int maxType = 0;
				for(User user : users){
					if (user.getBehaviorType() > maxType) {
						maxType = user.getBehaviorType();
					}
				}
				
				int count = users.size();
				if (maxType != 0) {
					weight += w[maxType-1];
				}
				weight += count;
				
				itemScoreMap.put(itemId, weight);
			}
			scoreTable.put(userId, itemScoreMap);
		}
		return scoreTable;
	}
	public static double calculateWeight(int behaviorType, int count) {
		double weight = w[behaviorType-1] + count;
		return weight;
	}
	public static List<User> reduceUserByItem(List<User> userList) {
		List<User> list = new ArrayList<User>();
		Map<String, User> userMap = new LinkedHashMap<String, User>();
		for(User user : userList){
			String itemId = user.getItemId();
			if (!userMap.containsKey(itemId)) {
				double weight = calculateWeight(user.getBehaviorType(), user.getCount());
				user.setWeight(weight);
				userMap.put(itemId, user);
				list.add(user);
			}else {
				User temp = userMap.get(itemId);
				if (temp.getBehaviorType() < user.getBehaviorType()) {
					double weight = calculateWeight(user.getBehaviorType(), user.getCount());
					user.setWeight(weight);
					userMap.put(itemId, user);
					list.add(user);
				}
			}
		}
		userMap.clear();
		return list;
	}
	
	public static void sortScoreMap(Map<String, List<Score>> scoreMap) {
		Set<String> userSet = scoreMap.keySet();
		for(String userId : userSet){
			List<Score> temp = scoreMap.get(userId);
			Collections.sort(temp);
			scoreMap.put(userId, temp);
		}
	}
	public static Map<String, Set<String>> predict(Map<String, List<Score>> scoreMap, List<String> fileNameList, String userDir,int topNUser,int topNItem) throws Exception {
		Map<String, Set<String>> recommendList = new HashMap<String, Set<String>>();
		for(Entry<String, List<Score>> entry : scoreMap.entrySet()){
			String userId1 = entry.getKey();
			List<Score> list = entry.getValue();
			int countUser = 0;
			Set<String> predictItemSet = new LinkedHashSet<String>();
			for(Score score : list){
				String userId2 = score.getItemId();
				if(fileNameList.contains(userId2)){
					List<User> userList = FileTool.readFileOne(userDir + userId2, false, "\t", "user");
					int countItem = 0;
					for(User user : userList){
						predictItemSet.add(user.getItemId());
						countItem++;
						if (countItem == topNItem) {
							break;
						}
					}
					countUser++;
				}
				if (countUser == topNUser) {
					break;
				}
			}
			recommendList.put(userId1, predictItemSet);
		}
		return recommendList;
	}
	public static void prediction(Map<String, List<String>> predictMap,int predictN, Map<String, List<String>> referenceMap, int refN) {
		int count = 0;
		for(Entry<String, List<String>> predictEntity : predictMap.entrySet()){
			String userId = predictEntity.getKey();
			if (referenceMap.containsKey(userId)) {
				List<String> predictList = predictEntity.getValue();
				for(String itemId : predictList){
					if (referenceMap.get(userId).contains(itemId)) {
						count++;
					}
				}
			}
		}
		double precision = (1.0 * count / predictN) * 100;
		double recall = (1.0 * count / refN) * 100;
		double f1 = (2 * precision * recall)/(precision + recall);
		System.out.println("precision="+precision+",recall="+recall+",f1="+f1);
	}
	
}
