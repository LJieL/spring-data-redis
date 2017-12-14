package cn.com.taiji.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.taiji.domain.User;
import cn.com.taiji.service.RedisService;

@Controller
public class RedisController {
@Autowired
RedisService redisService;

@RequestMapping(value="/index",method=RequestMethod.GET)
public String index() {
	return "index";
}

@RequestMapping(value="/redis",method=RequestMethod.POST)
public String serializeUser(User u,Model model) {
	redisService.serialize(u);
	model.addAttribute("id","user:"+u.getId());
	return "getUser";
}

@RequestMapping(value="/getuser",method=RequestMethod.GET)
@ResponseBody
public String getUser(String id) {
	User u = redisService.getUser(id);
	return u.toString();
}



}





