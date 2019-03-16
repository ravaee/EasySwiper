package com.git.ravaee.EasySwipe;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.LruCache;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class EasySwipe extends ViewPager {

    public EasySwipe(@NonNull Context context) {
        super(context);

    }

    public EasySwipe(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

    }

    public void initial(ArrayList<String> urls,int time){

        ImageAdapter adapterView = new ImageAdapter(EasySwipe.super.getContext(),urls);
        setAdapter(adapterView);

        final int count = getAdapter().getCount();
        final int[] i = {0};

        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if(i[0] == count-1){
                    i[0] = 0;
                }else{
                    i[0] = i[0] + 1;
                }
                setCurrentItem(i[0],true);
            }
        };

        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 4000, time);

    }
}


class ImageAdapter extends PagerAdapter {

    Context mContext;

    public ImageAdapter(Context context,ArrayList<String> imageUrls) {
        this.mContext = context;
        this.imageUrls = imageUrls;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    private ArrayList<String> imageUrls;

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        NetworkImageView imageView = new NetworkImageView(mContext);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageUrl(imageUrls.get(position),VolleySingleton.getInstance(mContext).getImageLoader());
        container.addView(imageView, 0);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ImageView) object);
    }

    @Override
    public int getCount() {
        return imageUrls.size();
    }
}


class VolleySingleton {

    private static VolleySingleton mInstance = null;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    private VolleySingleton(Context context){
        mRequestQueue = Volley.newRequestQueue(context);
        mImageLoader = new ImageLoader(this.mRequestQueue, new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap> mCache = new LruCache<String, Bitmap>(10);
            public void putBitmap(String url, Bitmap bitmap) {
                mCache.put(url, bitmap);
            }
            public Bitmap getBitmap(String url) {
                return mCache.get(url);
            }
        });
    }

    public static VolleySingleton getInstance(Context context){
        if(mInstance == null){
            mInstance = new VolleySingleton(context);
        }
        return mInstance;
    }


    public ImageLoader getImageLoader(){
        return this.mImageLoader;
    }

}
