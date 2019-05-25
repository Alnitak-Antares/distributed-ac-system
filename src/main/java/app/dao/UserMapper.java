package app.dao;

import app.entity.User;
import app.entity.UserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table distributedairconditioner..user
     *
     * @mbggenerated Sun May 26 02:41:16 CST 2019
     */
    int countByExample(UserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table distributedairconditioner..user
     *
     * @mbggenerated Sun May 26 02:41:16 CST 2019
     */
    int deleteByExample(UserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table distributedairconditioner..user
     *
     * @mbggenerated Sun May 26 02:41:16 CST 2019
     */
    int deleteByPrimaryKey(Integer userid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table distributedairconditioner..user
     *
     * @mbggenerated Sun May 26 02:41:16 CST 2019
     */
    int insert(User record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table distributedairconditioner..user
     *
     * @mbggenerated Sun May 26 02:41:16 CST 2019
     */
    int insertSelective(User record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table distributedairconditioner..user
     *
     * @mbggenerated Sun May 26 02:41:16 CST 2019
     */
    List<User> selectByExample(UserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table distributedairconditioner..user
     *
     * @mbggenerated Sun May 26 02:41:16 CST 2019
     */
    User selectByPrimaryKey(Integer userid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table distributedairconditioner..user
     *
     * @mbggenerated Sun May 26 02:41:16 CST 2019
     */
    int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table distributedairconditioner..user
     *
     * @mbggenerated Sun May 26 02:41:16 CST 2019
     */
    int updateByExample(@Param("record") User record, @Param("example") UserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table distributedairconditioner..user
     *
     * @mbggenerated Sun May 26 02:41:16 CST 2019
     */
    int updateByPrimaryKeySelective(User record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table distributedairconditioner..user
     *
     * @mbggenerated Sun May 26 02:41:16 CST 2019
     */
    int updateByPrimaryKey(User record);
}