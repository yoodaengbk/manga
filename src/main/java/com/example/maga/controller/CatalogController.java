/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.maga.controller;

import com.example.maga.model.Catalog;
import com.example.maga.model.Comic;
import com.example.maga.repository.CatalogRepository;
import com.example.maga.util.ResponseUtil;
import com.fasterxml.jackson.core.io.JsonEOFException;
import com.fasterxml.jackson.databind.node.ArrayNode;
import java.io.IOException;
import java.util.List;
import javax.validation.Valid;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author TAM
 */

@RestController
@RequestMapping("/api")
public class CatalogController {
    @Autowired
    CatalogRepository catalogRepository;
    
    @GetMapping(value = "/catalog", produces = "application/json")
    public ArrayNode getAllCatalogs() throws JsonEOFException{
        return ResponseUtil.returnListCatalog(catalogRepository.findAll());
    }
    
    @PostMapping("/catalog")
    public Catalog creatCatalog(@Valid @RequestBody Catalog catalog){
        return  catalogRepository.save(catalog);
    }
    
    @GetMapping("catalog_crawler")
    public ArrayNode crarnlerTest() throws IOException {
        Document doc = Jsoup.connect("https://truyenfull.vn/").get();
        System.out.println("Title : " + doc.title());
        Elements catalogs = doc.select(".dropdown-menu>li>a");
        for(Element catalog : catalogs){
            Catalog c = new Catalog();
            c.setTitle(catalog.text());
            c.setLink(catalog.attr("href"));
            if(!isExist(c, catalogRepository.findAll())){
                creatCatalog(c);
            }
        }
        
        return ResponseUtil.returnListCatalog(catalogRepository.findAll());
    }
    
    public boolean isExist(Catalog comic, List<Catalog> comics) {

        for (Catalog c : comics) {
            if (c.getLink().equals(comic.getLink())) {
                return true;
            }
        }
        return false;
    }
}
