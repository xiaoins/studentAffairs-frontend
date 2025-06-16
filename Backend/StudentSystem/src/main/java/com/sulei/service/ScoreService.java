package com.sulei.service;

import com.sulei.pojo.Score;
import com.sulei.pojo.ScoreVo;

import java.util.List;

public interface ScoreService {
    List<ScoreVo> getScoreList(String name);

    void add(Score score);

    void delete(Integer id);

    void update(Score score);
} 