package com.devh.project.htmlparser.service;

import com.devh.project.htmlparser.constant.Type;
import com.devh.project.htmlparser.exception.JsoupException;
import com.devh.project.htmlparser.exception.ParserServiceException;
import com.devh.project.htmlparser.repository.JsoupRepository;
import com.devh.project.htmlparser.vo.ParserRequestVO;
import com.devh.project.htmlparser.vo.ParserResponseVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ExtendWith(MockitoExtension.class)
public class ParserServiceTests {
    @SpyBean
    private JsoupRepository jsoupRepository;
    @Autowired
    private ParserService parserService;

    @LocalServerPort
    private int port;

    @BeforeEach
    public void before() {
        Mockito.reset(jsoupRepository);
    }

    @Nested
    @DisplayName("성공 케이스")
    class Success {
        @Test
        @DisplayName("특정 url, 'HTML 태그 제외' 옵션을 선택한 경우 - 문자열을 Mocking")
        public void parseWithMock() throws JsoupException, ParserServiceException {
            /* given */
            final String givenUrl = "http://localhost:"+port;
            final Type givenType = Type.TEXT;
            final int givenBunch = 5;
            final String givenRawString = "A2ors3144aaAbdD10";
            // [A0A1a, 1a2b3, D4d4o] ... rs
            final ParserRequestVO givenParserRequestVO = new ParserRequestVO();
            givenParserRequestVO.setUrl(givenUrl);
            givenParserRequestVO.setType(givenType);
            givenParserRequestVO.setBunch(givenBunch);
            given(jsoupRepository.findRawStringByUrlAndType(givenUrl, givenType)).willReturn(givenRawString);

            /* when */
            ParserResponseVO parserResponseVO = parserService.parse(givenParserRequestVO);
            List<String> bunches = parserResponseVO.getBunches();

            /* then */
            assertEquals(bunches.get(0), "A0A1a");
            assertEquals(bunches.get(1), "1a2b3");
            assertEquals(bunches.get(2), "D4d4o");
            assertEquals(parserResponseVO.getRemainder(), "rs");
        }
        @Test
        @DisplayName("특정 url, '전체' 옵션을 선택한 경우 - 현 웹서버로부터 test.html 가공된 문자열 반환")
        public void parseWithTestHTML() throws JsoupException, ParserServiceException {
            /* given */
            final String givenUrl = "http://localhost:"+port+"/test";
            final Type givenType = Type.HTML;
            final int givenBunch = 5;
            final ParserRequestVO givenParserRequestVO = new ParserRequestVO();
            givenParserRequestVO.setUrl(givenUrl);
            givenParserRequestVO.setType(givenType);
            givenParserRequestVO.setBunch(givenBunch);

            /* when */
            ParserResponseVO parserResponseVO = parserService.parse(givenParserRequestVO);
            List<String> bunches = parserResponseVO.getBunches();

            /* then */
            assertEquals(bunches.get(0), "a1a1a");
            assertEquals(bunches.get(1), "1a2a2");
            assertEquals(bunches.get(2), "a2b2b");
            assertEquals(bunches.get(3), "2c2d3");
            assertEquals(bunches.get(4), "d3d3d");
            assertEquals(bunches.get(5), "5d5e6");
            assertEquals(bunches.get(6), "e7e7e");
            assertEquals(bunches.get(7), "7e8e8");
            assertEquals(bunches.get(8), "e8e8e");
            assertEquals(bunches.get(9), "8F9g9");
            assertEquals(bunches.get(10), "H9Hhh");
            assertEquals(bunches.get(11), "hhhhh");
            assertEquals(bunches.get(12), "hhiiL");
            assertEquals(bunches.get(13), "lllll");
            assertEquals(bunches.get(14), "lllMm");
            assertEquals(bunches.get(15), "mmnno");
            assertEquals(bunches.get(16), "oooPr");
            assertEquals(bunches.get(17), "rrrss");
            assertEquals(bunches.get(18), "TTttt");
            assertEquals(bunches.get(19), "ttttt");
            assertEquals(parserResponseVO.getRemainder(), "UWyy");
        }

        @Test
        @DisplayName("특정 url, 'HTML 태그 제외' 옵션을 선택한 경우 - 현 웹서버로부터 test.html 가공된 문자열 반환")
        public void parseWithTestTEXT() throws JsoupException, ParserServiceException {
            /* given */
            final String givenUrl = "http://localhost:"+port+"/test";
            final Type givenType = Type.TEXT;
            final int givenBunch = 5;
            final ParserRequestVO givenParserRequestVO = new ParserRequestVO();
            givenParserRequestVO.setUrl(givenUrl);
            givenParserRequestVO.setType(givenType);
            givenParserRequestVO.setBunch(givenBunch);

            /* when */
            ParserResponseVO parserResponseVO = parserService.parse(givenParserRequestVO);
            List<String> bunches = parserResponseVO.getBunches();

            /* then */
            assertEquals(bunches.get(0), "a1d2e");
            assertEquals(bunches.get(1), "2e2H2");
            assertEquals(bunches.get(2), "H3L3l");
            assertEquals(bunches.get(3), "3l5l5");
            assertEquals(bunches.get(4), "M6o7o");
            assertEquals(bunches.get(5), "7P7r8");
            assertEquals(bunches.get(6), "r8r8s");
            assertEquals(bunches.get(7), "8T9W9");
            assertEquals(parserResponseVO.getRemainder(), "9");
        }
    }
}
