<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML>
<html lang="es">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

	<title>Retos - RadTree</title>
	<meta name="description" content="Listado de todos los retos">
	<meta name="author" content="Grupo A: Gregorio Largo, Alonso Muñoz y Diego Royo">

	<!-- Font -->
	<link href="https://fonts.googleapis.com/css?family=Encode+Sans+Expanded:400,600,700" rel="stylesheet">

	<!-- Stylesheets -->
	<link href="${pageContext.request.contextPath}/plugin-frameworks/bootstrap.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/fonts/ionicons.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/common/styles.css" rel="stylesheet">
	<link rel="icon" type="image/jpg" href="${pageContext.request.contextPath}/images/RadTree_Logo_x32.jpg" />
	<script src="${pageContext.request.contextPath}/common/switchFiltrosScript.js"></script>
</head>
<body>

	<%@ include file="/jsp/include/header.jsp" %>

	<section class="ptb-0">
		<div class="mb-30 brdr-ash-1 opacty-5"></div>
		<div class="container">
			<a class="mt-10" href="${pageContext.request.contextPath}/"><i class="mr-5 ion-ios-home"></i>Inicio<i class="mlr-10 ion-chevron-right"></i></a>
			<a class="mt-10 color-ash" href="#">Retos</a>
		</div><!-- container -->
	</section>

	<section>
		<div class="container">

			<div class="row">
                <!-- Barra de búsqueda -->
				<div class="col-12 col-md-8 offset-0 offset-md-2 mb-50">
					<form name="busqueda" action="retos" method="post">
						<div class="row form-block form-plr-15 form-h-45 form-brdr-lite-white">
							
							<c:if test="${empty param.busqueda}">
								<div class="col-9 col-md-10">
									<input class="w-100" type="text" name="busqueda"
										placeholder="Escribe aquí para buscar..."/>
								</div>
							</c:if>
							<c:if test="${not empty param.busqueda}">
								<div class="col-2">
									<button class="w-100 btn-fill-primary" name="busqueda" value="" type="submit">
										<i class="ion-close"></i>
									</button>
								</div>
								<div class="col-7 col-md-8">
									<input class="w-100" type="text" name="busqueda"
										placeholder="Escribe aquí para buscar..."
										value="<c:out value="${param.busqueda}"/>">
								</div>
							</c:if>
							
							<div class="col-3 col-md-2">
								<button class="w-100 btn-fill-primary" type="submit">
									<i class="ion-search"></i>
								</button>
							</div>
							
						</div>
						<!-- Abrir Filtros -->
						<div class="mtb-10">
							<button class="w-100 btn-fill-grey" id="filtrosButton"
								type="button"
								<c:choose>
									<c:when test="${param.filtroTitulo == 'on'
												or param.filtroCuerpo == 'on'}">
										onclick="hideFiltros()"
									</c:when>
									<c:otherwise>
										onclick="showFiltros()"
									</c:otherwise>
								</c:choose>>
								<i class="ion-ios-settings-strong mr-10"></i><b>Filtros</b>
							</button>
						</div>
						<!-- Filtros -->
						<div class="mtb-10 w-100" id="filtrosDiv"
							<c:if test="${param.filtroTitulo != 'on'
										and param.filtroCuerpo != 'on'}">
								style="display: none"
							</c:if> >
							<div class="row w-100">
								<div class="col-fit">
									<span class="w-100 m-10 m-sm-5 text alert-wo-margin alert-success">
										<input class="mr-10" type="checkbox" name="filtroTitulo"
											<c:if test="${param.filtroTitulo == 'on'}">
											checked
											</c:if> />
										<b>Título</b>
									</span>
								</div>
								<div class="col-fit">
									<span class="w-100 m-10 m-sm-5 text alert-wo-margin alert-success">
										<input class="mr-10" type="checkbox" name="filtroCuerpo"
											<c:if test="${param.filtroCuerpo == 'on'}">
											checked
											</c:if> /><b>Cuerpo</b>
									</span>
								</div>
							</div>
						</div>
						<!-- Separador -->
						<h4 class="p-title"></h4>
					</form>
				</div>
				<div class="col-12">
					<c:if test="${not empty requestScope.retos}">
                    	<ul class="lista-titulos">
							<c:forEach items="${requestScope.retos}" var="reto">
		                        <li><form name="reto" action="${pageContext.request.contextPath}/retos/ver" method="post">
		                        	<input type="hidden" name="id" value="<c:out value="${reto.idContenido}"/>"/>
			                        <button type="submit">
			                            <span><c:out value="${reto.titulo}"/></span><br>
			                            <span class="titulo-desc">
			                                <i class="mr-5 color-primary ion-ios-bolt"></i>
			                                <c:out value="${reto.numVisitas}"/>
			                                <c:if test="${reto.numVisitas == 1}">
			                                	 visita
			                                </c:if>
			                                <c:if test="${reto.numVisitas != 1}">
			                                	 visitas
			                                </c:if>
			                                <i class="ml-10 mr-5 color-primary ion-chatbubbles"></i>
			                                <c:out value="${reto.numComentarios}"/>
			                                <c:if test="${reto.numComentarios == 1}">
			                                	 comentario
			                                </c:if>
			                                <c:if test="${reto.numComentarios != 1}">
			                                	 comentarios
			                                </c:if>
			                            </span>
			                        </button>
		                        </form></li>
							</c:forEach>
                   		</ul>
                   	</c:if>
					<c:if test="${empty requestScope.retos}">
						<p><i>Parece que no hay nada por aquí...</i></p>
					</c:if>
                </div>
            </div>

		</div><!-- container -->
		
		<%-- Paginacion --%>
		<c:if test="${not empty requestScope.retos}">
			<div class="text-center mt-10">
				<form name="paginacion"
					action="${pageContext.request.contextPath}/retos" method="post">
					<%@ include file="/jsp/include/paginacion.jsp"%>
				</form>
			</div>
		</c:if>
			
	</section>

	<%@ include file="/jsp/include/footer.jsp" %>

	<!-- SCRIPTS -->
	<script src="${pageContext.request.contextPath}/plugin-frameworks/jquery-3.2.1.min.js"></script>
	<script src="${pageContext.request.contextPath}/plugin-frameworks/tether.min.js"></script>
	<script src="${pageContext.request.contextPath}/plugin-frameworks/bootstrap.js"></script>
	<script src="${pageContext.request.contextPath}/common/scripts.js"></script>

</body>
</html>
