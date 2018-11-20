<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML>
<html lang="es">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

	<title><c:out value="${requestScope.pregunta.enunciado}"/> - RadTree</title>
	<meta name="description" content="Una de las preguntas hechas por los usuarios de RadTree">
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
			<a class="mt-10" href="${pageContext.request.contextPath}"><i class="mr-5 ion-ios-home"></i>Inicio<i class="mlr-10 ion-chevron-right"></i></a>
			<a class="mt-10" href="${pageContext.request.contextPath}/preguntas">Preguntas<i class="mlr-10 ion-chevron-right"></i></a>
			<a class="mt-10 color-ash" href="#"><c:out value="${requestScope.pregunta.enunciado}"/></a>
		</div><!-- container -->
	</section>


	<section>
		<div class="container">
			
			<c:if test="${requestScope.pregunta == null or requestScope.pregunta.estado != 'VALIDADO'}">
				<c:redirect url="/preguntas"/>
			</c:if>
			
			<div class="row">
				<div class="col-md-12">
					<h3 class="mb-10"><b><c:out value="${requestScope.pregunta.enunciado}"/></b></h3>
					<p class="mb-30"><i><c:out value="${requestScope.autorCompleto}"/></i></p>

					<form class="pregunta" name="enviarPregunta" action="" method="post">
					
						<c:set var = "i" value = "${1}"/>
						<c:forEach items="${requestScope.respuestas}" var="respuesta">
							<div class="row mt-10">
								<div class="col-2 col-sm-1">
									<input type="checkbox" name="correcta<c:out value="${i}"/>"/>
								</div>
	
								<div class="col-10 col-sm-11">
									<p><c:out value="${respuesta.enunciado}"/></p>
								</div>
							</div>
							<c:set var = "i" value = "${i + 1}"/>
						</c:forEach>
						
					</form>
				</div>
            </div>
            
			<div class="row mt-20 mt-sm-40">
				<div class="col-12 col-md-4">
					<button class="mb-10 plr-90 plr-sm-10 w-100 btn-fill-primary" type="submit"><b>Validar pregunta</b></button>
				</div>
				<div class="col-12 col-md-4">
					<button class="mb-10 plr-90 plr-sm-10 w-100 btn-fill-grey" type="submit"><b>No responder y pasar</b></button>
				</div>
			</div>

			<div class="row">
				<div class="col-12">
				</div>
			</div>

		</div><!-- container -->

		<!-- COMENTARIOS -->
		<%@ include file="/jsp/include/comentarios.jsp" %>

	</section>

	<%@ include file="/jsp/include/footer.jsp" %>

	<!-- SCRIPTS -->
	<script src="${pageContext.request.contextPath}/plugin-frameworks/jquery-3.2.1.min.js"></script>
	<script src="${pageContext.request.contextPath}/plugin-frameworks/tether.min.js"></script>
	<script src="${pageContext.request.contextPath}/plugin-frameworks/bootstrap.js"></script>
	<script src="${pageContext.request.contextPath}/common/scripts.js"></script>

</body>
</html>