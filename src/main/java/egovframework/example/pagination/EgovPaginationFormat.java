package egovframework.example.pagination;

import java.text.MessageFormat;

import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

import lombok.NoArgsConstructor;

/**
 * Egov 페이징 형식
 */
@NoArgsConstructor
public class EgovPaginationFormat {

	/**
	 * 첫 번째 페이지 라벨
	 */
	protected String firstPageLabel = "<a onclick=\"{0}({1})\" class='paging_btn' id='first'><span class='blind'>처음</span></a>&nbsp;";

	/**
	 * 이전 페이지 라벨
	 */
	protected String previousPageLabel = "<a onclick=\"{0}({1})\" class='paging_btn' id='prev'><span class='blind'>이전</span></a>&nbsp;";

	/**
	 * 현재 페이지 라벨
	 */
	protected String currentPageLabel = "<strong>{0}</strong>&nbsp;";

	/**
	 * 다른 페이지 라벨
	 */
	protected String otherPageLabel = "<a onclick=\"{0}({1})\">{2}</a>&nbsp;";

	/**
	 * 다음 페이지 라벨
	 */
	protected String nextPageLabel = "<a onclick=\"{0}({1})\" class='paging_btn' id ='next'><span class='blind'>다음</span></a>&nbsp;";

	/**
	 * 마지막 페이지 라벨
	 */
	protected String lastPageLabel = "<a onclick=\"{0}({1})\" class='paging_btn' id='last'><span class='blind>마지막</span></a>";

	/**
	 * 
	 * @param paginationInfo
	 * @param jsFunction
	 * @return
	 */
	public String paginationFormat(final PaginationInfo paginationInfo, final String jsFunction) {
		final StringBuffer stringBuffer = new StringBuffer();

		final int firstPageNo = paginationInfo.getFirstPageNo();
		final int firstPageNoOnPageList = paginationInfo.getFirstPageNoOnPageList();
		final int totalPageCount = paginationInfo.getTotalPageCount();
		final int pageSize = paginationInfo.getPageSize();
		final int lastPageNoOnPageList = paginationInfo.getLastPageNoOnPageList();
		final int currentPageNo = paginationInfo.getCurrentPageNo();
		final int lastPageNo = paginationInfo.getLastPageNo();

		if (totalPageCount > pageSize) {
			if (firstPageNoOnPageList > pageSize) {
				stringBuffer.append(MessageFormat.format(firstPageLabel,
						new Object[] { jsFunction, Integer.toString(firstPageNo) }));
				stringBuffer.append(MessageFormat.format(previousPageLabel,
						new Object[] { jsFunction, Integer.toString(firstPageNoOnPageList - 1) }));
			} else {
				stringBuffer.append(MessageFormat.format(firstPageLabel,
						new Object[] { jsFunction, Integer.toString(firstPageNo) }));
				stringBuffer.append(MessageFormat.format(previousPageLabel,
						new Object[] { jsFunction, Integer.toString(firstPageNo) }));
			}
		}

		for (int i = firstPageNoOnPageList; i <= lastPageNoOnPageList; i++) {
			if (i == currentPageNo) {
				stringBuffer.append(MessageFormat.format(currentPageLabel, new Object[] { Integer.toString(i) }));
			} else {
				stringBuffer.append(MessageFormat.format(otherPageLabel,
						new Object[] { jsFunction, Integer.toString(i), Integer.toString(i) }));
			}
		}

		if (totalPageCount > pageSize) {
			if (lastPageNoOnPageList < totalPageCount) {
				stringBuffer.append(MessageFormat.format(nextPageLabel,
						new Object[] { jsFunction, Integer.toString(firstPageNoOnPageList + pageSize) }));
				stringBuffer.append(
						MessageFormat.format(lastPageLabel, new Object[] { jsFunction, Integer.toString(lastPageNo) }));
			} else {
				stringBuffer.append(
						MessageFormat.format(nextPageLabel, new Object[] { jsFunction, Integer.toString(lastPageNo) }));
				stringBuffer.append(
						MessageFormat.format(lastPageLabel, new Object[] { jsFunction, Integer.toString(lastPageNo) }));
			}
		}

		return stringBuffer.toString();
	}

}
