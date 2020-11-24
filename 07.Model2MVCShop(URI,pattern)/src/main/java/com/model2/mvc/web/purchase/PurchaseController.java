package com.model2.mvc.web.purchase;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.purcahse.PurchaseService;

//==> 회원관리 Controller
@Controller
public class PurchaseController {

	/// Field
	@Autowired
	@Qualifier("purchaseServiceImpl")
	private PurchaseService purchaseService;

	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	// setter Method 구현 않음

	public PurchaseController() {
		System.out.println(this.getClass());
	}

	// ==> classpath:config/common.properties , classpath:config/commonservice.xml
	// 참조 할것
	// ==> 아래의 두개를 주석을 풀어 의미를 확인 할것
	@Value("#{commonProperties['pageUnit']}")
	// @Value("#{commonProperties['pageUnit'] ?: 3}")
	int pageUnit;

	@Value("#{commonProperties['pageSize']}")
	// @Value("#{commonProperties['pageSize'] ?: 2}")
	int pageSize;
	/*
	 * @RequestMapping("/addProduct.do") public ModelAndView
	 * addProduct( @ModelAttribute("produt") Product product ) throws Exception {
	 * 
	 * System.out.println("/addProduct.do"); //Business Logic
	 * 
	 * ModelAndView modelAndView = new ModelAndView();
	 * System.out.println(product.getManuDate()); String manuDate=
	 * product.getManuDate().replace("-", ""); product.setManuDate(manuDate);
	 * System.out.println(product.getManuDate());
	 * productService.addProduct(product);
	 * 
	 * modelAndView.setViewName("forward:/listProduct.do?menu=manage");
	 * 
	 * //return "forward:/listProduct.do?menu=manage"; return modelAndView; }
	 */
	@RequestMapping("/addPurchaseView.do")
	public ModelAndView addPurchaseView(@RequestParam("prodNo") int prodNo) throws Exception{
		
		System.out.println("/addPurchaseView.do : 시작");
		
		//businessLogic 수행
		Product product = productService.getProduct(prodNo);
		
		//model view 연결
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("product", product);
		modelAndView.setViewName("forward:/purchase/addPurchaseView.jsp");
		
		return modelAndView;
	}
	
	@RequestMapping("/addPurchase.do")
	public ModelAndView addPurchase(@RequestParam("prodNo") int prodNo, @ModelAttribute("purchase") Purchase purchase,HttpSession session) throws Exception{
		
		System.out.println("/addPurchase.do : 시작");
		
		//businessLogic 수행
		//view에서 product를 세션에 담는게 좋을까 아니면 prodNo를 받아서 product를 가지고 오는게 좋을까 ??
		System.out.println("상품정보 :"+purchase);
		Product product = productService.getProduct(prodNo);
		purchase.setBuyer((User)session.getAttribute("user"));
		purchase.setPurchaseProd(product);
		purchase.setTranCode("0");
		purchaseService.addPurchase(purchase);
		//model view 연결
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("redirect:/listPurchase.do");
		System.out.println("/addPurchase.do : 끝 ");
		return modelAndView;
	}
	
	@RequestMapping("/getPurchase.do")
	public ModelAndView getPurchase(@RequestParam("tranNo") int tranNo) throws Exception{
		
		
		
		//businessLogic
		Purchase purchase = purchaseService.getPurchase(tranNo);
		
		ModelAndView modelAndView = new ModelAndView();
		System.out.println("구매자이름은 ?"+purchase.getReceiverName());
		modelAndView.addObject("purchase", purchase);
		modelAndView.setViewName("forward:/purchase/getPurchaseView.jsp");
		System.out.println("/getPurchase.do : 끝 ");
		return modelAndView;
		
	}
	
	@RequestMapping("/updatePurchaseView.do")
	public ModelAndView updatePurchaseView(@RequestParam("tranNo") int tranNo) throws Exception{
		
		//businessLogic
		ModelAndView modelAndView = new ModelAndView();
		
		Purchase purchase = purchaseService.getPurchase(tranNo);
		modelAndView.addObject("purchase", purchase);
		
		modelAndView.setViewName("forward:/purchase/updatePurchaseView.jsp");
		System.out.println("/updatePurchase.do : 끝 ");
		return modelAndView;
		
		
	}
	
	@RequestMapping("/updatePurchase.do")
	public ModelAndView updatePurchase(@ModelAttribute("purchase") Purchase purchase ) throws Exception{
		
		//businessLogic
		ModelAndView modelAndView = new ModelAndView();
		
		purchaseService.updatePurcahse(purchase);
		modelAndView.addObject("purchase", purchase);
		modelAndView.setViewName("forward:/getPurchase.do");//why tranNo를 붙이면 numberFromatException이 발생할까 ??
		System.out.println("/updatePurchase.do : 끝 ");
		return modelAndView;
		
		
	}

	/*
	 * @RequestMapping("/updateProduct.do") public ModelAndView
	 * update( @ModelAttribute("product") Product product , HttpSession session)
	 * throws Exception{
	 * 
	 * System.out.println("/updateProduct.do"); //Business Logic
	 * productService.updateProduct(product);; int prodNo = product.getProdNo();
	 * product = productService.getProduct(prodNo);
	 * 
	 * ModelAndView modelAndView = new ModelAndView();
	 * modelAndView.addObject("product", product);
	 * modelAndView.setViewName("forward:/product/readProduct.jsp");
	 * 
	 * 
	 * return modelAndView;
	 * 
	 * }
	 */
	@RequestMapping("/listPurchase.do")
	public ModelAndView listPurchase(@ModelAttribute("search") Search search, HttpSession session) throws Exception {

		System.out.println("/listPurchase.do 시작");

		if (search.getCurrentPage() == 0) {
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);

		User buyer = (User) session.getAttribute("user");

		Map<String, Object> map = purchaseService.getPurchaseList(search, buyer.getUserId());
		//List<Object> list = (List<Object>) map.get("list");

		Page resultPage = new Page(search.getCurrentPage(), ((Integer) map.get("totalCount")).intValue(), pageUnit,
				pageSize);
		
		ModelAndView modelAndView = new ModelAndView();
		
		modelAndView.addObject("list", map.get("list"));
		modelAndView.addObject("resultPage", resultPage);
		modelAndView.addObject("search", search);
		
		modelAndView.setViewName("forward:/purchase/listPurchase.jsp");
		System.out.println("/listPurchase.do끝");
		
		return modelAndView;
	}
	
	@RequestMapping("/updateTranCode.do")
	public ModelAndView updateTranCode(@RequestParam("tranCode") String tranCode, 
										@RequestParam("prodNo") int prodNo, HttpSession session) throws Exception{
		
		
		System.out.println("/updateTranCode.do시작");
		Purchase purchase = new Purchase();
		Product product = new Product();
		product.setProdNo(prodNo);
		purchase.setTranCode(tranCode);
		purchase.setPurchaseProd(product);
		
		purchaseService.updateTranCode(purchase);
		ModelAndView modelAndView = new ModelAndView();
		
		User user = (User)session.getAttribute("user");
		
		if( user.getRole().trim().equals("user")) {
			System.out.println("여기들어왔나 ?");
			modelAndView.setViewName("forward:/listPurchase.do"); 
		}else {
			
			modelAndView.setViewName("forward:/listProduct.do?menu=manage"); 

		}
		
		
		
		
		System.out.println("/updateTranCode.do끝");
		
		return modelAndView;
		
	}

}