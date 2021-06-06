package com.carson.order.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ClassName Result
 *
 * @author carson
 * @description
 * @Version V1.0
 * @createTime
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {

    private Object data;
    private Meta meta;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Meta {
        private int code;
        private String message;
    }


    public static Result ok(Object data) {
        return new Result(data, new Meta(200, ""));
    }

    public static Result ok() {
        return new Result(null, new Meta(200, ""));
    }

    public static Result error(int code, String message) {
        return new Result(null, new Meta(code, message));
    }
}