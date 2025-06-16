package com.sulei.service.impl;

import com.sulei.mapper.ScoreMapper;
import com.sulei.pojo.Score;
import com.sulei.pojo.ScoreVo;
import com.sulei.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScoreServiceImpl implements ScoreService {

    @Autowired
    private ScoreMapper scoreMapper;

    @Override
    public List<ScoreVo> getScoreList(String name) {
        return scoreMapper.getScoreList(name);
    }

    @Override
    public void add(Score score) {
        scoreMapper.add(score);
    }

    @Override
    public void delete(Integer id) {
        scoreMapper.delete(id);
    }

    @Override
    public void update(Score score) {
        scoreMapper.update(score);
    }
} 