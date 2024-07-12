package egovframework.example.pagination;

import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

import java.text.MessageFormat;

public class EgovPaginationFormat {

    protected String firstPageLabel = "<a onclick=\"{0}({1})\" class='paging_btn' id='first'><span class='blind'>처음</span></a>&nbsp;";
    protected String previousPageLabel = "<a onclick=\"{0}({1})\" class='paging_btn' id='prev'><span class='blind'>이전</span></a>&nbsp;";
    protected String currentPageLabel = "<strong>{0}</strong>&nbsp;";
    protected String otherPageLabel = "<a onclick=\"{0}({1})\">{2}</a>&nbsp;";
    protected String nextPageLabel = "<a onclick=\"{0}({1})\" class='paging_btn' id ='next'><span class='blind'>다음</span></a>&nbsp;";
    protected String lastPageLabel = "<a onclick=\"{0}({1})\" class='paging_btn' id='last'><span class='blind>마지막</span></a>";

    public String paginationFormat(PaginationInfo paginationInfo, String jsFunction) {
        StringBuffer stringBuffer = new StringBuffer();

        int firstPageNo = paginationInfo.getFirstPageNo();
        int firstPageNoOnPageList = paginationInfo.getFirstPageNoOnPageList();
        int totalPageCount = paginationInfo.getTotalPageCount();
        int pageSize = paginationInfo.getPageSize();
        int lastPageNoOnPageList = paginationInfo.getLastPageNoOnPageList();
        int currentPageNo = paginationInfo.getCurrentPageNo();
        int lastPageNo = paginationInfo.getLastPageNo();

        if (totalPageCount > pageSize) {
            if (firstPageNoOnPageList > pageSize) {
                stringBuffer.append(MessageFormat.format(firstPageLabel, new Object[]{jsFunction, Integer.toString(firstPageNo)}));
                stringBuffer.append(MessageFormat.format(previousPageLabel, new Object[]{jsFunction, Integer.toString(firstPageNoOnPageList-1)}));
            } else {
                stringBuffer.append(MessageFormat.format(firstPageLabel, new Object[]{jsFunction, Integer.toString(firstPageNo)}));
                stringBuffer.append(MessageFormat.format(previousPageLabel, new Object[]{jsFunction, Integer.toString(firstPageNo)}));
            }
        }

        for (int i = firstPageNoOnPageList; i <= lastPageNoOnPageList; i++) {
            if (i == currentPageNo) {
                stringBuffer.append(MessageFormat.format(currentPageLabel, new Object[]{Integer.toString(i)}));
            } else {
                stringBuffer.append(MessageFormat.format(otherPageLabel, new Object[]{jsFunction, Integer.toString(i), Integer.toString(i)}));
            }
        }

        if (totalPageCount > pageSize) {
            if (lastPageNoOnPageList < totalPageCount) {
                stringBuffer.append(MessageFormat.format(nextPageLabel, new Object[]{jsFunction, Integer.toString(firstPageNoOnPageList+pageSize)}));
                stringBuffer.append(MessageFormat.format(lastPageLabel, new Object[]{jsFunction, Integer.toString(lastPageNo)}));
            } else {
                stringBuffer.append(MessageFormat.format(nextPageLabel, new Object[]{jsFunction, Integer.toString(lastPageNo)}));
                stringBuffer.append(MessageFormat.format(lastPageLabel, new Object[]{jsFunction, Integer.toString(lastPageNo)}));
            }
        }

        return stringBuffer.toString();
    }

}
