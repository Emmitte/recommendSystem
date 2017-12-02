package script;

import java.util.List;
import java.util.Map;

import service.DataProcess;
import util.FileTool;

public class MatchTest {

	public static void main(String[] args) throws Exception {
		String inputDir = args[0];
		String inputPath1 = inputDir + args[1];
		String inputPath2 = inputDir + args[2];
		Map<String, List<String>> predictMap = FileTool.loadPredictData(inputPath1, false, ",");
		int predictN = FileTool.count;
		FileTool.count = 0;
		Map<String, List<String>> referenceMap = FileTool.loadPredictData(inputPath2, false, ",");
		int referenceN = FileTool.count;
		DataProcess.prediction(predictMap, predictN, referenceMap, referenceN);
	}

}
