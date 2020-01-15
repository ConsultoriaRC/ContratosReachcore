<%@ page import="java.util.ArrayList, com.rc.model.Contrato, javax.servlet.http.HttpSession" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="panel-heading">Mis Contratos</div>
	<div class="panel-body">
	<c:forEach var="contrato" begin="0" items="${requestScope.contratos}" varStatus="iterator">
		<div class="panel panel-primary">
			<table class="table">
  				<tr class="bg-primary">
					<th width="35%" colspan="2">Nombre</th>
					<th width="15%">Status</th>
					<th width="09%">Servicios</th>
					<th width="09%">Fecha de inicio</th>
					<th width="10%">Fecha de finalización</th>
					<th width="08%">Duración</th>
					<th width="07%"></th>
					<th width="07%"></th>
				</tr>
	 			<tr>
					<td colspan="2"> <c:out value="${contrato.getName()}"/></td>
					<c:choose>
						<c:when test="${contrato.getStatus() == 'Negociación' && (contrato.getActivacionContrato().getVoboCliente() == false && contrato.getActivacionContrato().getVoboComercial() == false)}">
							<td><span class="label label-default">En <c:out value="${contrato.getStatus()}"/></span>
							
								<input type="submit" name="btnactivar" value="Aprobar" id="btnactivar${iterator.index}" data-contractid="${contrato.getId()}"  data-consecuti="${iterator.index}" class="btn-success btn-xs"/>
							</td>
						</c:when>
						<c:when test="${contrato.getStatus() == 'Negociación' && (contrato.getActivacionContrato().getVoboCliente() == true && contrato.getActivacionContrato().getVoboComercial() == false)}">
							<td><span class="label label-default">En <c:out value="${contrato.getStatus()}"/></span>
								<span class="label label-info">En espera de VoBo comercial</span>
							</td>
						</c:when>
						<c:otherwise>
							<td><span class="label label-success"><c:out value="${contrato.getStatus()}"/></span>
						</c:otherwise>
					</c:choose>
					<td>
						<table>
							<tr>
								<c:forEach var="servicio" begin="0" items="${contrato.getServicio()}">
									<td><c:out value="${servicio}" /></td>
								</c:forEach>
							</tr>
						</table>
					</td>
					<td><c:out value="${contrato.getFinicio()}"/></td>
					<td><c:out value="${contrato.getFfin()}"/></td>
					<td><c:out value="${contrato.getDuracion()}"/> meses</td>
					<td>
						<div id="callFact${iterator.index}" name="callFact">
							<input type="hidden" name="fcontractId" id="fcontractId${iterator.index}" value="${contrato.getId()}"/>
							<input type="hidden" name="fcontractNm" id="fcontractNm${iterator.index}"  value="${contrato.getName()}"/> 
							<input type="submit" name="verFact" id="verFact${iterator.index}" value="Ver Facturas" data-contractid="${contrato.getId()}" data-contractnm="${contrato.getName()}" class="btn-primary	 btn"/> 
						</div>
					</td>
					<td>
						<a href="#uploadNewModal${iterator.index}" class="btn-primary btn" id="modal-newconver${iterator.index}" data-toggle="modal"> Dejar un comentario </a>
					</td>
					
				</tr>
				<tr class="bg-primary">
					<th>Tipo de facturación</th>
                    <th>Términos de pago</th>
                    <th>Pago del servicio</th>
                    <th>Valor del contrato</th>
                    <th></th>
                    <th></th>
                    <th></th>
                    <th></th>
                    <th></th>
				</tr>
				<tr>
					<td><c:out value="${contrato.getPlazoPago()}" /></td>
					<td><c:out value="${contrato.getTerminosPago()}"/></td>
					<td><c:out value="${contrato.getPagoServicio()}"/></td>
					<td><c:out value="${contrato.getMoneda()}"/> <c:out value="${contrato.getValor()}"/></td>
					
				</tr>
				
			</table>
		</div>
			<div id="uploadNewModal${iterator.index}" class="modal fade">
							<div class="modal-dialog modal-login">
								<div class="modal-content">
									<div class="modal-header">				
										<h4 class="modal-title">Deja un comentario</h4>
										<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
									</div>
									<div class="modal-body">
										<div class="form-group">
											<form method="POST" enctype="multipart/form-data" id="upload-form${iterator.index}">
												<div class="custom-file" id="divupload">
													<label class="custom-file-label" for="customFileLang">Adjunta evidencia en caso de ser necesario (un recibo, una factura, etc.)</label>
													<input type="file" class="custom-file-input" id="customFileLang${iterator.index}" lang="es" accept=".pdf,.jpg,.png">
													<input type="submit" name="btn-upload" value="Adjuntar" id ="btn-upload${iterator.index}" data-consecuti="${iterator.index}" class="btn-primary btn-xs" />
												</div>				
											</form>
										</div>
					
										<div class="form-group">		
											<select name="motivo" class="form-control" id="motivo${iterator.index}">
												<option value="default" selected>Selecciona una opción...</option>
												<option value="cotizacion">Corrección de cotización</option>
												<option value="factura">Corrección de factura</option>
												<option value="consumo">Solicitar reporte de consumo</option>
												<option value="adicionales">Cotización de servicios adicionales</option>
												<option value="ficha">Adjuntar ficha de pago</option>
											</select>
							
										</div>
										<div class="form-group">
											Deja tu comentario:
											<textarea name="newconver" id="tnewconver${iterator.index}" class="form-control"></textarea>
										</div>
										<div class="form-group" id="divnewconver${iterator.index}" >
											<input type="submit" name="btn-newconver" value="Enviar" id="btn-newconver${iterator.index}" data-contractid="${contrato.getId()}" data-contractnm="${contrato.getName()}" data-consecuti="${iterator.index}" class="btn-primary btn-block btn-sm" data-dismiss="modal" />
										</div>
									</div>
								</div>			
							</div>
						</div>
	</c:forEach>
	</div>

	
