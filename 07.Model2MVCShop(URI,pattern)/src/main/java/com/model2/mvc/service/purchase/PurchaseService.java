package com.model2.mvc.service.purchase;

import java.util.HashMap;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Purchase;


public interface PurchaseService {

	public void addPurchase(Purchase purchase) throws Exception;
	
	public Purchase getPurchase(int tranNo) throws Exception;
	
	public Purchase getPurchase2(int ProdNo) throws Exception;
	
	public Map<String, Object> getPurchaseList(Search search,String buyerId) throws Exception;
	
	public HashMap<String,Object> getSaleList(Search search) throws Exception;
	//나중에 Map으로 인터페이스화 해서 만들기 ..
	public void updatePurcahse(Purchase purchase) throws Exception;
	
	public void updateTranCode(Purchase purchase) throws Exception;
	
}