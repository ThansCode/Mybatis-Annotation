package mybatisAnnioation.mapper;


import mybatisAnnioation.annotation.Insert;
import mybatisAnnioation.annotation.Param;
import mybatisAnnioation.annotation.Select;
import mybatisAnnioation.bean.User;

/**
 * @program: XMind
 * @description
 * @author: yihang
 * @create: 2020-07-31 21:49
 **/
public interface UserMapper {

    // 查询信息

    @Select("select id,username from stu where id = #{id} and username = #{name}")
    User selectUser(@Param("id") Integer id, @Param("name") String name);

    // 插入数据信息

    @Insert("insert into stu(id,username) values (#{id},#{username})")
    int insertUser(@Param("id") Integer id, @Param(value = "username") String username);


}
