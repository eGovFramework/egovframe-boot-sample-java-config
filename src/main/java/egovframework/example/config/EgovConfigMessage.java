package egovframework.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.context.support.StaticMessageSource;

import java.util.Locale;

@Configuration
public class EgovConfigMessage {

  @Bean
  public ReloadableResourceBundleMessageSource messageSource() {
    ReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource = new ReloadableResourceBundleMessageSource();
    String classpath = System.getProperty("java.class.path");
    reloadableResourceBundleMessageSource.setBasenames(
        "classpath:/egovframework/message/com/message-common",
        "classpath:/org/egovframe/rte/fdl/idgnr/messages/idgnr",
        "classpath:/org/egovframe/rte/fdl/property/messages/properties");


    StaticMessageSource messageSource = new StaticMessageSource();

    addCommonMessages(messageSource); // 기본 메시지 (message-common.properties)
    addEnglishMessages(messageSource); // 영어 메시지 (message-common_en.properties)
    addKoreanMessages(messageSource); // 한국어 메시지 (message-common_ko.properties)

    reloadableResourceBundleMessageSource.setParentMessageSource(messageSource);
    reloadableResourceBundleMessageSource.setCacheSeconds(60);

    return reloadableResourceBundleMessageSource;
  }

  private void addCommonMessages(StaticMessageSource messageSource) {
    messageSource.addMessage("list.sample", Locale.getDefault(), "List Sample");
    messageSource.addMessage("button.search", Locale.getDefault(), "검색");
    messageSource.addMessage("button.list", Locale.getDefault(), "목록");
    messageSource.addMessage("button.modify", Locale.getDefault(), "수정");
    messageSource.addMessage("button.create", Locale.getDefault(), "등록");
    messageSource.addMessage("button.reset", Locale.getDefault(), "재설정");
    messageSource.addMessage("button.delete", Locale.getDefault(), "삭제");
    messageSource.addMessage("search.choose", Locale.getDefault(), "검색어 선택");
    messageSource.addMessage("search.keyword", Locale.getDefault(), "검색어 입력");
    messageSource.addMessage("search.name", Locale.getDefault(), "이름");
    messageSource.addMessage("search.id", Locale.getDefault(), "ID");
    messageSource.addMessage("search.error", Locale.getDefault(), "검색어를 입력해야 합니다.");
    messageSource.addMessage("title.sample.id", Locale.getDefault(), "카테고리ID");
    messageSource.addMessage("title.sample.name", Locale.getDefault(), "카테고리명");
    messageSource.addMessage("title.sample.useYn", Locale.getDefault(), "사용여부");
    messageSource.addMessage("title.sample.description", Locale.getDefault(), "설명");
    messageSource.addMessage("title.sample.regUser", Locale.getDefault(), "등록자");
    messageSource.addMessage("title.sample", Locale.getDefault(), "기본 게시판 목록");
    messageSource.addMessage("info.nodata.msg", Locale.getDefault(), "해당 데이터가 없습니다.");
    messageSource.addMessage("fail.common.msg", Locale.getDefault(), "에러가 발생했습니다!");
    messageSource.addMessage("add.confirm", Locale.getDefault(), "등록하시겠습니까?");
    messageSource.addMessage("add.confirm.success", Locale.getDefault(), "등록되었습니다.");
    messageSource.addMessage("modify.confirm", Locale.getDefault(), "수정하시겠습니까?");
    messageSource.addMessage("modify.confirm.success", Locale.getDefault(), "수정되었습니다.");
    messageSource.addMessage("delete.confirm", Locale.getDefault(), "삭제하시겠습니까?");
    messageSource.addMessage("delete.confirm.success", Locale.getDefault(), "삭제되었습니다.");
    messageSource.addMessage("confirm.required.name", Locale.getDefault(), "카테고리명은 필수 입력값입니다.");
    messageSource.addMessage("confirm.required.description", Locale.getDefault(), "설명은 필수 입력값입니다.");
    messageSource.addMessage("confirm.required.user", Locale.getDefault(), "등록자는 필수 입력값입니다.");
  }
  private void addEnglishMessages(StaticMessageSource messageSource) {
    messageSource.addMessage("list.sample", Locale.ENGLISH, "List Sample");
    messageSource.addMessage("button.search", Locale.ENGLISH, "Search");
    messageSource.addMessage("button.list", Locale.ENGLISH, "List");
    messageSource.addMessage("button.modify", Locale.ENGLISH, "Modify");
    messageSource.addMessage("button.create", Locale.ENGLISH, "Create");
    messageSource.addMessage("button.reset", Locale.ENGLISH, "Reset");
    messageSource.addMessage("button.delete", Locale.ENGLISH, "Delete");
    messageSource.addMessage("search.choose", Locale.ENGLISH, "Select Choose");
    messageSource.addMessage("search.keyword", Locale.ENGLISH, "Search");
    messageSource.addMessage("search.name", Locale.ENGLISH, "name");
    messageSource.addMessage("search.id", Locale.ENGLISH, "ID");
    messageSource.addMessage("search.error", Locale.ENGLISH, "You must enter a search word.");
    messageSource.addMessage("title.sample.id", Locale.ENGLISH, "Category ID");
    messageSource.addMessage("title.sample.name", Locale.ENGLISH, "Category Name");
    messageSource.addMessage("title.sample.useYn", Locale.ENGLISH, "useYn");
    messageSource.addMessage("title.sample.description", Locale.ENGLISH, "Description");
    messageSource.addMessage("title.sample.regUser", Locale.ENGLISH, "User Name");
    messageSource.addMessage("title.sample", Locale.ENGLISH, "Default BBS List");
    messageSource.addMessage("info.nodata.msg", Locale.ENGLISH, "No Data Found.");
    messageSource.addMessage("fail.common.msg", Locale.ENGLISH, "Error Occurred!");
    messageSource.addMessage("add.confirm", Locale.ENGLISH, "Are you sure you want to register?");
    messageSource.addMessage("add.confirm.success", Locale.ENGLISH, "Registered successfully.");
    messageSource.addMessage("modify.confirm", Locale.ENGLISH, "Are you sure you want to modify?");
    messageSource.addMessage("modify.confirm.success", Locale.ENGLISH, "Modified successfully.");
    messageSource.addMessage("delete.confirm", Locale.ENGLISH, "Are you sure you want to delete?");
    messageSource.addMessage("delete.confirm.success", Locale.ENGLISH, "Deleted successfully.");
    messageSource.addMessage("confirm.required.name", Locale.ENGLISH, "Category Name is required.");
    messageSource.addMessage("confirm.required.description", Locale.ENGLISH, "Description is required.");
    messageSource.addMessage("confirm.required.user", Locale.ENGLISH, "User is required.");
  }

