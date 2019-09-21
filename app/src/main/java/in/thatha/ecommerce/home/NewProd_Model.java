package in.thatha.ecommerce.home;

public class NewProd_Model {

    private String prod_id,prod_name,img_url;

    public NewProd_Model(String prod_id,String name,String img_url){
       this.prod_id = prod_id;
        this.prod_name = name;
        this.img_url = img_url;

    }
    public String getProd_id(){ return prod_id;}
    public void setProd_id(String id){this.prod_name = id;}


    public String getProd_name(){ return prod_name;}
    public void setProd_name(String name){this.prod_name = name;}


    public String getImg_url(){return img_url;}
    public void setImg_url(String url){this.img_url = url;}
}
