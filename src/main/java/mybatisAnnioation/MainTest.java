package mybatisAnnioation;

import mybatisAnnioation.annotation.Param;
import mybatisAnnioation.bean.Only;
import mybatisAnnioation.bean.User;
import mybatisAnnioation.mapper.UserMapper;
import mybatisAnnioation.sqlSession.SqlSession;
import mybatisAnnioation.utils.JDBCUtils;
import org.apache.ibatis.annotations.One;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.sql.Connection;

/**
 * @program: XMind
 * @description
 * @author: yihang
 * @create: 2020-07-31 21:59
 **/
public class MainTest {

    public static void main(String[] args) {


        String sql = "insert into stu(username) values (#{id},#{username})";
        //spliInserSql(sql);
        //insertTest();
        select();
    }
    // 测试连接状态

    private static void getConnection ( ) {
        Connection con =  JDBCUtils.getConnection();
        if ( con != null) {
            System.out.println(con);
        }
    }
    //测试-1 测试数据查询

    public static void select(){
        UserMapper userMapper = SqlSession.getUserMapper(UserMapper.class);
        User user = userMapper.selectUser(1,"张三");
        System.out.println(user);
    }

    //测试-2 测试插入查询

    public static void insertTest(){
        UserMapper userMapper = SqlSession.getUserMapper(UserMapper.class);
        int count =  userMapper.insertUser(23,"张三");
        System.out.println(count);
    }
    // 测试获取方法注解信息

    public static void getMethods(){
        // 处理接口的方法信息
        Class<?> clazz =UserMapper.class;
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            System.out.println(method.getName());
            Parameter[] parameters = method.getParameters();
            Annotation[][] annotations = method.getParameterAnnotations();
            for( int i = 0 ; i < parameters.length ; i ++ ) {
                for ( Annotation annotation : annotations[i]){
                    if ( annotation instanceof Param) {
                        String key = ((Param)annotation).value();
                        System.out.println("method  " + method + key);
                    }
                }
            }
        }
    }

    // 获取字段信息

    private void getFieldType(){
        Class<?> returnTypeClazz = User.class;
        Field[] fields = returnTypeClazz.getDeclaredFields();
        for ( int i = 0 ; i <fields.length ; i ++) {
            System.out.println(fields[i].getGenericType().toString());
        }
    }

    // 处理insert插入语句

    private static void spliInserSql(String sql){
        int startIndex = sql.indexOf("values");
        sql = sql.substring(startIndex + 6);

        System.out.println(sql.replaceAll("#\\{","")
                .replaceAll("\\}","")
        .replaceAll("\\(","").replaceAll("\\)",""));
    }
}
