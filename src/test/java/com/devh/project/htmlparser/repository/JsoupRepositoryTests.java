package com.devh.project.htmlparser.repository;

import com.devh.project.htmlparser.constant.Type;
import com.devh.project.htmlparser.exception.JsoupException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class JsoupRepositoryTests {
    @Autowired
    private JsoupRepository jsoupRepository;

    private final String givenTestHtml = ""
    		+ "<!DOCTYPE html>\r\n"
    		+ "<html lang=\"en\">\r\n"
    		+ "<head>\r\n"
    		+ "    <meta charset=\"UTF-8\">\r\n"
    		+ "    <title>HTML Parser</title>\r\n"
    		+ "</head>\r\n"
    		+ "<body>\r\n"
    		+ "<h1>Hello World !</h1>\r\n"
    		+ "<h2>982735892387126875329</h2>\r\n"
    		+ "</body>\r\n"
    		+ "</html>";
    
    private final String rawStringText    = "HelloWorld982735892387126875329";
    private final String answerStringText = "d1e2H2l2l2l3o3o3r5W567778888999";
    private final String rawStringHtml    = "htmllangenheadmetacharsetUTF8titleHTMLParsertitleheadbodyh1HelloWorldh1h2982735892387126875329h2bodyhtml";
    private final String answerStringHtml = "a1a1a1a2a2a2b2b2C2c3D3d3d5d5d6d7E7e7e8e8e8e8e8e9e9e9FgHHhhhhhhhhhhiiLlllllllllMmmmmnnOooooPPrrrrssTTTtttttttttUWYyy";
    
    
    @Nested
    @DisplayName("성공 케이스")
    class Success {
        @Test
        public void findRawStringByUrlAndTypeHTML() throws JsoupException {
            /* given */
            final String givenUrl = "http://localhost:8080/test";
            final Type givenType = Type.HTML;
            /* then */
            assertDoesNotThrow(() -> jsoupRepository.findRawStringByUrlAndType(givenUrl, givenType));
            String rawString = jsoupRepository.findRawStringByUrlAndType(givenUrl, givenType);
            assertNotNull(rawString);
            assertEquals(rawString, rawStringHtml);
        }
        @Test
        public void findRawStringByUrlAndTypeTEXT() throws JsoupException {
            /* given */
            final String givenUrl = "http://www.naver.com";
            final Type givenType = Type.TEXT;
            /* then */
            assertDoesNotThrow(() -> jsoupRepository.findRawStringByUrlAndType(givenUrl, givenType));
            assertNotNull(jsoupRepository.findRawStringByUrlAndType(givenUrl, givenType));
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
