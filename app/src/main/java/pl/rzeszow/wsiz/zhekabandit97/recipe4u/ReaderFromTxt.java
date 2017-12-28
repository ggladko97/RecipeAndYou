package pl.rzeszow.wsiz.zhekabandit97.recipe4u;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ggladko97 on 02.01.17.
 */
public class ReaderFromTxt {
    Context context;

    public ReaderFromTxt(Context context) {
        this.context = context;
    }

    public List<String> ReadTextFromFile(String file) {
        List<String> listOfIngredientsFromTxt = new ArrayList<>();

        AssetManager assetManager = context.getResources().getAssets();
        InputStream inputStream = null;
        try {
            inputStream = assetManager.open(file);
            BufferedReader in =
                    new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            String str;
            while ((str = in.readLine()) != null) {

               listOfIngredientsFromTxt.add(str);
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listOfIngredientsFromTxt;
    }



}