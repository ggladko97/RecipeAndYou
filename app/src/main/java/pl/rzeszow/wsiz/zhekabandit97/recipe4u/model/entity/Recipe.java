package pl.rzeszow.wsiz.zhekabandit97.recipe4u.model.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ggladko97 on 04.12.16.
 */
public class Recipe {

    @SerializedName("title")
    @Expose
    private String name;

    @SerializedName("f2f_url")
    @Expose
    private String url_recipe;

    @SerializedName("image_url")
    @Expose
    private String url_image;

    @SerializedName("social_rank")
    @Expose
    private String rate;

    @SerializedName("publisher")
    @Expose
    private String publisher;



    public String getName() {
        return name;
    }


    public String getUrl_recipe() {
        return url_recipe;
    }


    public String getUrl_image() {
        return url_image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUrl_recipe(String url_recipe) {
        this.url_recipe = url_recipe;
    }

    public void setUrl_image(String url_image) {
        this.url_image = url_image;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "name='" + name + '\'' +
                '}';
    }
}
