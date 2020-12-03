package com.tyzz.mvc.controller;

import com.tyzz.mvc.anno.Controller;
import com.tyzz.mvc.anno.PostMapping;
import com.tyzz.mvc.anno.RequestMapping;
import com.tyzz.mvc.anno.GetMapping;
import com.tyzz.mvc.config.core.engine.ModelAndView;
import com.tyzz.mvc.dispatcher.RequestMethod;
import com.tyzz.mvc.pojo.R;
import com.tyzz.mvc.pojo.User;


import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

@Controller
@RequestMapping("/api")
public class TestController {

    @GetMapping(value = "/get")
    public ModelAndView test1(ModelAndView mv, String name, String age) {
        ArrayList<String> list = new ArrayList<>();
        list.add("我是");
        list.add("我是");
        list.add("我是");
        list.add("我是");

        mv.setView("a");
        mv.addAttribute("title", "这是我随便取的title!");
        mv.addAttribute("list", list);
        return mv;
    }

    @PostMapping(value = "/post")
    public ModelAndView test2(ModelAndView mv) {
        return mv;
    }

    @RequestMapping(value = "/modify", method = {RequestMethod.POST, RequestMethod.GET, RequestMethod.OPTIONS})
    public User modify(HttpServletResponse response) throws IOException {
        User user = new User();
        user.setName("张三");
        user.setAge(18);
        return user;
        HashMap
    }

    @GetMapping(value = "/responseBody")
    public R responseBody() {
        return R.success(null);
    }

}
