<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="sistinfo.capadatos.dao.NoticiaDAO" %>
<%@ page import="sistinfo.capadatos.vo.NoticiaVO" %>
<%@ page import="sistinfo.utils.CookieManager" %>
<%@ page import="sistinfo.excepciones.ErrorInternoException" %>
<%--
	Almacena datos de usuario (UsuarioVO) en la request para que luego pueda ser usada por la bean
	Orden de comprobaciones:
	- Si ya hay un UsuarioVO en la request, no hacer nada
	- Si no hay un UsuarioVO en la request, intentar cargar los datos del usuario con el alias incluido en los parametros
		- Si no lo encuentra, intentar cargar los datos del usuario de las cookies
--%>
<%
	if (request.getAttribute("noticia") == null) {
		// Encontrar un ID de usuario para mostrar
		String alias = (String)request.getParameter("alias");
		if (alias == null || alias.trim().isEmpty()) {
			alias = CookieManager.getAliasFromCookies(request);
			if (alias == null) { // CookieManager ya comprueba que es vacio
				// No sabemos qué usuario mostrar
	            response.sendRedirect("errorInterno.html");
			} else {
				// Mostrar el usuario alias
				RequestDispatcher dispatcher = request.getRequestDispatcher("perfil.jsp?alias=" + alias);
				response.sendRedirect("perfil.jsp?alias=" + alias);
				dispatcher.include(request, response);
			}
		} else {// Cargar el usuario con ese alias
			UsuarioDAO usuarioDAO = new UsuarioDAO();
			try {
				UsuarioVO usuario = usuarioDAO.getUsuarioByAlias(alias);
				if (usuario == null) {
		            response.sendRedirect("errorInterno.html");
				} else {
					request.setAttribute("usuario", usuario);
				}
			} catch (ErrorInternoException e) {
	            response.sendRedirect("errorInterno.html");
			}
		}
	}
%>
<!DOCTYPE HTML>
<html lang="es">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

	<title><c:out value="${requestScope.usuario.alias}"/> - RadTree</title>
	<meta name="description" content="Página de perfil de usuario">
	<meta name="author" content="Grupo A: Gregorio Largo, Alonso Muñoz y Diego Royo">

	<!-- Font -->
	<link href="https://fonts.googleapis.com/css?family=Encode+Sans+Expanded:400,600,700" rel="stylesheet">

	<!-- Stylesheets -->
	<link href="plugin-frameworks/bootstrap.css" rel="stylesheet">
	<link href="fonts/ionicons.css" rel="stylesheet">
	<link href="common/styles.css" rel="stylesheet">
</head>
<body>

	<%@ include file="WEB-INF/header.jsp" %>

  <section>
		<div class="container">

			<div class="row">
				<div class="col-md-12 col-lg-8">
					<h3 class="mb-30"><b><c:out value="${requestScope.noticia.titulo}"/></b></h3>

          <p class="text-justify pr-30 mb-30">
            <c:out value="${requestScope.noticia.cuerpo}"/>
          </p>

          <p class="mb-20">URL de la fuente: <b><a href="${requestScope.noticia.url}">
            <c:out value="${requestScope.noticia.url}"/></b></a>
          </p>
          <p class="mb-30"><i>Autor: <c:out value="${requestScope.noticia.alias}"/></i></p>
        </div>

        <div class="col-md-12 col-lg-4">
              <img src="images/Eco-1_900x600.jpg" alt="Imagen de la noticia"/>
        </div>
      </div>

		</div><!-- container -->

	<%@ include file="WEB-INF/footer.jsp" %>

	<!-- SCRIPTS -->
	<script src="plugin-frameworks/jquery-3.2.1.min.js"></script>
	<script src="plugin-frameworks/tether.min.js"></script>
	<script src="plugin-frameworks/bootstrap.js"></script>
	<script src="common/scripts.js"></script>

</body>
</html>