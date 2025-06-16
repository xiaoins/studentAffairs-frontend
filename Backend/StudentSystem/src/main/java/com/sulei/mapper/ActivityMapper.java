package com.sulei.mapper;

import com.sulei.pojo.Activity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 活动数据访问层接口
 */
@Mapper
public interface ActivityMapper {
    /**
     * 查询所有活动
     * @return 活动列表
     */
    List<Activity> getAllActivities();

    /**
     * 新增活动
     * @param activity 活动对象
     */
    void addActivity(Activity activity);

    /**
     * 更新活动
     * @param activity 活动对象
     */
    void updateActivity(Activity activity);

    /**
     * 删除活动
     * @param id 活动ID
     */
    void deleteActivity(Integer id);
} 