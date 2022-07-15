package com.devh.project.htmlparser.repository;

import com.devh.project.htmlparser.constant.Type;
import com.devh.project.htmlparser.exception.JsoupException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.DocumentType;
import org.jsoup.nodes.Node;
import org.jsoup.parser.ParseSettings;
import org.jsoup.parser.Parser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/* 실제 현 서버로 test.html 내용을 요청하여 테스트 수행 */
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@ExtendWith(MockitoExtension.class)
public class JsoupRepositoryTests {
    @Autowired
    private JsoupRepository jsoupRepository;

    @Value("${server.port}")
    private int port;

    @Nested
    @DisplayName("Jsoup 라이브러리 테스트")
    class JsoupLibrary {
        @Nested
        @DisplayName("성공 케이스")
        class Success {
            @Test
            @DisplayName("JsoupRepository private 메소드 로직 체크 - '전체' 옵션")
            public void getTestHtml() throws IOException {
                /* given */
                final String givenUrl = "http://localhost:"+port+"/test";
                /* when */
                Document document = Jsoup.connect(givenUrl).get();
                document.childNodes()
                        .stream()
                        .filter(node -> node instanceof DocumentType)
                        .findFirst()
                        .ifPresent(Node::remove);
                String html = document.parser(Parser.htmlParser().settings(new ParseSettings(true, true))).html();
                String actualHtml = "" +
                        "<html lang=\"en\">\n" +
                        " <head>\n" +
                        "  <meta charset=\"UTF-8\">\n" +
                        "  <title>HTML Parser</title>\n" +
                        " </head>\n" +
                        " <body>\n" +
                        "  <h1>Hello World !</h1>\n" +
                        "  <h2>982735892387126875329</h2>\n" +
                        " </body>\n" +
                        "</html>";
                /* then */
                assertEquals(html, actualHtml);
            }
            @Test
            @DisplayName("JsoupRepository private 메소드 로직 체크 - 'HTML 태그 제외' 옵션")
            public void getTestText() throws IOException {
                /* given */
                final String givenUrl = "http://localhost:"+port+"/test";
                /* when */
                Document document = Jsoup.connect(givenUrl).get();
                document.childNodes()
                        .stream()
                        .filter(node -> node instanceof DocumentType)
                        .findFirst()
                        .ifPresent(Node::remove);
                String text = document.parser(Parser.htmlParser().settings(new ParseSettings(true, true))).text();
                String actualText = "HTML Parser Hello World ! 982735892387126875329";
                /* then */
                assertEquals(text, actualText);
            }
        }

        @Nested
        @DisplayName("실패 케이스")
        class Fail {
            @Test
            @DisplayName("잘못된 url 입력한 경우")
            public void invalidURL() {
                /* given */
                final String givenUrl = "http://invalid.url.32a32#WED";
                /* then */
                assertThrows(IOException.class, () -> Jsoup.connect(givenUrl).get());
            }
        }
    }

    @Nested
    @DisplayName("JsoupRepository 성공 케이스")
    class Success {
        @Test
        @DisplayName("특정 url, '전체' 옵션을 선택한 경우 반환되는 문자열 테스트")
        public void findRawStringByUrlAndTypeHTML() throws JsoupException {
            /* given */
            final String givenUrl = "http://localhost:"+port+"/test";
            final Type givenType = Type.HTML;
            /* then */
            assertDoesNotThrow(() -> jsoupRepository.findRawStringByUrlAndType(givenUrl, givenType));
            String rawString = jsoupRepository.findRawStringByUrlAndType(givenUrl, givenType);
            assertNotNull(rawString);
            assertEquals(rawString, "htmllangenheadmetacharsetUTF8titleHTMLParsertitleheadbodyh1HelloWorldh1h2982735892387126875329h2bodyhtml");
        }
        @Test
        @DisplayName("특정 url, 'HTML 태그 제외' 옵션을 선택한 경우 반환되는 문자열 테스트")
        public void findRawStringByUrlAndTypeTEXT() throws JsoupException {
            /* given */
            final String givenUrl = "http://localhost:"+port+"/test";
            final Type givenType = Type.TEXT;
            /* then */
            assertDoesNotThrow(() -> jsoupRepository.findRawStringByUrlAndType(givenUrl, givenType));
            String rawString = jsoupRepository.findRawStringByUrlAndType(givenUrl, givenType);
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
