/*
 * Copyright (c) 2009-2026 MOIS (MINISTRY OF THE INTERIOR AND SAFETY).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package egovframework.example.pagination;

import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;

import org.egovframe.rte.ptl.mvc.tags.ui.pagination.AbstractKrdsPaginationRenderer;
import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

/**
 * KRDS 페이지네이션 렌더러 (krds-uiux pagination 마크업, 반응형)
 *
 * <pre>
 * PC(1024px 이상)      : 이전 1 … 4 5 6 7 8 … 12 다음  (RTE AbstractKrdsPaginationRenderer 와 같은 규칙, 최대 9칸)
 * 태블릿(768~1023px)   : 이전 1 … 5 6 7 … 12 다음      (class="page-narrow" / "page-wide" 로 표시 범위 구분)
 * 모바일(767px 이하)   : 이전 1 … 6/12 … 12 다음       (class="page-mobile", 처음 · 끝 바로가기 + 현재/전체)
 * </pre>
 * 화면 폭별 표시는 egovframe.css 가 결정한다. (Thymeleaf : #egovKrdsPaginationRenderer)
 *
 * @Modification Information
 *  수정일      수정자    수정내용
 *  ----------  --------  ----------------------------------------
 *  2026.10.29  유지보수  최초생성 (egovframe-web-sample)
 *  2026.10.29  유지보수  mobile-sample 적용 : 현재 페이지 sr-only · aria-current, 링크 기본동작(#) 차단,
 *                        요청마다 라벨을 바꾸는 공유 필드 갱신을 동기화(다국어 동시 요청 시 라벨 섞임 방지)
 *  2026.10.29  유지보수  반응형 형식 추가 (태블릿 축약형, 모바일 현재/전체), 라벨을 요청마다 지역 변수로 생성해
 *                        공유 필드 · 동기화 제거 (web · boot · mobile 공통)
 *  2026.10.27  유지보수  모바일 형식에 처음(1) · 끝(전체) 페이지 바로가기 추가 : 이전 1 … 3/23 … 23 다음
 *                        (web · boot · mobile 공통)
 *
 * @author 개발프레임워크 실행환경 개발팀
 * @since 2026.10.29
 * @version 1.0
 */
@Component
public class EgovKrdsPaginationRenderer extends AbstractKrdsPaginationRenderer {

	/** 태블릿 축약형에서 앞뒤 페이지를 모두 표시하는 최대 전체 페이지 수 */
	private static final int NARROW_ALL_PAGES = 7;

	@Autowired
	private MessageSource messageSource;

