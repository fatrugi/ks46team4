package ks46team04.user.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import ks46team04.admin.controller.UserController;
import ks46team04.admin.dto.DonationPayMethod;
import ks46team04.admin.dto.DonationSub;
import ks46team04.admin.dto.FundingPay;
import ks46team04.admin.dto.FundingRefund;
import ks46team04.admin.dto.User;
import ks46team04.admin.dto.UserLevel;
import ks46team04.admin.mapper.UserMapper;
import ks46team04.admin.service.DonationService;
import ks46team04.admin.service.FundingService;
import ks46team04.admin.service.UserService;
import ks46team04.user.service.UserMainService;

@Controller
@RequestMapping("/user")
public class UserMainController {
	
	private static final Logger log = LoggerFactory.getLogger(UserController.class);

	private final UserService userService;
	private final UserMainService userMainService;
	private final UserMapper userMapper;
	private final HttpServletRequest request;
	
	public UserMainController(UserService userService, UserMapper userMapper, UserMainService userMainService, HttpServletRequest request) {
		this.userService = userService;
		this.userMapper = userMapper;
		this.userMainService = userMainService;
		this.request = request;
	}
	
	@GetMapping("/myPage_inquiry")
	public String mypageInquiry(HttpServletRequest request, Model model) {
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("SID"); // 세션에서 사용자 아이디를 가져옴

		model.addAttribute("title", "나의 문의내역");
		model.addAttribute("userId", userId);

		return "user/myPage_inquiry";
	}

	@GetMapping("/myPage_myPayment")
	public String mypagePayment(Model model
			 ,@RequestParam(name="keyword", required=false) String keyword
			 ,@RequestParam(name="searchValue", required=false) String searchValue) {
		
		/*
		 * List<FundingRefund> refundList = fundingService.getFundingRefundList(keyword,
		 * searchValue); log.info("getDonationPayMethod: {}", refundList);
		 * model.addAttribute("title", "나의 결제내역"); model.addAttribute("refundList",
		 * refundList);
		 */
		
		return "/user/myPage_myPayment";
	}
	
	
	
	
	@GetMapping("/myPage_myAutoPm")
	public String mypageAutoPm(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
	    String userId = (String) session.getAttribute("SID");
	    
		List<DonationPayMethod> getDonationPayMethod = userMainService.getDonationPayMethodByUserId(userId);
		

		if (getDonationPayMethod.isEmpty()) {
	        model.addAttribute("message", "자주쓰는 결제수단이 없습니다.");
	    } else {
	        model.addAttribute("title", "자주쓰는 결제수단");
	        model.addAttribute("getDonationPayMethod", getDonationPayMethod);
	    }
		
		return "/user/myPage_myAutoPm";
	}
	


	
	@PostMapping("/myPageDrop")
	@ResponseBody
	public Map<String, Object> myPageDrop(HttpServletRequest request, @RequestParam(name = "userPw") String userPw) {
	    Logger logger = LoggerFactory.getLogger(getClass());
	    Map<String, Object> response = new HashMap<>();

	    try {
	    	HttpSession session = request.getSession();
	        String userId = (String) session.getAttribute("SID"); // 세션에서 사용자 아이디를 가져옴
	        if (userService.pwCheck(userId, userPw)) {
	            userService.removeUser(userId);
	            response.put("success", true);
	            response.put("message", "삭제 성공");
	            
	            request.getSession().invalidate(); //세션 무효화
	        } else {
	            response.put("success", false);
	            response.put("message", "비밀번호가 일치하지 않습니다.");
	        }
	    } catch (Exception e) {
	        logger.error("An error occurred while deleting user information.", e);
	        response.put("success", false);
	        response.put("message", "회원 정보 삭제 중 오류가 발생하였습니다.");
	    }
	    
	    return response;
	}
	

	@PostMapping("/userpwCheck")
	@ResponseBody
	public boolean userpwCheck(@RequestParam(name = "userId") String userId, @RequestParam(name = "userPw") String userPw) {

		return userService.pwCheck(userId, userPw);
	}

	
	@GetMapping("/myPage_drop")
	public String myPageDrop(HttpServletRequest request, Model model) {
		HttpSession session = request.getSession();
	    String userId = (String) session.getAttribute("SID"); // 세션에서 사용자 아이디를 가져옴
	    
		model.addAttribute("title", "회원탈퇴");
		model.addAttribute("userId", userId);
		
		return "user/myPage_drop";
	}
	
