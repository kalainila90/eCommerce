package in.thatha.ecommerce.productpreview;

public class Review_Model {
    private String review_title,review_desc,review_username,review_date,review_rating;

    public Review_Model(String review_title,String review_desc,String review_username,String review_date,String review_rating){
        this.review_title = review_title;
        this.review_desc = review_desc;
        this.review_username = review_username;
        this.review_date =review_date;
        this.review_rating = review_rating;

    }


    public String getReview_title(){ return review_title;}
    public void setReview_title(String title){this.review_title = title;}


    public String getReview_desc(){ return review_desc;}
    public void setReview_desc(String desc){this.review_desc = desc;}


    public String getReview_username(){ return review_username;}
    public void setReview_username(String username){this.review_title = username;}


    public String getReview_date(){ return review_date;}
    public void setReview_date(String date){this.review_title = date;}


    public String getReview_rating(){ return review_rating;}
    public void setReview_rating(String rating){this.review_title = rating;}

}
