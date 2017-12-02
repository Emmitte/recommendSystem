package entity;

public class Score implements Comparable<Score> {
	private String userId;      // 用户标识
	private String itemId;      // 商品标识
	private double score;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	@Override
	public String toString() {
		return "Score [userId=" + userId + ", itemId=" + itemId + ", score="
				+ score + "]";
	}
	@Override
	public int compareTo(Score o) {
		if ((this.score - o.score) < 0) {
			return 1;
		}else if ((this.score - o.score) > 0) {
			return -1;
		}else {
			return 0;
		}
	}
	
}
