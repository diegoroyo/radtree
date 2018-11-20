USE sist_info_web;

/* Todos los usuarios tienen como contraseña "clave" */
INSERT INTO Usuario VALUES
(NULL, 'JuanEcologico28', 'Juan', 'Pérez Peñasco', '2000-01-01', 'jperez@gmail.com', '$31$1000$AAAAAAAAAAAAAAAAAAAAAGLPiriGp7lXdLflFuOuAyk', 'PARTICIPANTE', 0.0),
(NULL, 'ElArbolCaido', 'Isidoro', 'Chavez Linares', '1945-02-023', 'isidoro45@hotmail.com', '$31$1000$AAAAAAAAAAAAAAAAAAAAAGLPiriGp7lXdLflFuOuAyk', 'PARTICIPANTE', 0.0),
(NULL, 'GreenGlobal', 'Francisco Miguel', 'García Calvo', '1998-07-08', 'franCalvo@yahoo.es', '$31$1000$AAAAAAAAAAAAAAAAAAAAAGLPiriGp7lXdLflFuOuAyk', 'CREADOR', 0.0),
(NULL, 'Ramita64', 'Alejandro', 'Martinez Ruiz ', '1961-05-25', 'Alex_Ruiz@hotmail.com', '$31$1000$AAAAAAAAAAAAAAAAAAAAAGLPiriGp7lXdLflFuOuAyk', 'PARTICIPANTE', 0.0),
(NULL, 'LaRataDelReciclaje', 'Sergio', 'Ruperez Torres', '1980-06-28', 'sergioTorres@gmail.com', '$31$1000$AAAAAAAAAAAAAAAAAAAAAGLPiriGp7lXdLflFuOuAyk', 'PARTICIPANTE', 0.0),
(NULL, 'AlonsoElVerde', 'Fernando', 'Alonso Díaz', '1975-03-30', 'ferAlonso@yahoo.es', '$31$1000$AAAAAAAAAAAAAAAAAAAAAGLPiriGp7lXdLflFuOuAyk', 'ADMINISTRADOR', 0.0),
(NULL, 'ElInsectoEcologico', 'Victor', 'Pérez Peñasco', '1999-01-25', 'victor1999@unizar.es', '$31$1000$AAAAAAAAAAAAAAAAAAAAAGLPiriGp7lXdLflFuOuAyk', 'CREADOR', 0.0);

INSERT INTO Contenido VALUES
(NULL, 1, 0, '2018-01-01', 'PENDIENTE'),
(NULL, 2, 0, '2018-01-02', 'PENDIENTE'),
(NULL, 1, 0, '2018-01-03', 'PENDIENTE'),
(NULL, 3, 0, '2018-01-04', 'PENDIENTE'),
(NULL, 4, 0, '2018-01-05', 'PENDIENTE'),
(NULL, 2, 0, '2018-01-06', 'PENDIENTE');

