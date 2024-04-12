<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"
    isELIgnored="false"
    %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath"  value="${pageContext.request.contextPath}" />

<script type="text/javascript">
	var loopSearch=true; /*false 되면 함수 실행 중단*/
	function keywordSearch(){
		if(loopSearch==false)
			return;
	 var value=document.frmSearch.searchWord.value; // 사용자가 입력한 검색어 가져와서 value 변수에 저장
		$.ajax({ // jQuery의 $.ajax 메소드를 이용해 서버에 비동기적으로 검색 요청을 보냄.
			type : "get", //요청 방식 get
			async : true, //false인 경우 동기식으로 처리한다. 여기선 비동기 처리.
			url : "${contextPath}/goods/keywordSearch.do",
			data : {keyword:value},
			success : function(data, textStatus) { //서버로부터 응답이 오면 callback함수를 통해 응답 data를 처리함.
			    var jsonInfo = JSON.parse(data); //데이터는 JSON 형식으로 가정하고, JSON.parse(data)를 통해 객체로 변환.
				displayResult(jsonInfo); // 변환한 데이터를 displayResult 함수에 전달.
			},
			error : function(data, textStatus) {
				alert("에러가 발생했습니다." + textStatus + ": " + errorThrown);
			},
			complete : function(data, textStatus) {
				//alert("작업을완료 했습니다");
				
			}
		}); //end ajax	
	}
	
	function displayResult(jsonInfo){ //keywordSearch 함수로부터 전달받은 JSON 객체(jsonInfo)를 처리.
		var count = jsonInfo.keyword.length; //keyword 배열의 길이(count)를 계산하여, 검색 결과가 있는지 확인
		if(count > 0) { //결과가 있으면, 동적으로 HTML을 생성하여 검색 제안 목록을 구성
		    var html = '';
		    for(var i in jsonInfo.keyword){
			   html += "<a href=\"javascript:select('"+jsonInfo.keyword[i]+"')\">"+jsonInfo.keyword[i]+"</a><br/>";
		    } // 각 제안 항목을 클릭했을 때 select 함수를 호출하도록 설정
		    var listView = document.getElementById("suggestList");
		    listView.innerHTML = html;
		    show('suggest'); //최종적으로 생성된 HTML을 listView라는 ID를 가진 요소의 innerHTML에 설정하여 화면에 표시.
		}else{
		    hide('suggest'); //검색 결과가 없으면, hide('suggest') 함수를 호출하여 제안 목록을 숨김.
		} 
	}
	
	function select(selectedKeyword) {
		 document.frmSearch.searchWord.value=selectedKeyword;
		 loopSearch = false;
		 hide('suggest');
	}
		
	function show(elementId) {
		 var element = document.getElementById(elementId);
		 if(element) {
		  element.style.display = 'block';
		 }
		}
	
	function hide(elementId){
	   var element = document.getElementById(elementId);
	   if(element){
		  element.style.display = 'none';
	   }
	}

</script>
<body>
	<div id="logo">
	<a href="${contextPath}/main/main.do">
		<img width="176" height="80" alt="booktopia" src="${contextPath}/resources/image/Booktopia_Logo.jpg">
		</a>
	</div>
	<div id="head_link">
		<ul>
		   <c:choose>
		     <c:when test="${isLogOn==true and not empty memberInfo }">
			   <li><a href="${contextPath}/member/logout.do">로그아웃</a></li>
			   <li><a href="${contextPath}/mypage/myPageMain.do">마이페이지</a></li>
			   <li><a href="${contextPath}/cart/myCartList.do">장바구니</a></li>
			   <li><a href="#">주문배송</a></li>
			 </c:when>
			 <c:otherwise>
			   <li><a href="${contextPath}/member/loginForm.do">로그인</a></li>
			   <li><a href="${contextPath}/member/memberForm.do">회원가입</a></li> 
			 </c:otherwise>
			</c:choose>
			   <li><a href="#">고객센터</a></li>
      <c:if test="${isLogOn==true and memberInfo.member_id =='admin' }">  
	   	   <li class="no_line"><a href="${contextPath}/admin/goods/adminGoodsMain.do">관리자</a></li>
	    </c:if>
			  
		</ul>
	</div>
	<br>
	<div id="search" >
		<form name="frmSearch" action="${contextPath}/goods/searchGoods.do" >
			<input name="searchWord" class="main_input" type="text"  onKeyUp="keywordSearch()"> 
			<input type="submit" name="search" class="btn1"  value="검 색" >
		</form>
	</div>
   <div id="suggest">
        <div id="suggestList"></div>
   </div>
</body>
</html>