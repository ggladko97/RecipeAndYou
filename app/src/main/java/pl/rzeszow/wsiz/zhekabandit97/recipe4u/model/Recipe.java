package pl.rzeszow.wsiz.zhekabandit97.recipe4u.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ggladko97 on 04.12.16.
 */
public class Recipe {

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("url_recipe")
    @Expose
    private String url_recipe;

    @SerializedName("url_image")
    @Expose
    private String url_image;

    @SerializedName("social_rank")
    @Expose
    private String rate;

    @SerializedName("publisher")
    @Expose
    private String publisher;

    public Recipe(String name, String url_recipe, String url_image, String rate, String publisher) {
        this.name = name;
        this.url_recipe = url_recipe;
        this.url_image = url_image;
        this.rate = rate;
        this.publisher = publisher;
    }

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
                ", url_recipe='" + url_recipe + '\'' +
                ", url_image='" + url_image + '\'' +
                ", rate='" + rate + '\'' +
                ", publisher='" + publisher + '\'' +
                '}';
    }
}
