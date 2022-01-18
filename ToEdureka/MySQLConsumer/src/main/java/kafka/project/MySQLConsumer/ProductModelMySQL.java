package kafka.project.MySQLConsumer;

import java.sql.Timestamp;

public class ProductModelMySQL {
	private String pogId;
	private String supC;
	private String brand;
	private String description;
	private String size;
	private String category;
	private String subCategory;
	private float price;
	private int quantity;
	private String country;
	private String sellerCode;
	private String creation;
	private String stock;
	
	ProductModelMySQL(){};
	ProductModelMySQL(String pogId, String supC, String brand, 
			     String description, String size, String category, 
			     String subCategory, float price, int quantity, 
			     String country, String sellerCode, String creation, String stock){
		this.pogId = pogId;
		this.supC = supC;
		this.brand = brand;
		this.description = description;
		this.size = size;
		this.category = category;
	    this.subCategory = subCategory;
	    this.price = price;
	    this.quantity = quantity;
	    this.country = country;
	    this.sellerCode = sellerCode;
	    this.creation = creation;
	    this.stock = stock;
	}
	public String getPogId() {
		return pogId;
	}
	public void setPogId(String pogId) {
		this.pogId = pogId;
	}
	public String getSupC() {
		return supC;
	}
	public void setSupC(String supC) {
		this.supC = supC;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getSubCategory() {
		return subCategory;
	}
	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getSellerCode() {
		return sellerCode;
	}
	public void setSellerCode(String sellerCode) {
		this.sellerCode = sellerCode;
	}
	public String getCreation() {
		return creation;
	}
	public void setCreation(String creation) {
		this.creation = creation;
	}
	public String getStock() {
		return stock;
	}
	public void setStock(String stock) {
		this.stock = stock;
	}
	
}

