<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList, com.rc.model.Conversacion, com.rc.model.Comentario, com.rc.model.Adjunto"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

	<div class="panel-heading">Mis conversaciones activas</div>
		<c:forEach var="conver" begin="0" items="${requestScope.conver}" varStatus="status">
		<div class="panel-body">
			<div class="panel panel-primary">
				<div class="panel-heading">
					<h3 class="panel-title"> <c:out value="${conver.getName()}" /></h3>
				</div>
				<table class="table">
					<tr>
						<td>Fecha de creación: <c:out value="${conver.getFechaCreaci_n()}"/></td>
						<td>Fecha de Modificación: <c:out value="${conver.getModifiedTime()}"/></td>
						<td>
							<span class="label label-success">Estado: Activo</span>
						</td>
					</tr>
					<tr>
						<td>Comentarios <a href="#uploadModal${status.index}" class="btn-primary btn-sm" data-toggle="modal"> Agregar un comentario </a>
							<div id="uploadModal${status.index}" class="modal fade">
								<div class="modal-dialog modal-login">
									<div class="modal-content">
										<div class="modal-header">				
											<h4 class="modal-title">Agregar un comentario</h4>
											<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
										</div>
										<div class="modal-body">
											<div class="form-group">
												<form action="UploadFile" method="post" enctype="multipart/form-data">
													<div class="custom-file">
														<label class="custom-file-label" for="customFileLang">Adjunta evidencia en caso de ser necesario (un recibo, una factura, etc.)</label>
														<input type="file" class="custom-file-input" id="customFileLang${status.index}" lang="es" accept=".pdf,.jpg,.png">
														<input type="submit" name="btn-adjuntar" value="Adjuntar" id="btn-adjuntar${status.index}" data-consecuti="${status.index}" class="btn-primary btn-xs"/>
													</div>				
												</form>
											</div>
											<div class="form-group">		
												<form action="conver" METHOD="POST">
													<div class="form-group">			
														Deja tu comentario:
														<textarea name="tnewcoment" id="tnewcoment${status.index}" class="form-control"></textarea>
													</div>
													<div class="form-group" id="divnewcoment${status.index}">
														<input type="submit" name="btn-newcoment" value="Enviar" id="btn-newcoment${status.index}" data-converid="${conver.getId()}" data-consecuti="${status.index}" class="btn-primary btn-block btn-sm" data-dismiss="modal" aria-hidden="true"/>
													</div>
												</form>
											</div>
										</div>
									</div>
								</div>
							</div>
						</td>
					<td/>
					<td/>
				</tr>
				<tr>
					<table class="table">
						<tr class="bg-primary">
							<td>Nombre</td>
							<td>Correo</td>
							<td>Comentario</td>
							<td>Fecha</td>
							<td>Adjuntos</td>
						</tr>
						<c:forEach var="comment" begin="0" items="${conver.getComentarios()}" varStatus="ic">
						<tr>
							<td><c:out value="${comment.getInterlocutor()}"/></td>
							<td><c:out value="${comment.getEmail()}"/></td>
							<td><c:out value="${comment.getComment()}"/></td>
							<td><c:out value="${comment.getFecha()}"/></td>
							<c:choose>
								<c:when test="${fn:length(conver.getAdjuntos()) gt ic.index}">
									<td>
										<a href="${conver.getAdjuntos()[ic.index].getServerLocation()}" download="${conver.getAdjuntos()[ic.index].getFileName()}">  <c:out value="${conver.getAdjuntos()[ic.index].getFileName()}"/></a>
									</td>
								</c:when>
								<c:otherwise>
									<td></td>
								</c:otherwise>
							</c:choose>
						</tr>
						</c:forEach>						
					</table>
				</tr>
			</table>
		</div>
	</div>
	</c:forEach>

<script type="text/javascript">
	var loading = false;
	$("input[name=btn-newcoment]").unbind('click').bind('click', function (event) {
	//$(document).on("click", "input[name=btn-newcoment]", function(event) {
		event.preventDefault();
		if(!loading){
			loading = true;
			i = $(this).data("consecuti");
			$("#btn-newcoment"+i).attr("disabled", true);
			//contractid = $('input[name=contractId]').val();
			converid =  $(this).data("converid");
			comentario = $("#tnewcoment"+i).val();
			$.post("conver", {converId: converid, newcoment: comentario}, function(responseText) {
				$("#divselect").html(responseText);
				//alert("!Listo! Desde la pestaña Conversaciones podras darle seguimiento.");
				//alert(contractid + ", comentario:" + comentario + ", motivo:" + motivo + " id:" + i);
					
			});
			loading = false;
			$("#btn-newcoment"+i).removeAttr("disabled");
		}
	});	
</script>
<script type="text/javascript">
	$(document).on("click", "input[name=btn-adjuntar]",function(event) {
		event.preventDefault();
        i = $(this).data("consecuti");
         var data = new FormData();
        data.append("file",$("#customFileLang" + i)[0].files[0]);
		
        // Get form
        
		// Create an FormData object 
        
		// disabled the submit button
        $("#btn-adjuntar" + i).prop("disabled", true);

        $.ajax({
            type: "POST",
            enctype: 'multipart/form-data',
            url: './UploadFile',
            data: data,
            processData: false,
            contentType: false,
            cache: false,
            timeout: 600000,
            success: function (msg) {
            	alert("data:" + msg);
	        	console.log("SUCCESS : ", msg);
                $("#btn-adjuntar" + i).prop("disabled", true);
            },
            error: function (e) {
            	alert("consecutivo:" + i);
				console.log("ERROR : ", e);
                $("#btn-adjuntar" + i).prop("disabled", false);
            }
        });

    });
</script>