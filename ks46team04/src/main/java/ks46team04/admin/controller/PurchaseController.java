package ks46team04.admin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpSession;
import ks46team04.admin.dto.Goods;
import ks46team04.admin.dto.Purchase;
import ks46team04.admin.dto.User;
import ks46team04.admin.mapper.GoodsMapper;
import ks46team04.admin.service.PurchaseService;
import ks46team04.admin.service.UserService;

@Controller
@RequestMapping("/admin/purchase_sale")
public class PurchaseController {

	
	private static final Logger log = LoggerFactory.getLogger(PurchaseController.class);

	private final PurchaseService purchaseService;
	private final GoodsMapper goodsMapper;
	private final UserService userService;
	public PurchaseController(PurchaseService purchaseService, GoodsMapper goodsMapper, UserService userService) {
		this.purchaseService = purchaseService;
		this.goodsMapper = goodsMapper;
		this.userService = userService;
	}
	
	@GetMapping("/purchase_list")
	public String getTotalPurchaseList(Model model) {
		List<Purchase> totalPurchaseList = purchaseService.getTotalPurchaseList();
		log.info("totalPurchaseList: {}", totalPurchaseList);
		
		model.addAttribute("title", "Pilling Good - 관리 - 매입 기록 조회");
		model.addAttribute("totalPurchaseList", totalPurchaseList);
		
		return "admin/purchase_sale/purchase_list";
	}
	
	
	@GetMapping("/purchase_insert")
	public String addPurchase(Model model) {
		//goods_reg_info에서 goodsName을 가져온다
		List<Goods> goodsList = goodsMapper.getGoodsList();
		
		model.addAttribute("title", "Pilling Good - 관리 - 매입 등록");
		model.addAttribute("goodsList", goodsList);
		
		return "admin/purchase_sale/purchase_insert";
	}
	
	@GetMapping("/purchase_price")
	@ResponseBody
	public String getGoodsPrice(@RequestParam(name="goodsCode") String goodsCode) {
		log.info("goodsCode: {}", goodsCode);
		Goods goodsInfo = goodsMapper.getGoodsInfoByCode(goodsCode);
		log.info("goodsInfo: {}", goodsInfo);
		String goodsPrice = goodsInfo.getGoodsPrice();
		
		return goodsPrice;
	}
	
	@PostMapping("/purchase_insert")
	public String addPurchase(Purchase purchase, HttpSession session) {
		log.info("purchaseC1: {}", purchase);
		String regId = (String) session.getAttribute("SID");
		String regLevel = (String) session.getAttribute("SLEVEL");
		if(regLevel.equals("1")) {
			log.info("purchaseC2: {}", purchase);
			purchaseService.addPurchase(purchase, regId);
		}
		return "redirect:/admin/purchase_sale/purchase_list";
	}
	
	
	@GetMapping("/purchase_update")
	public String ModifyPurchase(Model model,
								@RequestParam(name="purchaseCode") String purchaseCode,
								@RequestParam(name="goodsCode") String goodsCode) {		
	
		log.info("purchaseCode: {}", purchaseCode);
		log.info("goodsCode: {}", goodsCode);
		Purchase purchaseInfo = purchaseService.getPurchaseByCode(purchaseCode, goodsCode);
	
		log.info("purchaseInfo: {}", purchaseInfo);
		model.addAttribute("title", "Pilling Good - 관리 - 매입 수정");
		model.addAttribute("purchaseInfo", purchaseInfo);
		return "admin/purchase_sale/purchase_update";
	}
	
	@PostMapping("/purchase_update")
	public String ModifyPurchase(Purchase purchase,
								HttpSession session) {
		String updateRegId = (String) session.getAttribute("SID");
		String regLevel = (String) session.getAttribute("SLEVEL");
		if(regLevel.equals("1")) {
			log.info("purchaseC: {}", purchase);
			purchaseService.modifyPurchase(purchase, updateRegId);
		}
		return "redirect:/admin/purchase_sale/purchase_list";
	}
	
	
	@GetMapping("/purchase_delete")
	public String DeletePuchase(Model model) {
		
		return "redirect:/admin/purchase_sale/purchase_list";
	}
	
	@PostMapping("/request_remove")
	@ResponseBody
	public Map<String, Object> DeletePuchase(@RequestParam(name="masterPw") String masterPw,
								@RequestParam(name="delPkValues[]") List<String> delPkValues,
								HttpSession session) {
		
		String userId = (String) session.getAttribute("SID");
		String userLevel = (String) session.getAttribute("SLEVEL");
		
		log.info("관리자 비번 masterPw: {}", masterPw);
		log.info("삭제 선택된 요소들 deleteEles: {}", delPkValues);
		log.info("관리자레벨 userLevel: {}", userLevel);
		
		String msg = "";
		boolean isDel = false;
		Map<String, Object> delResultMap = null;
		delResultMap = new HashMap<String, Object>();
		if(userLevel !=null && userLevel.equals("1")) {
			User userInfo = userService.getUserInfoById(userId);
			if(masterPw.equals(userInfo.getUserPw())){
				//비밀번호 일치, 지우는 로직 추가
				int delResultNumber = purchaseService.deletePurchase(delPkValues);	//요소 코드들을 가져와야한다. 지우려는 요소 정보 배열을 파라미터
				if(delResultNumber > 0) {
					isDel = true;
				}
			}else {
				msg = "관리자 비밀번호가 일치하지 않습니다";
				isDel = false;
			}
		}
		delResultMap.put("isDel", isDel);
		delResultMap.put("msg", msg);
		
		return delResultMap;	//제대로 지워지면 true
	}
	
}
