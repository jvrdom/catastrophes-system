<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>



	<%@ page import="managedBeans.AccountBean" %>
	<%@ page import="java.util.Date" %>
	<%@ page import="javax.ejb.EJB" %>
	<%@ page import="javax.faces.bean.ManagedBean" %>
	<%@ page import="com.ssacn.ejb.business.remote.UserManagerRemote" %>

		<script>
		  // Esto es llamado con los resultados de FB.getLoginStatus ().
		  function statusChangeCallback(response) {
		    console.log('statusChangeCallback');
		    console.log(response);
		    
		    // El objeto de respuesta es retornado con un campo de estado que permite a la aplicación de conocer el estado de inicio de sesión actual de la persona.
		    // Documentos completos sobre el objeto de respuesta se puede encontrar en la documentación para FB.getLoginStatus ().
		    
		    if (response.status === 'connected') {
		      // Logueado en su aplicación y Facebook.      
		      testAPI();
		    } else if (response.status === 'not_authorized') {
		      // La persona se Logueo en Facebook, pero no en su aplicacion.
		      document.getElementById('status').innerHTML = 'Please log ' +
		        'into this app.';
		    } else {
			// La persona no se logueo en facebook, por lo que no estamos seguros de si están registrados en esta aplicación o no.    
		      
		      document.getElementById('status').innerHTML = 'Please log ' +
		        'into Facebook.';
		    }
		  }
		
		  
		// Esta función se llama cuando alguien termina con el botón Login. 
		// Ver el manejador log on que se le anexa en el código de ejemplo siguiente.  
		  function checkLoginState() { 
		    FB.getLoginStatus(function(response) { statusChangeCallback(response); });
		  }
		
		  window.fbAsyncInit = function() {
		  FB.init({
		    appId      : 1529143143999592,
		    cookie     : true,  // enable cookies to allow the server to access 
		                        // the session
		    xfbml      : true,  // parse social plugins on this page
		    version    : 'v2.1' // use version 2.1
		  });
		
		  
		//  Ahora que hemos inicializado el SDK de JavaScript, llamamos a FB.getLoginStatus ().
		//  Esta función obtiene el estado de la persona que visita esta página 
		//  y puede devolver uno de tres estados para la devolución de llamada que usted proporciona.
		  
		//     1. accedido a su aplicación ("conectado")
		//     2. iniciado sesión en Facebook, pero no su aplicación ('not_authorized')
		//     3. No iniciar sesión en Facebook y no se puede decir si se registran en su aplicación o no.
		//     Estos tres casos son manejados en la función de devolución de llamada
		
		  FB.getLoginStatus(function(response) { statusChangeCallback(response); });
		
		  };
		
		
		// Carga del SDK de forma asíncrona
		
		  (function(d, s, id) {
		    var js, fjs = d.getElementsByTagName(s)[0];
		    if (d.getElementById(id)) return;
		    js = d.createElement(s); js.id = id;
		    js.src = "//connect.facebook.net/en_US/sdk.js";
		    fjs.parentNode.insertBefore(js, fjs);
		  }(document, 'script', 'facebook-jssdk'));
		
		// Aquí se corre una prueba muy simple de la API de gráficos después de inicio de sesión se realiza correctamente.
		// Ver statusChangeCallback () para cuando se realiza la presente convocatoria.  
		  
		  function testAPI() {
		    console.log('Bienvenido!  Actualizando su informacion.... ');
		    FB.api('/me', function(response) {
		      console.log('Logueo satisfactorio para: ' + response.name);
		      document.getElementById('status').innerHTML =
		        'Gracias por loguearse, ' + response.name + response.first_name + response.last_name + response.gender + response.username + '!';		        
		<%				
	
		AccountBean ab = new AccountBean();
		
		
		String nom = "msr";
		String ape = "msr";
		String ema = "msr";
		String con = "msr";
		String sex = "msr";
		java.util.Date fecha = new Date();
		
		ab.setNombre(nom);
		ab.setApellido(ape);
		ab.setEmail(ema);
		ab.setContraseña(con);
		ab.setSexo(sex);
		ab.setFechaNac(fecha);
		
		// ab.altaUsuario();
	%>


		    });
		  }
		</script>
		
		
		<!-- A continuación incluimos el boton de logueo.
		     Este botón utiliza el SDK de JavaScript para presentar un botón de Inicio de sesión gráfica
		     que activa la función FB.login () cuando se hace clic. -->		
		
		<fb:login-button scope="public_profile,email" onlogin="checkLoginState();">
		</fb:login-button>
		
		<div id="status">  </div>
	

</body>
</html>