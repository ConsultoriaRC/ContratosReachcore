<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Mis contratos</title>
<link href="https://fonts.googleapis.com/css?family=Roboto|Varela+Round" rel="stylesheet">
<link rel="stylesheet" href="css/font-awesome.min.css">
<link rel="stylesheet" href="css/bootstrap.min.css">
<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
	<!-- javascript libraries -->
	<script src="js/jquery-3.4.1.js" type="text/javascript"></script>
	<!-- script src="js/app-ajax.js" type="text/javascript"></script>  -->
	
	<style type="text/css">
		body {
			font-family: 'Varela Round', sans-serif;
		}
	</style>
	
	<script type="text/javascript">
	$(document).on("click", "#btncontratos", function() {
		$.post("deals", function(responseText) { 
	        $("#divselect").html(responseText);
	    });
	});	
	</script>
	<script type="text/javascript">
	$(document).on("click", "#btnconvers", function() {
		$.get("conver", function(responseText) { 
	        $("#divselect").html(responseText);
	    });
	});	
	</script>
	<script type="text/javascript">
		$(document).ready(function() {
			$.post("deals", function(responseText) { 
		        $("#divselect").html(responseText);
			});
		});
	</script>
	
<style type="text/css">
    body {
font-family: 'Nunito Sans', sans-serif;
	}
	.modal-login {
		color: #636363;
		width: 650px;
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
	}
	.modal-login  .form-group {
		position: relative;
	}
	.modal-login i {
		position: absolute;
		left: 13px;
		top: 11px;
		font-size: 18px;
	}
	.modal-login .form-control {
		padding-left: 40px;
	}
	.modal-login .form-control:focus {
		border-color: #12b5e5;
	}
	.modal-login .form-control, .modal-login .btn {
		min-height: 40px;
		border-radius: 3px; 
        transition: all 0.5s;
	}
	.modal-login .close {
        position: absolute;
		top: -5px;
		right: -5px;
	}
    .modal-login input[type="checkbox"] {
        margin-top: 1px;
    }
    .modal-login .forgot-link {
        color: #12b5e5;
        float: right;
    }
	.modal-login .btn {
		background: #12b5e5;
		border: none;
		line-height: normal;
	}
	.modal-login .btn:hover, .modal-login .btn:focus {
		background: #10a3cd;
	}
	.modal-login .modal-footer {
		color: #999;
		border: none;
		text-align: center;
		border-radius: 5px;
		font-size: 13px;
        margin-top: -20px;
		justify-content: center;
	}
	.modal-login .modal-footer a {
		color: #12b5e5;
	}
	.trigger-btn {
		display: inline-block;
		margin: 100px auto;
	}	
	
</style>
</head>
	<body>

	<!--Menú-->
		
		<nav class="navbar navbar-inverse">
	  		<div class="container-fluid" >
			    <div class="navbar-header" >
    		  	<!--<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
        		<span class="sr-only">Toggle navigation</span>
	        	<span class="icon-bar"></span>
    	    	<span class="icon-bar"></span>
	        	<span class="icon-bar"></span>
	    	  	</button>-->
    		  		<a class="navbar-brand" href="#">Portal de administración de Contratos de servicios Reachcore </a>
	    		</div>
		    	<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
    		 		 <ul class="nav navbar-nav">
				        <li class="active"><a id="btncontratos" href="#">Mis contratos <span class="sr-only">(current)</span></a></li>
    	    			<li><a id="btnconvers" href="#">Mis conversaciones<span class="sr-only">(current)</span></a></li>
      				</ul>
					<ul class="nav navbar-nav navbar-right">
    	    			<li class="dropdown">
        	  				<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Mi cuenta <span class="caret"></span></a>
          					<ul class="dropdown-menu">
            				<!--<li><a href="#">Cerrar sesión</a></li>-->
								<li style="float:right">
									<form action="close" METHOD="POST">
										<input type="submit" value="Cerrar sesión" class="btn btn-link" />
									</form>
								</li>
    	      				</ul>	
        				</li>
      				</ul>
    			</div>
 			</div>
		</nav>
		
		<div class="panel panel-default" id="divselect">
 		
		</div>
	
	</body>
</html>  