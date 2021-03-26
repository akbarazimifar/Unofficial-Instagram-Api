/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ig.unofficial.resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import javax.net.ssl.HttpsURLConnection;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Nakul
 */
public class Funs {
    
    private static String parseQuery(String query) {
        String v;
        if (query.contains(" ")) {
            v = query.replace(" ", "%20");
        } else {
            return query;
        }
        return v;
    }
    
    public static String getUserProfile(String userId) throws ProtocolException, IOException {
        URL url1 = new URL("https://i.instagram.com/api/v1/users/" + userId.trim() + "/info");
        HttpsURLConnection con = (HttpsURLConnection) url1.openConnection();

        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", "Mozilla/5.0 (Linux; Android 9; GM1903 Build/PKQ1.190110.001; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/75.0.3770.143 Mobile Safari/537.36 Instagram 103.1.0.15.119 Android (28/9; 420dpi; 1080x2260; OnePlus; GM1903; OnePlus7; qcom; sv_SE; 164094539)");
        con.addRequestProperty("Accept-Charset", "UTF-8");
        con.setRequestProperty("cookie", "mid=XV7P-wALAAGLNm_Wuayy7JSyB2MU"
                + " fbm_124024574287414=base_domain=.instagram.com;"
                + " ig_did=433AAE75-2510-4634-B574-6D44EF9CE852;"
                + " datr=YsIGX6nGyA93KmlWK9yjqfnR; ig_nrcb=1;"
                + " shbid=10183; shbts=1614932427.7177079;"
                + " csrftoken=CoZqXUUF2pIdditnykJwPfkaQzpqu9Es;"
                + " ds_user_id=44304503059;"
                + " sessionid=44575565797%3ABmE1YbzyAK5yAL%3A14;"
                + " rur=FRC; "
                + " \"{\\\"103.115.195.17\\\": 136946\\054 \\\"103.115.195.10\\\": 136946}:1keinN:dADGc2diVcpoVr7zg8QJSInUkgI\"");
        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
        StringBuilder stream = new StringBuilder();
        String line;
        Pojo pojo = new Pojo();
        while ((line = br.readLine()) != null) {
            stream.append(line);
            System.out.println(stream);
            pojo.data(stream.toString());
        }
        return pojo.data;
    }
    
    private static JSONObject getInfo(String shortcode, String video_urls, String thumbnail) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException {
        JSONObject ob = new JSONObject();
        Field changeMap = ob.getClass().getDeclaredField("map");
        changeMap.setAccessible(true);
        changeMap.set(ob, new LinkedHashMap<>());
        changeMap.setAccessible(false);

        ob.put("shortcode", shortcode);
        ob.put("video_url", video_urls);
        ob.put("thumbnail", thumbnail);
        return ob;
    }
    
    public static JSONObject userDetails(String data) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        JSONObject object = new JSONObject(data);

        JSONObject object1 = object.getJSONObject("user");
        JSONObject object2 = object1.getJSONObject("hd_profile_pic_url_info");  // highest quality

        long pk = object1.getLong("pk");
        String username = object1.getString("username");
        String full_name = object1.getString("full_name");
        String profile_pic_url = object1.getString("profile_pic_url");
        String status = object.getString("status");
        long follower_count = object1.getInt("follower_count");
        long following_count = object1.getInt("following_count");
        String biography = object1.getString("biography");
        String external_url = object1.getString("external_url");
        
        JSONObject obj = new JSONObject();
        Field changeMap = obj.getClass().getDeclaredField("map");
        changeMap.setAccessible(true);
        changeMap.set(obj, new LinkedHashMap<>());
        changeMap.setAccessible(false);

        obj.put("user_id", pk);
        obj.put("username", username);
        obj.put("full_name", full_name);
        obj.put("follower_count", follower_count);
        obj.put("following_count", following_count);
        obj.put("biography", biography);
        obj.put("external_url", external_url);
        obj.put("profile_pic_url_small", profile_pic_url);
        obj.put("hd_profile_pic_url_info", object2);
        
        JSONObject obj1 = new JSONObject();
        obj1.put("user", obj);
        obj1.put("status", status);
        
        JSONArray array = object1.getJSONArray("hd_profile_pic_versions");
        obj1.put("hd_profile_pic_versions", array);
       
        return obj1;
    }
    
