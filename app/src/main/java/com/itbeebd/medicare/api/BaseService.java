package com.itbeebd.medicare.api;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class BaseService {
    protected final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(ApiUrls.BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    protected final RetrofitService service = retrofit.create(RetrofitService.class);

    protected MultipartBody.Part getImageFile(String imageFilePath){
        if(imageFilePath == null || imageFilePath.isEmpty())
            return null;
        File file = new File(imageFilePath); // initialize file here
        System.out.println(">>>>>>>>> file " + file.toString());
        String imageName = imageFilePath.substring(imageFilePath.lastIndexOf("/")+1);
        return MultipartBody.Part.createFormData("image", imageName, okhttp3.RequestBody.create(MediaType.parse("image/*"), file));
    }
}
