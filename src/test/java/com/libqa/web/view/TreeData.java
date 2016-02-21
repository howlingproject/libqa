package com.libqa.web.view;

import com.libqa.web.domain.Wiki;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : yion
 * @Date : 2016. 2. 21.
 * @Description :
 */
public class TreeData {


    @Test
    public void makeTree() {
        List<Wiki> wikiList = setData();

        for (Wiki wiki : wikiList) {
            System.out.println("wiki = " + wiki);
        }

    }







    public List<Wiki> setData() {
        List<Wiki> wikiList = new ArrayList<>();

        Wiki wiki1 = new Wiki();
        wiki1.setGroupIdx(126);
        wiki1.setWikiId(126);
        wiki1.setParentsId(126);
        wiki1.setDepthIdx(0);
        wiki1.setOrderIdx(0);

        wikiList.add(wiki1);

        Wiki wiki2 = new Wiki();
        wiki2.setGroupIdx(126);
        wiki2.setWikiId(128);
        wiki2.setParentsId(126);
        wiki2.setDepthIdx(1);
        wiki2.setOrderIdx(1);

        wikiList.add(wiki2);

        Wiki wiki3 = new Wiki();
        wiki3.setGroupIdx(126);
        wiki3.setWikiId(129);
        wiki3.setParentsId(128);
        wiki3.setDepthIdx(2);
        wiki3.setOrderIdx(2);

        wikiList.add(wiki3);

        Wiki wiki4 = new Wiki();
        wiki4.setGroupIdx(126);
        wiki4.setWikiId(133);
        wiki4.setParentsId(129);
        wiki4.setDepthIdx(3);
        wiki4.setOrderIdx(3);

        wikiList.add(wiki4);

        Wiki wiki5 = new Wiki();
        wiki5.setGroupIdx(126);
        wiki5.setWikiId(132);
        wiki5.setParentsId(128);
        wiki5.setDepthIdx(2);
        wiki5.setOrderIdx(4);

        wikiList.add(wiki5);

        Wiki wiki6 = new Wiki();
        wiki6.setGroupIdx(126);
        wiki6.setWikiId(134);
        wiki6.setParentsId(132);
        wiki6.setDepthIdx(3);
        wiki6.setOrderIdx(5);

        wikiList.add(wiki6);

        Wiki wiki7 = new Wiki();
        wiki7.setGroupIdx(126);
        wiki7.setWikiId(135);
        wiki7.setParentsId(132);
        wiki7.setDepthIdx(3);
        wiki7.setOrderIdx(6);

        wikiList.add(wiki7);


        Wiki wiki8 = new Wiki();
        wiki8.setGroupIdx(130);
        wiki8.setWikiId(130);
        wiki8.setParentsId(130);
        wiki8.setDepthIdx(0);
        wiki8.setOrderIdx(0);

        wikiList.add(wiki8);


        Wiki wiki9 = new Wiki();
        wiki9.setGroupIdx(130);
        wiki9.setWikiId(131);
        wiki9.setParentsId(130);
        wiki9.setDepthIdx(1);
        wiki9.setOrderIdx(1);

        wikiList.add(wiki9);

        return wikiList;
    }
}
