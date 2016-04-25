package io.github.lingnanlu.volleydemo;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.LruCache;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    RequestQueue mQueue;

    Button mLoadPicture;
    ImageView mPicture;
    Button mImageLoader;
    NetworkImageView mNetWorkImageView;
    TextView mText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mQueue = Volley.newRequestQueue(this);

        mText = (TextView) findViewById(R.id.tv_result);


        StringRequest stringRequest = new StringRequest(
                "http://115.156.163.234:94/api/temperature?limit=10",
                new SuccessListener(),
                new ErrorListener()
        );

        stringRequest.setTag("TestTag");
        mQueue.add(stringRequest);

//        XMLRequest xmlRequest = new XMLRequest(
//                "http://flash.weather.com.cn/wmaps/xml/china.xml",
//                new Response.Listener<XmlPullParser>() {
//
//                    @Override
//                    public void onResponse(XmlPullParser response) {
//                        try {
//                            int eventType = response.getEventType();
//                            while (eventType != XmlPullParser.END_DOCUMENT) {
//                                switch (eventType) {
//                                    case XmlPullParser.START_TAG:
//                                        String nodeName = response.getName();
//                                        if ("city".equals(nodeName)) {
//                                            String pName = response.getAttributeValue(0);
//                                            Log.d(TAG, "pName is " + pName);
//                                        }
//                                        break;
//                                }
//                                eventType = response.next();
//                            }
//                        } catch (XmlPullParserException e) {
//                            e.printStackTrace();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                },
//                new Response.ErrorListener(){
//
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.e(TAG, "onErrorResponse: " + error.getMessage(), error);
//                    }
//                }
//        );
//        mQueue.add(xmlRequest);

//        mLoadPicture = (Button) findViewById(R.id.bt_load_picture);
//        mPicture = (ImageView) findViewById(R.id.iv_pics);
//        mImageLoader = (Button) findViewById(R.id.bt_image_loader);
//
//        mLoadPicture.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ImageRequest imageRequest = new ImageRequest(
//                        "http://g.hiphotos.baidu.com/baike/c0%3Dbaike180%2C5%2C5%2C180%2C60/sign=7652bea3bf315c60579863bdecd8a076/6609c93d70cf3bc70991df5ad200baa1cc112af6.jpg",
//                        new Response.Listener<Bitmap>() {
//                            @Override
//                            public void onResponse(Bitmap response) {
//                                Log.d(TAG, "onResponse: sucessful");
//                                mPicture.setImageBitmap(response);
//                            }
//                        },
//                        0,0, Bitmap.Config.RGB_565, null
//                );
//
//                //当访问onCreate中的变量时, 才需要使其为final的, 访问类成员, 不需要, 因为
//                //有外转对象的this
//                mQueue.add(imageRequest);
//            }
//        });
//
//        final ImageLoader imageLoader = new ImageLoader(mQueue, new BitmapCache());
//
//        final ImageLoader.ImageListener listener = ImageLoader.getImageListener(mPicture, R.drawable
//                .leak_canary_icon, R.drawable.leak_canary_icon);
//
//        mImageLoader.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                imageLoader.get("http://b.hiphotos.baidu" +
//                        ".com/baike/c0%3Dbaike80%2C5%2C5%2C80%2C26/sign" +
//                        "=24300955970a304e462fa8a8b0a1cce3/4d086e061d950a7bdb2733950ad162d9f2d3c955.jpg",
//                        listener);
//            }
//        });
//
//        mNetWorkImageView = (NetworkImageView) findViewById(R.id.network_image_view);
//        mNetWorkImageView.setImageUrl("http://f.hiphotos.baidu.com/baike/c0%3Dbaike80%2C5%2C5%2C80%2C26/sign=e242025b36fae6cd18b9a3336eda6441/eaf81a4c510fd9f9a243b605262dd42a2834a402.jpg"
//        ,imageLoader);


    }

    private class SuccessListener implements Response.Listener<String> {

        @Override
        public void onResponse(String response) {
            mText.setText(response);
            Log.d(TAG, "onResponse: " + response);
        }

    }

    private class ErrorListener implements Response.ErrorListener {


        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e(TAG, "onErrorResponse: " + error.getMessage(), error);
        }
    }


    static class BitmapCache implements ImageLoader.ImageCache {

        private LruCache<String, Bitmap> mCache;

        public BitmapCache() {
            int maxSize = 10 * 1024 * 1024;
            mCache = new LruCache<String, Bitmap>(maxSize) {
                @Override
                protected int sizeOf(String key, Bitmap value) {
                    return value.getRowBytes() * value.getHeight();
                }
            };
        }

        @Override
        public Bitmap getBitmap(String url) {
            return mCache.get(url);
        }

        @Override
        public void putBitmap(String url, Bitmap bitmap) {
            mCache.put(url, bitmap);
        }


    }

    @Override
    protected void onStop() {
        super.onStop();

        if (mQueue != null) {
            mQueue.cancelAll("TestTag");
        }
    }
}
