package homeInventory;

public class InventoryItem {
	public String item;
	public String location;
	public boolean marked;
	public String price;
	public String date;
	public String store;
	public String note;
	public String src;
	public String id;
	public String toString() {
		return item+" "+location;
	}
}
