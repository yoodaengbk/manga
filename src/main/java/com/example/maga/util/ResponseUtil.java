/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.maga.util;
import com.example.maga.constant.StatusCode;
import com.example.maga.model.Catalog;
import com.example.maga.model.Category;
import com.example.maga.model.Chapter;
import com.example.maga.model.Comic;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.List;

/**
 *
 * @author TAM
 */
public class ResponseUtil {
    private static ObjectMapper mapper  = new ObjectMapper();
    
    public static ObjectNode returnComic(Comic comic){
        ObjectNode node = mapper.createObjectNode();
        node.put("id",comic.getId());
        node.put("title",comic.getTitle());
        node.put("link", comic.getLink());
        node.put("chapters",returnListChapter(comic.getChapters()));
        return node;
    }
    public static ArrayNode returnListComic(List<Comic> comics){
        ArrayNode node = mapper.createArrayNode();
        for(Comic comic : comics){
            node.add(returnComic(comic));
        }
        return node;
    }
    
    public static ObjectNode returnCategory(Category category){
        ObjectNode node = mapper.createObjectNode();
        node.put("id",category.getId());
        node.put("title",category.getTitle());
        node.put("link", category.getLink());
        node.put("comics", returnListComic(category.getComics()));
        return node;
    }
    public static ArrayNode returnListCategory(List<Category> categorys){
        ArrayNode node = mapper.createArrayNode();
        for(Category category : categorys){
            node.add(returnCategory(category));
        }
        return node;
    }
    public static ObjectNode returnCatalog(Catalog catalog){
        ObjectNode node = mapper.createObjectNode();
        node.put("id",catalog.getId());
        node.put("title",catalog.getTitle());
        node.put("link", catalog.getLink());
        node.put("comics", returnListComic(catalog.getComics()));
        return node;
    }
    public static ArrayNode returnListCatalog(List<Catalog> catalogs){
        ArrayNode node = mapper.createArrayNode();
        for(Catalog catalog : catalogs){
            node.add(returnCatalog(catalog));
        }
        return node;
    }
    public static ObjectNode returnChapter(Chapter chapter){
        ObjectNode node = mapper.createObjectNode();
        node.put("id",chapter.getId());
        node.put("title",chapter.getTitle());
        node.put("link", chapter.getLink());
        
        return node;
    }
    public static ArrayNode returnListChapter(List<Chapter> chapters){
        ArrayNode node = mapper.createArrayNode();
        for(Chapter chapter : chapters){
            node.add(returnChapter(chapter));
        }
        return node;
    }
    public static String success(JsonNode body){
        ObjectNode node = mapper.createObjectNode();
        node.put(StatusCode.class.getSimpleName(), StatusCode.SUCCESS.getValue());
        node.put("message",StatusCode.SUCCESS.name());
        node.put("reponse", body);
        return node.toString();
    }
    public static String NotFound(){
        ObjectNode node = mapper.createObjectNode();
        node.put(StatusCode.class.getSimpleName(), StatusCode.NOT_FOUND.getValue());
        node.put("message",StatusCode.NOT_FOUND.name());
        node.put("reponse", "");
        return node.toString();
    }
         
}
