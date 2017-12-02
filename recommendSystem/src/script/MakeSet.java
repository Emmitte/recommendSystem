package script;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import entity.User;
import util.FileTool;

public class MakeSet {

	public static void main(String[] args) throws Exception {
		String inputDir = args[0];
		String outputDir = args[1];
		Set<String> userSet = new HashSet<String>();
		Set<String> itemSet = new HashSet<String>();
		List<String> pathList = FileTool.traverseFolder(inputDir);
		for(String path : pathList){
			String inputPath = inputDir + path;
			List<User> list = FileTool.readFileOne(inputPath, false, "\t", "user");
			for(User user : list){
				userSet.add(user.getUserId());
				itemSet.add(user.getItemId());
			}
		}
		FileTool.initWriter1(outputDir+"userSet");
		for(String userId : userSet){
			FileTool.ps1.println(userId);
		}
		FileTool.closeWriter1();
		FileTool.initWriter1(outputDir+"itemSet");
		for(String itemId : itemSet){
			FileTool.ps1.println(itemId);
		}
		FileTool.closeWriter1();
		
	}

}