	/**
	 * 페이지네이션 HTML 을 만든다.
	 * 라벨은 요청의 로케일로 매번 지역 변수에 만들므로 동시 요청 간에 섞이지 않는다. (공유 필드 미사용)
	 */
	@Override
	public String renderPagination(PaginationInfo paginationInfo, String jsFunction) {
		Locale locale = LocaleContextHolder.getLocale();
		String prevLabel = message("page.prev", "이전", locale);
		String nextLabel = message("page.next", "다음", locale);
		String currentLabel = message("page.current", "현재 페이지", locale);
		String totalLabel = message("page.total", "전체 페이지", locale);

		// 0건(전체 0페이지)이어도 "1 / 1" 로 표시하고 이전 · 다음은 비활성
		int total = Math.max(1, paginationInfo.getTotalPageCount());
		int current = Math.min(Math.max(1, paginationInfo.getCurrentPageNo()), total);

		Set<Integer> wide = widePages(current, total);
		Set<Integer> narrow = narrowPages(current, total);

		StringBuilder sb = new StringBuilder();

		// 이전
		if (current == 1) {
			sb.append("<span class=\"page-navi prev disabled\">").append(prevLabel).append("</span>");
		} else {
			sb.append(link("page-navi prev", jsFunction, current - 1, prevLabel));
		}

		// 페이지 번호 (PC · 태블릿)
		sb.append("<div class=\"page-links\">");
		Integer lastWide = null;
		Integer lastNarrow = null;
		for (int page : wide) {
			boolean inNarrow = narrow.contains(page);
			boolean wideDot = lastWide != null && page - lastWide > 1;
			boolean narrowDot = inNarrow && lastNarrow != null && page - lastNarrow > 1;
			if (wideDot || narrowDot) {
				String scope = wideDot && narrowDot ? "" : wideDot ? " page-wide" : " page-narrow";
				sb.append("<span class=\"page-link link-dot").append(scope).append("\">...</span>");
			}
			String scope = inNarrow ? "" : " page-wide";
			if (page == current) {
				sb.append("<a class=\"page-link active").append(scope)
					.append("\" aria-current=\"page\" href=\"#\" onclick=\"return false;\"><span class=\"sr-only\">")
					.append(currentLabel).append(" </span>").append(page).append("</a>");
			} else {
				sb.append(link("page-link" + scope, jsFunction, page, Integer.toString(page)));
			}
			lastWide = page;
			if (inNarrow) {
				lastNarrow = page;
			}
		}
		sb.append("</div>");

		// 처음 … 현재 / 전체 … 끝 (모바일)
		sb.append("<div class=\"page-links page-mobile\">");
		if (current > 1) {
			sb.append(link("page-link", jsFunction, 1, "1"));
		}
		if (current > 2) {
			sb.append("<span class=\"page-link link-dot\">...</span>");
		}
		sb.append("<span class=\"page-summary\"><span class=\"sr-only\">").append(currentLabel).append(" </span>")
			.append("<strong>").append(current).append("</strong>")
			.append("<span aria-hidden=\"true\">/</span><span class=\"sr-only\">, ").append(totalLabel).append(" </span>")
			.append(total).append("</span>");
		if (current < total - 1) {
			sb.append("<span class=\"page-link link-dot\">...</span>");
		}
		if (current < total) {
			sb.append(link("page-link", jsFunction, total, Integer.toString(total)));
		}
		sb.append("</div>");

		// 다음
		if (current == total) {
			sb.append("<span class=\"page-navi next disabled\">").append(nextLabel).append("</span>");
		} else {
			sb.append(link("page-navi next", jsFunction, current + 1, nextLabel));
		}

		return sb.toString();
	}

	/** PC : 전체 8페이지 이하면 모두, 아니면 처음 · 현재 주변 · 끝 (최대 9칸) */
	static Set<Integer> widePages(int current, int total) {
		Set<Integer> pages = new TreeSet<>();
		if (total <= 8) {
			addRange(pages, 1, total);
		} else if (current <= 4) {
			addRange(pages, 1, 7);
			pages.add(total);
		} else if (current >= total - 3) {
			pages.add(1);
			addRange(pages, total - 6, total);
		} else {
			pages.add(1);
			addRange(pages, current - 2, current + 2);
			pages.add(total);
		}
		return pages;
	}

	/** 태블릿 : 전체 7페이지 이하면 모두, 아니면 처음 · 현재 ±1 · 끝 (최대 7칸, PC 표시 범위의 부분집합) */
	static Set<Integer> narrowPages(int current, int total) {
		Set<Integer> pages = new TreeSet<>();
		if (total <= NARROW_ALL_PAGES) {
			addRange(pages, 1, total);
		} else if (current <= 3) {
			addRange(pages, 1, 4);
			pages.add(total);
		} else if (current >= total - 2) {
			pages.add(1);
			addRange(pages, total - 3, total);
		} else {
			pages.add(1);
			addRange(pages, current - 1, current + 1);
			pages.add(total);
		}
		return pages;
	}

	private static void addRange(Set<Integer> pages, int from, int to) {
		for (int i = from; i <= to; i++) {
			pages.add(i);
		}
	}

	private static String link(String cssClass, String jsFunction, int page, String label) {
		return "<a class=\"" + cssClass + "\" href=\"#\" onclick=\"" + jsFunction + "(" + page + "); return false;\">"
				+ label + "</a>";
	}

	private String message(String code, String defaultMessage, Locale locale) {
		return messageSource.getMessage(code, null, defaultMessage, locale);
	}

}
