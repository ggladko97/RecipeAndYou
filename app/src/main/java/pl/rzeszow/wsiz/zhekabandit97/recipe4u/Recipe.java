package pl.rzeszow.wsiz.zhekabandit97.recipe4u;

/**
 * Created by ggladko97 on 04.12.16.
 */
public class Recipe {
    private String name;
    private String url_recipe;
    private String url_image;
    private String rate;
    private String publisher;

    public Recipe() {
    }

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

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl_recipe() {
        return url_recipe;
    }

    public void setUrl_recipe(String url_recipe) {
        this.url_recipe = url_recipe;
    }

    public String getUrl_image() {
        return url_image;
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
}
