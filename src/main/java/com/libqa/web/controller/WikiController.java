package com.libqa.web.controller;

import com.libqa.domain.Wiki;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by songanji on 2015. 3. 1..
 */
@RestController
@RequestMapping("/")
@Slf4j
public class WikiController {

    @RequestMapping("wiki/main")
    public ModelAndView main(Model model){
        ModelAndView mav = new ModelAndView("wiki/main");
        return mav;
    }

    @RequestMapping("wiki/write")
    public ModelAndView write(Model model){
        ModelAndView mav = new ModelAndView("wiki/write");
        return mav;
    }

    @RequestMapping("wiki/bestList")
    public ModelAndView bestList(Model model){
        ModelAndView mav = new ModelAndView("wiki/template/_bestList");

        List<Wiki> list = new ArrayList<Wiki>();
        for(int i=0; i<5; i++){
            Wiki wiki = new Wiki();
            wiki.setUserNick("테스트"+i);
            wiki.setInsertDate(new Date());
            wiki.setLikeCount(i);
            wiki.setContents("지금은 "+i+" 베스트위키 테스트중");
            list.add(wiki);
        }
        mav.addObject("bestList",list);

        return mav;
    }

    @RequestMapping("wiki/allList")
    public ModelAndView allList(Model model){
        ModelAndView mav = new ModelAndView("wiki/template/_allList");

        List<Wiki> list = new ArrayList<Wiki>();
        for(int i=0; i<5; i++){
            Wiki wiki = new Wiki();
            wiki.setUserNick("테스트"+i);
            wiki.setInsertDate(new Date());
            wiki.setLikeCount(i);
            wiki.setContents("지금은 "+i+" 모든위키 테스트중");
            list.add(wiki);
        }
        mav.addObject("allList",list);

        return mav;
    }

    @RequestMapping("wiki/recentList")
    public ModelAndView recentList(Model model){
        ModelAndView mav = new ModelAndView("wiki/template/_recentList");

        List<Wiki> list = new ArrayList<Wiki>();
        for(int i=0; i<5; i++){
            Wiki wiki = new Wiki();
            wiki.setUserNick("테스트"+i);
            wiki.setTitle("위키 타이틀");
            wiki.setInsertDate(new Date());
            wiki.setLikeCount(i);
            wiki.setContents("지금은 "+i+" 최근활동위키 테스트중");
            list.add(wiki);
        }
        mav.addObject("recentList",list);


        return mav;
    }




}
