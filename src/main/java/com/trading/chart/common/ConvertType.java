package com.trading.chart.common;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * @author SeongRok.Oh
 * @since 2021/11/04
 */
public class ConvertType {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T stringToType(String convert, Class<T> clazz) {
        T result;
        try {
            result = objectMapper.readValue(convert.getBytes(), clazz);
        } catch (IOException e) {
            e.printStackTrace();
            //TODO : Exception 정의
            throw new RuntimeException();
        }
        return result;
    }
}