INSERT INTO Noticia VALUES
(1, 'Los bosques secundarios tienen vidas cortas', 'Los bosques secundarios solo duran un promedio de 20 años. El hallazgo presenta un problema importante para la política de restauración a gran escala, que a menudo se enfoca en compromisos para restaurar un cierto número de hectáreas para un año determinado. Pero los beneficios de la restauración dependen de los bosques que persisten. Según muestra una investigación, se tarda mucho más de 20 años para que un bosque secundario absorba grandes cantidades de carbono o proporcione un hábitat para muchas especies forestales.', 'https://www.sciencedaily.com/releases/2018/10/181002102900.htm'),
(2, 'Aragón, la región más afectada por la construcción de embalses que están paralizados', 'Según denuncia Ecologistas en Acción en su último informe, la Comunidad es puntera tanto en "luchas" como en proyectos que se han hecho y carecen de utilidad hoy. Biscarrués, Yesa y Almudévar son algunos ejemplos de estas guerras que siguen abiertas.
  Una problemática que se extiende a lo largo de toda la geografía española y en la que, según denuncia Ecologistas en Acción, la Comunidad "es puntera tanto en luchas como en proyectos que se han hecho y carecen de utilidad" hoy. ', 'https://www.heraldo.es/noticias/aragon/2018/10/03/aragon-region-mas-afectada-por-construccion-embalses-que-estan-paralizados-1269480-300.html');

INSERT INTO Reto VALUES
(3, '¿Serías capaz de mejorar el aire de tu ciudad?', 'Te proponemos un reto que consiste en que durante una semana acudas en transporte público,
andando, en bici o otro tipo de transporte electrico, a tu lugar de trabajo o a clase. De esta forma no solo mejoraras el aire de tu ciudad si no que tambien contribuirás a tu salud física.'),
(4, 'Reciclar es necesario', 'Debes de pensar que tu aportación al planeta con esta actividad es mínima, pero de esta forma se obtienen una gran cantidad de materias primas. Serías
  capaz de aplicar estas medidas en tu hogar o en tu lugar de trabajo, y de esta forma contribuir en el planeta.');

INSERT INTO Pregunta VALUES
(5, '¿Cuántos árboles se deforestan por minuto en el amazonas?'),
(6, '¿Cuántas toneladas de residuos se pierden por un mal reciclado en un año en España?');

/* 5 respuestas para la priemra pregunta y 5 respuestas para la segunda */
INSERT INTO Respuesta VALUES
(NULL, 5, 'Cada minuto se deforesta media hectarea, y se repuebla el doble de lo deforestado.', FALSE),
(NULL, 5, 'Cada minuto se deforestan diez hectareas.', TRUE),
(NULL, 5, 'El mismo número de árboles que se platan en españa en todo un mes.', FALSE),
(NULL, 5, 'Tantos árboles como se necesitaban para costruir un galeón.', FALSE),
(NULL, 5, 'Cada minuto se deforestan el equivalente en superficie a 25 campos de fútbol.', FALSE),
(NULL, 6, 'El equivalente en peso a 100 barcos de carga.', FALSE),
(NULL, 6, 'El 45 % de toneladas de las que llegan a reciclarse de forma correcta.', TRUE),
(NULL, 6, 'Unas 1000 toneladas al mes, lo que equivale a 12000 al año.', FALSE),
(NULL, 6, 'El 10 % de toneladas de las que llegan a reciclarse de forma correcta.', FALSE),
(NULL, 6, 'El equivalente en toneladas de cereal consumidos en un año en españa.', FALSE);

/* modificar cuerpo solamente, el resto ya esta */
INSERT INTO Comentario VALUES
(NULL, 1, 3, 'cuerpo 1', '2000-02-01', NULL),
(NULL, 2, 3, 'cuerpo 2', '2000-02-02', 1),
(NULL, 3, 3, 'cuerpo 3', '2000-02-03', 2),
(NULL, 2, 3, 'cuerpo 4', '2000-02-04', NULL),
(NULL, 3, 3, 'cuerpo 5', '2000-02-05', NULL),
(NULL, 1, 2, 'cuerpo 6', '2000-02-06', NULL),
(NULL, 1, 2, 'cuerpo 7', '2000-02-07', NULL),
(NULL, 2, 2, 'cuerpo 8', '2000-02-08', 6);

/* no hace falta cambiar nada aqui */
INSERT INTO Contesta VALUES
(1, 1, FALSE),
(1, 2, FALSE),
(1, 3, FALSE),
(1, 4, FALSE),
(1, 5, FALSE),
(1, 6, FALSE),
(1, 7, FALSE),
(1, 8, FALSE),
(1, 9, FALSE),
(1, 10, FALSE),
(2, 1, TRUE),
(2, 2, TRUE),
(2, 3, TRUE),
(2, 4, TRUE),
(2, 5, TRUE),
(2, 6, TRUE),
(2, 7, TRUE),
(2, 8, TRUE),
(2, 9, TRUE),
(2, 10, TRUE);
