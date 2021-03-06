package sistinfo.capadatos.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sistinfo.capadatos.jdbc.ConnectionFactory;
import sistinfo.capadatos.vo.ContenidoVO;
import sistinfo.capadatos.vo.PreguntaVO;
import sistinfo.capadatos.vo.RespuestaVO;
import sistinfo.excepciones.ErrorInternoException;
import sistinfo.excepciones.PreguntaYaRespondidaException;

public class PreguntaDAO extends ContenidoDAO {

	/**
	 * Añade a una lista de preguntas el número de veces que ha sido contestada cada
	 * una en su atributo vecesContestada
	 * 
	 * @param preguntas
	 * @return La lista de preguntas actualizada
	 * @throws ErrorInternoException
	 */
	public List<PreguntaVO> addVecesContestadaToPregunta(List<PreguntaVO> preguntas) throws ErrorInternoException {
		try {
			Connection connection = ConnectionFactory.getConnection();

			for (PreguntaVO pregunta : preguntas) {
				PreparedStatement stmt = connection.prepareStatement("SELECT COUNT(DISTINCT idUsuario) FROM Contesta NATURAL JOIN Respuesta WHERE idPregunta=?");
				stmt.setLong(1, pregunta.getIdContenido());
				ResultSet rs = stmt.executeQuery();
				if (rs.last() && rs.getRow() == 1) {
					pregunta.setVecesContestada(rs.getLong(1));
				}
				stmt.close();
			}

			connection.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new ErrorInternoException();
		}
		return preguntas;
	}
	

	/**
	 * Comprueba si un usuario ha respondido a una pregunta en concreto
	 * 
	 * @param idUsuario  Usuario que ha respondido a la pregunta
	 * @param idPregunta Pregunta ha comprobar si el usuario ha respondido.
	 * @return Booleano con valor true si el usuario ha respondido a la pregunta,
	 *         false en caso contrario.
	 * @throws ErrorInternoException
	 */
	public boolean haContestadoAPregunta(Long idUsuario, Long idPregunta) throws ErrorInternoException {
		Long idPreguntaEncontrada = null;

		try {
			Connection connection = ConnectionFactory.getConnection();

			PreparedStatement stmt = connection.prepareStatement("SELECT DISTINCT idPregunta FROM Contesta NATURAL JOIN Respuesta WHERE idUsuario = ? AND idPregunta = ?");
			stmt.setLong(1, idUsuario);
			stmt.setLong(2, idPregunta);
			ResultSet rs = stmt.executeQuery();
			if (rs.last() && rs.getRow() == 1) {
				idPreguntaEncontrada = rs.getLong("idPregunta");
			}

			stmt.close();
			connection.close();

		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new ErrorInternoException();
		}

		return idPreguntaEncontrada == idPregunta;
	}

	/**
	 * Devuelve un mapa con las respuestas que un usuario ha dado a una pregunta en concreto
	 * @param idUsuario Usuario que ha respondido a la pregunta
	 * @param idPregunta Pregunta a comprobar
	 * @return Un mapa Long -> Boolean que relaciona ID respuesta con contestación dada, o null si no había respondido
	 * @throws ErrorInternoException
	 */
	public Map<Long, Boolean> getContestacionesAPregunta(Long idUsuario, Long idPregunta) throws ErrorInternoException {
		Map<Long, Boolean> mapaRespuestas = null;
		try {
			Connection connection = ConnectionFactory.getConnection();

			PreparedStatement stmt = connection.prepareStatement("SELECT idRespuesta, respuesta FROM Contesta NATURAL JOIN Respuesta WHERE idUsuario = ? AND idPregunta = ?");
			stmt.setLong(1, idUsuario);
			stmt.setLong(2, idPregunta);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				mapaRespuestas = new HashMap<Long, Boolean>();
				do {
					mapaRespuestas.put(rs.getLong("idRespuesta"), rs.getBoolean("respuesta"));
				} while (rs.next());
			}

			stmt.close();
			connection.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new ErrorInternoException();
		}

