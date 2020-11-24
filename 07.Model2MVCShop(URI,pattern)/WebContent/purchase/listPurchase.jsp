
<%@ page contentType="text/html; charset=euc-kr" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--
<%@page import="com.model2.mvc.service.purchase.vo.PurchaseVO"%>
<%@ page import="java.util.*"  %>
<%@ page import="com.model2.mvc.service.product.vo.*" %>
<%@ page import="com.model2.mvc.common.*" %>
 
<%
	HashMap<String,Object> map=(HashMap<String,Object>)request.getAttribute("map");
	//Search searchVO=(Search)request.getAttribute("searchVO");
	
	int total=0;
	ArrayList<PurchaseVO> list=null;
	if(map != null){
		total=((Integer)map.get("count")).intValue();
		list=(ArrayList<PurchaseVO>)map.get("list");
	}
	
	int currentPage=searchVO.getPage();
	
	int totalPage=0;
	if(total > 0) {
		totalPage= total / searchVO.getPageUnit() ;
		if(total%searchVO.getPageUnit() >0)
	totalPage += 1;
	}
	
	//String menu = (String)request.getAttribute("menu");
	//String menu = (String)session.getAttribute("menu");
	//System.out.println("list 세션  JSP의 : "+menu);
%>
--%>



<html>
<head>
<title>구매 목록조회</title>
<link rel="stylesheet" href="/css/admin.css" type="text/css">
<script type="text/javascript">
function fncGetPurchaseList(currentPage) {
	document.getElementById("currentPage").value = currentPage;
   	document.detailForm.submit();		
}
</script>
</head>
<body bgcolor="#ffffff" text="#000000">
<div style="width: 98%; margin-left: 10px;">
<form name="detailForm" action="/listPurchase.do" method="post">
<table width="100%" height="37" border="0" cellpadding="0" cellspacing="0">
<tr>
<td width="15" height="37"><img src="/images/ct_ttl_img01.gif"width="15" height="37"></td>
<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left: 10px;">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
<tr>
<td width="93%" class="ct_ttl01">구매 목록조회</td>
</tr>
</table>
</td>
<td width="12" height="37"><img src="/images/ct_ttl_img03.gif" width="12" height="37"></td>
</tr>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top: 10px;">
<tr>
<td colspan="11">전체 ${resultPage.totalCount } 건수, 현재  ${resultPage.currentPage } 페이지</td>
</tr>
<tr>
<td class="ct_list_b" width="100">No</td>
<td class="ct_line02"></td>
<td class="ct_list_b" width="150">회원ID</td>
<td class="ct_line02"></td>
<td class="ct_list_b" width="150">회원명</td>
<td class="ct_line02"></td>
<td class="ct_list_b">전화번호</td>
<td class="ct_line02"></td>
<td class="ct_list_b">배송현황</td>
<td class="ct_line02"></td>
<td class="ct_list_b">정보수정</td>
</tr>
<tr>
<td colspan="11" bgcolor="808285" height="1"></td>
</tr>
<%-- 
	<% 	
		int no=list.size();
		System.out.println(no);
		for(int i=0; i<list.size(); i++) {
			PurchaseVO vo = (PurchaseVO)list.get(i);
	%>
	--%>
<c:set var="i" value="0" />
<c:forEach var="purchase" items="${list}">
<c:set var="i" value="${i+1 }"/>
<tr class="ct_list_pop">
<td align="center">
<a href="/getPurchase.do?tranNo=${purchase.tranNo}">${i }</a>
</td>
<td></td>
<td align="left">
<a href="/getUser.do?userId=${purchase.buyer.userId}">${purchase.buyer.userId}</a>
</td>
<td></td>
<td align="left">${user.userName}</td>
<td></td>
<td align="left">${purchase.receiverPhone}</td>
<td></td>
<c:choose>
<c:when test="${ !empty purchase.tranCode && purchase.tranCode =='0  ' }">
<td align="left">현재 구매완료 상태 입니다.</td>
<td></td>
</c:when>

<c:when test="${ !empty purchase.tranCode && purchase.tranCode =='1  ' }">
<td align="left">현재배송중 상태입니다. </td>
<td></td>
</c:when>

<c:when test="${ !empty purchase.tranCode && purchase.tranCode == '2  ' }">
<td align="left">현재배송완료상태 입니다.</td>
<td></td>
</c:when>

</c:choose>

<c:if test="${ !empty purchase.tranCode && purchase.tranCode eq '1  ' }">
<td align="left"><a href="/updateTranCode.do?tranCode=${purchase.tranCode}&prodNo=${purchase.purchaseProd.prodNo}">물건도착</a></td>
</c:if>

</tr>
<tr>
<td colspan="11" bgcolor="D6D7D6" height="1"></td>
</tr>
</c:forEach>

</table>
<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top: 10px;">
<tr>
<td align="center">

<input type="hidden" id="currentPage" name="currentPage" value=""/>
	
	<!-- jsp include 말고 EL의 import를 사용 해서 pageNavigator를 인터페이스화 시킴 .. 장점 어떤 page던 페이지 value(fnc)만 바꿔주면 다 사용가능 -->
	<c:set var="fnc" value="fncGetPurchaseList" scope="request" />
	
	<c:import var="pageNavi" url="/common/pageNavigator.jsp" scope="request"/>
	${pageNavi}
</td>
</tr>
</table>
<!-- 페이지 Navigator 끝 -->
</form>
</div>
</body>
</html>