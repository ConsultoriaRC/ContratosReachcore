<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="en">
	<head>
		<meta name="google-site-verification" content="yelqoTFYlM8vzocOal038MlMeAsGVDnWkSH0fQTdTzc" />
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>Iniciar sesión</title>
		<link href="https://fonts.googleapis.com/css?family=Roboto|Varela+Round" rel="stylesheet">
		<link rel="stylesheet" href="css/font-awesome.min.css">
		<link rel="stylesheet" href="css/bootstrap.min.css">
		<script src="js/jquery.min.js"></script>
		<script src="js/bootstrap.min.js"></script>
		<script src="js/jquery-3.4.1.js" type="text/javascript"></script>
		<style type="text/css">
    		body {
				font-family: 'Nunito Sans', sans-serif;
			}	
			.modal-login {		
				color: #636363;
				width: 350px;
			}
			.modal-login .modal-content {
				padding: 20px;
				border-radius: 5px;
				border: none;
			}
			.modal-login .modal-header {
				border-bottom: none;   
        		position: relative;
        		justify-content: center;
			}
			.modal-login h4 {
				text-align: center;
				font-size: 26px;
				margin: 30px 0 -15px;
			}
			.modal-login .form-control:focus {
				border-color: #70c5c0;
			}
			.modal-login .form-control, .modal-login .btn {
				min-height: 40px;
				border-radius: 3px; 
			}
			.modal-login .close {
        		position: absolute;
				top: -5px;
				right: -5px;
			}	
			.modal-login .modal-footer {
				background: #ecf0f1;
				border-color: #dee4e7;
				text-align: center;
        		justify-content: center;
				margin: 0 -20px -20px;
				border-radius: 5px;
				font-size: 13px;
			}
			.modal-login .modal-footer a {
				color: #999;
			}		
			.modal-login .avatar {
				position: absolute;
				margin: 0 auto;
				left: 0;
				right: 0;
				top: -70px;
				width: 95px;
				height: 95px;
				border-radius: 50%;
				z-index: 9;
				background: #F0B500;
				padding: 15px;
				box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.1);
			}
			.modal-login .avatar img {
				width: 100%;
			}
			.modal-login.modal-dialog {
				margin-top: 80px;
			}
   			 .modal-login .btn {
		        color: #fff;
        		border-radius: 4px;
				background: #F0B500;
				text-decoration: none;
				transition: all 0.4s;
		        line-height: normal;
		        border: none;
    		}
			.modal-login .btn:hover, .modal-login .btn:focus {
				background: #484848;
				outline: none;
			}
			.trigger-btn {
				display: inline-block;
				margin: 100px auto;
			}
		</style>

		<script type="text/javascript">
			function validate(){
				var c = document.getElementById("correo").value;
				var p = document.getElementById("passw").value;
				if(c == ""){
					alert("No has ingresado el correo.");
					return false;
				}
				if(p == ""){
					alert("No has ingresado tu contraseña.");
					return false;
				}			
			}
		</script>
		
	</head>
	<body>

		<div class="modal-dialog modal-login">
			<div class="modal-content">
				<div class="modal-header">
					
						<img src="img/reachcore.png" width="280" height="75">
									
					<h4 class="modal-title">Contratos</h4>	
				</div>
				<div class="modal-body">
					<form action="menu" method="post">
						<div class="form-group">
							<input type="text" class="form-control" name="correo" placeholder="Correo electrónico" id="correo" required="required">		
						</div>
						<div class="form-group">
							<input type="password" class="form-control" name="passw" placeholder="Contraseña" id="passw" required="required">	
						</div>        
						<div class="form-group">
							<button type="submit" onClick="return validate()" class="btn btn-primary btn-lg btn-block login-btn">Iniciar sesión</button>
						</div>
					</form>
					<c:choose>
						<c:when test="${not empty requestScope.message && requestScope.type == 'error'}">
							<div class="alert alert-danger" role="alert"><c:out value="${requestScope.message}"/></div>
						</c:when>
						<c:otherwise>
							<span><c:out value="${requestScope.message}"/></span>
						</c:otherwise>
					</c:choose>
					<div class="small"><a href="RecuperarPassw.jsp">¿Olvidaste tu contraseña?</a></div>
				</div>
				<div class="modal-footer">
					<a href="registro.jsp">Registrarse</a>
				</div>
			</div>
		</div> 
	</body>
</html> 