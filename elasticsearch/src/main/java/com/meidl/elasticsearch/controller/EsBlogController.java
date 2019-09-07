package com.meidl.elasticsearch.controller;

import com.meidl.elasticsearch.domain.EsBlog;
import com.meidl.elasticsearch.repository.EsBlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/blogs")
public class EsBlogController {

    @Autowired
    private EsBlogRepository esBlogRepository;


    //http://localhost:8080/blogs?title=2&summary=2&content=2
    @GetMapping
    public List<EsBlog> list(@RequestParam(value = "title") String title,
                             @RequestParam(value = "summary") String summary,
                             @RequestParam(value = "content") String content,
                             @RequestParam(value = "pageIndex", defaultValue = "0") int pageIndex,
                             @RequestParam(value = "pageSize", defaultValue = "10") int pageSize){


        Pageable pageable = new PageRequest(0,20);
        Page<EsBlog> page = esBlogRepository.findDistinctEsBlogByTitleContainingOrSummaryContainingOrContentContaining(title,summary,content,pageable);

        return page.getContent();
    }

}
