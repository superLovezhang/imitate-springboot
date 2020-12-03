package com.tyzz.mvc.controller;

import com.tyzz.mvc.anno.Controller;
import com.tyzz.mvc.anno.GetMapping;
import com.tyzz.mvc.anno.PostMapping;
import com.tyzz.mvc.anno.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: zhangzhao<zhao.zhang@eventslack.com>
 * Date: 2020/11/20
 * Time: 11:19
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @PostMapping("/add")
    public void add(HttpServletResponse response, String name) throws IOException {
        response.setCharacterEncoding("utf8");
        response.setContentType("text/plain;");
        response.getWriter().print(name + "添加成功");
    }

    @GetMapping("/delete")
    public void delete(HttpServletResponse response, String name) throws IOException {
        response.setCharacterEncoding("utf8");
        response.setContentType("text/plain;");
        response.getWriter().print(name + "删除成功");
    }

    @GetMapping("/download")
    public void download(HttpServletResponse response) {
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + 12 + ".xlsx");
    }
}
