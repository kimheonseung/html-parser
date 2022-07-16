package com.devh.project.htmlparser.util;

import com.devh.project.htmlparser.exception.JsoupException;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/* test.html 반환을 위해 랜덤포트 개방 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class JsoupUtilsTests {
    @Autowired
    JsoupUtils jsoupUtils;

    @LocalServerPort
    int port;

    @Nested
    @DisplayName("성공 케이스")
    class Success {
        @Test
        @DisplayName("정상적인 url 입력")
        public void findDocumentByUrl_validUrl() {
            /* given */
            final String givenUrl = "http://localhost:"+port+"/test";
            /* then */
            assertDoesNotThrow(() -> jsoupUtils.findDocumentByUrl(givenUrl));
        }

        @Test
        @DisplayName("정상적인 url 입력 후 Document 반환")
        public void findDocumentByUrl_document() throws JsoupException {
            /* given */
            final String givenUrl = "http://localhost:"+port+"/test";
            /* when */
            Document document = jsoupUtils.findDocumentByUrl(givenUrl);
            /* then */
            assertNotNull(document);
        }
    }

    @Nested
    @DisplayName("실패 케이스")
    class Fail {
        @Test
        @DisplayName("비 정상적인 url 입력")
        public void findDocumentByUrl_validUrl() {
            /* given */
            final String givenUrl = "http://localhost:"+port+"/thisIsInvalidUrl";
            /* then */
            assertThrows(JsoupException.class, () -> jsoupUtils.findDocumentByUrl(givenUrl));
        }
    }
}
