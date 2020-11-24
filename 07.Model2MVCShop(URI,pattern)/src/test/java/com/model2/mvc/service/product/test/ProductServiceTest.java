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
 * �� JUnit4 (Test Framework) �� Spring Framework ���� Test( Unit Test)
 * �� Spring �� JUnit 4�� ���� ���� Ŭ������ ���� ������ ��� ���� �׽�Ʈ �ڵ带 �ۼ� �� �� �ִ�.
 * �� @RunWith : Meta-data �� ���� wiring(����,DI) �� ��ü ����ü ����
 * �� @ContextConfiguration : Meta-data location ����
 * �� @Test : �׽�Ʈ ���� �ҽ� ����
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:config/context-common.xml",
		"classpath:config/context-aspect.xml",
		"classpath:config/context-mybatis.xml",
		"classpath:config/context-transaction.xml" })
public class ProductServiceTest {

	// ==>@RunWith,@ContextConfiguration �̿� Wiring, Test �� instance DI
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	private Object object;

	 @Test
	public void testAddProduct() throws Exception {

		Product product = new Product();

		product.setProdName("Ʈ���׽�Ʈ");
		product.setManuDate("20200819");
		product.setFileName("�׽�Ʈ");
		product.setPrice(5000);
		product.setProdDetail("2��");

		 productService.addProduct(product);
		//product.setProdNo(10069);

		//product = productService.getProduct(product.getProdNo());

		System.out.println(product);
		// ==> console Ȯ��
		// System.out.println(user);

		// ==> API Ȯ��
		/*
		 * Assert.assertEquals(10069, product.getProdNo()); Assert.assertEquals("Ŀ��",
		 * product.getProdName()); Assert.assertEquals("��Ŀ��", product.getFileName());
		 * Assert.assertEquals("20200819", product.getManuDate());
		 * Assert.assertEquals(5000, product.getPrice()); Assert.assertEquals("2��",
		 * product.getProdDetail());
		 */
	}

	// @Test
	public void testUpdateProduct() throws Exception {

		Product product = productService.getProduct(10069);
		
		Assert.assertNotNull(product);

		/*
		 * Assert.assertEquals(10069, product.getProdNo()); Assert.assertEquals("Ŀ��",
		 * product.getProdName()); Assert.assertEquals("�ÿ��Ѿ��̽�Ŀ��",
		 * product.getFileName()); Assert.assertEquals("20200819",
		 * product.getManuDate()); Assert.assertEquals(5000, product.getPrice());
		 * Assert.assertEquals("2��", product.getProdDetail());
		 */
		product.setFileName("������Ŀ��");
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
	 	search.setSearchKeyword("������");
	 	
	 	map = productService.getProductList(search);
	 	list = (List<Object>)map.get("list");
	 	Assert.assertEquals(1, list.size());
	 	
	 	totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	}
	 
	 
}