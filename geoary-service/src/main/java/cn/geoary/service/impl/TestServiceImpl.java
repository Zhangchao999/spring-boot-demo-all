package cn.geoary.service.impl;

import cn.geoary.service.TestService;
import org.springframework.stereotype.Service;

import static cn.geoary.util.TimeUtil.getStr;

@Service
public class TestServiceImpl implements TestService {
    @Override
    public String testSomeStr() {
        return getStr();
    }
}
