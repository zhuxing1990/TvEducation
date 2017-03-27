package com.vunke.education.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DataPosttingUtil {

    public static List<Map<String,Object>> posttingList(int[] recommendpicture, String[] recommendptext){

        List<Map<String,Object>> datalists = new ArrayList<Map<String, Object>>();
        for (int i=0;i< recommendpicture.length;i++){

            Map<String,Object> map = new HashMap<String,Object>();
            map.put("image",recommendpicture[i]);
            map.put("name",recommendptext[i]);

            datalists.add(map);
        }



        return datalists;
    }

}
