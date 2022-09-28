package com.charles.knightonline.utils;

public class FunctionUtils {

    private FunctionUtils() {
    }

    public static Integer validatePageSize(Integer size) {
        if (size <= 0 || size > 20) {
            return 1;
        }
        return size;
    }
}
