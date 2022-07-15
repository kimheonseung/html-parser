package com.devh.project.htmlparser.controller;

import com.devh.project.htmlparser.constant.ApiStatus;
import com.devh.project.htmlparser.constant.Type;
import com.devh.project.htmlparser.exception.ParserServiceException;
import com.devh.project.htmlparser.repository.JsoupRepository;
import com.devh.project.htmlparser.service.ParserService;
import com.devh.project.htmlparser.vo.ApiResponseVO;
import com.devh.project.htmlparser.vo.ParserRequestVO;
import com.devh.project.htmlparser.vo.ParserResponseVO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class ParserControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext ctx;
    @Autowired
    private ObjectMapper objectMapper;

    @SpyBean
    private ParserService parserService;
    @SpyBean
    private JsoupRepository jsoupRepository;

    @Value("${server.port}")
    private int port;

    @BeforeEach
    public void before() {
        Mockito.reset(parserService);
        Mockito.reset(jsoupRepository);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
//                .alwaysDo(print())
                .build();
    }

    @Nested
    @DisplayName("성공 케이스")
    class Success {
        @Test
        @DisplayName("특정 url, 'HTML 태그 제외' 옵션 선택한 경우")
        public void parseTEXT() throws Exception {
            /* given */
            final String givenUrl = "http://localhost:"+port+"/test";
            final Type givenType = Type.TEXT;
            final int givenBunch = 5;
            final ParserRequestVO parserRequestVO = new ParserRequestVO();
            parserRequestVO.setUrl(givenUrl);
            parserRequestVO.setType(givenType);
            parserRequestVO.setBunch(givenBunch);

            /* when */
            final String content = objectMapper.writeValueAsString(parserRequestVO);
            MvcResult result = mockMvc.perform(post("/parser")
                    .content(content)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

            String resultContent = result.getResponse().getContentAsString();
            ApiResponseVO<ParserResponseVO> apiResponse = objectMapper.readValue(resultContent, new TypeReference<>() {});
            int status = apiResponse.getStatus();
            List<ParserResponseVO> dataList = apiResponse.getDataArray();
            List<String> bunches = dataList.get(0).getBunches();
            String remainder = dataList.get(0).getRemainder();

            /* then */
            assertEquals(status, ApiStatus.Success.OK.getCode());
            assertNotNull(dataList);
            assertEquals(bunches.get(0), "a1d2e");
            assertEquals(bunches.get(1), "2e2H2");
            assertEquals(bunches.get(2), "H3L3l");
            assertEquals(bunches.get(3), "3l5l5");
            assertEquals(bunches.get(4), "M6o7o");
            assertEquals(bunches.get(5), "7P7r8");
            assertEquals(bunches.get(6), "r8r8s");
            assertEquals(bunches.get(7), "8T9W9");
            assertEquals(remainder, "9");
        }
        @Test
        @DisplayName("특정 url, '전체' 옵션 선택한 경우")
        public void parseHTML() throws Exception {
            /* given */
            final String givenUrl = "http://localhost:"+port+"/test";
            final Type givenType = Type.HTML;
            final int givenBunch = 5;
            final ParserRequestVO parserRequestVO = new ParserRequestVO();
            parserRequestVO.setUrl(givenUrl);
            parserRequestVO.setType(givenType);
            parserRequestVO.setBunch(givenBunch);

            /* when */
            final String content = objectMapper.writeValueAsString(parserRequestVO);
            MvcResult result = mockMvc.perform(post("/parser")
                    .content(content)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

            String resultContent = result.getResponse().getContentAsString();
            ApiResponseVO<ParserResponseVO> apiResponse = objectMapper.readValue(resultContent, new TypeReference<>() {});
            int status = apiResponse.getStatus();
            List<ParserResponseVO> dataList = apiResponse.getDataArray();
            List<String> bunches = dataList.get(0).getBunches();
            String remainder = dataList.get(0).getRemainder();

            /* then */
            assertEquals(status, ApiStatus.Success.OK.getCode());
            assertNotNull(dataList);
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
            assertEquals(remainder, "UWyy");
        }
    }

    @Nested
    @DisplayName("실패 케이스")
    class Fail {
        @Test
        @DisplayName("잘못된 URL 입력")
        public void invalidURL() throws Exception {
            /* given */
            final String givenUrl = "http://!nva!du.rl.c2wa";
            final Type givenType = Type.HTML;
            final int givenBunch = 5;
            final ParserRequestVO parserRequestVO = new ParserRequestVO();
            parserRequestVO.setUrl(givenUrl);
            parserRequestVO.setType(givenType);
            parserRequestVO.setBunch(givenBunch);

            /* when */
            final String content = objectMapper.writeValueAsString(parserRequestVO);
            MvcResult result = mockMvc.perform(post("/parser")
                    .content(content)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
            )
                    .andExpect(status().isOk())
                    .andReturn();

            String resultContent = result.getResponse().getContentAsString();
            ApiResponseVO<ApiStatus.CustomError> apiResponse = objectMapper.readValue(resultContent, new TypeReference<>() {});
            int status = apiResponse.getStatus();
            assertEquals(status, ApiStatus.CustomError.JSOUP_ERROR.getCode());
        }
        @Test
        @DisplayName("파싱 도중 예외")
        public void parsingException() throws Exception {
            /* given */
            final String givenUrl = "http://localhost:"+port+"/test";
            final Type givenType = Type.HTML;
            final int givenBunch = 5;
            final ParserRequestVO parserRequestVO = new ParserRequestVO();
            parserRequestVO.setUrl(givenUrl);
            parserRequestVO.setType(givenType);
            parserRequestVO.setBunch(givenBunch);
            given(jsoupRepository.findRawStringByUrlAndType(givenUrl, givenType)).willReturn("meaningless_string");
            given(parserService.parse(parserRequestVO)).willAnswer(invocation -> {
                throw new ParserServiceException("test");
            });
            /* when */
            final String content = objectMapper.writeValueAsString(parserRequestVO);
            MvcResult result = mockMvc.perform(post("/parser")
                    .content(content)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
            )
                    .andExpect(status().isOk())
                    .andReturn();

            String resultContent = result.getResponse().getContentAsString();
            ApiResponseVO<ApiStatus.CustomError> apiResponse = objectMapper.readValue(resultContent, new TypeReference<>() {});
            int status = apiResponse.getStatus();
            assertEquals(status, ApiStatus.CustomError.PARSER_SERVICE_ERROR.getCode());
        }
    }
}
