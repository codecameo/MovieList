package com.example.hp.assessment.utils;

import java.util.List;

/**
 * Created by Md. Sifat-Ul Haque on 12/01/2018.
 */

public class ListUtils {
    public static final boolean isEmpty(List<?> list){
        return list == null || list.size() == 0;
    }
}
