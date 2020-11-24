package com.model2.mvc.service.product.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductDao;
import com.sun.tracing.dtrace.Attributes;
@Repository("productDaoImpl")
public class ProductDaoImpl implements ProductDao {
	
	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSession sqlSession;

	public ProductDaoImpl() {
		System.out.println(getClass()+"디폴트 생성자");
	}

	@Override
	public int insertProduct(Product product) throws Exception {
		return sqlSession.insert("productMapper.addProduct",product);
		
	}

	@Override
	public Product findProduct(int prodNo) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("productMapper.findProduct",prodNo);
	}

	@Override
	public List<Product> getProductList(Search search) throws Exception {
		return sqlSession.selectList("productMapper.getProductList", search);
	}

	@Override
	public int updateProduct(Product product) throws Exception {
		return sqlSession.update("productMapper.updateProduct",product);
	}

	@Override
	public int getTotalCount(Search search) throws Exception {
		return sqlSession.selectOne("productMapper.getTotalCount", search);
	}
	
	public void setSqlSession(SqlSession sqlSession) {
		System.out.println("::"+getClass()+"setSql()");
		this.sqlSession=sqlSession;
		
	}

}
