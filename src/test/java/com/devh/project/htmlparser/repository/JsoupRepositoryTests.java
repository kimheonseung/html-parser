package com.devh.project.htmlparser.repository;

import com.devh.project.htmlparser.constant.Type;
import com.devh.project.htmlparser.exception.JsoupException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class JsoupRepositoryTests {
    @Autowired
    private JsoupRepository jsoupRepository;

    @Nested
    @DisplayName("성공 케이스")
    class Success {
        @Test
        public void findRawStringByUrlAndTypeHTML() {
            /* given */
            final String givenUrl = "http://www.naver.com";
            final Type givenType = Type.HTML;
            /* then */
            assertDoesNotThrow(() -> jsoupRepository.findRawStringByUrlAndType(givenUrl, givenType));
        }
        @Test
        public void findRawStringByUrlAndTypeTEXT() {
            /* given */
            final String givenUrl = "http://www.naver.com";
            final Type givenType = Type.TEXT;
            /* then */
            assertDoesNotThrow(() -> jsoupRepository.findRawStringByUrlAndType(givenUrl, givenType));
        }
    }

    @Nested
    @DisplayName("실패 케이스")
    class Fail {
        @Test
        public void findRawStringByUrlAndTypeHTML() {
            /* given */
            final String givenUrl = "http://in.valid.url.!@4.com";
            final Type givenType = Type.HTML;
            /* then */
            assertThrows(JsoupException.class, () -> jsoupRepository.findRawStringByUrlAndType(givenUrl, givenType));
        }
        @Test
        public void findRawStringByUrlAndTypeTEXT() {
            /* given */
            final String givenUrl = "http://in.valid.url.!@4.com";
            final Type givenType = Type.TEXT;
            /* then */
            assertThrows(JsoupException.class, () -> jsoupRepository.findRawStringByUrlAndType(givenUrl, givenType));
        }
    }
}
