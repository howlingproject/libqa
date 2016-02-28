package com.libqa.web.view;

import com.libqa.web.domain.Wiki;
import com.libqa.web.service.wiki.WikiService;
import com.libqa.web.view.space.WikiTree;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

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
    public void nodeTree() {
        List<WikiTree> wikiList = setData();

        System.out.println("@@@ 변경전 wikiList : " + wikiList);

        List<Integer> wikiIds = new ArrayList<>();

        for (WikiTree wikiTree : wikiList) {
            wikiIds.add(wikiTree.getWikiId());
        }

        List<WikiTree> children = new ArrayList<>();
        WikiTree wikiTree = new WikiTree();
        for (int i = 0; i < wikiList.size(); i++) {
            if (wikiList.get(i).getDepthIdx() > 0) {
                children.add(wikiList.get(i));
            } else {
                wikiTree.setParentsId(wikiList.get(i).getParentsId());
                wikiTree.setWikiId(wikiList.get(i).getWikiId());
                wikiTree.setTitle(wikiList.get(i).getTitle());
            }
        }

        System.out.println("@@@ wikiTree : " + wikiTree);

        // String tree = createTree2(wikiList);


    }




    @Test
    public void makeTree() {
        List<WikiTree> wikiList = setData();

        System.out.println("@@@ 변경전 wikiList : " + wikiList);

        // 자식 위키가 있는지 검사한다.
        for (int i = 0; i < wikiList.size(); i++) {
            if (wikiList.get(i).getWikiId() == wikiList.get(i).getParentsId()) {
                continue;
            } else {
                // 자식 위키가 있음
                setChild(wikiList, wikiList.get(i).getParentsId());
            }
        }

        int depth = 0;
        for (int x = 0; x < wikiList.size(); x++) {
            if (depth != 0 &&depth == wikiList.get(x).getDepthIdx()) {
                wikiList.get(x-1).setHasBrother(true);
            }

            depth = wikiList.get(x).getDepthIdx();
        }

        System.out.println("@@@ wikiList : " + wikiList);
        String tree = htmlTree(wikiList);
       // String tree = createTree2(wikiList);


        System.out.println("#### tree = \n" + tree);

    }


    private String htmlTree(List<WikiTree> wikiList) {
        String appendTag = "<ul id=\"tree\">\n";
        int groupId = 0;
        int depth = 0;

        for (int i = 0; i < wikiList.size(); i++) {
            System.out.println(i + "번째  ## group = " + wikiList.get(i).getGroupIdx()
                    + " ## parentId = " + wikiList.get(i).getParentsId()
                    + " ## wikiId = " + wikiList.get(i).getWikiId()
                    + " ## depth = " + wikiList.get(i).getDepthIdx()
                    + " ## child = " + wikiList.get(i).isHasChild()
                    + " ## brother = " + wikiList.get(i).isHasBrother()
                    + " || title = " + wikiList.get(i).getTitle() );
            boolean isClosed = wikiList.get(i).getDepthIdx() < depth;

            if (groupId != wikiList.get(i).getGroupIdx()) { //새로은 그룹 아이디일 경우 새로운 li생성

                if (isClosed) {
                    appendTag += " \t\t뎁스</li>\n\t</ul>\n</li>\n";
                }

                appendTag += "\t새<ll><strong>" + wikiList.get(i).getTitle() + " depth : " + wikiList.get(i).getDepthIdx()
                        + " parent : " + wikiList.get(i).getParentsId()
                        + " wiki : " + wikiList.get(i).getWikiId()
                        + " child : " + wikiList.get(i).isHasChild()
                        + " bro : " + wikiList.get(i).isHasBrother()
                        +"</strong>\n";

                if (wikiList.get(i).isHasChild() == true) {
                    appendTag += "\t<ul>\n";
                }
            } else {

                System.out.println("depth = " + depth + " listDepth = " + wikiList.get(i).getDepthIdx() + " isClose =" + isClosed);

                if (isClosed) {
                    appendTag += "\t\t중간</li>\n\t</ul>\n";
                    appendTag += "\t\t닫기</li>\n";
                }
                appendTag += "\t\t<ll><a href=\"#\">" + wikiList.get(i).getTitle() + " depth : " + wikiList.get(i).getDepthIdx()
                        + " parent : " + wikiList.get(i).getParentsId()
                        + " wiki : " + wikiList.get(i).getWikiId()
                        + " child : " + wikiList.get(i).isHasChild()
                        + " bro : " + wikiList.get(i).isHasBrother()
                        +"</a>";
                if (wikiList.get(i).isHasChild() == true) {
                    appendTag += "\n\t\t차일드<ul>\n";
                } else {
                    if (!wikiList.get(i).isHasBrother()) {
                        appendTag += "</li>\n\t\t</ul>\n";
                    } else {
                        appendTag += "</li>\n";
                    }
                }
            }


            if (wikiList.size() - 1 == i) { // 마지막 태그
                if (!wikiList.get(i).isHasBrother()) {
                    appendTag += "\t마지막</li>\n";
                }
            }
            depth = wikiList.get(i).getDepthIdx();
            groupId = wikiList.get(i).getGroupIdx();

        }
        appendTag += "</ul>";
        return appendTag;
    }

    private void setChild(List<WikiTree> wikiList, int parentId) {
        for (WikiTree tree : wikiList) {
            if (parentId == tree.getWikiId()) {
                tree.setHasChild(true);
            }
        }
    }


    private String createTree(List<WikiTree> wikiList) {
        String appendTag = "<ul>\n";
        int parentId = 0;
        int wikiId = 0;
        int groupId = 0;
        for (int i = 0; i < wikiList.size(); i++) {

            System.out.println(i + "번째  ## group = " + wikiList.get(i).getGroupIdx()
                    + " ## parentId = " + wikiList.get(i).getParentsId()
                    + " ## wikiId = " + wikiList.get(i).getWikiId()
                    + " ## depth = " + wikiList.get(i).getDepthIdx()
                    + " ## child = " + wikiList.get(i).isHasChild()
                    + " || title = " + wikiList.get(i).getTitle() );


            if (wikiList.get(i).isHasChild() == true) {
                if (wikiList.get(i).getDepthIdx() == 0) {
                    appendTag += "\t<ll><strong>" + wikiList.get(i).getTitle() + "</strong>";
                } else {
                    appendTag += "\t<ll><a href=\"#\">" + wikiList.get(i).getTitle() + "</a>";
                }
                appendTag += "\n\t<ul>\n";
            } else {        // 자식이 없으면 닫는다.
                if (wikiList.get(i).getDepthIdx() == 0) {
                    appendTag += "\t<ll><strong>" + wikiList.get(i).getTitle() + "</strong>\t</li>\n";
                } else {

                    appendTag += "\t<ll><a href=\"#\">" + wikiList.get(i).getTitle() + "</a></li>\n</ul>\n";
                }
            }

            parentId = wikiList.get(i).getParentsId();
            wikiId = wikiList.get(i).getWikiId();
            groupId = wikiList.get(i).getGroupIdx();
        }

        appendTag += "</ul>";

        return  appendTag;
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
        wiki1.setTitle("첫번째 테스트");
        wiki1.setMaxRows(7);
        wikiList.add(wiki1);

        WikiTree wiki2 = new WikiTree();
        wiki2.setGroupIdx(126);
        wiki2.setWikiId(128);
        wiki2.setParentsId(126);
        wiki2.setDepthIdx(1);
        wiki2.setOrderIdx(1);
        wiki2.setTitle("첫번째 위키의 자식 위키");
        wiki2.setMaxRows(7);
        wikiList.add(wiki2);

        WikiTree wiki3 = new WikiTree();
        wiki3.setGroupIdx(126);
        wiki3.setWikiId(129);
        wiki3.setParentsId(128);
        wiki3.setDepthIdx(2);
        wiki3.setOrderIdx(2);
        wiki3.setTitle("첫번째 자식의 자식");
        wiki3.setMaxRows(7);
        wikiList.add(wiki3);

        WikiTree wiki4 = new WikiTree();
        wiki4.setGroupIdx(126);
        wiki4.setWikiId(133);
        wiki4.setParentsId(129);
        wiki4.setDepthIdx(3);
        wiki4.setOrderIdx(3);
        wiki4.setTitle("첫번째의 1자식의 자식의 자식");
        wiki4.setMaxRows(7);
        wikiList.add(wiki4);

        WikiTree wiki5 = new WikiTree();
        wiki5.setGroupIdx(126);
        wiki5.setWikiId(132);
        wiki5.setParentsId(128);
        wiki5.setDepthIdx(2);
        wiki5.setOrderIdx(4);
        wiki5.setTitle("첫번째 자식의 두번째 자식");
        wiki5.setMaxRows(7);
        wikiList.add(wiki5);

        WikiTree wiki6 = new WikiTree();
        wiki6.setGroupIdx(126);
        wiki6.setWikiId(134);
        wiki6.setParentsId(132);
        wiki6.setDepthIdx(3);
        wiki6.setOrderIdx(5);
        wiki6.setTitle("첫번째 자식의 두번째 자식의 자식");
        wiki6.setMaxRows(7);
        wikiList.add(wiki6);

        WikiTree wiki7 = new WikiTree();
        wiki7.setGroupIdx(126);
        wiki7.setWikiId(135);
        wiki7.setParentsId(132);
        wiki7.setDepthIdx(3);
        wiki7.setOrderIdx(6);
        wiki7.setTitle("첫번째 자식의 두번째 자식의 두번째 자식 ");
        wiki7.setMaxRows(7);
        wikiList.add(wiki7);

        WikiTree wiki8 = new WikiTree();
        wiki8.setGroupIdx(130);
        wiki8.setWikiId(130);
        wiki8.setParentsId(130);
        wiki8.setDepthIdx(0);
        wiki8.setOrderIdx(0);
        wiki8.setTitle("두번째");
        wiki8.setMaxRows(2);
        wikiList.add(wiki8);

        WikiTree wiki9 = new WikiTree();
        wiki9.setGroupIdx(130);
        wiki9.setWikiId(131);
        wiki9.setParentsId(130);
        wiki9.setDepthIdx(1);
        wiki9.setOrderIdx(1);
        wiki9.setTitle("두번째의 1자식");
        wiki9.setMaxRows(2);
        wikiList.add(wiki9);

        return wikiList;
    }
}
