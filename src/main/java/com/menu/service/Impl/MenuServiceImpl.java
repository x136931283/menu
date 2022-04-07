package com.menu.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.menu.pojo.CustomPageDto;
import com.menu.pojo.Result;
import com.menu.redisCache.annotation.RedisCache;
import com.menu.service.MenuService;
import com.xiaoleilu.hutool.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Time;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class MenuServiceImpl implements MenuService {


    @Value("${jd.appkey}")
    private String appkey;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    @RedisCache(region = "classification",value = "classification",time = 10)
    public Result search(CustomPageDto customPageDto) {
        String keyword = (String) customPageDto.getQuery().get("keyword");
        Integer num = customPageDto.getPageSize();
        Integer start = customPageDto.getStart();
        //查询redis
        JSONObject forObject = restTemplate.getForObject("https://way.jd.com/jisuapi/search?keyword="
                + keyword + "&num=" + num + "&start="
                + start+ "&appkey=" + appkey, JSONObject.class);

        if (errorRequest(forObject)) {
            return Result.error("查询失败");
        }
        JSONObject result = forObject.getJSONObject("result").getJSONObject("result");
        customPageDto.setPageCount(result.getInteger("total"));
        customPageDto.setData(result.getJSONArray("list"));
        return Result.ok(customPageDto);
    }

    private boolean errorRequest(JSONObject forObject) {
        if (forObject ==null || !"10000".equals(forObject.getString("code" ))){
            return true;
        }
        return false;
    }

    @Override
    @RedisCache(region = "classification",value = "classification",time = 10)
    public Result classification() {
        //查询redis
        JSONObject forObject = restTemplate.getForObject("https://way.jd.com/jisuapi/recipe_class?appkey="+appkey, JSONObject.class);
        if (errorRequest(forObject)) {
            return Result.error("查询失败");
        }
        JSONObject result = forObject.getJSONObject("result");
        return Result.ok(result);
    }

    @Override
    @RedisCache(region = "classSearch",value = "customPageDto",time = 10)
    public Result classSearch(CustomPageDto customPageDto) {
        Integer classId = (Integer) customPageDto.getQuery().get("classId");
        Integer num = customPageDto.getPageSize();
        Integer start = customPageDto.getStart();

        JSONObject forObject = restTemplate.getForObject("https://way.jd.com/jisuapi/byclass?classid="+classId
                +"&start="+start+"&num="+ num+"&appkey="+appkey, JSONObject.class);
        JSONObject result = forObject.getJSONObject("result").getJSONObject("result");
        customPageDto.setPageCount(result.getInteger("total"));
        customPageDto.setData(result.getJSONArray("list"));
        return Result.ok(customPageDto);
    }

    @Override
    @RedisCache(region = "getById",value = "id",time = 10)
    public Result getById(Integer id) {
        JSONObject forObject = restTemplate.getForObject("https://way.jd.com/jisuapi/detail?id="+id+"&appkey="+appkey, JSONObject.class);
        JSONObject result = forObject.getJSONObject("result").getJSONObject("result");
        return Result.ok(result);
    }

    @Override
    public Result getRand(Integer num) {

        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < num; i++) {
            int randomInt = RandomUtil.randomInt(1, 56391);
            Object data = getById(randomInt).getData();
            JSONObject jsonObject = JSON.parseObject(data.toString());
            jsonArray.add(jsonObject);
        }
        return Result.ok(jsonArray);
    }

    @Override
    @RedisCache(region = "simpleClassSearch",value = "simpleClassSearch",time = 10)
    public Result simpleClassSearch(CustomPageDto customPageDto) {
        CustomPageDto data = (CustomPageDto) classSearch(customPageDto).getData();
        JSONArray jsonArray = JSONArray.parseArray(data.getData().toString());
        JSONArray jsonArray1 = new JSONArray();
        for (Object o : jsonArray) {
            JSONObject jsonObject = JSON.parseObject(o.toString());
            jsonObject.remove("material");
            jsonObject.remove("process");
            jsonArray1.add(jsonObject);
        }
        data.setData(jsonArray1);
        return Result.ok(data);
    }


}
