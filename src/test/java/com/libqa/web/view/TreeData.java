package com.libqa.web.view;

import com.libqa.web.view.space.WikiTree;
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
        List<WikiTree> wikiList = setData();
        System.out.println("## wikiList : " + wikiList);
        String result = recursiveTree(wikiList);
        System.out.println("### result : \n" + result);
    }


    public String recursiveTree(List<WikiTree> wikiList) {
        String ret = "<ul>";
        String sub = "";
        int root = 0;
        int groupId = 0;
        for (int i = 0; i < wikiList.size(); i++) {

            System.out.println("## depth [" + i + "] = " + wikiList.get(i).getDepthIdx() + " : " + wikiList.get(i).getTitle());
            if (root == wikiList.get(i).getDepthIdx()) {
                ret += "<li>" + wikiList.get(i).getTitle() ;
            }

            if (groupId == wikiList.get(i).getGroupIdx() && groupId != 0) {
                ret += "<ul><li>" + wikiList.get(i).getTitle() + "</li></ul>\n";
            }

            if (wikiList.get(i).getOrderIdx() == wikiList.get(i).getMaxRows()) {
                ret += "</li>\n";
            }

            groupId = wikiList.get(i).getGroupIdx();
        }

        return ret+"</ul>";
    }



    public String recursived(List<WikiTree> categories , int parent)
    {
        String ret = "<ul>";
        String sub ="";

        try {
            //String xx=categories.getString("sap_code");
            for(int i=0;i<categories.size();i++)
            {
                if(categories.get(i).getParentsId() == parent)
                {
                    ret += "<li><a href=='#'>"+ categories.get(i).getTitle() + "</a>";
                    List newList = categories.subList(0,categories.size() -1);
                    sub = recursived(newList ,categories.get(i).getParentsId());
                    if(sub != "<ul></ul>")
                        ret += sub;
                    ret += "</li>";
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

        return ret+"</ul>";
    }


    public List<WikiTree> setData() {
        List<WikiTree> wikiList = new ArrayList<>();

        WikiTree wiki1 = new WikiTree();
        wiki1.setGroupIdx(126);
        wiki1.setWikiId(126);
        wiki1.setParentsId(126);
        wiki1.setDepthIdx(0);
        wiki1.setOrderIdx(0);
        wiki1.setTitle("첫번째 위키");
        wiki1.setMaxRows(6);
        wikiList.add(wiki1);

        WikiTree wiki2 = new WikiTree();
        wiki2.setGroupIdx(126);
        wiki2.setWikiId(128);
        wiki2.setParentsId(126);
        wiki2.setDepthIdx(1);
        wiki2.setOrderIdx(1);
        wiki2.setTitle("1 - 1");
        wiki2.setMaxRows(6);
        wikiList.add(wiki2);

        WikiTree wiki3 = new WikiTree();
        wiki3.setGroupIdx(126);
        wiki3.setWikiId(129);
        wiki3.setParentsId(128);
        wiki3.setDepthIdx(2);
        wiki3.setOrderIdx(2);
        wiki3.setTitle("1 - 1 - 1");
        wiki3.setMaxRows(6);
        wikiList.add(wiki3);

        WikiTree wiki4 = new WikiTree();
        wiki4.setGroupIdx(126);
        wiki4.setWikiId(133);
        wiki4.setParentsId(129);
        wiki4.setDepthIdx(3);
        wiki4.setOrderIdx(3);
        wiki4.setTitle("1 - 1 - 1 - 1");
        wiki4.setMaxRows(6);
        wikiList.add(wiki4);

        WikiTree wiki5 = new WikiTree();
        wiki5.setGroupIdx(126);
        wiki5.setWikiId(132);
        wiki5.setParentsId(128);
        wiki5.setDepthIdx(2);
        wiki5.setOrderIdx(4);
        wiki5.setTitle("1 - 1 - 2");
        wiki5.setMaxRows(6);
        wikiList.add(wiki5);

        WikiTree wiki6 = new WikiTree();
        wiki6.setGroupIdx(126);
        wiki6.setWikiId(134);
        wiki6.setParentsId(132);
        wiki6.setDepthIdx(3);
        wiki6.setOrderIdx(5);
        wiki6.setTitle("1 - 1 - 2 - 1");
        wiki6.setMaxRows(6);
        wikiList.add(wiki6);

        WikiTree wiki7 = new WikiTree();
        wiki7.setGroupIdx(126);
        wiki7.setWikiId(135);
        wiki7.setParentsId(132);
        wiki7.setDepthIdx(3);
        wiki7.setOrderIdx(6);
        wiki7.setTitle("1 - 1 - 2 - 2");
        wiki7.setMaxRows(6);
        wikiList.add(wiki7);




        return wikiList;
    }
}
