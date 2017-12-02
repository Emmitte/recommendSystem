package entity;

public class User implements Comparable<User> {
	private String userId;      // 用户标识
	private String itemId;      // 商品标识
	private int behaviorType;   // 用户对商品的行为类型,可以为空,包括浏览、收藏、加购物车、购买，对应取值分别是1、2、3、4.
	private String userGeoHash; // 用户位置的空间标识
	private String itemCategory;// 商品分类标识
	private String time;        // 行为时间
	private int count;
	private double weight;      // 权重
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
	public int getBehaviorType() {
		return behaviorType;
	}
	public void setBehaviorType(int behaviorType) {
		this.behaviorType = behaviorType;
	}
	
	public String getUserGeoHash() {
		return userGeoHash;
	}
	public void setUserGeoHash(String userGeoHash) {
		this.userGeoHash = userGeoHash;
	}
	public String getItemCategory() {
		return itemCategory;
	}
	public void setItemCategory(String itemCategory) {
		this.itemCategory = itemCategory;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
	
	
	@Override
	public String toString() {
		return "User [userId=" + userId + ", itemId=" + itemId
				+ ", behaviorType=" + behaviorType + ", count=" + count + "]";
	}
	
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	@Override
	public int compareTo(User o) {
		return (int)((-1) * (this.weight - o.weight));
	}
	
}
