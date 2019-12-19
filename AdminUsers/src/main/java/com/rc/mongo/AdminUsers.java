package com.rc.mongo;


public class AdminUsers {
	public static String EnviaInvitacion(String correo){
		
		String Subject = "tst-Invitaci�n para registrarte";
		String mensaje = "Da clic en la siguiente liga para registrarte en el portal a de administraci�n de Contratos de Reachcore: \r\n http://rcproveedores-slb.reachcore.net/ProveedoresSLB/registro.jsp";
		try {

			return CorreoConnection.enviarCorreo(correo, Subject, mensaje);

		} 
		catch (Exception e) {
			return "error";
		}
	}
	
	public static String EnviaRecuperarPassw(String correo){
		
		String Subject = "tst-Solicitud de nueva contrase�a";
		String mensaje = "Se ha realizado la solicitud para establecer una nueva contrase�a para poder ingresar al portal de Proveedores SLB. \r\n" + 
				"  \r\n" + 
				"Para establecer la nueva contrase�a, da clic en el siguiente enlace para poder continuar con el proceso. \r\n";
	
		try {
			
			mensaje = mensaje + "Atentamente, \r\n Servicio al cliente Reachcore";
			return CorreoConnection.enviarCorreo(correo, Subject, mensaje);

		} 
		catch (Exception e) {
			return "error";
		}
	}
	
}

    