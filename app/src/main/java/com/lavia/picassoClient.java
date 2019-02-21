package com.lavia;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class picassoClient {

    public static void  downloading(Context c, String url, ImageView img) {
        if(url != null && url.length()>0)
        {
            Picasso.get().load(url).placeholder(R.drawable.sld3).into(img);
        }else {
            Picasso.get().load(R.drawable.sld3).into(img);
        }
    }
}
