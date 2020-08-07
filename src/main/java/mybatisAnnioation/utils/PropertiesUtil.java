package mybatisAnnioation.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/****
 * 装载配置信息
 *
 * prop文件的初始化借助于
 * 1. 实例化对象
 * 2. 通过load装载内容信息
 */

/**
 * @program: XMind
 * @description
 * @author: yihang
 * @create: 2020-07-22 17:58
 **/
public class PropertiesUtil {


    private static Properties props;

    // 静态初始化
    static {
        String fileName = "JdbcConfig.properties";
        // 实例化一个对象后，装载内容
        props = new Properties();
        InputStream inputStream = PropertiesUtil.class.getClassLoader().getResourceAsStream(fileName);
        try {
            props.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String getProperty(String key,String defaultValue){

        String value = props.getProperty(key.trim());
        if(StringUtils.isBlank(value)){
            value = defaultValue;
        }
        return value.trim();
    }


    private static String getProperty(String key) {
        String value = props.getProperty(key.trim());
        if (StringUtils.isBlank(value)) {
            return null;
        }
        return value.trim();
    }


}
