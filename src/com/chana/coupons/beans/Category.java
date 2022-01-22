package com.chana.coupons.beans;

public enum Category {
	FOOD(1), ELECTRICITY(2), RESTAURANT(3),VACATION(4);

	private int id;
	private Category(int id) {
		this.id = id;
	}
	public int getId() {
		 return id;
	}
	public int getId(Category category) {
		
		 return id;
	}
	public void setId(int id,Category category ) {
		
		this.id = id;
	}
	public static Category fromString(String string) {
		for (Category c : Category.values()) {
			if(c.equals(string)) {
				return c;
			}
		}
		return null;
	}
}
