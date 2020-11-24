package com.model2.mvc.service.product.test;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;

/*
 *	FileName :  UserServiceTest.java
 * ㅇ JUnit4 (Test Framework) 과 Spring Framework 통합 Test( Unit Test)
 * ㅇ Spring 은 JUnit 4를 위한 지원 클래스를 통해 스프링 기반 통합 테스트 코드를 작성 할 수 있다.
 * ㅇ @RunWith : Meta-data 를 통한 wiring(생성,DI) 할 객체 구현체 지정
 * ㅇ @ContextConfiguration : Meta-data location 지정
 * ㅇ @Test : 테스트 실행 소스 지정
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:config/context-common.xml",
		"classpath:config/context-aspect.xml",
		"classpath:config/context-mybatis.xml",
		"classpath:config/context-transaction.xml" })
public class ProductServiceTest {

	// ==>@RunWith,@ContextConfiguration 이용 Wiring, Test 할 instance DI
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	private Object object;

	 @Test
	public void testAddProduct() throws Exception {

		Product product = new Product();

		product.setProdName("트랜테스트");
		product.setManuDate("20200819");
		product.setFileName("테스트");
		product.setPrice(5000);
		product.setProdDetail("2샷");

		 productService.addProduct(product);
		//product.setProdNo(10069);

		//product = productService.getProduct(product.getProdNo());

		System.out.println(product);
		// ==> console 확인
		// System.out.println(user);

		// ==> API 확인
		/*
		 * Assert.assertEquals(10069, product.getProdNo()); Assert.assertEquals("커피",
		 * product.getProdName()); Assert.assertEquals("블랙커피", product.getFileName());
		 * Assert.assertEquals("20200819", product.getManuDate());
		 * Assert.assertEquals(5000, product.getPrice()); Assert.assertEquals("2샷",
		 * product.getProdDetail());
		 */
	}

	// @Test
	public void testUpdateProduct() throws Exception {

		Product product = productService.getProduct(10069);
		
		Assert.assertNotNull(product);

		/*
		 * Assert.assertEquals(10069, product.getProdNo()); Assert.assertEquals("커피",
		 * product.getProdName()); Assert.assertEquals("시원한아이스커피",
		 * product.getFileName()); Assert.assertEquals("20200819",
		 * product.getManuDate()); Assert.assertEquals(5000, product.getPrice());
		 * Assert.assertEquals("2샷", product.getProdDetail());
		 */
		product.setFileName("따듯한커피");
		product.setManuDate("20201116");
		product.setPrice(3500);
		
		productService.updateProduct(product);
		
		product = productService.getProduct(product.getProdNo());
		
		//System.out.println(product);
		
		Assert.assertNotNull(product);
		
		
		
		
	}
	
	//@Test
	public void TestProductList() throws Exception{
		
		Search search = new Search();
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	
	 	Map<String,Object> map = productService.getProductList(search);
	 	
	 	List<Object> list = (List<Object>)map.get("list");
	 	Assert.assertEquals(3, list.size());
	 	
	 	//System.out.println(list);
	 	
	 	Integer totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 	
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	search.setSearchCondition("1");
	 	search.setSearchKeyword("보르도");
	 	
	 	map = productService.getProductList(search);
	 	list = (List<Object>)map.get("list");
	 	Assert.assertEquals(1, list.size());
	 	
	 	totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	}
	 
	 
}