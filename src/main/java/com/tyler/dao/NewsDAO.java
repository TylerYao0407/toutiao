package com.tyler.dao;

import com.tyler.model.News;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by tyler on 2017/4/3.
 */
@Mapper
@Component
public interface NewsDAO {
    String TABLE_NAME = " news ";
    String INSERT_FIELDS = " title, link, image, like_count, comment_count, created_date, user_id";
    String SELECT_FIELDS = " id ,"+INSERT_FIELDS;
    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS, ") values (#{title},#{link},#{image},#{likeCount},#{commentCount},#{createdDate},#{userId})"})
    int insertNews(News news);

    @Select({"select ", SELECT_FIELDS , " from ", TABLE_NAME, " where id=#{id}"})
    News getById(int id);

    @Update({"update ", TABLE_NAME, " set comment_count = #{commentCount} where id=#{id} "})
    int updateCommentCount(@Param("id") int id, @Param("commentCount") int commentCount);


    List<News> selectByUserIdAndOffset(@Param("userId") int userId,
                          @Param("offset") int offset,
                          @Param("limit") int limit);
}
