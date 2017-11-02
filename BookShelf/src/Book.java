/**
 * @author  by sinkala.Kundananji on 31.10.16.
 */
public class Book {
    private String title;
    private String author;
    private String review;
    private int rating;
    private int price;
    private int count;

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
        this.review = "n/a";
        this.rating = 0;
        this.price = 0;
        this.count = 1;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return the author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * @return the review
     */
    public String getReview() {
        return review;
    }

    /**
     * @return the rating
     */
    public int getRating() {
        return rating;
    }

    /**
     * @return the price
     */
    public int getPrice() {
        return price;
    }
    
    /**
     * @return the count
     */
    public int getCount() {
        return count;
    }

    /**
     * @param count the count to set
     */
    public void setCount(int count) {
        this.count = count;
    }

    /**
     * @param review the review to set
     */
    public void setReview(String review) {
        this.review = review;
    }

    /**
     * @param rating the rating to set
     */
    public void setRating(int rating) {
        this.rating = rating;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(int price) {
        this.price = price;
    }
    
    

    public String toString() {
        return "Title:  "+this.getTitle()+"\n"+
               "Author: "+this.getAuthor()+"\n"+
               "Rating: "+this.getRating()+"\n"+
               "Review: "+this.getReview()+"\n"+
               "Price:  "+this.getPrice()+"\n"+
               "Count:  "+this.getCount();
    }

}

