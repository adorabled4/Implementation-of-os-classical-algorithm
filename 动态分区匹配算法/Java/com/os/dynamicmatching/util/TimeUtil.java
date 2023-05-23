package com.os.dynamicmatching.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author adorabled4
 * @className TimeUtil
 * @date : 2023/05/13/ 16:45
 **/
public class TimeUtil {
    public static String getCurrentTime(){
        //2023-5-13 16:45:55
        return new SimpleDateFormat("yyyy-MM--dd hh:mm:ss").format(new Date()) + " ";
    }
}
