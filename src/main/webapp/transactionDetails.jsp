<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
     <%		
		if(session.getAttribute("username")==""||session.getAttribute("username")==null)
		{
			response.sendRedirect("login.jsp");
		}
	%>
    <%@taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.min.js" integrity="sha384-BBtl+eGJRgqQAUMxJ7pMwbEyER4l1g+O15P+16Ep7Q9Q+zqX6gSbd85u4mG4QzX+" crossorigin="anonymous"></script>
  <link href="css/registration.css" rel="stylesheet" />
</head>
<body>
	<%@include file="include/navbar.jsp" %>
	
	<div class="container-lg text-center bg-dark">
  		<div class="row bg-light my-2 fw-bold">

		    <div class="col p-4">
		      Account Number
		    </div>
		    <div class="col p-4">
		      Detail
		    </div>
		    <div class="col p-4">
		      Date
		    </div>
		    <div class="col p-4">
		      Amount
		    </div>
		    <div class="col p-4">
		      Balance
		    </div>
		</div>
		<C:forEach items="${transactionList}" var="s">
			
		
		
					<div class="row bg-light my-2">
				    <div class="col p-4">
				      ${s.getAccount_no()}
				    </div>
				    <div class="col p-4">
				      ${s.getDetail()}
				    </div>
				    <div class="col p-4">
				      ${s.getTransaction_date()}
				    </div>
				    <div class="col p-4">
				      ${s.getAmount()}
				    </div>
				    <div class="col p-4">
				      ${s.getBalance()}
				    </div>
				    
		  		</div>

		</C:forEach>
  		
	</div>
	
</body>
</html>