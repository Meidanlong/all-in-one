package com.meidl.elasticsearch.repository;


import com.meidl.elasticsearch.domain.EsBlog;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * 测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest//引入上下文
public class EsBlogRepositoryTest {

    @Autowired
    private EsBlogRepository esBlogRepository;

    @Before
    public void initRepositoryData(){

        //清楚所有数据
        esBlogRepository.deleteAll();
        esBlogRepository.save(new EsBlog("我是标题1","我是概要1","我是内容1"));
        esBlogRepository.save(new EsBlog("我是标题2","我是概要2","我是内容2"));
        esBlogRepository.save(new EsBlog("我是标题3","我是概要3","我是内容3"));
    }
    @Test
    public void findDistinctEsBlogByTitleContainingOrSummaryContainingOrContentContaining(){

        Pageable pageable = new PageRequest(0,20);//分页对象

        String title = "2";
        String summary = "2";
        String content = "2";
        Page<EsBlog> page = esBlogRepository.findDistinctEsBlogByTitleContainingOrSummaryContainingOrContentContaining(title,summary,content,pageable);
        //assertThat(page.getTotalElements()).isEqualTo(2);

        System.out.println("----start");
        for(EsBlog blog : page.getContent()){
            System.out.println(blog.toString());
        }
        System.out.println("----end");


    }
}
