package script;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import entity.User;
import service.DataProcess;
import util.FileTool;

public class ReduceFileTest {
	public static void main(String[] args) throws Exception {
		//String inputDir = "data/fresh_comp_offline/";
		//String outputDir = "data/fresh_comp_offline/sample/";
		//String inputDir = "data/fresh_comp_offline/sample/";
		//String outputDir = "data/fresh_comp_offline/sample/out/";
		String inputDir = args[0];
		String outputDir = args[1];
		//String userPath = inputDir + "tianchi_fresh_comp_train_user.csv";
		//String itemPath = inputDir + args[2];
		//String userPath = inputDir + args[3];
		
		List<String> pathList = FileTool.traverseFolder(inputDir);
		for(String path : pathList){
			List<User> userList = FileTool.readFileOne(inputDir+path, false, "\t", "user");
			List<User> list = DataProcess.reduceUserByItem(userList);
			userList.clear();
			FileTool.initWriter1(outputDir + path);
			Collections.sort(list);
			DataProcess.outputUser(list);
			FileTool.closeWriter1();
			list.clear();
		}
	}
}
