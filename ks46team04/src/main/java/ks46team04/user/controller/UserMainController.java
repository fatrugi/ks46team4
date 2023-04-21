package ks46team04.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserMainController {
	
	@GetMapping("/mypage_index")
	public String mypageMain(Model model) {
		
		return "user/mypage_index";
	}
	
}