    public static String getUserId (String URL) throws MalformedURLException, ProtocolException, IOException {
        URL url1 = new URL(URL +"?__a=1");
        HttpsURLConnection con = (HttpsURLConnection) url1.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", "Mozilla/5.0 (Linux; Android 9; GM1903 Build/PKQ1.190110.001; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/75.0.3770.143 Mobile Safari/537.36 Instagram 103.1.0.15.119 Android (28/9; 420dpi; 1080x2260; OnePlus; GM1903; OnePlus7; qcom; sv_SE; 164094539)");
        con.addRequestProperty("Accept-Charset", "UTF-8");
        con.setRequestProperty("cookie", "mid=XV7P-wALAAGLNm_Wuayy7JSyB2MU"
                + " fbm_124024574287414=base_domain=.instagram.com;"
                + " ig_did=433AAE75-2510-4634-B574-6D44EF9CE852;"
                + " datr=YsIGX6nGyA93KmlWK9yjqfnR; ig_nrcb=1;"
                + " shbid=10183; shbts=1614932427.7177079;"
                + " csrftoken=CoZqXUUF2pIdditnykJwPfkaQzpqu9Es;"
                + " ds_user_id=44304503059;"
                + " sessionid=44575565797%3ABmE1YbzyAK5yAL%3A14;"
                + " rur=FRC; "
                + " \"{\\\"103.115.195.17\\\": 136946\\054 \\\"103.115.195.10\\\": 136946}:1keinN:dADGc2diVcpoVr7zg8QJSInUkgI\"");
        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
        StringBuilder stream = new StringBuilder();
        String line;
        Pojo pojo = new Pojo();
        while ((line = br.readLine()) != null) {
            stream.append(line);
            pojo.data(stream.toString());
        }
        JSONObject ob = new JSONObject(pojo.data());
        JSONObject ob1 = ob.getJSONObject("graphql");
        JSONObject obj2 = ob1.getJSONObject("user");
        JSONObject obj3 = new JSONObject();
        obj3.put("username",obj2.getString("username"));
        obj3.put("id",obj2.getString("id"));
        return obj3.toString();
    }
    
    public static String topSearch (String query) throws MalformedURLException, IOException {
        URL url1 = new URL("https://www.instagram.com/web/search/topsearch/?context=blended&query="+parseQuery(query)+"&rank_token=0.23621512399748612");
        HttpsURLConnection con = (HttpsURLConnection) url1.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", "Mozilla/5.0 (Linux; Android 9; GM1903 Build/PKQ1.190110.001; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/75.0.3770.143 Mobile Safari/537.36 Instagram 103.1.0.15.119 Android (28/9; 420dpi; 1080x2260; OnePlus; GM1903; OnePlus7; qcom; sv_SE; 164094539)");
        con.addRequestProperty("Accept-Charset", "UTF-8");
        con.setRequestProperty("cookie", "mid=XV7P-wALAAGLNm_Wuayy7JSyB2MU"
                + " fbm_124024574287414=base_domain=.instagram.com;"
                + " ig_did=433AAE75-2510-4634-B574-6D44EF9CE852;"
                + " datr=YsIGX6nGyA93KmlWK9yjqfnR; ig_nrcb=1;"
                + " shbid=10183; shbts=1614932427.7177079;"
                + " csrftoken=CoZqXUUF2pIdditnykJwPfkaQzpqu9Es;"
                + " ds_user_id=44304503059;"
                + " sessionid=44575565797%3ABmE1YbzyAK5yAL%3A14;"
                + " rur=FRC; "
                + " \"{\\\"103.115.195.17\\\": 136946\\054 \\\"103.115.195.10\\\": 136946}:1keinN:dADGc2diVcpoVr7zg8QJSInUkgI\"");
        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
        StringBuilder stream = new StringBuilder();
        String line;
        Pojo pojo = new Pojo();
        while ((line = br.readLine()) != null) {
            stream.append(line);
            pojo.data(stream.toString());
        }
        System.out.println(pojo.data());
        JSONObject ob = new JSONObject(pojo.data());
        JSONArray ar = ob.getJSONArray("users");
        JSONObject ob1 = new JSONObject();
        ob1.put("users",ar);
        
        return ob1.toString();
    }
    
    public static String videos (String videoLink) throws MalformedURLException, ProtocolException, IOException, IOException, IllegalArgumentException, NoSuchFieldException, IllegalAccessException {
        Pojo pojo = new Pojo();
        videoLink = videoLink.substring(25);
        URL url = new URL("https://www.instagram.com" + videoLink + "?__a=1");
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent", "Chrome/86.0.4240.75");
        connection.addRequestProperty("Accept-Charset", "UTF-8");
        connection.setRequestProperty("cookie", "mid=XV7P-wALAAGLNm_Wuayy7JSyB2MU"
                + " fbm_124024574287414=base_domain=.instagram.com;"
                + " ig_did=433AAE75-2510-4634-B574-6D44EF9CE852;"
                + " datr=YsIGX6nGyA93KmlWK9yjqfnR; ig_nrcb=1;"
                + " shbid=10183; shbts=1614932427.7177079;"
                + " csrftoken=CoZqXUUF2pIdditnykJwPfkaQzpqu9Es;"
                + " ds_user_id=44304503059;"
                + " sessionid=44575565797%3ABmE1YbzyAK5yAL%3A14;"
                + " rur=FRC; "
                + " \"{\\\"103.115.195.17\\\": 136946\\054 \\\"103.115.195.10\\\": 136946}:1keinN:dADGc2diVcpoVr7zg8QJSInUkgI\"");
        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
        String inputLine;
        int responseCode = connection.getResponseCode();

        System.out.println(responseCode);
        if (responseCode == 500) {
            return "Server returns 500";
        }
        StringBuilder stream = new StringBuilder();
        while ((inputLine = br.readLine()) != null) {
            stream.append(inputLine);
            pojo.data(stream.toString());
        }

        System.out.println(pojo.data());

        JSONObject obj = new JSONObject(pojo.data());
        JSONObject obj1 = obj.getJSONObject("graphql");
        JSONObject obj2 = obj1.getJSONObject("shortcode_media");
        
        // This function is for slide videos
        if (obj2.getBoolean("is_video") == false) {
            JSONObject obj3 = obj2.getJSONObject("edge_sidecar_to_children");
            JSONArray array = obj3.getJSONArray("edges");
            System.out.println("is video false");
            ArrayList<JSONObject> ar = new ArrayList();
            JSONObject bo = new JSONObject();

            for (int i = 0; i < array.length(); i++) {
                JSONObject json = array.getJSONObject(i);
                JSONObject json1 = json.getJSONObject("node");
                String typename = json1.getString("__typename");

                if (typename.contains("GraphVideo")) {
                    String shortcode = json1.getString("shortcode");
                    String video_url = json1.getString("video_url");

                    JSONArray array1 = json1.getJSONArray("display_resources");
                    for (int k = 0; k < array1.length(); k++) {
                        JSONObject jo = array1.getJSONObject(k);
                        String src = jo.getString("src");
                        pojo.data(src);
                        if (src.contains("s640x640")) {
                            System.out.println("thumbnails " + src);
                        }
                    }
                    ar.add(getInfo(shortcode, video_url, pojo.data()));
                }
            }

            bo.put("info", ar);
            bo.put("no_of_videos", ar.size());
            System.out.println(bo);
            return bo.toString();
        } else { // This function is for single video.
            ArrayList<JSONObject> ar = new ArrayList();
            JSONObject bo = new JSONObject();

            System.out.println("tru h kya?");
            if (obj2.getBoolean("is_video") == true) {

                String video_url = obj2.getString("video_url");
                String shortcode = obj2.getString("shortcode");
                String thumbnail = obj2.getString("display_url");
                ar.add(getInfo(shortcode, video_url, thumbnail));
                bo.put("info", ar);
                bo.put("no_of_videos", 1);

                return bo.toString();
            }
        }
    return null;
    }
}
