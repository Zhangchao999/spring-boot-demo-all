package cn.geoary.service.impl;

import cn.geoary.service.TestService;
import org.springframework.stereotype.Service;


@Service
public class TestServiceImpl implements TestService {

    @Override
    public String testSomeStr() {
        return "哈哈";
    }

}
