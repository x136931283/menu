package com.menu.service;

import com.menu.pojo.CustomPageDto;
import com.menu.pojo.Result;
import org.springframework.stereotype.Service;


public interface MenuService {
    Result search(CustomPageDto customPageDto);

    Result classification();

    Result classSearch(CustomPageDto customPageDto);

    Result getById(Integer id);

    Result getRand(Integer num);

    Result simpleClassSearch(CustomPageDto customPageDto);
}
