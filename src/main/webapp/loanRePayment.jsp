<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%		
		if(session.getAttribute("username")==""||session.getAttribute("username")==null)
		{
			response.sendRedirect("login.jsp");
		}
	%>
<!DOCTYPE html>
<html>
<head>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.min.js" integrity="sha384-BBtl+eGJRgqQAUMxJ7pMwbEyER4l1g+O15P+16Ep7Q9Q+zqX6gSbd85u4mG4QzX+" crossorigin="anonymous"></script>
<link href="css/registration.css" rel="stylesheet" />
<meta charset="ISO-8859-1">
<title>Transaction page</title>
</head>
<body>
	<%@include file="include/navbar.jsp" %>
	
		<div class="container">
     	<div class="wrapper">
		<div class="title"><span>Re payment</span></div>
        <form method="post" action="transaction?id=${account.getId()}&account_type=${account.getAccount_type()}" id="createaccount-form">
        <label>Account Number</label><label class="offset-md-1"> :</label><label> ${account.getAccount_no()}</label><br/><br/>
        <div class="row">
        	<label for="transaction">Choose a Transaction Type:</label>
				<select id="transactiontype" name="transactiontype">
  				<option value="repayment">re payment</option>
  				
			</select>
		</div>
          <div class="row">
            <input type="number" name="amount" id="amount" placeholder="Amount" required>
          </div>
          <div class="row button">
            <input type="submit" value="Submit">
          </div>
      		
        </form>
        	<div class="m-2"></div>
            <a href="home.jsp"><button class="btn btn-secondary w-100 mt-2">Back</button></a>
            
        </div>
      </div>
 
</body>
</html>