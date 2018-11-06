<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%--
	Comprueba los errores que se han podido obtener al crear noticia y les añade formato
	SuppressWarnings para evitar el warning de type cast de "errores" (aunque esta bien hecho)
--%>
<%! @SuppressWarnings("unchecked") %>
<%
	UsuarioVO usuario = (UsuarioVO)request.getSession().getAttribute("usuario");
	if (usuario == null || usuario.getTipoUsuario() == TipoUsuario.PARTICIPANTE) {
		// El usuario no es un creador de contenido o admin, no deberia estar en esta página
		response.sendRedirect("errorInterno.html");
	} else if (request.getAttribute("errores") instanceof HashMap) {
		// Mostrar errores
		Map<String, String> errores = (HashMap<String, String>)request.getAttribute("errores");
		String estiloCabecera = "<i class=\"ml-10 ion-close color-red\"></i><span class=\"pl-5 font-10 color-red\">";
		String estiloFinal = "</span>";
		// Añadir formato
		for (String k : errores.keySet()) {
			errores.replace(k, estiloCabecera + errores.get(k) + estiloFinal);
		}
	}
%>
<!DOCTYPE HTML>
<html lang="es">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

	<title>Crear noticia - RadTree</title>
	<meta name="description" content="Formulario para la creación de noticias">
	<meta name="author" content="Grupo A: Gregorio Largo, Alonso Muñoz y Diego Royo">

	<!-- Font -->
	<link href="https://fonts.googleapis.com/css?family=Encode+Sans+Expanded:400,600,700" rel="stylesheet">

	<!-- Stylesheets -->
	<link href="plugin-frameworks/bootstrap.css" rel="stylesheet">
	<link href="fonts/ionicons.css" rel="stylesheet">
	<link href="common/styles.css" rel="stylesheet">
</head>
<body>

	<%@ include file="WEB-INF/header.jsp"%>

  <section class="ptb-0">
		<div class="mb-30 brdr-ash-1 opacty-5"></div>
		<div class="container">
			<a class="mt-10" href="index.html"><i class="mr-5 ion-ios-home"></i>Inicio<i class="mlr-10 ion-chevron-right"></i></a>
			<a class="mt-10" href="30_gestionContenido.html">Gestionar contenido<i class="mlr-10 ion-chevron-right"></i></a>
			<a class="mt-10 color-ash" href="">Editar reto</a>
		</div><!-- container -->
	</section>


	<section>
		<div class="container">

			<div class="row">
				<div class="col-md-12 col-lg-8">
					<h3 class="mb-30"><b>Editar reto</b></h3>
					<form name="crearReto" action="TODO.do" method="post">

						<div class="row form-block form-plr-15 form-h-45 form-mb-20 form-brdr-lite-white">

							<div class="col-sm-8">
								<label for="titulo">Título del reto</label>
								<input type="text" name="titulo" placeholder="Título" value="<c:out value="${sessionScope.reto.titulo}"/>"/>
							</div>

							<div class="col-sm-12">
								<label for="reto">Planteamiento del reto</label>
								<textarea class="p-10" type="text" name="reto" value="<c:out value="${sessionScope.reto.cuerpo}"/>"</textarea>
							</div>

						</div>

						<div class="row">
							<div class="col-sm-12 mt-30">
								<button class="w-100 btn-fill-primary" type="submit"><b>Finalizar edición</b></button>
							</div>
						</div>

					</form>
				</div>
			</div>

		</div><!-- container -->
	</section>

	<%@ include file="WEB-INF/footer.jsp"%>

	<!-- SCRIPTS -->
	<script src="plugin-frameworks/jquery-3.2.1.min.js"></script>
	<script src="plugin-frameworks/tether.min.js"></script>
	<script src="plugin-frameworks/bootstrap.js"></script>
	<script src="common/scripts.js"></script>

</body>
</html>