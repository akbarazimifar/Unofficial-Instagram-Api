package com.ig.unofficial.resources;

import com.ig.unofficial.resources.constants.Constant;
import com.ig.unofficial.resources.constants.Endpoints;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
// httpurlconnection replaces with httpclient
public class Insta {

    static String parseString(String s) {

        if (s.contains(" "))
            return s.replace(" ","%20");
        else
            return s;
    }

    public static String searchUser(String query) {
        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();

        try {
            String url = Endpoints.first +parseString(query)+Endpoints.end;
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .header("User-Agent", Constant.USER_AGENT)
                    .header("Accept-Charset",Constant.UTF_8)
                    .header("cookie", Constant.MID
                            + Constant.FBM_BASE_DOMAIN
                            + Constant.IG_DID
                            + Constant.DATR
                            + Constant.SHBID
                            + Constant.CSRF_TOKEN
                            + Constant.DS_USER_ID
                            + Constant.SESSION_ID
                            + Constant.RUR_FRC
                            + Constant.RUR_FRC_END)
                    .GET()
                    .build();
            HttpResponse<String> response  = client.send(httpRequest,HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
            return response.body();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
