<%@ page contentType="text/html; charset=euc-kr" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- 
<%@ page import="java.util.*"  %>

<%@ page import="com.model2.mvc.common.Search" %>
<%@ page import="com.model2.mvc.service.product.vo.*" %>
<%@page import="com.model2.mvc.common.Page"%>
<%@page import="com.model2.mvc.common.util.CommonUtil"%>

<%
	System.out.println("리스트 jsp 시작");
	List<ProductVO> list= (List<ProductVO>)request.getAttribute("list");
	Page resultPage=(Page)request.getAttribute("resultPage");


	Search search=(Search)request.getAttribute("search");
	
	String searchCondition = CommonUtil.null2str(search.getSearchCondition());
	String searchKeyword = CommonUtil.null2str(search.getSearchKeyword());
	
	//String menu = (String)request.getAttribute("menu");
	String menu = (String)session.getAttribute("menu");
	System.out.println("list 세션  JSP의 : "+menu);
%>
--%>  
 




<html>
<head>

<title>상품 목록조회</title>

<link rel="stylesheet" href="/css/admin.css" type="text/css">

<script type="text/javascript">
//검색 / page 두가지 경우 모두 Form 전송을 위해 JavaScrpt 이용  
function fncGetProductList(currentPage) {
	document.getElementById("currentPage").value = currentPage;
   	document.detailForm.submit();		
}

//최종적으로 .submit90을 호출 

</script>
</head>

<body bgcolor="#ffffff" text="#000000">

<div style="width:98%; margin-left:10px;">
														
<form name="detailForm" action="/listProduct.do?menu=${menu}" method="post">

<table width="100%" height="37" border="0" cellpadding="0"	cellspacing="0">
	<tr>
		<td width="15" height="37">
			<img src="/images/ct_ttl_img01.gif" width="15" height="37">
		</td>
		<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left:10px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
				
				<c:if test="${menu eq 'manage' }">
			<%-- 	<%if(menu.equals("manage")){ %>--%>
					<td width="93%" class="ct_ttl01">상품 관리</td>
				</c:if>
			
			
		<%--<%}else{ %>--%>
		<c:if test="${ !(menu eq 'manage') }">
			<td width="93%" class="ct_ttl01">상품 목록조회</td>
	</c:if>
				</tr>
			</table>
		</td>
		
		<%--<%} %>--%>
		
		
		<td width="12" height="37">
			<img src="/images/ct_ttl_img03.gif" width="12" height="37">
		</td>
	</tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
	
		<td align="right">
		<select name="searchCondition" class="ct_input_g" style="width:80px">
			<%-- 	<option value="0" <%= (searchCondition.equals("0") ? "selected" : "")%>>상품번호</option>
					<option value="1" <%= (searchCondition.equals("1") ? "selected" : "")%>>상품명</option>--%>
					<option value="0" ${! empty search.searchCondition && search.searchCondition==0 ? "selected" : "" }>상품번호</option>
					<option value="1" ${search.searchCondition==1 ? "selected" : "" }>상품명</option>
					<option value="2" ${search.searchCondition==2 ? "selected" : "" }>낮은가격순</option>
					<option value="3" ${search.searchCondition==3 ? "selected" : "" }>높은가격순</option>
			</select>
			
			<input 	type="text" name="searchKeyword"  value="${! empty search.searchKeyword ? search.searchKeyword : ""}" 
							class="ct_input_g" style="width:200px; height:19px" >
							<button id="search" onclick="javascript:fncGetProductList('1');">검색</button>
		</td>
	
					
						
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td colspan="11" >전체 ${resultPage.totalCount } 건수, 현재  ${resultPage.currentPage } 페이지</td>
	</tr>
	<tr>
		<td class="ct_list_b" >No</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" >상품명</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" >가격</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" >등록일</td>	
		<td class="ct_list_b" >현재상태</td>
		<td class="ct_line02"></td>
		
			
	</tr>
	<tr>
		<td colspan="11" bgcolor="808285" height="1"></td>
	</tr>
	
	 
	<%--<% 	
		int no=list.size();
		System.out.println(no);
		for(int i=0; i<list.size(); i++) {
			ProductVO vo = (ProductVO)list.get(i);
	--%>
	
	<c:set var="i" value="0" />
	<c:forEach var="product" items="${list}">
	<c:set var="i" value="${i+1 }"/>
	<tr class="ct_list_pop">
			<td align="center">${ i }</td>
		<td></td>
		<td align="left">
			<a href="/getProduct.do?prodNo=${product.prodNo }&menu=${menu}">${product.prodName}</a>
		</td>
		<td></td>
		<td align="left">${product.price}</td>
		<td></td>
		<td align="left">${product.regDate}
		</td>
		
		<c:if test="${! (menu eq 'manage') }">
		<c:if test="${product.proTranCode==null}">
		<%--<%if(vo.getProTranCode()==null){ --%>		
		<td align="left">판매중
		</c:if>
		<c:if test="${!(product.proTranCode==null)}">
		<td align="left">재고없음.
		</c:if>
		</c:if>
		
		<c:if test="${ menu eq 'manage' }">
		<c:if test="${product.proTranCode==null}">
		<td align="left">판매중
		</c:if>
		<c:if test="${product.proTranCode=='0  '}">
		<td align="left">구매완료 <a href="/updateTranCode.do?tranCode=${product.proTranCode}&prodNo=${product.prodNo}">배송하기</a>
		</c:if>
		<c:if test="${product.proTranCode=='1  '}">
			<td align="left">배송중
		</td>	
		</c:if>
		<c:if test="${product.proTranCode=='2  '}">
			<td align="left">배송완료
		</td>	
		</c:if>
		</c:if>
		
		<%--<%}else{ --%>
		</td>	
		<%--<%} --%>
	</tr>
	<tr>
		<td colspan="11" bgcolor="D6D7D6" height="1"></td>
	</tr>
	
	<%--<% } --%>
	</c:forEach>
</table>

	
	

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
	<td align="center">
	<input type="hidden" id="currentPage" name="currentPage" value=""/>
	
	<!-- jsp include 말고 EL의 import를 사용 해서 pageNavigator를 인터페이스화 시킴 .. 장점 어떤 page던 페이지 value(fnc)만 바꿔주면 다 사용가능 -->
	<c:set var="fnc" value="fncGetProductList" scope="request" />
	
	<c:import var="pageNavi" url="/common/pageNavigator.jsp" scope="request"/>
	${pageNavi}
	
	
		
    	</td>
	</tr>
</table>
<!--  페이지 Navigator 끝 -->
</form>
</div>

</body>
</html>
