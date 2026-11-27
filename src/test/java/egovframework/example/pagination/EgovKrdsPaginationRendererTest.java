package egovframework.example.pagination;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.StaticMessageSource;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * EgovKrdsPaginationRenderer 단위 테스트 (PC · 태블릿 · 모바일 표시 범위, 다국어 동시 요청)
 *
 * @author 개발프레임워크 실행환경 개발팀
 * @since 2026.10.27
 */
class EgovKrdsPaginationRendererTest {

	private static final Pattern MOBILE = Pattern.compile(
		"class=\"page-link(?: [^\"]*)?\"[^>]*>([^<]*)<|<strong>(\\d+)</strong>.*?</span>(\\d+)</span>");

	private static final Pattern LINK = Pattern.compile("<(?:a|span) class=\"page-link([^\"]*)\"[^>]*>(?:<span class=\"sr-only\">[^<]*</span>)?([^<]*)<");

	private EgovKrdsPaginationRenderer renderer;

	@BeforeEach
	void setUp() {
		StaticMessageSource messageSource = new StaticMessageSource();
		messageSource.addMessage("page.prev", Locale.KOREAN, "이전");
		messageSource.addMessage("page.next", Locale.KOREAN, "다음");
		messageSource.addMessage("page.current", Locale.KOREAN, "현재 페이지");
		messageSource.addMessage("page.total", Locale.KOREAN, "전체 페이지");
		messageSource.addMessage("page.prev", Locale.ENGLISH, "Prev");
		messageSource.addMessage("page.next", Locale.ENGLISH, "Next");
		messageSource.addMessage("page.current", Locale.ENGLISH, "Current page");
		messageSource.addMessage("page.total", Locale.ENGLISH, "Total pages");

		renderer = new EgovKrdsPaginationRenderer();
		ReflectionTestUtils.setField(renderer, "messageSource", messageSource);
		LocaleContextHolder.setLocale(Locale.KOREAN);
	}

	@AfterEach
	void tearDown() {
		LocaleContextHolder.resetLocaleContext();
	}

	private static PaginationInfo pagination(int currentPageNo, int totalRecordCount) {
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(currentPageNo);
		paginationInfo.setRecordCountPerPage(10);
		paginationInfo.setPageSize(10);
		paginationInfo.setTotalRecordCount(totalRecordCount);
		return paginationInfo;
	}

	/** 화면 폭별로 보이는 항목 ("..." 포함). wide=true 면 PC, false 면 태블릿 (모바일 영역 제외) */
	private static List<String> visible(String html, boolean wide) {
		List<String> items = new ArrayList<>();
		Matcher m = LINK.matcher(html.substring(0, html.indexOf("<div class=\"page-links page-mobile\">")));
		while (m.find()) {
			String cls = m.group(1);
			boolean show = wide ? !cls.contains("page-narrow") : !cls.contains("page-wide");
			if (show) {
				items.add(m.group(2));
			}
		}
		return items;
	}

	@Test
	@DisplayName("PC 는 RTE 규칙(최대 9칸), 태블릿은 처음 · 현재 ±1 · 끝으로 표시한다")
	void middlePage() {
		String html = renderer.renderPagination(pagination(7, 120), "fnPage");

		assertThat(visible(html, true)).containsExactly("1", "...", "5", "6", "7", "8", "9", "...", "12");
		assertThat(visible(html, false)).containsExactly("1", "...", "6", "7", "8", "...", "12");
	}

	@Test
	@DisplayName("앞 · 뒤 페이지 근처에서도 태블릿 축약형에 현재 · 마지막 페이지가 보인다")
	void edgePages() {
		assertThat(visible(renderer.renderPagination(pagination(2, 120), "fnPage"), false))
			.containsExactly("1", "2", "3", "4", "...", "12");
		assertThat(visible(renderer.renderPagination(pagination(11, 120), "fnPage"), false))
			.containsExactly("1", "...", "9", "10", "11", "12");
		// KRDS 기본 규칙(6~8번째 숨김)이면 사라지던 경우 : 9페이지 중 7페이지
		assertThat(visible(renderer.renderPagination(pagination(7, 90), "fnPage"), false))
			.containsExactly("1", "...", "6", "7", "8", "9");
	}

