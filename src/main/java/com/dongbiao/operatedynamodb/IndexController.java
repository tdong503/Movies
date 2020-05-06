package com.dongbiao.operatedynamodb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class IndexController {

    @RequestMapping(value = "/getmovies")
    @ResponseBody
    public List<String> get()
    {
        return new MovieGetData().get();
    }
}
