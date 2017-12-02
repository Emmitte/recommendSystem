package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import entity.Item;
import entity.Score;
import entity.User;


public class FileTool {
	
	public static FileReader fr=null;
	public static BufferedReader br=null;
	public static String line=null;
	
	public static FileOutputStream fos1 = null,fos2 = null,fos3 = null;
	public static PrintStream ps1 = null,ps2 = null,ps3 = null;
	
	public static int count = 0;
	
	/** 
	 * 初始化写文件器(单一指针)
	 * */
	public static void initWriter1(String writePath) {
		try {
			fos1 = new FileOutputStream(writePath);
			ps1 = new PrintStream(fos1);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	/** 
	 * 关闭文件器(单一指针)
	 * */
	public static void closeRedaer() {
		try {
			br.close();
			fr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/** 
	 * 关闭文件器(单一指针)
	 * */
	public static void closeWriter1() {
		try {
			ps1.close();
			fos1.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/** 
	 * 初始化写文件器(双指针)
	 * */
	public static void initWriter2(String writePath1,String writePath2) {
		try {
			fos1 = new FileOutputStream(writePath1);
			ps1 = new PrintStream(fos1);
			fos2 = new FileOutputStream(writePath2);
			ps2 = new PrintStream(fos2);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	/** 
	 * 关闭文件器(双指针)
	 * */
	public static void closeWriter2() {
		try {
			ps1.close();
			fos1.close();
			ps2.close();
			fos2.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/** 
	 * 初始化写文件器(三指针)
	 * */
	public static void initWriter3(String writePath1,String writePath2,String writePath3) {
		try {
			fos1 = new FileOutputStream(writePath1);
			ps1 = new PrintStream(fos1);
			fos2 = new FileOutputStream(writePath2);
			ps2 = new PrintStream(fos2);
			fos3 = new FileOutputStream(writePath3);
			ps3 = new PrintStream(fos3);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	/** 
	 * 关闭文件器(三指针)
	 * */
	public static void closeWriter3() {
		try {
			ps1.close();
			fos1.close();
			ps2.close();
			fos2.close();
			ps3.close();
			fos3.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static List readFileOne(String path,boolean isTitle,String token,String pattern) throws Exception {
		List<Object> ret = new ArrayList<Object>();
		
		fr = new FileReader(path);
		br = new BufferedReader(fr);
		int count = 0,i = 0;
		
		if (isTitle) {
			line = br.readLine();
			count++;
		}
		
		while((line = br.readLine()) != null){
			String[] strArr = line.split(token);
			switch (pattern) {
			case "item":
				ret.add(ParseTool.parseItem(strArr));
				break;
			case "user":
				ret.add(ParseTool.parseUser(strArr));
				break;
			case "score":
				ret.add(ParseTool.parseScore(strArr));
			default:
				ret.add(line);
				break;
			}
			count++;
			if (count/100000 == 1) {
				i++;
				System.out.println(100000*i);
				count = 0;
			}
		}
		
		closeRedaer();
		
		return ret;
	}
	public static void makeSampleData(String inputPath,boolean isTitle,String outputPath,int threshold) throws Exception {
		
		fr = new FileReader(inputPath);
		br = new BufferedReader(fr);
		initWriter1(outputPath);
		
		if (isTitle) {
			line = br.readLine();
		}
		int count = 0;
		while((line = br.readLine()) != null){
			ps1.println(line);
			count++;
			if (count == threshold) {
				break;
			}
		}
		closeRedaer();
	}
	public static List<String> traverseFolder(String dir) {
        File file = new File(dir);
        String[] fileList = null;
        if (file.exists()) {
        	fileList = file.list();
        }
        List<String> list = new ArrayList<String>();
        for(String path : fileList){
        	list.add(path);
        }
        return list;
    }
	public static Map<String, List<Score>> loadScoreMap(String path,boolean isTitle,String token) throws Exception {
		fr = new FileReader(path);
		br = new BufferedReader(fr);
		
		if (isTitle) {
			line = br.readLine();
		}
		
		Map<String, List<Score>> scoreMap = new HashMap<String, List<Score>>();
		
		while((line = br.readLine()) != null){
			String[] arr = line.split(token);
			Score score = ParseTool.parseScore(arr);
			List<Score> temp = new ArrayList<Score>();
			if (scoreMap.containsKey(score.getUserId())) {
				temp = scoreMap.get(score.getUserId());
			}
			temp.add(score);
			scoreMap.put(score.getUserId(), temp);
		}
		closeRedaer();
		return scoreMap;
	}
	
	public static Map<String, List<String>> loadPredictData(String path,boolean isTitle,String token) throws Exception {
		fr = new FileReader(path);
		br = new BufferedReader(fr);
		
		if (isTitle) {
			line = br.readLine();
		}
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		while((line = br.readLine()) != null){
			String[] arr = line.split(token);
			String userId = arr[0];
			String itemId = arr[1];
			List<String> temp = new ArrayList<String>();
			if (map.containsKey(userId)) {
				temp = map.get(userId);
			}
			temp.add(itemId);
			map.put(userId, temp);
			count++;
		}
		
		closeRedaer();
		return map;
	}
	
	public static Map<String, List<String>> loadTestData(Map<String, List<String>> predictMap, String dir, boolean isTitle, String token) throws Exception {
		
		List<String> fileList = traverseFolder(dir);
		Set<String> predictKeySet = predictMap.keySet();
		Map<String, List<String>> testMap = new HashMap<String, List<String>>();
		for(String predictKey : predictKeySet){
			if (fileList.contains(predictKey)) {
				List<String> itemList = loadTestData(dir + predictKey, isTitle, token);
				testMap.put(predictKey, itemList);
			}
		}
		return testMap;
	}
	
	public static List<String> loadTestData(String path, boolean isTitle, String token) throws Exception {
		fr = new FileReader(path);
		br = new BufferedReader(fr);
		
		if (isTitle) {
			line = br.readLine();
		}
		
		List<String> list = new ArrayList<String>();
		Set<String> set = new HashSet<String>();
		while((line = br.readLine()) != null){
			String[] arr = line.split(token);
			set.add(arr[1]);
			count++;
		}
		closeRedaer();
		for(String item : set){
			list.add(item);
		}
		return list;
	}
	
	public static Map<String, Double> loadUser_ItemData(String path,boolean isTitle,String token) throws Exception {
		fr = new FileReader(path);
		br = new BufferedReader(fr);
		
		if (isTitle) {
			line = br.readLine();
		}
		Map<String, Double> map = new HashMap<String, Double>();
		while((line = br.readLine()) != null){
			String[] arr = line.split(token);
			String itemId = arr[1];
			double score = Double.valueOf(arr[2]);
			if(map.containsKey(itemId)){
				double temp = map.get(itemId);
				if (temp > score) {
					score = temp;
				}
			}
			map.put(itemId, score);
		}
		closeRedaer();
		return map;
	}
	
	public static Map<String, Set<String>> loadTestUser(String path,boolean isTitle,String token) throws Exception {
		fr = new FileReader(path);
		br = new BufferedReader(fr);
		int count = 0,i = 0;
		
		if (isTitle) {
			line = br.readLine();
			count++;
		}
		Map<String, Set<String>> map = new HashMap<String, Set<String>>();
		while((line = br.readLine()) != null){
			String[] arr = line.split(token);
			String userId = arr[0];
			String itemId = arr[1];
			Set<String> set = new HashSet<String>();
			if (map.containsKey(userId)) {
				set = map.get(userId);
				set.add(itemId);
			}
			map.put(userId, set);
			count++;
			if (count/100000 == 1) {
				i++;
				System.out.println(100000*i);
				count = 0;
			}
		}
		closeRedaer();
		return map;
	}
	
}
