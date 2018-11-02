<!DOCTYPE HTML>
<html lang="es">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

	<!-- TODO: Cambiar Titulo por el titulo de la pregunta + descripcion -->
	<title>*Título*</title>
	<meta name="description" content="Descripción">
	<meta name="author" content="Grupo A: Gregorio Largo, Alonso Muñoz y Diego Royo">

	<!-- Font -->
	<link href="https://fonts.googleapis.com/css?family=Encode+Sans+Expanded:400,600,700" rel="stylesheet">

	<!-- Stylesheets -->
	<link href="plugin-frameworks/bootstrap.css" rel="stylesheet">
	<link href="fonts/ionicons.css" rel="stylesheet">
	<link href="common/styles.css" rel="stylesheet">

</head>
<body>

	<header>
		<div class="bg-191">
			<div class="container">	
				<div class="oflow-hidden color-ash font-9 text-sm-center ptb-sm-5">
				
					<ul class="float-left list-a-plr-10 list-a-plr-sm-5 list-a-ptb-10 list-a-ptb-sm-5">
						<li><a class="pl-0 pl-sm-10" href="#"><i class="ion-social-facebook"></i></a></li>
						<li><a href="#"><i class="ion-social-twitter"></i></a></li>
						<li><a href="#"><i class="ion-social-instagram"></i></a></li>
					</ul>

					<ul class="float-right list-a-plr-10 list-a-plr-sm-5 mtb-5 mtb-sm-0">
						<li><a class="btn-fill-primary btn-b-sm plr-20 plr-sm-5" href="02_registro.html">Regístrate</a></li>
						<li><span class="plr-10 plr-sm-5">o</i></li>
						<li><a class="btn-fill-primary btn-b-sm plr-20 plr-sm-5" href="03_inicioSesion.html">Inicia sesión</a></li>
					</ul>
					<!-- TODO: Para cuando este logueado, añadir menu que lleve al perfil o logout
					<ul class="float-right list-a-plr-10 list-a-plr-sm-5 ptb-5 mtb-5 mtb-sm-0">
						<li>
							<a href="#">Bienvenido, Diego<i class="pl-10 ion-arrow-down-b"></i></a>
						</li>
					</ul>-->
					
				</div><!-- top-menu -->
			</div><!-- container -->
		</div><!-- bg-191 -->
		
		<div class="container">
			<a class="logo" href="index.html"><img src="images/logo-black.png" alt="Logo"></a>
			
			<a class="right-area src-btn" href="#" >
				<i class="active src-icn ion-search"></i>
				<i class="close-icn ion-close"></i>
			</a>
			<div class="src-form">
				<form>
					<input type="text" placeholder="Search here">
					<button type="submit"><i class="ion-search"></i></a></button>
				</form>
			</div><!-- src-form -->
			
			<a class="menu-nav-icon" data-menu="#main-menu" href="#"><i class="ion-navicon"></i></a>
			
			<ul class="main-menu" id="main-menu">
				<li class="drop-down"><a href="">CARTELES<i class="ion-arrow-down-b"></i></a>
					<ul class="drop-down-menu drop-down-inner">
						<li><a href="50_listaDeNoticias.html">NOTICIAS</a></li>
						<li><a href="52_listaDePreguntas.html">PREGUNTAS</a></li>
						<li><a href="54_listaDeRetos.html">RETOS</a></li>
					</ul>
				</li>
				<li><a href="60_clasificacion.html">CLASIFICACION</a></li>
				<li><a href="20_quienesSomos.html">QUIENES SOMOS</a></li>
				<li><a class="btn-b-md btn-fill-primary lh-30" href="30_gestionContenido.html">GESTIONAR CONTENIDO</a></li>
			</ul>
			<div class="clearfix"></div>
		</div><!-- container -->
	</header>


	<section class="ptb-0">
		<div class="mb-30 brdr-ash-1 opacty-5"></div>
		<div class="container">
			<a class="mt-10" href="index.html"><i class="mr-5 ion-ios-home"></i>Inicio<i class="mlr-10 ion-chevron-right"></i></a>
			<a class="mt-10" href="52_listaDePreguntas.html">Preguntas<i class="mlr-10 ion-chevron-right"></i></a>
			<a class="mt-10 color-ash" href="">¿Cuántos árboles se deforestan por minuto en el amazonas?</a>
		</div><!-- container -->
	</section>


	<section>
		<div class="container">

			<div class="row">
				<div class="col-md-12">
					<h3 class="mb-10"><b>¿Cuántos árboles se deforestan por minuto en el amazonas?</b></h3>
					<p class="mb-30"><i>Autor: Martín Martínez</i></p>

					<form class="pregunta" name="enviarPregunta" action="TODO.do" method="post">
						<div class="row mt-10">
							<div class="col-2 col-sm-1">
								<input type="radio" name="correcta"/>
							</div>

							<div class="col-10 col-sm-11">
								<p>Cada minuto se deforesta media hectarea, y se repuebla el doble de lo deforestado.</p>
							</div>
						</div>

						<div class="row mt-10">
							<div class="col-2 col-sm-1">
								<input type="radio" name="correcta"/>
							</div>

							<div class="col-10 col-sm-11">
								<p class=text-justify">Cada minuto se deforesta la superficie de un campo de futbol.</p>
							</div>
						</div>

						<div class="row mt-10">
							<div class="col-2 col-sm-1">
								<input type="radio" name="correcta"/>
							</div>

							<div class="col-10 col-sm-11">
								<p class=text-justify">Cada minuto se deforestan diez hectareas.</p>
							</div>
						</div>
						
						<div class="row mt-10">
							<div class="col-2 col-sm-1">
								<input type="radio" name="correcta"/>
							</div>

							<div class="col-10 col-sm-11">
								<p class=text-justify">Cada minuto se talan cincuenta arboles con hacha y ocho con serrucho.</p>
							</div>
						</div>
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
		<hr class="mtb-50 mtb-sm-20">
		<div class="container">
			<div class="row">
				<div class="col-12">
					<h3 class="p-title mb-40"><b>Comentarios</b></h3>

					<div class="sided-70 mb-40">

						<div class="s-left rounded">
							<img src="images/profile-3-120x120.jpg" alt="Foto de perfil de *usuario*">
						</div><!-- s-left -->
						
						<div class="s-right ml-100 ml-xs-85">
							<h5><b>Shuhein Chui, </b> <span class="font-8 color-ash">Nov 21, 2017</span></h5>
							<p class="mtb-15">Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium 
								doloremque laudantium, totam rem aperiam.</p>
							<button class="btn-brdr-grey btn-b-sm plr-15 mr-10 mt-5"><b>Like</b></button>
							<button class="btn-brdr-grey btn-b-sm plr-15 mt-5"><b>Responder</b></button>
						</div><!-- s-right -->
						
					</div><!-- sided-70 -->
					
					<div class="sided-70 mb-40">
					
						<div class="s-left rounded">
							<img src="images/profile-1-120x120.jpg" alt="Foto de perfil de *usuario*">
						</div><!-- s-left -->
						
						<div class="s-right ml-100 ml-xs-85">
							<h5><b>Shuhein Chui, </b> <span class="font-8 color-ash">Nov 21, 2017</span></h5>
							<p class="mtb-10">Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium 
								doloremque laudantium, totam rem aperiam.</p>
							<button class="btn-brdr-grey btn-b-sm plr-15 mr-10 mt-5"><b>Like</b></button>
							<button class="btn-brdr-grey btn-b-sm plr-15 mt-5"><b>Responder</b></button>
						</div><!-- s-right -->
						
					</div><!-- sided-70 -->
					
					<div class="sided-70 mb-50">
					
						<div class="s-left rounded">
							<img src="images/profile-2-120x120.jpg" alt="Foto de perfil de *usuario*">
						</div><!-- s-left -->
						
						<div class="s-right ml-100 ml-xs-85">
							<h5><b>Shuhein Chui, </b> <span class="font-8 color-ash">Nov 21, 2017</span></h5>
							<p class="mt-10 mb-15">Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium 
								doloremque laudantium, totam rem aperiam.</p>
							<button class="btn-brdr-grey btn-b-sm plr-15 mr-10 mt-5"><b>Like</b></button>
							<button class="btn-brdr-grey btn-b-sm plr-15 mt-5"><b>Responder</b></button>
						</div><!-- s-right -->
						
					</div><!-- sided-70 -->
				</div>


				<div class="col-md-12 col-lg-8 sided-70">
					<h4 class="p-title mt-20"><b>Deja un comentario</b></h4>
					<h5 class="mb-20">Comentando como <b>Shuhein Chui</b></h5>
					<form class="form-block form-plr-15 form-h-45 form-mb-20 form-brdr-lite-white mb-md-50">
						<textarea class="ptb-10" placeholder="Deja un comentario..." rows=3></textarea>
						<button class="btn-fill-primary plr-30" rows="4" cols="50" type="submit"><b>Comentar</b></button>
					</form>
				</div>
			</div>
		</div>

	</section>


	<footer class="bg-191 color-ccc">
		<div class="container">
			<div class="pt-50 pb-20 pos-relative">
				<div class="abs-tblr pt-50 z--1 text-center">
					<div class="h-80 pos-relative"><img class="opacty-1 h-100 w-auto" src="images/map.png" alt=""></div>
				</div>
				<div class="row">
				
					<div class="col-sm-4">
						<div class="mb-30">
							<a href="index.html"><img src="images/logo-white.png"></a>
							<p class="mt-20 color-ccc">
								RadTree es un proyecto de la Universidad de Zaragoza que intenta concienciar a la población sobre los peligros medioambientales.
							</p>
							<p class="mb-20"><a class="mt-10 link-brdr-btm-primary color-primary" href="20_quienesSomos.html">¿Quieres saber más?</a></p>
							<p class="color-ash">
							<!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. -->
							Copyright &copy;<script>document.write(new Date().getFullYear());</script> All rights reserved | This template is made with <i class="ion-heart" aria-hidden="true"></i> by <a href="https://colorlib.com" target="_blank">Colorlib</a>
							<!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. -->
							</p>
						</div><!-- mb-30 -->
					</div><!-- col-md-4 -->
					
					<div class="col-sm-4">
						<div class="mb-30">
							<h5 class="color-primary mb-20"><b>ÚLTIMAS NOTICIAS</b></h5>
							<div class="mb-20">
								<a class="color-white" href="51_noticia.html"><b>Secondary forests have short lifespans</b></a>
								<h6 class="mt-10">Jan 25, 2018</h6>
							</div>
							<div class="brdr-ash-1 opacty-2 mr-30"></div>
							<div class="mt-20">
								<a class="color-white" href="51_noticia.html"><b>Feeding 10 billion people by 2050 within planetary limits may be achievable</b></a>
								<h6 class="mt-10">Jan 25, 2018</h6>
							</div>
						</div><!-- mb-30 -->
					</div><!-- col-md-4 -->
					
					<div class="col-sm-4">
						<div class="mb-30">
							<h5 class="color-primary mb-20"><b>NOTICIAS MÁS POPULARES</b></h5>
							<div class="mb-20">
								<a class="color-white" href="51_noticia.html"><b>Feeding 10 billion people by 2050 within planetary limits may be achievable</b></a>
								<h6 class="mt-10">Jan 25, 2018</h6>
							</div>
							<div class="brdr-ash-1 opacty-2 mr-30"></div>
							<div class="mt-20">
								<a class="color-white" href="51_noticia.html"><b>Secondary forests have short lifespans</b></a>
								<h6 class="mt-10">Jan 25, 2018</h6>
							</div>
						</div><!-- mb-30 -->
					</div><!-- col-md-4 -->
					
				</div><!-- row -->
			</div><!-- ptb-50 -->
			
			<div class="brdr-ash-1 opacty-2"></div>
			
			<div class="oflow-hidden color-ash font-9 text-sm-center ptb-sm-5">
			
				<ul class="float-right float-sm-none list-a-plr-10 list-a-plr-sm-5 list-a-ptb-15 list-a-ptb-sm-5">
					<li><a class="pl-0 pl-sm-10" href="#"><i class="ion-social-facebook"></i></a></li>
					<li><a href="#"><i class="ion-social-twitter"></i></a></li>
					<li><a href="#"><i class="ion-social-instagram"></i></a></li>
				</ul>
				
			</div><!-- oflow-hidden -->
		</div><!-- container -->
	</footer>

	<!-- SCRIPTS -->
	<script src="plugin-frameworks/jquery-3.2.1.min.js"></script>
	<script src="plugin-frameworks/tether.min.js"></script>
	<script src="plugin-frameworks/bootstrap.js"></script>
	<script src="common/scripts.js"></script>

</body>
</html>
