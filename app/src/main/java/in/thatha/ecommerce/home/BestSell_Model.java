package in.thatha.ecommerce.home;

public class BestSell_Model {

     private String prod_name,img_url,old_price,price,rating;

    public BestSell_Model(String prod_name,String img_url,String old_price,String price,String rating){
this.prod_name = prod_name;
this.img_url = img_url;
this.old_price =old_price;
this.price = price;
this.rating = rating;
    }

    public String getProd_name(){ return prod_name;}
  public void setProd_name(String name){this.prod_name = name;}


    public String getImg_url(){return img_url;}
    public void setImg_url(String url){this.img_url = url;}


    public String getOld_price(){ return old_price;}
    public void setOld_price(String old_price){this.old_price = old_price;}


    public String getPrice(){return price;}
    public void setPrice(String price){this.price = price;}


    public String getRating(){return rating;}
    public void setRating(String rating){this.rating = rating;}


}
