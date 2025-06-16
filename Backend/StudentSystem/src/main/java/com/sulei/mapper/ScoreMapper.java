package com.sulei.mapper;

import com.sulei.pojo.Score;
import com.sulei.pojo.ScoreVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ScoreMapper {
    List<ScoreVo> getScoreList(@Param("name") String name);

    int add(Score score);

    int delete(Integer id);

    int update(Score score);
} 