		return mapaRespuestas;
	}

	/**
	 * Búsqueda de preguntas por el identificador de su autor
	 * 
	 * @param idAutor
	 * @return Lista de preguntas de ese autor
	 * @throws ErrorInternoException
	 */
	public List<PreguntaVO> getPreguntasByAutor(Long idAutor) throws ErrorInternoException {
		List<PreguntaVO> preguntas = new ArrayList<PreguntaVO>();
		try {
			Connection connection = ConnectionFactory.getConnection();

			PreparedStatement stmt = connection
					.prepareStatement("SELECT * FROM Pregunta NATURAL JOIN Contenido WHERE idAutor=?");
			stmt.setLong(1, idAutor);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				PreguntaVO pregunta = extractPreguntaFromResultSet(rs);
				preguntas.add(pregunta);
			}

			stmt.close();
			connection.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new ErrorInternoException();
		}
		return preguntas;
	}

	/**
	 * Búsqueda de pregunta por su identificador interno.
	 * 
	 * @param id
	 * @return La pregunta si el id existe, null en caso contrario
	 * @throws ErrorInternoException
	 */
	public PreguntaVO getPreguntaById(Long id) throws ErrorInternoException {
		try {
			Connection connection = ConnectionFactory.getConnection();

			PreparedStatement stmt = connection
					.prepareStatement("SELECT * FROM Pregunta NATURAL JOIN Contenido WHERE idContenido=?");
			stmt.setLong(1, id);
			ResultSet rs = stmt.executeQuery();

			if (rs.last()) {
				if (rs.getRow() == 1) {
					PreguntaVO pregunta = extractPreguntaFromResultSet(rs);
					stmt.close();
					connection.close();
					return pregunta;
				}
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new ErrorInternoException();
		}
		return null;
	}

	/**
	 * Búsqueda de respuesta por su identificador interno.
	 * 
	 * @param id
	 * @return La respuesta si el id existe, null en caso contrario
	 * @throws ErrorInternoException
	 */
	public RespuestaVO getRespuestaById(Long id) {
		try {
			Connection connection = ConnectionFactory.getConnection();

			PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Respuesta WHERE idRespuesta=?");
			stmt.setLong(1, id);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				RespuestaVO respuesta = extractRespuestaFromResultSet(rs);
				stmt.close();
				connection.close();
				return respuesta;
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * Búsqueda de hasta las últimas num preguntas según su fecha de realización
	 * 
	 * @param num
	 * @return Lista de hasta num preguntas ordenadas por fecha de realización
	 * @throws ErrorInternoException
	 */
	public List<PreguntaVO> getPreguntasUltimas(int num) throws ErrorInternoException {
		return getPreguntasUltimasHelper(num, null, null, null);
	}
	
	/**
	 * Búsqueda de hasta las últimas num preguntas según su fecha de realización
	 * 
	 * @param num
	 * @return Lista de hasta num preguntas ordenadas por fecha de realización
	 * @throws ErrorInternoException
	 */
	public List<PreguntaVO> getPreguntasUltimasByPag(int num, int pagina) throws ErrorInternoException {
		return getPreguntasUltimasHelperByPag(num, null, null, null, pagina);
	}
	
	/**
	 * Búsqueda de hasta las últimas num preguntas según su fecha de realización
	 * 
	 * @param num
	 * @return numero de preguntas
	 * @throws ErrorInternoException
	 */
	public int getNumPreguntasUltimas(int num) throws ErrorInternoException {
		return getNumPreguntasUltimasHelper(num, null, null, null);
	}

	/**
	 * Búsqueda de hasta las últimas num preguntas según su fecha de realización y
	 * si las ha contestado el usuario o no
	 * 
	 * @param num
	 * @param contestadas Si el usuario ha contestado a la pregunta o no
	 * @return Lista de hasta num preguntas ordenadas por fecha de realización
	 * @throws ErrorInternoException
	 */
	public List<PreguntaVO> getPreguntasUltimasContestadas(boolean contestadas, Long idUsuario, int num)
			throws ErrorInternoException {
		return getPreguntasUltimasHelper(num, contestadas, idUsuario, null);
	}
	/**
	 * Búsqueda de hasta las últimas num preguntas según su fecha de realización y
	 * si las ha contestado el usuario o no
	 * 
	 * @param num
	 * @param contestadas Si el usuario ha contestado a la pregunta o no
	 * @return Lista de hasta num preguntas ordenadas por fecha de realización
	 * @throws ErrorInternoException
	 */
	public List<PreguntaVO> getPreguntasUltimasContestadasByPag(boolean contestadas, Long idUsuario, int num, int pagina)
			throws ErrorInternoException {
		return getPreguntasUltimasHelperByPag(num, contestadas, idUsuario, null, pagina);
	}
	
	/**
	 * Búsqueda de hasta las últimas num preguntas según su fecha de realización y
	 * si las ha contestado el usuario o no
	 * 
	 * @param num
	 * @param contestadas Si el usuario ha contestado a la pregunta o no
	 * @return numero de preguntas
	 * @throws ErrorInternoException
	 */
	public int getNumPreguntasUltimasContestadas(boolean contestadas, Long idUsuario, int num)
			throws ErrorInternoException {
		return getNumPreguntasUltimasHelper(num, contestadas, idUsuario, null);
	}

	/**
	 * Búsqueda de hasta num preguntas validadas que contienen search en su
	 * enunciado, ordenados por su fecha de realización
	 * 
	 * @param search
	 * @return Lista con todas las preguntas
	 * @throws ErrorInternoException
	 */
	public List<PreguntaVO> getPreguntasBySearch(String search, int num) throws ErrorInternoException {
		return getPreguntasUltimasHelper(num, null, null, search);
	}
	/**
	 * Búsqueda de hasta num preguntas validadas que contienen search en su
	 * enunciado, ordenados por su fecha de realización
	 * 
	 * @param search
	 * @return Lista con todas las preguntas
	 * @throws ErrorInternoException
	 */
	public List<PreguntaVO> getPreguntasBySearchByPag(String search, int num, int pagina) throws ErrorInternoException {
		return getPreguntasUltimasHelperByPag(num, null, null, search, pagina);
	}
	
	/**
	 * Búsqueda de hasta num preguntas validadas que contienen search en su
	 * enunciado, ordenados por su fecha de realización
	 * 
	 * @param search
	 * @return numero de preguntas
	 * @throws ErrorInternoException
	 */
	public int getNumPreguntasBySearch(String search, int num) throws ErrorInternoException {
		return getNumPreguntasUltimasHelper(num, null, null, search);
	}

	/**
	 * Búsqueda de hasta num preguntas validadas que contienen search en su
	 * enunciado y han sido contestadas o no, ordenados por su fecha de realización
	 * 
	 * @param search
	 * @return Lista con todas las preguntas
	 * @throws ErrorInternoException
	 */
	public List<PreguntaVO> getPreguntasBySearchContestadas(String search, boolean contestadas, Long idUsuario, int num)
			throws ErrorInternoException {
		return getPreguntasUltimasHelper(num, contestadas, idUsuario, search);
	}
	/**
	 * Búsqueda de hasta num preguntas validadas que contienen search en su
	 * enunciado y han sido contestadas o no, ordenados por su fecha de realización
	 * 
	 * @param search
	 * @return Lista con todas las preguntas
	 * @throws ErrorInternoException
	 */
	public List<PreguntaVO> getPreguntasBySearchContestadasByPag(String search, boolean contestadas, Long idUsuario, int num, int pagina)
			throws ErrorInternoException {
		return getPreguntasUltimasHelperByPag(num, contestadas, idUsuario, search, pagina);
	}
	
	/**
	 * Búsqueda de hasta num preguntas validadas que contienen search en su
	 * enunciado y han sido contestadas o no, ordenados por su fecha de realización
	 * 
	 * @param search
	 * @return Lista con todas las preguntas
	 * @throws ErrorInternoException
	 */
	public int getNumPreguntasBySearchContestadas(String search, boolean contestadas, Long idUsuario, int num)
			throws ErrorInternoException {
		return getNumPreguntasUltimasHelper(num, contestadas, idUsuario, search);
	}

	/**
	 * Búsqueda de respuesta por su identificador interno.
	 * 
	 * @param id
	 * @return La respuesta si el id existe, null en caso contrario
	 * @throws ErrorInternoException
	 */
	public List<RespuestaVO> getRespuestasByPregunta(Long idPregunta) throws ErrorInternoException {
		List<RespuestaVO> listRespuesta = new ArrayList<RespuestaVO>();
		try {
			Connection connection = ConnectionFactory.getConnection();

			PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Respuesta WHERE idPregunta=?");
			stmt.setLong(1, idPregunta);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				RespuestaVO respuesta = extractRespuestaFromResultSet(rs);
				listRespuesta.add(respuesta);
			}

			stmt.close();
			connection.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return listRespuesta;
	}

	/**
	 * Inserta una pregunta con sus respectivas preguntas en la base de datos.
	 * 
	 * @param pregunta
	 * @return ID de contenido si la inserción ha sido correcta, -1 si no
	 * @throws ErrorInternoException
	 */
	public Long insertPregunta(PreguntaVO pregunta, List<RespuestaVO> respuestas) throws ErrorInternoException {
		Connection connection = null;
		try {
			connection = ConnectionFactory.getConnection();
			connection.setAutoCommit(false);
			
			Long idContenido = insertContenido(pregunta);

			if (idContenido > 0) {
				PreparedStatement stmt = connection.prepareStatement("INSERT INTO Pregunta VALUES (?, ?)");
				stmt.setLong(1, idContenido);
				stmt.setString(2, pregunta.getEnunciado());
				int result = stmt.executeUpdate();

				if (result == 1) {
					for (RespuestaVO respuesta : respuestas) {
						stmt = connection.prepareStatement("INSERT INTO Respuesta VALUES (NULL, ?, ?, ?)");
						stmt.setLong(1, idContenido);
						stmt.setString(2, respuesta.getEnunciado());
						stmt.setBoolean(3, respuesta.getCorrecta());
						if (stmt.executeUpdate() != 1) {
							return -1L;
						}
					}
					stmt.close();
					connection.commit();
					connection.close();
					return idContenido;
				}
			}

		} catch (SQLException ex) {
			if (connection != null) {
				try {
					connection.rollback();
				} catch (SQLException exroll) {
					exroll.printStackTrace();
				}
			}
			ex.printStackTrace();
			throw new ErrorInternoException();
		}
		return -1L;
	}

	/**
	 * Inserta una respuesta en la base de datos.
	 * 
	 * @param respuesta
	 * @return true si la inserción ha sido correcta, false en caso contrario
	 * @throws ErrorInternoException
	 */
	public boolean insertRespuesta(RespuestaVO respuesta) throws ErrorInternoException {
		try {
			Connection connection = ConnectionFactory.getConnection();

			PreparedStatement stmt = connection.prepareStatement("INSERT INTO Respuesta VALUES (NULL, ?, ?, ?)");
			stmt.setLong(1, respuesta.getIdPregunta());
			stmt.setString(2, respuesta.getEnunciado());
			stmt.setBoolean(3, respuesta.getCorrecta());
			int result = stmt.executeUpdate();

			stmt.close();
			connection.close();
			if (result == 1) {
				return true;
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new ErrorInternoException();
		}
		return false;
	}

	/**
	 * Inserta la contestación que da un usuario a una pregunta y actualiza su
	 * puntuación
	 * 
	 * @param idUsuario  ID del usuario que responde
	 * @param idPregunta ID de la pregunta que responde
	 * @param contesta   Mapa de correspondencia entre idRespuesta y la contestaci�n
	 *                   del usuario
	 * @return true si la inserción ha sido correcta, false en caso contrario
	 * @throws PreguntaYaRespondidaException
	 * @throws ErrorInternoException
	 */
	public boolean insertContestacion(Long idUsuario, Long idPregunta, Map<Long, Boolean> contesta)
			throws PreguntaYaRespondidaException, ErrorInternoException {
		Connection connection = null;
		try {
			connection = ConnectionFactory.getConnection();
			connection.setAutoCommit(false);
			
			// Comprobar que existe un usuario con idUsuario
			PreparedStatement stmtPreUsuario = connection.prepareStatement("SELECT idUsuario FROM Usuario where idUsuario=?");
			stmtPreUsuario.setLong(1, idUsuario);
			ResultSet rsPreUsuario = stmtPreUsuario.executeQuery();
			rsPreUsuario.last();
			if (rsPreUsuario.getRow() != 1) {
				throw new ErrorInternoException(); // no existia un usuario con ese id
			}
			stmtPreUsuario.close();

			// Buscar todas las respuestas de la pregunta
			PreparedStatement stmtPregunta = connection.prepareStatement("SELECT idRespuesta, correcta FROM Respuesta WHERE idPregunta=?");
			stmtPregunta.setLong(1, idPregunta);
			ResultSet rsPregunta = stmtPregunta.executeQuery();
			rsPregunta.last();
			int numRespuestas = (int) rsPregunta.getRow();
			rsPregunta.first();

			// Insertar, para cada pregunta, la respuesta del usuario e ir almacenando la
			// puntuación
			double puntuacion = 0.0;
			do {
				Long idRespuesta = rsPregunta.getLong("idRespuesta");
				if (contesta.containsKey(idRespuesta)) {
					// Sumar la puntuación
					if (contesta.get(idRespuesta) == rsPregunta.getBoolean("correcta")) {
						puntuacion += 10.0 / numRespuestas;
					}
					// Insertar la contestacion en la BDD
					PreparedStatement stmtRespuesta = connection.prepareStatement("INSERT INTO Contesta VALUES (?, ?, ?)");
					stmtRespuesta.setLong(1, idUsuario);
					stmtRespuesta.setLong(2, idRespuesta);
					stmtRespuesta.setBoolean(3, contesta.get(idRespuesta));
					int result = stmtRespuesta.executeUpdate();
					if (result != 1) {
						stmtRespuesta = connection.prepareStatement("SELECT * FROM Contesta WHERE idRespuesta=?");
						stmtRespuesta.setLong(1, idRespuesta);
						ResultSet rsRespuesta = stmtRespuesta.executeQuery();
						if (rsRespuesta.next()) {
							throw new PreguntaYaRespondidaException();
						} else {
							throw new ErrorInternoException();
						}
					}
					stmtRespuesta.close();
				} else {
					throw new ErrorInternoException(); // no ha contestado a una respuesta de la pregunta
				}
			} while (rsPregunta.next());
			stmtPregunta.close();

			// Actualizar la puntuación del usuario
			PreparedStatement stmtUsuario = connection
					.prepareStatement("UPDATE Usuario SET puntuacion=puntuacion+? WHERE idUsuario=?");
			stmtUsuario.setDouble(1, puntuacion);
			stmtUsuario.setDouble(2, idUsuario);
			int resultUsuario = stmtUsuario.executeUpdate();

			stmtUsuario.close();
			if (resultUsuario == 1) {
				connection.commit();
				connection.close();
				return true;
			}
		} catch (SQLException ex) {
			if (connection != null) {
				try {
					connection.rollback();
				} catch (SQLException exroll) {
					exroll.printStackTrace();
				}
			}
			ex.printStackTrace();
			throw new ErrorInternoException();
		}
		return false;
	}

	/**
	 * Actualiza los datos de una pregunta (asumiendo que ya existe una pregunta con
	 * ese ID).
	 * 
	 * @param pregunta
	 * @return true si la actualización ha sido correcta, false en caso contrario
	 * @throws ErrorInternoException
	 */
	public boolean updatePregunta(PreguntaVO pregunta) throws ErrorInternoException {
		try {
			Connection connection = ConnectionFactory.getConnection();

			boolean resultContenido = updateContenido(pregunta);

			if (!resultContenido) {
				return false;
			}

			PreparedStatement stmt = connection.prepareStatement("UPDATE Pregunta SET enunciado=? WHERE idContenido=?");
			stmt.setString(1, pregunta.getEnunciado());
			stmt.setLong(2, pregunta.getIdContenido());
			int result = stmt.executeUpdate();

			stmt.close();
			connection.close();
			if (result == 1) {
				return false;
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new ErrorInternoException();
		}
		return false;
	}

	/**
	 * Actualiza los datos de un reto (asumiendo que ya existe un reto con ese ID).
	 * 
	 * @param reto
	 * @return true si la actualizaci�n ha sido correcta, false en caso contrario
	 * @throws ErrorInternoException
	 */
	public boolean updateRespuesta(RespuestaVO respuesta) throws ErrorInternoException {
		try {
			Connection connection = ConnectionFactory.getConnection();

			PreparedStatement stmt = connection.prepareStatement("UPDATE Respuesta SET enunciado=?, correcta=? WHERE idRespuesta=?");
			stmt.setString(1, respuesta.getEnunciado());
			stmt.setBoolean(2, respuesta.getCorrecta());
			stmt.setLong(3, respuesta.getIdRespuesta());
			int result = stmt.executeUpdate();

			stmt.close();
			connection.close();
			if (result == 1) {
				return true;
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new ErrorInternoException();
		}
		return false;
	}

	/**
	 * Elimina a una pregunta y sus respectivas respuestas de la base de datos según
	 * su id.
	 * 
	 * @param id
	 * @return true si el borrado ha sido correcto, false en caso contrario
	 * @throws ErrorInternoException
	 */
	public boolean deletePregunta(Long id) throws ErrorInternoException {
		return deleteContenido(id);
	}

	/**
	 * Elimina a una respuesta de la base de datos según su id.
	 * 
	 * @param id
	 * @return true si el borrado ha sido correcto, false en caso contrario
	 * @throws ErrorInternoException
	 */
	public boolean deleteRespuesta(Long id) throws ErrorInternoException {
		try {
			Connection connection = ConnectionFactory.getConnection();

			PreparedStatement stmt = connection.prepareStatement("DELETE FROM Respuesta WHERE idRespuesta=?");
			stmt.setLong(1, id);
			int result = stmt.executeUpdate();

			stmt.close();
			connection.close();
			if (result == 1) {
				return true;
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new ErrorInternoException();
		}
		return false;
	}

	
	/**
	 * Búsqueda de hasta las últimas num preguntas según su fecha de realización y
	 * según si ha respondido el usuario
	 * 
	 * @param num
	 * @param elegirContestadas true/false para si/no elegir las contestadas, null
	 *                          para elegir todas
	 * @param busqueda          null para elegir todas, no null para buscar
	 *                          preguntas con ese contenido en el enunciado
	 * @param idUsuario         Si busqueda es diferente de null, idUsuario que ha
	 *                          respondido a esas preguntas
	 * @return Lista de hasta num preguntas ordenadas por fecha de realización
	 * @throws ErrorInternoException
	 */
	private List<PreguntaVO> getPreguntasUltimasHelper(int num, Boolean elegirContestadas, Long idUsuario, String busqueda) throws ErrorInternoException {
		List<PreguntaVO> listPregunta = new ArrayList<PreguntaVO>();
		try {
			Connection connection = ConnectionFactory.getConnection();

			String where = "WHERE estado='VALIDADO'";
			if (elegirContestadas != null) {
				String dentro = "SELECT DISTINCT idPregunta FROM Contesta NATURAL JOIN Respuesta WHERE idUsuario = ?";
				if (elegirContestadas) {
					where += " AND idContenido IN (" + dentro + ")";
				} else {
					where += " AND idContenido NOT IN (" + dentro + ")";
				}
			}
			if (busqueda != null && !busqueda.trim().isEmpty()) {
				where += " AND enunciado LIKE ?";
			}

			PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Pregunta NATURAL JOIN Contenido " + where + " ORDER BY fechaRealizacion DESC");
			int i = 1;
			if (elegirContestadas != null) {
				stmt.setLong(i, idUsuario);
				i++;
			}
			if (busqueda != null && !busqueda.trim().isEmpty()) {
				stmt.setString(i, "%" + busqueda + "%");
				i++;
			}
			ResultSet rs = stmt.executeQuery();
			while (rs.next() && listPregunta.size() < num) {
				PreguntaVO pregunta = extractPreguntaFromResultSet(rs);
				listPregunta.add(pregunta);
			}

			stmt.close();
			connection.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new ErrorInternoException();
		}
		return listPregunta;
	}
	
	/**
	 * Búsqueda de hasta las últimas num preguntas según su fecha de realización y
	 * según si ha respondido el usuario
	 * 
	 * @param num
	 * @param elegirContestadas true/false para si/no elegir las contestadas, null
	 *                          para elegir todas
	 * @param busqueda          null para elegir todas, no null para buscar
	 *                          preguntas con ese contenido en el enunciado
	 * @param idUsuario         Si busqueda es diferente de null, idUsuario que ha
	 *                          respondido a esas preguntas
	 * @return Lista de hasta num preguntas ordenadas por fecha de realización
	 * @throws ErrorInternoException
	 */
	private List<PreguntaVO> getPreguntasUltimasHelperByPag(int preguntasPorPagina, Boolean elegirContestadas, Long idUsuario, String busqueda, int pagina) throws ErrorInternoException {
		List<PreguntaVO> listPregunta = new ArrayList<PreguntaVO>();
		try {
			Connection connection = ConnectionFactory.getConnection();

			String where = "WHERE estado='VALIDADO'";
			if (elegirContestadas != null) {
				String dentro = "SELECT DISTINCT idPregunta FROM Contesta NATURAL JOIN Respuesta WHERE idUsuario = ?";
				if (elegirContestadas) {
					where += " AND idContenido IN (" + dentro + ")";
				} else {
					where += " AND idContenido NOT IN (" + dentro + ")";
				}
			}
			if (busqueda != null && !busqueda.trim().isEmpty()) {
				where += " AND enunciado LIKE ?";
			}

			PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Pregunta NATURAL JOIN Contenido " + where + " ORDER BY fechaRealizacion DESC");
			int i = 1;
			if (elegirContestadas != null) {
				stmt.setLong(i, idUsuario);
				i++;
			}
			if (busqueda != null && !busqueda.trim().isEmpty()) {
				stmt.setString(i, "%" + busqueda + "%");
				i++;
			}
			ResultSet rs = stmt.executeQuery();
			 rs.absolute(preguntasPorPagina * (pagina - 1));
            while (rs.next() && listPregunta.size() < preguntasPorPagina) {
            	PreguntaVO pregunta = extractPreguntaFromResultSet(rs);
            	listPregunta.add(pregunta);
            }

			stmt.close();
			connection.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new ErrorInternoException();
		}
		return listPregunta;
	}
	
	/**
	 * Búsqueda de hasta las últimas num preguntas según su fecha de realización y
	 * según si ha respondido el usuario
	 * 
	 * @param num
	 * @param elegirContestadas true/false para si/no elegir las contestadas, null
	 *                          para elegir todas
	 * @param busqueda          null para elegir todas, no null para buscar
	 *                          preguntas con ese contenido en el enunciado
	 * @param idUsuario         Si busqueda es diferente de null, idUsuario que ha
	 *                          respondido a esas preguntas
	 * @return Lista de hasta num preguntas ordenadas por fecha de realización
	 * @throws ErrorInternoException
	 */
	private int getNumPreguntasUltimasHelper(int num, Boolean elegirContestadas, Long idUsuario, String busqueda) throws ErrorInternoException {
		int noPreguntas;
		
		try {
			Connection connection = ConnectionFactory.getConnection();

			String where = "WHERE estado='VALIDADO'";
			if (elegirContestadas != null) {
				String dentro = "SELECT DISTINCT idPregunta FROM Contesta NATURAL JOIN Respuesta WHERE idUsuario = ?";
				if (elegirContestadas) {
					where += " AND idContenido IN (" + dentro + ")";
				} else {
					where += " AND idContenido NOT IN (" + dentro + ")";
				}
			}
			if (busqueda != null && !busqueda.trim().isEmpty()) {
				where += " AND enunciado LIKE ?";
			}

			PreparedStatement stmt = connection.prepareStatement("SELECT COUNT(idContenido) AS numContenido FROM Pregunta NATURAL JOIN Contenido " + where + " ORDER BY fechaRealizacion DESC");
			int i = 1;
			if (elegirContestadas != null) {
				stmt.setLong(i, idUsuario);
				i++;
			}
			if (busqueda != null && !busqueda.trim().isEmpty()) {
				stmt.setString(i, "%" + busqueda + "%");
				i++;
			}
			ResultSet rs = stmt.executeQuery();
			
			rs.first();
			noPreguntas = rs.getInt("numContenido");

			stmt.close();
			connection.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new ErrorInternoException();
		}
		return noPreguntas;
	}

	/**
	 * Extrae los datos de una pregunta dado un ResultSet. IMPORTANTE: El resultado
	 * de la consulta debe tener los atributos de Contenido además de Pregunta.
	 * 
	 * @param rs
	 * @return Datos de la pregunta de la fila que apunta rs
	 * @throws SQLException
	 */
	private PreguntaVO extractPreguntaFromResultSet(ResultSet rs) throws SQLException {
		PreguntaVO pregunta = new PreguntaVO(
			rs.getLong("idContenido"),
			rs.getLong("idAutor"),
			rs.getLong("numVisitas"),
			rs.getDate("fechaRealizacion"),
			ContenidoVO.Estado.valueOf(rs.getString("estado")),
			rs.getString("enunciado")
		);
		return pregunta;
	}

	/**
	 * Extrae los datos de una respuesta dado un ResultSet.
	 * 
	 * @param rs
	 * @return Datos de la respuesta de la fila que apunta rs
	 * @throws SQLException
	 */
	private RespuestaVO extractRespuestaFromResultSet(ResultSet rs) throws SQLException {
		RespuestaVO respuesta = new RespuestaVO(
			rs.getLong("idRespuesta"),
			rs.getLong("idPregunta"),
			rs.getString("enunciado"),
			rs.getBoolean("correcta")
		);
		return respuesta;
	}

}
