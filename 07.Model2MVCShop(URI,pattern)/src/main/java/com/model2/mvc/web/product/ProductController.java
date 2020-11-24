package com.model2.mvc.web.product;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;


//==> 회원관리 Controller
@Controller
public class ProductController {
	
	///Field
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	//setter Method 구현 않음
		
	public ProductController(){
		System.out.println(this.getClass());
	}
	
	//==> classpath:config/common.properties  ,  classpath:config/commonservice.xml 참조 할것
	//==> 아래의 두개를 주석을 풀어 의미를 확인 할것
	@Value("#{commonProperties['pageUnit']}")
	//@Value("#{commonProperties['pageUnit'] ?: 3}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize']}")
	//@Value("#{commonProperties['pageSize'] ?: 2}")
	int pageSize;
	
	/*	
	@RequestMapping("/addUserView.do")
	public String addUserView() throws Exception {

		System.out.println("/addUserView.do");
		
		return "redirect:/user/addUserView.jsp";
	}
	*/

	/*@RequestMapping("/addProduct.do")
	public String addProduct( @ModelAttribute("produt") Product product ) throws Exception {

		System.out.println("/addProduct.do");
		//Business Logic
		System.out.println(product.getManuDate());
		String manuDate= product.getManuDate().replace("-", "");
		product.setManuDate(manuDate);
		System.out.println(product.getManuDate());
		productService.addProduct(product);
		
		return "forward:/listProduct.do?menu=manage";
	}
	*/
	@RequestMapping("/addProduct.do")
	public ModelAndView addProduct( @ModelAttribute("produt") Product product ) throws Exception {

		System.out.println("/addProduct.do");
		//Business Logic
		
		ModelAndView modelAndView = new ModelAndView();
		System.out.println(product.getManuDate());
		String manuDate= product.getManuDate().replace("-", "");
		product.setManuDate(manuDate);
		System.out.println(product.getManuDate());
		productService.addProduct(product);
		
		modelAndView.setViewName("forward:/listProduct.do?menu=manage");
		
		//return "forward:/listProduct.do?menu=manage";
		return modelAndView;
	}
	
	/*
	 * @RequestMapping("/getProduct.do") public String
	 * getProduct( @RequestParam("prodNo") int prodNo , HttpSession session, Model
	 * model ) throws Exception {
	 * 
	 * 
	 * //Business Logic Product product = productService.getProduct(prodNo); //
	 * Model 과 View 연결 model.addAttribute("product", product);
	 * 
	 * System.out.println("/getProduct.do");
	 * if(session.getAttribute("menu").equals("manage")) { return
	 * "forward:/product/updateProductView.jsp"; }
	 * 
	 * return "forward:/product/readProduct.jsp"; }
	 */
	
	
	@RequestMapping("/getProduct.do")
	public ModelAndView getProduct( @RequestParam("prodNo") int prodNo , HttpSession session ) throws Exception {
		
		System.out.println("/getProduct.do");
		//Business Logic
		Product product = productService.getProduct(prodNo);
		// Model 과 View 연결
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("product", product);
		
		modelAndView.setViewName("forward:/product/readProduct.jsp");
		
		if(session.getAttribute("menu").equals("manage")) {
			modelAndView.setViewName("forward:/product/updateProductView.jsp");
			//return "forward:/product/updateProductView.jsp";
		}
		
		return modelAndView;
	}
	
	/*
	 * @RequestMapping("/updateProduct.do") public String
	 * updateUser( @ModelAttribute("product") Product product , Model model ,
	 * HttpSession session) throws Exception{
	 * 
	 * System.out.println("/updateProduct.do"); //Business Logic
	 * productService.updateProduct(product);; int prodNo = product.getProdNo();
	 * product = productService.getProduct(prodNo);
	 * 
	 * model.addAttribute("product", product); //session.setAttribute("product",
	 * product);
	 * 
	 * 
	 * return "forward:/product/readProduct.jsp";
	 * 
	 * }
	 */
	
	@RequestMapping("/updateProduct.do")
	public ModelAndView updateProduct( @ModelAttribute("product") Product product , HttpSession session) throws Exception{

		System.out.println("/updateProduct.do");
		//Business Logic
		productService.updateProduct(product);;
		int prodNo = product.getProdNo();
		product = productService.getProduct(prodNo);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("product", product);
		modelAndView.setViewName("forward:/product/readProduct.jsp");
		
		
		return modelAndView;
		
	}
	/*
	@RequestMapping("/listProduct.do")
	public String listUser( @RequestParam("menu") String menu ,@ModelAttribute("search") Search search , Model model , HttpServletRequest request, HttpSession session) throws Exception{
		
		System.out.println("/listProduct.do");
		
		if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		System.out.println("서치키워드는 ?"+search.getSearchKeyword());
		
		// Business logic 수행
		Map<String , Object> map=productService.getProductList(search);
		
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		session.setAttribute("menu", menu);
		
		// Model 과 View 연결
		model.addAttribute("list", map.get("list"));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);
		
		return "forward:/product/listProduct.jsp";
	}
	*/
	
	@RequestMapping("/listProduct.do")
	public ModelAndView listProduct( @RequestParam("menu") String menu ,@ModelAttribute("search") Search search , HttpSession session) throws Exception{
		
		System.out.println("/listProduct.do");
		
		if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		System.out.println("서치키워드는 ?"+search.getSearchKeyword());
		
		// Business logic 수행
		Map<String , Object> map=productService.getProductList(search);
		
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		session.setAttribute("menu", menu);
		
		ModelAndView modelAndView = new ModelAndView();
		// Model 과 View 연결
		modelAndView.addObject("list", map.get("list"));
		modelAndView.addObject("resultPage", resultPage);
		modelAndView.addObject("search", search);
		
		modelAndView.setViewName("forward:/product/listProduct.jsp");
		//return "forward:/product/listProduct.jsp";
		
		return modelAndView;
	}
}