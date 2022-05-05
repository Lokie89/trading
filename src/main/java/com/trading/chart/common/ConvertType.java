package com.trading.chart.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author SeongRok.Oh
 * @since 2021/11/04
 */
@Slf4j
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

    public static String ObjectToQueryString(Object object, String... excludeFields) {
        Field[] declaredFields = object.getClass().getDeclaredFields();
        ArrayList<String> queryElements = new ArrayList<>();
        List<String> excludeFieldList = Arrays.asList(excludeFields);
        for (Field field : declaredFields) {
            field.setAccessible(true);
            try {
                if (!excludeFieldList.contains(field.getName()) && (Objects.nonNull(field.get(object)))) {
                    queryElements.add(field.getName() + "=" + field.get(object));
                }
            } catch (IllegalAccessException e) {
                log.debug("Reflection Error. {}", e);
            }
        }

        return String.join("&", queryElements.toArray(new String[0]));
    }
}
