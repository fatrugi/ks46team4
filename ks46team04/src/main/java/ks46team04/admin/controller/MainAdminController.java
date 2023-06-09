package ks46team04.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class MainAdminController {
	
	@GetMapping("/admin_index")
	public String adminMain(Model model) {
		model.addAttribute("title", "pillingGood 메인화면");
		model.addAttribute("content", "thymeleaf layout 완성");
		
		return "admin/admin_index";
	}
}
