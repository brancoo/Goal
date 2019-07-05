package com.example.golo;

import android.content.Context;
import android.widget.ImageView;
import android.widget.Toast;
import com.pixplicity.sharp.Sharp;
import com.pixplicity.sharp.SvgParseException;
import okhttp3.*;

import java.io.IOException;
import java.io.InputStream;

public class Utils {

    private static OkHttpClient httpClient;

    public static void fetchSvg(Context context, String url, final ImageView target) throws SvgParseException {
        if (httpClient == null) {
            // Use cache for performance and basic offline capability
            httpClient = new OkHttpClient.Builder()
                    .cache(new Cache(context.getCacheDir(), 5 * 200 * 200))
                    .build();
        }

        Request request = new Request.Builder().url(url).build();
        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                target.setImageResource(R.drawable.fallback_image);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream stream = response.body().byteStream();
                try {
                    Sharp.loadInputStream(stream).into(target);
                    stream.close();
                }catch (SvgParseException e){

                }
            }
        });
    }
}