package com.yangyang.dao;

import com.yangyang.dataobject.CommentDao;

import java.util.List;

public interface CommentDaoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_comment
     *
     * @mbg.generated Tue Nov 26 14:48:06 CST 2019
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_comment
     *
     * @mbg.generated Tue Nov 26 14:48:06 CST 2019
     */
    int insert(CommentDao record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_comment
     *
     * @mbg.generated Tue Nov 26 14:48:06 CST 2019
     */
    int insertSelective(CommentDao record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_comment
     *
     * @mbg.generated Tue Nov 26 14:48:06 CST 2019
     */
    CommentDao selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_comment
     *
     * @mbg.generated Tue Nov 26 14:48:06 CST 2019
     */
    int updateByPrimaryKeySelective(CommentDao record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_comment
     *
     * @mbg.generated Tue Nov 26 14:48:06 CST 2019
     */
    int updateByPrimaryKeyWithBLOBs(CommentDao record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_comment
     *
     * @mbg.generated Tue Nov 26 14:48:06 CST 2019
     */
    int updateByPrimaryKey(CommentDao record);

    /**
     * 根据帖子id查询所有的评论
     * @param itemId
     * @return
     */
    List<CommentDao> selectByItemId(Integer itemId);
}