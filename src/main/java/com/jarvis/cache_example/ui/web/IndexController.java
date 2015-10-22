package com.jarvis.cache_example.ui.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.jarvis.cache_example.common.service.UserService;
import com.jarvis.cache_example.common.to.UserTO;

@Controller
public class IndexController {

    @Autowired
    private UserService userService;

    @RequestMapping("/index.html")
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
        int id=100;
        UserTO user=userService.getUserById(id);
        request.setAttribute("user", user);
        return new ModelAndView("/index");
    }

    @RequestMapping("/updateuser.html")
    public ModelAndView updateUser(HttpServletRequest request, HttpServletResponse response) {
        int id=100;
        UserTO user=userService.getUserById(id);
        userService.updateUser(user);
        return index(request, response);
    }
}