<script type="text/javascript">
	$(document).on("click", "input[name=btn-upload]",function(event) {
		// $("#testFileUpload").on('submit', function(event) {
		 //stop submit the form, we will post it manually.
        event.preventDefault();
        i = $(this).data("consecuti");
        //var form = $("#upload-form" +i)[0];
        //var data = new FormData(form);
        var data = new FormData();
        data.append("file",$("#customFileLang" + i)[0].files[0]);
		
        // Get form
        
		// Create an FormData object 
        
		// disabled the submit button
        $("#btnSubmit" + i).prop("disabled", true);

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
                $("#btnSubmit" + i).prop("disabled", true);
            },
            error: function (e) {
            	alert("consecutivo:" + i);
				console.log("ERROR : ", e);
                $("#btnSubmit" + i).prop("disabled", false);
            }
        });

    });
</script>

<script type="text/javascript">
	$("div[name=callFact]").on("click","input",function(){
   		//contractId =$('input[name=fcontractId]').val($('#fcontractId').val());
   		//contractNm =$('input[name=fcontractNm]').val($('#fcontractNm').val());
   		contractId = $(this).data("contractid");
   		//contractNm =$('input[name=fcontractNm]').val();
   		contractNm = $(this).data("contractnm");
   		$.post("Srvlt_facturas", { contractId: contractId, contractNm: contractNm }, function(responseText) { 
	        $("#divselect").html(responseText);
	    });
    });
	
</script>

<script type="text/javascript">
	$(document).on("click", "input[name=btnactivar]",function() {
		i = $(this).data("consecuti");
		$("#btnactivar"+i).attr("disabled", true);
		//contractid = $('input[name=contractId]').val($(this).val());
		contractid = $(this).data("contractid");
		$.post("deals", { activar: true, contractId: contractid}, function(responseText) { 
	        $("#divselect").html(responseText);
	    });
	});	
</script>
 
<script type="text/javascript">

		$("input[name=btn-newconver]").unbind('click').bind("click", function(event) {
			event.preventDefault();
			i = $(this).data("consecuti");
			$("#btn-newconver"+i).attr("disabled", true);
			//contractid = $('input[name=contractId]').val();
			contractid = $(this).data("contractid");
			contratonm = $(this).data("contractnm");
			comentario = $("#tnewconver"+i).val();
			motivo = $("#motivo"+i).children("option:selected").val();
			$.post("conver", {contractId: contractid, contratoNm: contratonm, newconver: comentario, motivo: motivo}, function(responseText) {
				//$("#divselect").html(responseText);
				alert("!Listo! Desde la pestaña Conversaciones podras darle seguimiento.");
				//alert(contractid + ", comentario:" + comentario + ", motivo:" + motivo + " id:" + i);
					
	 	   });
			$("#btn-newconver"+i).removeAttr("disabled");
			return true;
		});	
	</script>
	<script type="text/javascript">
		function validarConver(){
		var coment;
	coment =  $.trim($('textarea[name=newconver]').val());
	if(coment === ""){
		alert("No has añadido ningún comentario.");
		return false;
	}
}
	</script>			