	@GetMapping("/myPage_myDonation")
	public String mypageDonation(HttpServletRequest request, Model model) {
		HttpSession session = request.getSession();
	    String userId = (String) session.getAttribute("SID");
	    
		List<DonationSub> getDonationSub = userMainService.getDonationSubByUserId(userId);
		

		if (getDonationSub.isEmpty()) {
	        model.addAttribute("message", "정기기부 참여내역이 없습니다.");
	    } else {
	        model.addAttribute("title", "정기기부 참여내역");
	        model.addAttribute("getDonationSub", getDonationSub);
	    }
		
		return "user/myPage_myDonation";
	}

	
	
	@GetMapping("/myPage_myFunding")
	public String mypageFunding(HttpServletRequest request,Model model) {
		HttpSession session = request.getSession();
	    String userId = (String) session.getAttribute("SID");

	    
		List<FundingPay> fundingPayList = userMainService.getFundingPayListByUserId(userId);
		log.info("fundingPayList_Service: {}", fundingPayList);
		
		if (fundingPayList.isEmpty()) {
	        model.addAttribute("message", "펀딩 참여내역이 없습니다.");
	    } else {
	        model.addAttribute("title", "펀딩 참여내역");
	        model.addAttribute("fundingPayList", fundingPayList);
	    }
		
		return "user/myPage_myFunding";
	}
	
	
	@PostMapping("/mypageInfoModify")
	@ResponseBody
	public Map<String, Object> mypageInfoModify(HttpServletRequest request,@ModelAttribute User user) {
		 	Logger logger = LoggerFactory.getLogger(getClass());
		    Map<String, Object> response = new HashMap<>();
		    
		    try {
		    	HttpSession session = request.getSession();
		        String userId = (String) session.getAttribute("SID"); // 세션에서 사용자 아이디를 가져옴
		        user.setUserId(userId);
		        boolean success = userService.modifyUser(user);
		            response.put("success", success);

		    } catch (Exception e) {
		        logger.error("An error occurred while during user modification", e);
		        response.put("success", false);
		        response.put("message", "회원 정보 수정 중 오류가 발생하였습니다.");
		    }
		    
		    return response;
		}
		
		
	@GetMapping("/myPage_myInfoModify")
	public String mypageInfoModify(HttpServletRequest request,Model model) {
		
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("SID"); // 세션에서 사용자 아이디를 가져옴
		
		// 사용자 정보 조회
	    User userInfo = userService.getUserInfoById(userId);
		
		model.addAttribute("title", "회원정보수정");
		model.addAttribute("userInfo", userInfo);
		
		return "user/myPage_myInfoModify";
	}
	
	
	/*
	 * @GetMapping("/myPage_myInfo") public String mypageInfo(HttpServletRequest
	 * request, Model model) { HttpSession session = request.getSession(); String
	 * userId = (String) session.getAttribute("SID"); // 세션에서 사용자 아이디를 가져옴
	 * 
	 * // 비밀번호 확인 로직 String userPw = "사용자 입력 비밀번호"; // 사용자로부터 입력받은 비밀번호 boolean
	 * isPasswordCorrect = userService.pwCheck(userId, userPw); // pwCheck 메서드 호출
	 * 
	 * if (isPasswordCorrect) { model.addAttribute("title", "회원정보");
	 * model.addAttribute("userId", userId); return "user/myPage_myInfo"; } else {
	 * return "user/myPage_index"; } }
	 */
	 
	

	@GetMapping("/myPage_myInfo")
	public String mypageInfo(HttpServletRequest request,Model model) {
		
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("SID"); // 세션에서 사용자 아이디를 가져옴
		
		// 사용자 정보 조회
	    User userInfo = userService.getUserInfoById(userId);
		
		model.addAttribute("title", "회원정보");
		model.addAttribute("userInfo", userInfo);
		
		return "user/myPage_myInfo";
	}

	
	@GetMapping("/mypage_index")
	public String mypageMain(Model model) {
		
		return "user/mypage_index";
	}
	
}
