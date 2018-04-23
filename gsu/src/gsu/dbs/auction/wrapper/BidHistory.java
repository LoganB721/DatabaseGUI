package gsu.dbs.auction.wrapper;

public class BidHistory {
	private Product product;
	private int user;
	private int bid;
	
	public BidHistory(Product product, int user, int currentBid) {
		this.product = product;
		this.user = user;
		this.bid = currentBid;
	}
	
	public Product getProduct() {
		return this.product;
	}
	
	public int getUserID() {
		return this.user;
	}
	
	public int getBidAmount() {
		return this.bid;
	}
}
