package com.devh.project.htmlparser.repository;

import com.devh.project.htmlparser.constant.Type;
import com.devh.project.htmlparser.exception.JsoupException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/* 실제 현 서버로 test.html 내용을 요청하여 테스트 수행 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class JsoupRepositoryTests {
    @Autowired
    private JsoupRepository jsoupRepository;

    @LocalServerPort
    private int port;

    @Nested
    @DisplayName("JsoupRepository 성공 케이스")
    class Success {
        @Test
        @DisplayName("특정 url, '전체' 옵션을 선택한 경우 URL 정상 케이스")
        public void findRawStringByUrlAndTypeHTML_validUrl() {
            /* given */
            final String givenUrl = "http://localhost:"+port+"/test";
            final Type givenType = Type.HTML;
            /* then */
            assertDoesNotThrow(() -> jsoupRepository.findRawStringByUrlAndType(givenUrl, givenType));
        }
        @Test
        @DisplayName("특정 url, '전체' 옵션을 선택한 경우 반환되는 문자열 테스트")
        public void findRawStringByUrlAndTypeHTML() throws JsoupException {
            /* given */
            final String givenUrl = "http://localhost:"+port+"/test";
            final Type givenType = Type.HTML;
            /* when */
            String rawString = jsoupRepository.findRawStringByUrlAndType(givenUrl, givenType);
            /* then */
            assertNotNull(rawString);
            assertEquals(rawString, "htmllangenheadmetacharsetUTF8titleHTMLParsertitleheadbodyh1HelloWorldh1h2982735892387126875329h2bodyhtml");
        }
        @Test
        @DisplayName("특정 url, 'HTML 태그 제외' 옵션을 선택한 경우 URL 정상 케이스")
        public void findRawStringByUrlAndTypeTEXT_validUrl() {
            /* given */
            final String givenUrl = "http://localhost:"+port+"/test";
            final Type givenType = Type.TEXT;
            /* then */
            assertDoesNotThrow(() -> jsoupRepository.findRawStringByUrlAndType(givenUrl, givenType));
        }
        @Test
        @DisplayName("특정 url, 'HTML 태그 제외' 옵션을 선택한 경우 반환되는 문자열 테스트")
        public void findRawStringByUrlAndTypeTEXT() throws JsoupException {
            /* given */
            final String givenUrl = "http://localhost:"+port+"/test";
            final Type givenType = Type.TEXT;
            /* when */
            String rawString = jsoupRepository.findRawStringByUrlAndType(givenUrl, givenType);
            /* then */
            assertNotNull(rawString);
            assertEquals(rawString, "HTMLParserHelloWorld982735892387126875329");
        }
    }

    @Nested
    @DisplayName("실패 케이스")
    class Fail {
        @Test
        @DisplayName("잘못된 URL, '전체' 옵션을 선택")
        public void findRawStringByUrlAndTypeHTML() {
            /* given */
            final String givenUrl = "http://in.valid.url.!@4.com";
            final Type givenType = Type.HTML;
            /* then */
            assertThrows(JsoupException.class, () -> jsoupRepository.findRawStringByUrlAndType(givenUrl, givenType));
        }
        @Test
        @DisplayName("잘못된 URL, 'HTML 태그 제외' 옵션을 선택")
        public void findRawStringByUrlAndTypeTEXT() {
            /* given */
            final String givenUrl = "http://in.valid.url.!@4.com";
            final Type givenType = Type.TEXT;
            /* then */
            assertThrows(JsoupException.class, () -> jsoupRepository.findRawStringByUrlAndType(givenUrl, givenType));
        }
    }
}
