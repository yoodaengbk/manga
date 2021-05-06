/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.maga.controller;

import com.example.maga.model.Chapter;
import com.example.maga.model.Comic;
import com.example.maga.repository.ChapterRepository;
import com.example.maga.repository.ComicRepository;
import static com.example.maga.util.ResponseUtil.returnListComic;
import com.fasterxml.jackson.databind.node.ArrayNode;
import java.io.IOException;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author TAM
 */
@RestController
@RequestMapping("/api")
public class GetData {

    @Autowired
    ComicRepository comicRepository;

    @Autowired
    ChapterRepository chapterRepository;

    @Autowired
    ChapterController chapterController;

    @Autowired
    ComicController comicController;

    @GetMapping("/test")
    public Boolean crarnlerTest() throws IOException, NullPointerException {
        Document doc = Jsoup.connect("https://truyenfull.vn/choc-tuc-vo-yeu-mua-mot-tang-mot-241019/").get();
        System.out.println("Title : " + doc.title());
        Element comics = doc.select(".pagination>li>a>span").first();
        Element comix = comics.parent();
        System.out.println(comics.text() + comix.attr("href"));

        return true;
    }

    public boolean isExist(Comic comic, List<Comic> comics) {

        for (Comic c : comics) {
            if (c.getLink().equals(comic.getLink())) {
                return true;
            }
        }
        return false;
    }

    public boolean isExist(Chapter chapter, List<Chapter> chapters) {

        for (Chapter c : chapters) {
            if (c.getLink().equals(chapter.getLink())) {
                return true;
            }
        }
        return false;
    }

    @GetMapping("/test3")
    public Boolean crawler () throws IOException {
        Boolean cont = true;
        String url = "https://truyenfull.vn/danh-sach/truyen-hot/";
        String nextUrl = null;
        int i = 1;
        do {
            
            Document doc = Jsoup.connect(url).get();
            System.out.println("Title : " + doc.title());
            Elements comics = doc.select(".list>.row>div>div>.truyen-title>a");

            for (Element comic : comics) {
            
                Comic cm = new Comic();
//                System.out.println(comic.text());
//                System.out.println(comic.attr("href"));
                crarnlerTest2(comic.attr("href"));
                cm.setTitle(comic.text());
                cm.setLink(comic.attr("href"));

                if (!isExist(cm, comicRepository.findAll())) {
                    comicController.createComic(cm);
                }

            }
            Elements nextEls = doc.select(".pagination>li>a>span");
            for (Element nextEl : nextEls) {
                if (nextEl.text().equals("Trang tiếp")) {

                    nextUrl = nextEl.parent().attr("href");
                    break;

                }
            }
            if (nextUrl == null) {
                cont = false;
                
            } else {
                url = nextUrl;
                nextUrl = null;
            }
            i++;
            
        } while (i<2);
        
        return true;
    }
    
    
    
    
    
    
    public void crarnlerTest2(String url) throws IOException {

//        String url = "https://truyenfull.vn/doc-ton-tam-gioi/";
        String nextUrl = null;
        Boolean cont = true;
        int i = 0;
        do {
            Document doc = Jsoup.connect(url).get();
            Elements chapters = doc.select(".list-chapter>li>a");
            for (Element chapter : chapters) {
                System.out.println(chapter.text());
                System.out.println(chapter.attr("href"));
                Chapter c = new Chapter();
            c.setTitle(chapter.text());
            c.setLink(chapter.attr("href"));
            if (!isExist(c, chapterRepository.findAll())) {
                chapterController.createChapter(c);

            }
            }

            Elements nextEls = doc.select(".pagination>li>a>span");
            for (Element nextEl : nextEls) {
                if (nextEl.text().equals("Trang tiếp")) {

                    nextUrl = nextEl.parent().attr("href");
                    break;

                }
            }

            
            if (nextUrl == null) {
                cont = false;
            } else {
                url = nextUrl;
                nextUrl = null;
            }
            i++;

        } while (cont);

        
    }
}
