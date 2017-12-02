package script;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import service.CalculateSimilarity;
import util.FileTool;

public class MakeScoreTable {

	public static void main(String[] args) throws Exception {
		String inputDir = args[0];
		String outputDir = args[1];
		String outputPath = outputDir + args[2];
		String userSetPath = args[3];
		String itemSetPath = args[4];
		List<String> pathList = FileTool.traverseFolder(inputDir);
		FileTool.initWriter1(outputPath);
		List<String> userList = FileTool.readFileOne(userSetPath, false, "\t", "");
		Set<String> userSet = new HashSet<String>();
		userSet.addAll(userList);
		userList.clear();
		List<String> itemList = FileTool.readFileOne(itemSetPath, false, "\t", "");
		Set<String> itemSet = new HashSet<String>();
		itemSet.addAll(itemList);
		itemList.clear();
		FileTool.initWriter1(outputPath);
		int count = 1;
		for(String path : pathList){
			Map<String, Double> userMap1 = FileTool.loadUser_ItemData(inputDir + path, false, "\t");
			for(String compareUserPath : pathList){
				if (!path.equals(compareUserPath)) {
					Map<String, Double> userMap2 = FileTool.loadUser_ItemData(inputDir + compareUserPath, false, "\t");
					double similar = CalculateSimilarity.execute(userMap1, userMap2, userSet, itemSet);
					FileTool.ps1.println(path.replace("_", "") + "\t" + compareUserPath.replace("_", "") + "\t" + similar);
					userMap2.clear();
				}
			}
			userMap1.clear();
			System.out.println("userN:"+count);
			count++;
		}
		userSet.clear();
		itemSet.clear();
		FileTool.closeWriter1();
	}

}