	/** 모바일 영역에 보이는 항목. 현재/전체 요약은 "현재/전체" 로 표시 */
	private static List<String> mobile(String html) {
		String area = html.substring(html.indexOf("<div class=\"page-links page-mobile\">"));
		area = area.substring(0, area.indexOf("</div>"));
		List<String> items = new ArrayList<>();
		Matcher m = MOBILE.matcher(area);
		while (m.find()) {
			items.add(m.group(1) != null ? m.group(1) : m.group(2) + "/" + m.group(3));
		}
		return items;
	}

	@Test
	@DisplayName("모바일은 이전 · 처음 … 현재/전체 … 끝 · 다음으로 표시한다")
	void mobilePages() {
		assertThat(mobile(renderer.renderPagination(pagination(3, 230), "fnPage")))
			.containsExactly("1", "...", "3/23", "...", "23");
		assertThat(mobile(renderer.renderPagination(pagination(1, 230), "fnPage")))
			.containsExactly("1/23", "...", "23");
		assertThat(mobile(renderer.renderPagination(pagination(2, 230), "fnPage")))
			.containsExactly("1", "2/23", "...", "23");
		assertThat(mobile(renderer.renderPagination(pagination(22, 230), "fnPage")))
			.containsExactly("1", "...", "22/23", "23");
		assertThat(mobile(renderer.renderPagination(pagination(23, 230), "fnPage")))
			.containsExactly("1", "...", "23/23");
		assertThat(mobile(renderer.renderPagination(pagination(1, 0), "fnPage")))
			.containsExactly("1/1");

		String html = renderer.renderPagination(pagination(3, 230), "fnPage");
		assertThat(html).contains("<a class=\"page-link\" href=\"#\" onclick=\"fnPage(1); return false;\">1</a>")
			.contains("<a class=\"page-link\" href=\"#\" onclick=\"fnPage(23); return false;\">23</a>");
	}

	@Test
	@DisplayName("모바일용 현재/전체 요약과 현재 페이지 표시(aria-current)를 출력한다")
	void summaryAndCurrent() {
		String html = renderer.renderPagination(pagination(7, 120), "fnPage");

		assertThat(html).contains("<span class=\"page-summary\"><span class=\"sr-only\">현재 페이지 </span><strong>7</strong>")
			.contains("<span class=\"sr-only\">, 전체 페이지 </span>12</span>")
			.contains("aria-current=\"page\"")
			.contains("onclick=\"fnPage(6); return false;\">이전</a>")
			.contains("onclick=\"fnPage(8); return false;\">다음</a>");
	}

	@Test
	@DisplayName("0건이면 1 / 1 로 표시하고 이전 · 다음을 비활성으로 출력한다")
	void noData() {
		String html = renderer.renderPagination(pagination(1, 0), "fnPage");

		assertThat(html).contains("<span class=\"page-navi prev disabled\">이전</span>")
			.contains("<span class=\"page-navi next disabled\">다음</span>")
			.contains("<strong>1</strong>");
		assertThat(visible(html, true)).containsExactly("1");
	}

	@Test
	@DisplayName("한국어 · 영어 요청이 동시에 들어와도 라벨이 섞이지 않는다")
	void concurrentLocales() throws Exception {
		ExecutorService pool = Executors.newFixedThreadPool(8);
		try {
			List<Future<Boolean>> results = new ArrayList<>();
			for (int i = 0; i < 400; i++) {
				Locale locale = i % 2 == 0 ? Locale.KOREAN : Locale.ENGLISH;
				Callable<Boolean> task = () -> {
					LocaleContextHolder.setLocale(locale);
					try {
						String html = renderer.renderPagination(pagination(5, 120), "fnPage");
						return Locale.KOREAN.equals(locale)
							? html.contains(">이전<") && html.contains(">다음<") && !html.contains("Prev")
							: html.contains(">Prev<") && html.contains(">Next<") && !html.contains("이전");
					} finally {
						LocaleContextHolder.resetLocaleContext();
					}
				};
				results.add(pool.submit(task));
			}
			for (Future<Boolean> result : results) {
				assertThat(result.get()).isTrue();
			}
		} finally {
			pool.shutdown();
		}
	}

}
