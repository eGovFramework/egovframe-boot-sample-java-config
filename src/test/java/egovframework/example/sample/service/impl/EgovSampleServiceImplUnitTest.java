package egovframework.example.sample.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import egovframework.example.sample.service.SampleVO;

/**
 * EgovSampleServiceImpl의 순수 단위 테스트.
 * <p>
 * Spring 컨텍스트 로딩 없이 Mockito를 사용하여 Service의 로직만 검증합니다.
 * {@link ExtendWith(MockitoExtension.class)}: Mockito 기능을 JUnit 5 테스트에 활성화합니다.
 * {@link Mock}: 가짜(Mock) 객체를 생성합니다. 여기서는 DB와 연동하는 Mapper를 가짜로 만듭니다.
 * {@link InjectMocks}: Mock 객체를 실제 테스트 대상 객체에 주입합니다.
 */
@ExtendWith(MockitoExtension.class)
class EgovSampleServiceImplUnitTest {

    @InjectMocks
    private EgovSampleServiceImpl egovSampleService;

    @Mock
    private SampleMapper sampleMapper;

    @Test
    @DisplayName("ID로 게시글 조회 시, Mapper가 반환한 값을 그대로 반환해야 한다")
    void selectSample_shouldReturnSampleVO_whenDataExists() throws Exception {
        // given (준비)
        // 1. Mapper가 반환할 가상의 결과 데이터 생성
        SampleVO expectedResult = new SampleVO();
        expectedResult.setId("SAMPLE-00001");
        expectedResult.setName("Mockito 테스트");

        // 2. Mock Mapper의 동작 정의: selectSample 메서드가 어떤 SampleVO 객체로 호출되든(any()) 위에서 만든 결과를 반환하도록 설정
        when(sampleMapper.selectSample(any(SampleVO.class))).thenReturn(expectedResult);

        // when (실행)
        SampleVO actualResult = egovSampleService.selectSample(new SampleVO());

        // then (검증)
        assertNotNull(actualResult);
        assertEquals(expectedResult.getId(), actualResult.getId(), "ID가 일치해야 합니다.");
        assertEquals(expectedResult.getName(), actualResult.getName(), "이름이 일치해야 합니다.");
    }
}