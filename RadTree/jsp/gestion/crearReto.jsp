<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML>
<html lang="es">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

	<title>Crear reto - RadTree</title>
	<meta name="description" content="Formulario para la creación de retos">
	<meta name="author" content="Grupo A: Gregorio Largo, Alonso Muñoz y Diego Royo">

	<!-- Font -->
	<link href="https://fonts.googleapis.com/css?family=Encode+Sans+Expanded:400,600,700" rel="stylesheet">

	<!-- Stylesheets -->
	<link href="${pageContext.request.contextPath}/plugin-frameworks/bootstrap.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/fonts/ionicons.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/common/styles.css" rel="stylesheet">
	<link rel="icon" type="image/jpg" href="${pageContext.request.contextPath}/images/RadTree_Logo_x32.jpg" />
</head>
<body>

	<%@ include file="/jsp/include/header.jsp" %>

	<section class="ptb-0">
		<div class="mb-30 brdr-ash-1 opacty-5"></div>
		<div class="container">
			<a class="mt-10" href="${pageContext.request.contextPath}/"><i class="mr-5 ion-ios-home"></i>Inicio<i class="mlr-10 ion-chevron-right"></i></a>
			<a class="mt-10" href="${pageContext.request.contextPath}/gestion-contenido">Gestionar contenido<i class="mlr-10 ion-chevron-right"></i></a>
			<a class="mt-10 color-ash" href="#">Crear reto</a>
		</div><!-- container -->
	</section>

	<section>
		<div class="container">

			<div class="row">
				<div class="col-md-12 col-lg-8">
					<h3 class="mb-30">
						<b>Crear reto</b>
					</h3>
					<form name="crearReto" action="${pageContext.request.contextPath}/gestion-contenido/crear-reto/crear" method="post">

						<div
							class="row form-block form-plr-15 form-h-45 form-mb-20 form-brdr-lite-white">

							<div class="col-sm-8">
								<label for="titulo">Título del reto</label>
								<c:if test="${not empty requestScope.errores.get('titulo')}">
									<i class="ml-10 ion-close color-red"></i><span class="pl-5 font-10 color-red">
									<c:out value="${requestScope.errores.get('titulo')}"/>
									</span>
								</c:if>
								<input type="text" name="titulo" placeholder="Título"
									value="<c:out value="${param.titulo}"/>" />
							</div>

							<div class="col-sm-12">
								<label for="cuerpo">Planteamiento del reto</label>
								<c:if test="${not empty requestScope.errores.get('cuerpo')}">
									<i class="ml-10 ion-close color-red"></i><span class="pl-5 font-10 color-red">
									<c:out value="${requestScope.errores.get('cuerpo')}"/>
									</span>
								</c:if>
								<textarea class="p-10" name="cuerpo" rows=4><c:out value="${param.cuerpo}"/></textarea>
							</div>

						</div>

						<div class="row">
							<div class="col-sm-12 mt-30">
								<p class="mb-20"><i>
									Recuerda que todos los contenidos deben ser aprobados previamente por un administrador antes de ser visibles para el público.
									Este proceso puede tomar un tiempo, así que ten paciencia.
								</i></p>
								<button class="w-100 btn-fill-primary" type="submit">
									<b>Enviar reto</b>
								</button>
							</div>
						</div>

					</form>
				</div>
			</div>

		</div>
		<!-- container -->
	</section>

	<%@ include file="/jsp/include/footer.jsp" %>

	<!-- SCRIPTS -->
	<script src="${pageContext.request.contextPath}/plugin-frameworks/jquery-3.2.1.min.js"></script>
	<script src="${pageContext.request.contextPath}/plugin-frameworks/tether.min.js"></script>
	<script src="${pageContext.request.contextPath}/plugin-frameworks/bootstrap.js"></script>
	<script src="${pageContext.request.contextPath}/common/scripts.js"></script>

</body>
</html>
