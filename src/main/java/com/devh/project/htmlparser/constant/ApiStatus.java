package com.devh.project.htmlparser.constant;

import lombok.Getter;

/**
 * <pre>
 * Description :
 *     API 결과 상태 관련 상수를 갖는 클래스
 * ===============================================
 * Member fields :
 *     Success
 *     ClientError
 *     ServerError
 *     CustomError
 * ===============================================
 *
 * Author : HeonSeung Kim
 * Date   : 2022. 7. 14.
 * </pre>
 */
public class ApiStatus {
    @Getter
    public enum Success {
        OK("Ok", 200, "Standard response for successful HTTP requests.");

        private final String status;
        private final int code;
        private final String description;

        Success(String status, int code, String description) {
            this.status = status;
            this.code = code;
            this.description = description;
        }
    }

    @Getter
    public enum ServerError {
        INTERNAL_SERVER_ERROR("Internal Server Error", 500, "Unexpected condition was encountered.");

        private final String status;
        private final int code;
        private final String description;

        ServerError(String status, int code, String description) {
            this.status = status;
            this.code = code;
            this.description = description;
        }
    }

    @Getter
    public enum CustomError {
        PARSER_SERVICE_ERROR("Parser Service Error", 800, "Failed to execute parser service."),
        JSOUP_ERROR("Jsoup Error", 801, "Exception occurred while using Jsoup.");

        private final String status;
        private final int code;
        private final String description;

        CustomError(String status, int code, String description) {
            this.status = status;
            this.code = code;
            this.description = description;
        }
    }
}
