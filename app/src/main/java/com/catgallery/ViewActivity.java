package com.catgallery;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.volley.Cache;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class ViewActivity extends AppCompatActivity {

    private static final String TAG = ViewActivity.class.getSimpleName();
    private HashMap<String, Bitmap> catPic = new HashMap<>();
    private GridView gridView;
    private GridViewAdapter gridAdapter;
    private ArrayList<ImageItem> data = new ArrayList<>();
    private HashMap<ImageItem,String> imageName = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        Intent intent = getIntent();
        HashMap<String, String[]> catJSON = (HashMap<String,String[]>) intent.getSerializableExtra("cat_json");
        System.out.println("catJSON SIZE:" + catJSON.size());
        Collection c = catJSON.values();
        Iterator it = c.iterator();
        while(it.hasNext()){
            final String[] file = (String[]) it.next();
            Cache cache = AppController.getInstance().getRequestQueue().getCache();
            Cache.Entry entry = cache.get(AppConfig.URL_GET_IMAGES + file[6] + ".jpg");
            if(entry != null){
                try {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(entry.data, 0, entry.data.length);
                    catPic.put(file[6], bitmap);
                    updater(bitmap, file[6]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else{
                ImageLoader imageLoader = AppController.getInstance().getImageLoader();
                imageLoader.get(AppConfig.URL_GET_IMAGES + file[6] + ".jpg", new ImageLoader.ImageListener() {
                    @Override
                    public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                        catPic.put(file[6], response.getBitmap());
                        updater(response.getBitmap(), file[6]);
                    }

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Image Load Error: " + error.getMessage());
                    }
                });
            }
        }
    }

    public void updateView(){
        int id = 1110;
        LinearLayout main_layer= (LinearLayout) findViewById(R.id.dynamic_layout);
        Collection collection = catPic.values();
        Iterator it = collection.iterator();
        while(it.hasNext()){
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
            LinearLayout layout = new LinearLayout(getApplicationContext());
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setLayoutParams(params);
            params.gravity = Gravity.CENTER;
            ImageView imageView = new ImageView(getApplicationContext());
            imageView.setLayoutParams(params);
            //imageView.setId(id);
            imageView.setImageBitmap((Bitmap)it.next());
            imageView.setOnClickListener(getOnClickDoSomething(imageView));
            layout.addView(imageView);
            main_layer.addView(layout);
        }

    }

    public void updateView(Bitmap b){
        int id = 1110;
        LinearLayout main_layer= (LinearLayout) findViewById(R.id.dynamic_layout);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        LinearLayout layout = new LinearLayout(getApplicationContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setLayoutParams(params);
        params.gravity = Gravity.CENTER;
        ImageView imageView = new ImageView(getApplicationContext());
        imageView.setLayoutParams(params);
        //imageView.setId(id);
        imageView.setImageBitmap(b);
        imageView.setOnClickListener(getOnClickDoSomething(imageView));
        layout.addView(imageView);
        main_layer.addView(layout);

    }

    public void updater(Bitmap bitmap, String name){
        try{
            ImageItem imageItem = new ImageItem(bitmap,bitmap.toString());
            //data.add(new ImageItem(bitmap, bitmap.toString()));
            data.add(imageItem);
            imageName.put(imageItem,name);
            gridView = (GridView) findViewById(R.id.gridView);
            gridAdapter = new GridViewAdapter(this, R.layout.grid_item_layout, data,imageName);
            gridView.setAdapter(gridAdapter);

            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                    ImageItem item = (ImageItem) parent.getItemAtPosition(position);

                    //Create intent
                    Intent intent = new Intent(ViewActivity.this, DetailsActivity.class);
                    intent.putExtra("title", item.getTitle());
                    intent.putExtra("image", item.getImage());

                    //Start details activity
                    startActivity(intent);
                }
            });
        }catch(Exception e){
            //oops
        }
    }

    View.OnClickListener getOnClickDoSomething(final ImageView iView)  {
        return new View.OnClickListener() {
            public void onClick(View v) {

            }
        };
    }
}
