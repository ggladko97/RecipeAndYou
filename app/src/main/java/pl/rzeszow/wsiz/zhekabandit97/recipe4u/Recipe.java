package pl.rzeszow.wsiz.zhekabandit97.recipe4u;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ggladko97 on 04.12.16.
 */
public class Recipe {
    private String name;
    private String url_recipe;
    private String url_image;
    private String rate;
    private String publisher;
    private boolean isSearched = false;


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


}
