package com.bhartiyamonline.smart_school.constant;

import java.io.File;
import java.util.Calendar;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

//public class MultiPartHelperClass {
//
//    public static RequestBody getRequestBody(String value){
//        if (!value.isEmpty() && value != null) {
//            RequestBody body = RequestBody.create(MediaType.parse("text/plain"), value);
//            return body;
//        }else {
//            RequestBody body = RequestBody.create(MediaType.parse("text/plain"), "");
//            return body;
//        }
//    }
//
//    public static MultipartBody.Part getRequestFile(File jsonFile, String key){
//        MultipartBody.Part body = null;
//        if (jsonFile.exists()) {
//            RequestBody requestFile =
//                    RequestBody.create(MediaType.parse("multipart/form-data"), jsonFile);
//            // MultipartBody.Part is used to send also the actual file name
//            body = MultipartBody.Part.createFormData(key, getTimeinMillis()+"_media.jpg", requestFile);
//        }
//
//
//        return body;
//
//    }
//
//   /* public static MultipartBody.Part getMultipartData(File imageFile, String key,String type,String extension){
//        MultipartBody.Part body = null;
//        if (imageFile.exists()) {
//            RequestBody requestFile =
//                    RequestBody.create(MediaType.parse(type), imageFile);
//            // MultipartBody.Part is used to send also the actual file name
//            body = MultipartBody.Part.createFormData(key, getTimeinMillis()+"_media"+extension, requestFile);
//        }
//
//
//        return body;
//    }*/
//
//    public static MultipartBody.Part getMultipartData(File imageFile, String key){
//        MultipartBody.Part body = null;
//        if (imageFile.exists()) {
//            RequestBody requestFile =
//                    RequestBody.create(MediaType.parse("multipart/form-data"), imageFile);
//            // MultipartBody.Part is used to send also the actual file name
//            body = MultipartBody.Part.createFormData(key, getTimeinMillis()+"_media.jpg", requestFile);
//        }
//
//
//        return body;
//    }
//
//    public static RequestBody getRequestBody1(File imageFile, String key){
//        RequestBody body = null;
//        if (imageFile.exists()) {
//            body = RequestBody.create(MediaType.parse("multipart/form-data"), imageFile);
//            // MultipartBody.Part is used to send also the actual file name
//        }
//        return body;
//    }
//
//    public static long getTimeinMillis(){
//        Calendar cal = Calendar.getInstance();
//        cal.set(cal.get(Calendar.YEAR),
//                cal.get(Calendar.MONTH),
//                cal.get(Calendar.DAY_OF_MONTH),
//                cal.get(Calendar.HOUR_OF_DAY),
//                cal.get(Calendar.MINUTE),
//                cal.get(Calendar.SECOND));
//        return cal.getTimeInMillis();
//    }
//
//
//}
