package code.jack.myblog.dto;

import jdk.nashorn.internal.ir.IdentNode;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
public class PageDto<T> {
    private List<T>  data;
    private boolean showPre;
    private boolean showNext;
    private Integer currentPage;
    private List<Integer> pages = new ArrayList<>();

    public void setPagein(int totalcount, Integer page, Integer size) {
        int totalPage;
        currentPage = page;
        if (totalcount % size == 0) {
            totalPage = totalcount / size;
        } else {
            totalPage = totalcount / size + 1;
        }
        pages.add(page);
        if(totalPage==1){
        }else if(totalPage==2){
            for(int i=1;i<=2;i++){
                if(i!=page){
                    pages.add(i);
                }
            }
        }
        else if(totalPage==3){
            for(int i=1;i<=3;i++){
                if(i!=page){
                    pages.add(i);
                }
            }
        }
        else if(totalPage==4){
            for(int i=1;i<=4;i++){
                if(i!=page){
                    pages.add(i);
                }
            }
        }
        else if(totalPage==5){
            for(int i=1;i<=5;i++){
                if(i!=page){
                    pages.add(i);
                }
            }
        }
        else{
            if (totalPage - page == 0) {
                pages.add(page - 1);
                pages.add(page - 2);
            } else if (totalPage - page == 1) {
                pages.add(page - 1);
                pages.add(page - 2);
                pages.add(page + 1);
            } else if (page > 3) {
                pages.add(page - 1);
                pages.add(page - 2);
                pages.add(page + 1);
                pages.add(page + 2);
            } else {
                for (int i = 1; i <= 5; i++) {
                    if (i != page) {
                        pages.add(i);
                    }
                }
            }
        }
        Collections.sort(pages);
        //是否展示上一页
        if (currentPage == 1) {
            showPre = false;
        } else {
            showPre = true;
        }
        //是否展示下一页
        if (currentPage == totalPage) {
            showNext = false;
        } else {
            showNext = true;
        }
    }
}
