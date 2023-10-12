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
	
	<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
  <div class="container-fluid">
    <h1 class="navbar-brand">Finance Management System</h1>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavDropdown" aria-controls="navbarNavDropdown" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse offset-md-3" id="navbarNavDropdown">
      <ul class="navbar-nav">
        <li class="nav-item">
          <a class="nav-link active" aria-current="page" href="home.jsp">Home</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="LoanAccount">Manage Loan</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="FixedDeposit">Manage Fixed Deposit</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="SavingsAccount">Manage Saving</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="createaccount">Create Accounts</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="login">Log Out</a>
        </li>
      </ul>
    </div>
  </div>
</nav>
	
	<div class="container-lg text-center bg-dark">
  		<div class="row bg-light my-2 fw-bold">
		    <div class="col p-4 ">
		      Number
		    </div>
		    <div class="col p-4">
		      Account Number
		    </div>
		    <div class="col p-4">
		      Balance
		    </div>
		    <div class="col p-4">
		      Interest Earned
		    </div>
		    <div class="col p-4">
		      Do Transaction
		    </div>
		    <div class="col p-4">
		      See Transaction
		    </div>
		</div>
		<C:forEach items="${accountList}" var="s">
			
		
		
					<div class="row bg-light my-2">
				    <div class="col p-4">
				     ${s.getId()}
				    </div>
				    <div class="col p-4">
				      ${s.getAccount_no()}
				    </div>
				    <div class="col p-4">
				      ${s.getBalance()}
				    </div>
				    <div class="col p-4">
				      ${s.getInterestEarned()}
				    </div>
				    <div class="col p-4">
				      <a href="transaction?id=${s.getId()}&account_type=${s.getAccount_type()}"><button class="btn btn-sm btn-success">Do Transaction</button></a>
				    </div>
				    <div class="col p-4">
				      <a href="TransactionDetails?id=${s.getId()}&account_type=${s.getAccount_type()}"><button class="btn btn-sm btn-primary">See Transaction</button></a>
				    </div>
		  		</div>

		</C:forEach>
  		
	</div>
	
</body>
</html>