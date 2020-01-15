/**
 * 
 */
function genera_tabla() {
  // Obtener la referencia del elemento body
  var body = document.getElementsByTagName("body")[0];
 
  // Crea un elemento <table> y un elemento <tbody>
  var tabla   = document.createElement("table");
  var tblBody = document.createElement("tbody");
 
  // Crea las celdas
    // Crea las hileras de la tabla
    var hilera = document.createElement("tr");
 
      // Crea un elemento <td> y un nodo de texto, haz que el nodo de
      // texto sea el contenido de <td>, ubica el elemento <td> al final
      // de la hilera de la tabla
      var celda1 = document.createElement("td");
      var textoCelda1 = document.createTextNode("Nombre");
      celda1.appendChild(textoCelda1);
      
      var celda2 = document.createElement("td");
      var textoCelda2 = document.createTextNode("Status");
      celda2.appendChild(textoCelda2);
      
      var celda3 = document.createElement("td");
      var textoCelda3 = document.createTextNode("Servicios");
      celda3.appendChild(textoCelda3);
      
      var celda4 = document.createElement("td");
      var textoCelda4 = document.createTextNode("fecha de inicio");
      celda4.appendChild(textoCelda4);
      
      var celda5 = document.createElement("td");
      var textoCelda5 = document.createTextNode("Duración");
      celda5.appendChild(textoCelda5);
      
      
      hilera.appendChild(celda);
      hilera.appendChild(celda2);
      hilera.appendChild(celda3);
      hilera.appendChild(celda4);
      hilera.appendChild(celda5);
    
 
    // agrega la hilera al final de la tabla (al final del elemento tblbody)
    tblBody.appendChild(hilera);
  
 
  // posiciona el <tbody> debajo del elemento <table>
  tabla.appendChild(tblBody);
  // appends <table> into <body>
  body.appendChild(tabla);
  // modifica el atributo "border" de la tabla y lo fija a "2";
  tabla.setAttribute("border", "2");
}
function validarConver(){
	var coment;
	coment = document.getElementById("coment").value;
	if(coment === ""){
		alert("No has añadido ningún comentario.");
		return false;
	}
}