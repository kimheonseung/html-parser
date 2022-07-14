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
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ParserServiceTests {
    @SpyBean
    private JsoupRepository jsoupRepository;
    @Autowired
    private ParserService parserService;

    @BeforeEach
    public void before() {
        Mockito.reset(jsoupRepository);
    }

    @Nested
    @DisplayName("성공 케이스")
    class Success {
        @Test
        public void parse() throws JsoupException, ParserServiceException {
            /* given */
            final String givenUrl = "http://www.naver.com";
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

    }
}
