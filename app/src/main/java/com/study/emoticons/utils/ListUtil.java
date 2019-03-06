package com.study.emoticons.utils;

import java.util.List;

public class ListUtil {

    /**
     * 判断list是否为空
     * @param list
     * @return
     */
    public static boolean isEmpty(List<?> list){
        return list == null||list.isEmpty();
    }
}
