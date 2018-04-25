package gsu.dbs.auction.wrapper;

public class Review {
	private Customer buyer;
	private Vendor vendor;
	private int rating;
	private String comment;
	
	public Review(Customer customer, Vendor vendor, int rating, String comment ) {
		this.buyer = customer;
		this.vendor = vendor;
		this.rating = rating;
		this.comment = comment;
	}
	
	public Vendor getVendor() {
		return this.vendor;
	}
	
	public Customer getBuyer() {
		return this.buyer;
	}
	
	public int getRating() {
		return this.rating;
	}
	
	public String getComment() {
		return this.comment;
	}
}