  private void addKoreanMessages(StaticMessageSource messageSource) {
    Locale KOREAN = Locale.KOREAN;
    messageSource.addMessage("list.sample", KOREAN, "List Sample");
    messageSource.addMessage("button.search", KOREAN, "검색");
    messageSource.addMessage("button.list", KOREAN, "목록");
    messageSource.addMessage("button.modify", KOREAN, "수정");
    messageSource.addMessage("button.create", KOREAN, "등록");
    messageSource.addMessage("button.reset", KOREAN, "재설정");
    messageSource.addMessage("button.delete", KOREAN, "삭제");
    messageSource.addMessage("search.choose", KOREAN, "검색어 선택");
    messageSource.addMessage("search.keyword", KOREAN, "검색어 입력");
    messageSource.addMessage("search.name", KOREAN, "이름");
    messageSource.addMessage("search.id", KOREAN, "ID");
    messageSource.addMessage("search.error", KOREAN, "검색어를 입력해야 합니다.");
    messageSource.addMessage("title.sample.id", KOREAN, "카테고리ID");
    messageSource.addMessage("title.sample.name", KOREAN, "카테고리명");
    messageSource.addMessage("title.sample.useYn", KOREAN, "사용여부");
    messageSource.addMessage("title.sample.description", KOREAN, "설명");
    messageSource.addMessage("title.sample.regUser", KOREAN, "등록자");
    messageSource.addMessage("title.sample", KOREAN, "기본 게시판 목록");
    messageSource.addMessage("info.nodata.msg", KOREAN, "해당 데이터가 없습니다.");
    messageSource.addMessage("fail.common.msg", KOREAN, "에러가 발생했습니다!");
    messageSource.addMessage("add.confirm", KOREAN, "등록하시겠습니까?");
    messageSource.addMessage("add.confirm.success", KOREAN, "등록되었습니다.");
    messageSource.addMessage("modify.confirm", KOREAN, "수정하시겠습니까?");
    messageSource.addMessage("modify.confirm.success", KOREAN, "수정되었습니다.");
    messageSource.addMessage("delete.confirm", KOREAN, "삭제하시겠습니까?");
    messageSource.addMessage("delete.confirm.success", KOREAN, "삭제되었습니다.");
    messageSource.addMessage("confirm.required.name", KOREAN, "카테고리명은 필수 입력값입니다.");
    messageSource.addMessage("confirm.required.description", KOREAN, "설명은 필수 입력값입니다.");
    messageSource.addMessage("confirm.required.user", KOREAN, "등록자는 필수 입력값입니다.");
  }
}
