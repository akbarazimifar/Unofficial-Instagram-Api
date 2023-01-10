/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ig.unofficial.resources;

import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ig.unofficial.resources.constants.Constant;
import org.jetbrains.annotations.NotNull;

/**
 * @author Nakul
 */

public class Instagram {

    private static ObjectNode getInfo1(@NotNull String video_urls, String thumbnail, String id) {
        ObjectMapper object = new ObjectMapper();
        ObjectNode ob = object.createObjectNode();
        ob.put("url", video_urls);
        ob.put("thumbnail", thumbnail);
        ob.put("shortcode", id);

        return ob;
    }

    private static String subStringQuery(String query) {
        if (query.contains("reel"))
            return query.substring(25).replace("reel", "p").trim();
        return query.substring(25).trim();
    }

    public static String videos3(String query) {
        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();

        try {

            String url = "https://www.instagram.com" + subStringQuery(query) + "?__a=1&__d=dis";
            System.out.println(url);
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .header("User-Agent", Constant.USER_AGENT)
                    .header("Accept-Charset", Constant.UTF_8)
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

            HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.statusCode());
            ObjectMapper mapper = new ObjectMapper();

            JsonNode node = mapper.readTree(response.body());
            JsonNode node4 = node.get("graphql").get("shortcode_media");
            JsonNode node0 = node4.get("is_video");

            ArrayNode jsonAdd = mapper.createArrayNode();
            ObjectNode ob = mapper.createObjectNode();
            HoldData holdData = null;
            // not true
            if (!node0.booleanValue()) {
                JsonNode edge_sidecar_to_children = node4.get("edge_sidecar_to_children");
                ArrayNode edges = edge_sidecar_to_children.withArray("edges");

                for (int i = 0; i < edges.size(); i++) {
                    JsonNode n = edges.get(i);
                    JsonNode actualNode = n.get("node");
                    String actualNode1 = actualNode.get("__typename").asText();

                    if (actualNode1.contains("GraphVideo")) {
                        String codeshort = actualNode.get("shortcode").asText();
                        String video = actualNode.get("video_url").asText();
                        //System.out.println(video);
                        ArrayNode display_resources = actualNode.withArray("display_resources");

                        for (int j = 0; j < display_resources.size(); j++) {
                            JsonNode dr = display_resources.get(j);
                            String sources = dr.get("src").asText();

                            if (sources.contains("s640x640")) {
                                holdData = new HoldData();
                                holdData.data(sources);
                            }

                        }
                        assert holdData != null;
                        System.out.println(holdData.data);
                        //assert pojo != null;
                        jsonAdd.addAll(List.of(getInfo1(video, holdData.data(), codeshort)));
                    }

                }
                ob.put("no_of_videos", jsonAdd.size());
                ob.set("info", jsonAdd);
            } else {
                String video = node4.get("video_url").asText();
                String shortcode = node4.get("shortcode").asText();
                String thumbnail = node4.get("display_url").asText();

                ob.put("no_of_videos", 1);
                ob.set("info", getInfo1(video, thumbnail, shortcode));
            }
            return ob.toPrettyString();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
