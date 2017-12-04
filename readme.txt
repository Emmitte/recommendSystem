脚本：
1.SpliteFileAndMakeScoreTable
将文件拆分，并生成score
调用时需对ParseTool操作：
放开
if (contents[3] != null && !contents[3].isEmpty()) {
	user.setUserGeoHash(contents[3].trim());
}
if (contents[4] != null && !contents[4].isEmpty()) {
	user.setItemCategory(contents[4].trim());
}
if (contents[5] != null && !contents[5].isEmpty()) {
	user.setTime(contents[5].trim());
}
注释：
if (contents[2] != null && !contents[2].isEmpty()) {
	user.setBehaviorType(Integer.valueOf(contents[2].trim()));
}
if (contents[n-1] != null && !contents[n-1].isEmpty()) {
	user.setCount(Integer.valueOf(contents[n-1].trim()));
}
if (contents[n-1] != null && !contents[n-1].isEmpty()) {
	user.setWeight(Double.valueOf(contents[n-1].trim()));
}
2.ReduceFileTest
对拆分后的小文件排序
调用时需对ParseTool操作：
放开
if (contents[2] != null && !contents[2].isEmpty()) {
	user.setBehaviorType(Integer.valueOf(contents[2].trim()));
}
if (contents[n-1] != null && !contents[n-1].isEmpty()) {
	user.setCount(Integer.valueOf(contents[n-1].trim()));
}
注释：
if (contents[n-1] != null && !contents[n-1].isEmpty()) {
	user.setWeight(Double.valueOf(contents[n-1].trim()));
}
if (contents[3] != null && !contents[3].isEmpty()) {
	user.setUserGeoHash(contents[3].trim());
}
if (contents[4] != null && !contents[4].isEmpty()) {
	user.setItemCategory(contents[4].trim());
}
if (contents[5] != null && !contents[5].isEmpty()) {
	user.setTime(contents[5].trim());
}
3.PredictTest
使用score及排序后的小文件，对userId进行预测
调用时需对ParseTool操作：
放开
if (contents[n-1] != null && !contents[n-1].isEmpty()) {
	user.setWeight(Double.valueOf(contents[n-1].trim()));
}
注释：
if (contents[2] != null && !contents[2].isEmpty()) {
	user.setBehaviorType(Integer.valueOf(contents[2].trim()));
}
if (contents[n-1] != null && !contents[n-1].isEmpty()) {
	user.setCount(Integer.valueOf(contents[n-1].trim()));
}
if (contents[3] != null && !contents[3].isEmpty()) {
	user.setUserGeoHash(contents[3].trim());
}
if (contents[4] != null && !contents[4].isEmpty()) {
	user.setItemCategory(contents[4].trim());
}
if (contents[5] != null && !contents[5].isEmpty()) {
	user.setTime(contents[5].trim());
}
4.MakeTestSet
生成测试集
5.MatchTest2
统计准确率、召回率、F值
6.MakeSet
生成集合：userSet、itemSet
7.MakeScoreTable(其基础为MakeSet及单个用户文件)
将用户文件(小文件)进行遍历计算相似度，生成score
此方法由于多次读文件时间开销较大，适合分布式操作.

流程：
1.SpliteFileAndMakeScoreTable
将文件拆分，并生成score
2.ReduceFileTest
对拆分后的小文件排序
3.PredictTest
使用score及排序后的小文件，对userId进行预测
4.MakeTestSet
生成测试集
5.MatchTest2
统计准确率、召回率、F值

script:
运行顺序：
1.runSpliteFileAndMakeScoreTable.sh //map文件并生成user-user的score
2.runSortFile.sh  //对map后的文件排序，主要对user内的item的score排序
3.runPredict.sh   //预测，生成预测列表user-item
4.runMakeTestSet.sh  //生成测试集
5.runSpliteFile.sh   //对测试集文件进行map
6.runMatch.sh     //将预测列表与测试集进行匹配，计算预测准确率及召回率