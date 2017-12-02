package entity;

public class Item {
	private String itemId;
	private String itemGeoHash;
	private String itemCategory;
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getItemGeoHash() {
		return itemGeoHash;
	}
	public void setItemGeoHash(String itemGeoHash) {
		this.itemGeoHash = itemGeoHash;
	}
	public String getItemCategory() {
		return itemCategory;
	}
	public void setItemCategory(String itemCategory) {
		this.itemCategory = itemCategory;
	}
	@Override
	public String toString() {
		return "item [itemId=" + itemId + ", itemGeoHash=" + itemGeoHash
				+ ", itemCategory=" + itemCategory + "]";
	}
	
}
