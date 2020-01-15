<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList, com.rc.model.Factura, com.rc.model.Concepto"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	
	<div class="panel-heading">Facturas relacionas al contrato <c:out value="${requestScope.invoices[0].getContractName()}"/></div>
	<c:forEach var="invoice" begin="0" items="${requestScope.invoices}">
	<div class="panel-body">
		<div class="panel panel-primary">
			<table class="table">
				<tr class="bg-primary">
					<td>Fecha de Facturación</td>
					<td>Status</td>
					<td>RFC</td>
					<td>Válido hasta</td>
					<td>Uso de CFDI</td>
					<td>Método de Pago</td>
					<td>Forma de Pago</td>
					<td>Moneda</td>			
				</tr>
				<tr>
					<td><c:out value="${invoice.getFechaFacturaci_n()}"/></td>
					<td><c:out value="${invoice.getStage()}"/></td>
					<td><c:out value="${sessionScope.account.rfc}"/></td>
					<td><c:out value="${invoice.getValidTill()}"/></td>
					<td><c:out value="${invoice.getUsoCFDI()}"/></td>
					<td><c:out value="${invoice.getM_todoPago()}"/></td>
					<td><c:out value="${invoice.getFormaPago()}"/></td>
					<td><c:out value="${invoice.getMoneda()}"/></td>				
				</tr>
			</table>
		
			<p>&nbsp;Conceptos</p>
			<table class="table">
				<tr class="bg-primary">
					<td>Producto</td>
					<td>Precio</td>
					<td>Cantidad</td>
					<td>Subtotal</td>
					<td>Descuento</td>
					<td>IVA</td>
					<td>Total</td>
				</tr>
				<c:forEach var="concepto" begin="0" items="${invoice.getConceptos()}">
					<tr>
						<td><c:out value="${concepto.getProducto()}"/></td>
						<td><c:out value="${concepto.getPricelist()}"/></td>
						<td><c:out value="${concepto.getQuantity()}"/></td>
						<td><c:out value="${concepto.getSubtotal()}"/></td>
						<td><c:out value="${concepto.getDiscount()}"/></td>
						<td><c:out value="${concepto.getTax()}"/></td>
						<td><c:out value="${concepto.getTotal()}"/></td>
					</tr>	
				</c:forEach>									
			</table>
			
			<table class="table">
				<tr align="right">
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td>Sub Total</td>
					<td align="left"><c:out value="${invoice.getMoneda()}"/> <c:out value="${invoice.getSubTotal()}"></c:out></td>
				</tr>
				<tr align="right">
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td >Impuesto</td>
					<td align="left"><c:out value="${invoice.getMoneda()}"/> <c:out value="${invoice.getTax()}"></c:out></td>		
				</tr>	
				<tr align="right">
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td ><b>Gran Total</b></td>
					<td align="left"><b><c:out value="${invoice.getMoneda()}"/> <c:out value="${invoice.getGrandTotal()}"></c:out></b></td>		
				</tr>								
			</table>
			
		</div>
	</div>
	</c:forEach>