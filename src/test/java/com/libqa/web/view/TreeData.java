package com.libqa.web.view;

import com.libqa.web.view.space.WikiTree;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @Author : yion
 * @Date : 2016. 2. 21.
 * @Description :
 */
public class TreeData {

    @Test
    public void makeSubTree() {
        List<WikiTree> wikiList = setData();
        System.out.println(" wiki List : " + wikiList.size());

        List<WikiTree> newList = wikiList.subList(1, wikiList.size());
        System.out.println("new List : " + newList);
        System.out.println("new List : " + newList.size());


        List<WikiTree> newList1 = newList.subList(1, newList.size());
        System.out.println("new List1 : " + newList1);
        System.out.println("new List1 : " + newList1.size());

    }


    @Test
    public void makeTree() {
        List<WikiTree> wikiList = simpleData();

        System.out.println("@@@ 변경전 wikiList : " + wikiList);

        for (int i = 0; i < wikiList.size(); i++) {
            if (wikiList.get(i).getWikiId() == wikiList.get(i).getParentsId()) {
                continue;
            } else {
                // 자식 위키가 있음
                setChild(wikiList, wikiList.get(i).getParentsId());
            }
        }

        System.out.println("@@@ wikiList : " + wikiList);
        String tree = createTree(wikiList);

        System.out.println("#### tree = \n" + tree);

    }

    private String createTree(List<WikiTree> wikiList) {
        String appendTag = "<ul>";
        int parentId = 0;
        int wikiId = 0;
        for (int i = 0; i < wikiList.size(); i++) {

            System.out.println(i + "번째  parentId = "
                    + wikiList.get(i).getParentsId() + "  ## wikiId = "
                    + wikiList.get(i).getWikiId() + " ## child = "
                    + wikiList.get(i).isHasChild() + " || wikiId = "
                    + wikiId + " || parentId = " + parentId);


            if (wikiList.get(i).isHasChild() == true) {

                appendTag += "<ll>" + wikiList.get(i).getTitle();
                appendTag += "<ul>";
            } else {
                appendTag += "<ll>" + wikiList.get(i).getTitle();
                appendTag += "</li>";

            }

            if (wikiList.get(i).isHasChild() == false) {

                if (wikiList.get(i).getParentsId() != parentId) {
                    appendTag += "</ul>";
                    appendTag += "</li>";
                }
            }

            parentId = wikiList.get(i).getParentsId();
            wikiId = wikiList.get(i).getWikiId();
        }

        appendTag += "</ul>";

        return  appendTag;
    }

    private void setChild(List<WikiTree> wikiList, int wikiId) {
        for (WikiTree tree : wikiList) {
            if (wikiId == tree.getWikiId()) {
                tree.setHasChild(true);
            }
        }
    }


    public List<WikiTree> simpleData() {
        List<WikiTree> wikiList = new ArrayList<>();

        WikiTree wiki1 = new WikiTree();
        wiki1.setGroupIdx(126);
        wiki1.setWikiId(126);
        wiki1.setParentsId(126);
        wiki1.setDepthIdx(0);
        wiki1.setOrderIdx(0);
        wiki1.setTitle("첫번째 위키");
        wiki1.setMaxRows(4);
        wikiList.add(wiki1);

        WikiTree wiki2 = new WikiTree();
        wiki2.setGroupIdx(126);
        wiki2.setWikiId(128);
        wiki2.setParentsId(126);
        wiki2.setDepthIdx(1);
        wiki2.setOrderIdx(1);
        wiki2.setTitle("첫번째 위키의 첫번째자식");
        wiki2.setMaxRows(4);
        wikiList.add(wiki2);

        WikiTree wiki3 = new WikiTree();
        wiki3.setGroupIdx(126);
        wiki3.setWikiId(129);
        wiki3.setParentsId(128);
        wiki3.setDepthIdx(2);
        wiki3.setOrderIdx(2);
        wiki3.setTitle("첫번째 위키의 자식위키의 자식");
        wiki3.setMaxRows(4);
        wikiList.add(wiki3);

        WikiTree wiki4 = new WikiTree();
        wiki4.setGroupIdx(126);
        wiki4.setWikiId(133);
        wiki4.setParentsId(126);
        wiki4.setDepthIdx(1);
        wiki4.setOrderIdx(3);
        wiki4.setTitle("첫번째 위키의 두번째자식");
        wiki4.setMaxRows(4);
        wikiList.add(wiki4);


        return wikiList;
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
