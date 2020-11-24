package com.model2.mvc.service.purchase.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.purchase.PurchaseDao;
@Repository("purchaseDaoImpl")
public class PurchaseDaoImpl implements PurchaseDao {
	
	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSession sqlSession;
	
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	public PurchaseDaoImpl() {
		System.out.println(this.getClass());
	}

	@Override
	public void insertPurchase(Purchase purchase) throws Exception {
		sqlSession.insert("purchaseMapper.addPurchase",purchase );
	}

	@Override
	public Purchase findPurchase(int tranNo) throws Exception {
		
		return sqlSession.selectOne("purchaseMapper.findPurchase", tranNo);
	}

	@Override
	public Map<String, Object> getPurchaseList(Search search, String buyerId) throws Exception {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("search", search);
		map.put("buyerId", buyerId);
		
		List<Purchase> list = sqlSession.selectList("purchaseMapper.getPurchaseList",map);
		int totalCount = this.getTotalCount(buyerId);
		
		map.put("list", list);
		map.put("totalCount", new Integer(totalCount));
		
		
		
		return map;
	}

	@Override
	public void updatePuchase(Purchase purchase) throws Exception {
		sqlSession.update("purchaseMapper.updatePurchase",purchase );
	}

	@Override
	public void updateTranCode(Purchase purchase) throws Exception {
		System.out.println("트랜코드는 ? :"+purchase.getTranCode());
		int tranCode = Integer.parseInt(purchase.getTranCode().trim());
		
		System.out.println("업데이트될 코드넘버는 ? :"+tranCode);
		int updateTranCode = tranCode+1;
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("tranCode", String.valueOf(updateTranCode));
		map.put("prodNo", purchase.getPurchaseProd().getProdNo());
		sqlSession.update("purchaseMapper.updateTranCode", map);
	}

	@Override
	public int getTotalCount(String buyerId) throws Exception {
			
		return sqlSession.selectOne("purchaseMapper.getTotalCount", buyerId);
	}


}
