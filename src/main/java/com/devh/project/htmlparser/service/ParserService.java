package com.devh.project.htmlparser.service;

import com.devh.project.htmlparser.exception.JsoupException;
import com.devh.project.htmlparser.exception.ParserServiceException;
import com.devh.project.htmlparser.repository.JsoupRepository;
import com.devh.project.htmlparser.util.ExceptionUtils;
import com.devh.project.htmlparser.vo.ParserRequestVO;
import com.devh.project.htmlparser.vo.ParserResponseVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <pre>
 * Description : 
 *     사용자 요청에 맞는 파싱을 수행하는 객체
 * ===============================================
 * Member fields : 
 *     
 * ===============================================
 * 
 * Author : HeonSeung Kim
 * Date   : 2022. 7. 14.
 * </pre>
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ParserService {
    private final JsoupRepository jsoupRepository;

    /**
     * <pre>
     * Description
     *     요청객체에 맞게 파싱을 수행
     * ===============================================
     * Parameters
     *     ParserRequestVO parserRequestVO
     * Returns
     *     ParserResponseVO
     * Throws
     *     JsoupException
     *     ParserServiceException
     * ===============================================
     *
     * Author : HeonSeung Kim
     * Date   : 2022. 7. 14.
     * </pre>
     */
    public ParserResponseVO parse(ParserRequestVO parserRequestVO) throws JsoupException, ParserServiceException {
        final String rawString = jsoupRepository.findRawStringByUrlAndType(parserRequestVO.getUrl(), parserRequestVO.getType());

        try {
            final String sortedString = sort(rawString);
            final int bunch = parserRequestVO.getBunch();

            return calculateBunchesAndRemainder(sortedString, bunch);
        } catch (Exception e) {
            log.error(ExceptionUtils.stackTraceToString(e));
            throw new ParserServiceException(e.getMessage());
        }
    }

    /**
     * <pre>
     * Description
     *     영문과 숫자를 정렬
     * ===============================================
     * Parameters
     *     String rawString
     * Returns
     *     String
     * Throws
     *
     * ===============================================
     *
     * Author : HeonSeung Kim
     * Date   : 2022. 7. 15.
     * </pre>
     */
    private String sort(String rawString) {

        /* 중복된 데이터를 위해 ArrayList 사용 */
        List<String> alphabets = new ArrayList<>();
        List<Integer> numbers = new ArrayList<>();

        /* 받아온 문자열을 영문과 숫자로 분리 */
        Arrays.stream(rawString.split("")).forEach(s -> {
            try {
                int number = Integer.parseInt(s);
                numbers.add(number);
            } catch (Exception e) {
                alphabets.add(s);
            }
        });

        /* 알파벳을 오름차순으로 정렬 */
        alphabets.sort((o1, o2) -> {
            if(o1.equalsIgnoreCase(o2)) {
                if(o1.equals(o2))
                    return 0;
                else if(o1.toLowerCase().equals(o1))
                    return 1;
                else
                    return -1;
            } else {
                return o1.compareToIgnoreCase(o2);
            }
        });

        /* 숫자를 오름차순으로 정렬 */
        numbers.sort((o1, o2) -> {
            if(o1.equals(o2)) {
                return 0;
            } else if (o1 > o2) {
                return 1;
            } else {
                return -1;
            }
        });

        final int alphabetsSize = alphabets.size();
        final int numbersSize = numbers.size();

        StringBuilder sbSorted = new StringBuilder();
        for(int i = 0 ; i < Math.max(alphabetsSize, numbersSize) ; ++i) {
            if(i < alphabetsSize)
                sbSorted.append(alphabets.get(i));
            if(i < numbersSize)
                sbSorted.append(numbers.get(i));
        }

        return sbSorted.toString();
    }

    /**
     * <pre>
     * Description
     *     졍렬된 결과를 몫으로 나누어 결과를 가공
     * ===============================================
     * Parameters
     *     String sortedString
     *     int bunch
     * Returns
     *     ParserResponseVO
     * Throws
     *
     * ===============================================
     *
     * Author : HeonSeung Kim
     * Date   : 2022. 7. 15.
     * </pre>
     */
    private ParserResponseVO calculateBunchesAndRemainder(String sortedString, int bunch) {
        /* 만약에라도 동일하게 반복되는 내용이 있는 경우를 위해 중복을 허용하는 자료구조 사용 */
        final List<String> bunches = new ArrayList<>();

        final int length = sortedString.length();
        final int loopCount = length / bunch;
        int index = 0;

        for(int i = 0 ; i < loopCount ; ++i) {
            bunches.add(sortedString.substring(index, index += bunch));
        }

        return ParserResponseVO.builder()
                .bunches(bunches)
                .remainder(sortedString.substring(index, length))
                .build();
    }
}
