package mybatisAnnioation.utils;

import org.apache.ibatis.jdbc.SQL;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: XMind
 * @description
 * @author: yihang
 * @create: 2020-07-31 22:22
 **/
public class SQLUtils {


    // where 关键字 and关键字
    private  static final int IGNORE_WHERE = 5;
    private static  final int IGNORE_AND = 3;
    private static  final int IGNORE_VALUES = 6;
    /***
     * 替换参数信息
     * #{param} ---> ？
     */

    public static String replaceParam(String sql , List<Object> params){
        for ( int i = 0 ; i < params.size() ; i ++ ) {
            // 获取参数名
            Object paramName = params.get(i);
            // 替换信息
            sql = sql.replace("#{" + paramName +"}","?" );
        }
        return sql;
    }

    /***
     * 提供不同的重载方案
     * @param sql
     * @param parameterName
     * @return
     */
    public static String replaceParam(String sql, String[] parameterName) {
        for (int i = 0; i < parameterName.length; i++) {
            String string = parameterName[i].trim();
            sql = sql.replace("#{" + string + "}", "?");
        }
        return sql;
    }


    /***
     *
     *获取sql的中的参数信息名
     * sql形式：select id,username from stu where id = #{id} and username = #{name}
     * @param sql
     * @return
     */
    public static List<Object> getSelectParams(String sql) {
        // 存放信息
        List<Object> paramList = new ArrayList<>();
        //定位到where的位置信息
        int startIndex = sql.indexOf("where");
        // 跳过where关键字 截取到where关键字的内容信息
        String whereAfter = sql.substring(startIndex + IGNORE_WHERE);
        // 通过and分隔
        String  [] params = whereAfter.split("and");
        for(String param : params) {
            // id = #{id}
            // 通过定位#{ 和}的位置来截取信息
            int start = param.indexOf("{");
            int end = param.indexOf("}");
            String paramName = param.substring(start + 1 , end);
            System.out.println(paramName.trim());
            //加入到集合 利用trim避免多用空信息
            paramList.add(paramName.trim());
        }
        return paramList;
    }

    /***
     * 处理insert的sql语句信息
     * @param insertSql
     * @return
     */
    public static String[] getInsertParams(String insertSql) {
        int startIndex = insertSql.indexOf("values");
        insertSql = insertSql.substring(startIndex + IGNORE_VALUES);
        //(#{id},#{username})
        // 转变为 id,username
        insertSql = insertSql.replaceAll("#\\{","")
                .replaceAll("\\}","")
                .replaceAll("\\(","").replaceAll("\\)","");
        // 注意此处，截取后的信息会多个连个空格信息！！！！ 因为用空字符进行了替换
        System.out.println(insertSql);
        return insertSql.split(",");
    }
}
