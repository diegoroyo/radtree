package sistinfo.capadatos.dao;
import java.sql.*;
import java.util.LinkedList;
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
	 * B�squeda de pregunta por su identificador interno.
	 * @param id
	 * @return La pregunta si el id existe, null en caso contrario
	 * @throws ErrorInternoException 
	 */
	public PreguntaVO getPreguntaById(long id) throws ErrorInternoException {
		Connection connection = ConnectionFactory.getConnection();
        try {
        	
        	PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Pregunta NATURAL JOIN Contenido WHERE idContenido=?");
        	stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.last()) {
            	if (rs.getRow() == 1) {
            		PreguntaVO pregunta = extractPreguntaFromResultSet(rs);
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
	 * B�squeda de respuesta por su identificador interno.
	 * @param id
	 * @return La respuesta si el id existe, null en caso contrario
	 * @throws ErrorInternoException 
	 */
	public RespuestaVO getRespuestaById(long id) {
		Connection connection = ConnectionFactory.getConnection();
        try {
        	
        	PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Respuesta WHERE idRespuesta=?");
        	stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
            	RespuestaVO respuesta = extractRespuestaFromResultSet(rs);
                return respuesta;
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
	}
	
	/**
	 * B�squeda de preguntas que contienen search en su nombre nombre, cuerpo o URL, por orden de creaci�n (m�s recientes primero).
	 * @param search
	 * @return Lista con todas las preguntas
	 * @throws ErrorInternoException 
	 */
	public LinkedList<PreguntaVO> getPreguntaBySearch(String search) throws ErrorInternoException {
		Connection connection = ConnectionFactory.getConnection();
		LinkedList<PreguntaVO> listPregunta = new LinkedList<PreguntaVO>();
        try {
        	
        	PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Pregunta NATURAL JOIN Contenido WHERE enunciado LIKE '%?%' ORDER BY fechaRealizacion DESC");
        	stmt.setString(1, search);
            ResultSet rs = stmt.executeQuery();
            
            do {
            	PreguntaVO pregunta = extractPreguntaFromResultSet(rs);
            	listPregunta.add(pregunta);
            } while (rs.next());
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErrorInternoException();
        }
        return listPregunta;
	}
	
	/**
	 * B�squeda de hasta las �ltimas num preguntas seg�n su fecha de realizaci�n
	 * @param num
	 * @return Lista de hasta num preguntas ordenadas por fecha de realizaci�n
	 * @throws ErrorInternoException 
	 */
	public LinkedList<PreguntaVO> getPreguntasUltimas(int num) throws ErrorInternoException {
		return getPreguntasUltimasHelper(num, null);
	}
	
	/**
	 * B�squeda de hasta las �ltimas num preguntas seg�n su fecha de realizaci�n y si las ha contestado el usuario o no
	 * @param num
	 * @param contestadas Si el usuario ha contestado a la pregunta o no
	 * @return Lista de hasta num preguntas ordenadas por fecha de realizaci�n
	 * @throws ErrorInternoException 
	 */
	public LinkedList<PreguntaVO> getPreguntasUltimasContestadas(int num, boolean contestadas) throws ErrorInternoException {
		return getPreguntasUltimasHelper(num, contestadas);
	}

	/**
	 * B�squeda de respuesta por su identificador interno.
	 * @param id
	 * @return La respuesta si el id existe, null en caso contrario
	 * @throws ErrorInternoException 
	 */
	public LinkedList<RespuestaVO> getRespuestasByPregunta(long idPregunta) {
		Connection connection = ConnectionFactory.getConnection();
		LinkedList<RespuestaVO> listRespuesta = new LinkedList<RespuestaVO>();
        try {
        	
        	PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Respuesta WHERE idPregunta=?");
        	stmt.setLong(1, idPregunta);
            ResultSet rs = stmt.executeQuery();
            do {
            	RespuestaVO respuesta = extractRespuestaFromResultSet(rs);
            	listRespuesta.add(respuesta);
            } while (rs.next());
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return listRespuesta;
	}

	/**
	 * Inserta una pregunta con sus respectivas preguntas en la base de datos.
	 * @param pregunta
	 * @return true si la inserci�n ha sido correcta, false en caso contrario
	 * @throws ErrorInternoException
	 */
	public boolean insertPregunta(PreguntaVO pregunta, List<RespuestaVO> respuestas) throws ErrorInternoException {
		Connection connection = ConnectionFactory.getConnection();
        try {

        	int idContenido = insertContenido(pregunta);
        	
        	if (idContenido > 0) {
            	PreparedStatement stmt = connection.prepareStatement("INSERT INTO Pregunta VALUES (?, ?)");
            	stmt.setLong(1, idContenido);
            	stmt.setString(2, pregunta.getEnunciado());
            	int result = stmt.executeUpdate();
                
            	if (result == 1) {
            		int i = 0;
            		do {
                    	stmt = connection.prepareStatement("INSERT INTO Respuesta VALUES (NULL, ?, ?, ?)");
                    	stmt.setLong(1, idContenido);
                    	stmt.setString(2, respuestas.get(i).getEnunciado());
                    	stmt.setBoolean(3, respuestas.get(i).isCorrecta());
                    	i++;
                    	if (stmt.executeUpdate() != 1) {
                    		return false;
                    	}
            		} while (i < respuestas.size());
            		return true;
            	}
            	
        	}
        	
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErrorInternoException();
        }
        return false;
	}
	
	/**
	 * Inserta una respuesta en la base de datos.
	 * @param respuesta
	 * @return true si la inserci�n ha sido correcta, false en caso contrario
	 * @throws ErrorInternoException 
	 */
	public boolean insertRespuesta(RespuestaVO respuesta) throws ErrorInternoException {
		Connection connection = ConnectionFactory.getConnection();
        try {
        	
        	PreparedStatement stmt = connection.prepareStatement("INSERT INTO Respuesta VALUES (NULL, ?, ?, ?)");
        	stmt.setLong(1, respuesta.getIdPregunta());
        	stmt.setString(2, respuesta.getEnunciado());
        	stmt.setBoolean(3, respuesta.isCorrecta());
        	int result = stmt.executeUpdate();
        	
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
	 * Inserta la contestaci�n que da un usuario a una pregunta y actualiza su puntuaci�n
	 * @param idUsuario ID del usuario que responde
	 * @param idPregunta ID de la pregunta que responde
	 * @param contesta Mapa de correspondencia entre idRespuesta y la contestaci�n del usuario
	 * @return true si la inserci�n ha sido correcta, false en caso contrario
	 * @throws PreguntaYaRespondidaException
	 * @throws ErrorInternoException
	 */
	public boolean insertContestacion(long idUsuario, long idPregunta, Map<Long, Boolean> contesta) throws PreguntaYaRespondidaException, ErrorInternoException {
		Connection connection = ConnectionFactory.getConnection();
        try {
        	
        	// Comprobar que existe un usuario con idUsuario
        	PreparedStatement stmtPreUsuario = connection.prepareStatement("SELECT idUsuario FROM Usuario where idUsuario=?");
        	stmtPreUsuario.setLong(1, idUsuario);
            ResultSet rsPreUsuario= stmtPreUsuario.executeQuery();
            rsPreUsuario.last();
            if (rsPreUsuario.getRow() != 1) {
            	throw new ErrorInternoException(); // no existia un usuario con ese id
            }
        	
        	// Buscar todas las respuestas de la pregunta
        	PreparedStatement stmtPregunta = connection.prepareStatement("SELECT idRespuesta, correcta FROM Respuesta WHERE idPregunta=?");
        	stmtPregunta.setLong(1, idPregunta);
            ResultSet rsPregunta = stmtPregunta.executeQuery();
            rsPregunta.last();
            double numRespuestas = (double) rsPregunta.getRow();
            rsPregunta.first();
            
            // Insertar, para cada pregunta, la respuesta del usuario e ir almacenando la puntuaci�n
            double puntuacion = 0.0;
            do {
            	long idRespuesta = rsPregunta.getLong("idRespuesta");
            	if (contesta.containsKey(idRespuesta)) {
            		// Sumar la puntuaci�n
            		if (contesta.get(idRespuesta) == rsPregunta.getBoolean("correcta")) {
                		puntuacion += 10.0 / numRespuestas;	/* TODO: mejorar el sistema de puntuacion como se dijo */
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
            	} else {
            		throw new ErrorInternoException(); // no ha contestado a una respuesta de la pregunta
            	}
            } while (rsPregunta.next());
            
            // Actualizar la puntuaci�n del usuario
            PreparedStatement stmtUsuario = connection.prepareStatement("UPDATE Usuario SET puntuacion=puntuacion+?");
            stmtUsuario.setDouble(1, puntuacion);
            int resultUsuario = stmtUsuario.executeUpdate();
            
            if (resultUsuario == 1) {
            	return true;
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErrorInternoException();
        }
        return false;
	}	
	
	/**
	 * Actualiza los datos de una pregunta (asumiendo que ya existe una pregunta con ese ID).
	 * @param pregunta
	 * @return true si la actualizaci�n ha sido correcta, false en caso contrario
	 * @throws ErrorInternoException 
	 */
	public boolean updatePregunta(PreguntaVO pregunta) throws ErrorInternoException {
		Connection connection = ConnectionFactory.getConnection();
        try {
        	
        	boolean resultContenido = updateContenido(pregunta);
        	
        	if (!resultContenido) {
        		return false;
        	}
        	
        	PreparedStatement stmt = connection.prepareStatement("UPDATE Pregunta SET enunciado=? WHERE idContenido=?");
        	stmt.setString(1, pregunta.getEnunciado());
        	stmt.setLong(2, pregunta.getIdContenido());
        	int result = stmt.executeUpdate();

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
	 * @param reto
	 * @return true si la actualizaci�n ha sido correcta, false en caso contrario
	 * @throws ErrorInternoException 
	 */
	public boolean updateRespuesta(RespuestaVO respuesta) throws ErrorInternoException {
		Connection connection = ConnectionFactory.getConnection();
        try {
        	
        	PreparedStatement stmt = connection.prepareStatement("UPDATE Respuesta SET enunciado=?, correcta=? WHERE idRespuesta=?");
        	stmt.setString(1, respuesta.getEnunciado());
        	stmt.setBoolean(2, respuesta.isCorrecta());
        	stmt.setLong(3, respuesta.getIdRespuesta());
        	int result = stmt.executeUpdate();
        	
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
	 * Elimina a una pregunta y sus respectivas respuestas de la base de datos seg�n su id.
	 * @param id
	 * @return true si el borrado ha sido correcto, false en caso contrario
	 * @throws ErrorInternoException 
	 */
	public boolean deletePregunta(long id) throws ErrorInternoException {
        return deleteContenido(id);
	}
	
	/**
	 * Elimina a una respuesta de la base de datos seg�n su id.
	 * @param id
	 * @return true si el borrado ha sido correcto, false en caso contrario
	 * @throws ErrorInternoException
	 */
	public boolean deleteRespuesta(long id) throws ErrorInternoException {
		Connection connection = ConnectionFactory.getConnection();
        try {
        	
        	PreparedStatement stmt = connection.prepareStatement("DELETE FROM Respuesta WHERE idRespuesta=?");
        	stmt.setLong(1, id);
        	int result = stmt.executeUpdate();
            
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
	 * B�squeda de hasta las �ltimas num preguntas seg�n su fecha de realizaci�n y seg�n si ha respondido el usuario
	 * @param num
	 * @param elegirContestadas true/false para si/no elegir las contestadas, null para elegir todas
	 * @return Lista de hasta num preguntas ordenadas por fecha de realizaci�n
	 * @throws ErrorInternoException 
	 */
	private LinkedList<PreguntaVO> getPreguntasUltimasHelper(int num, Boolean elegirContestadas) throws ErrorInternoException {
		Connection connection = ConnectionFactory.getConnection();
		LinkedList<PreguntaVO> listPregunta = new LinkedList<PreguntaVO>();
        try {
        	
        	String where = "";
        	if (elegirContestadas != null) {
        		where = "SELECT DISTINCT idPregunta FROM Contesta NATURAL JOIN Respuesta";
        		if (elegirContestadas) {
        			where = "WHERE idPregunta IN (" + where + ")";
        		} else {
        			where = "WHERE idPregunta NOT IN (" + where + ")";
        		}
        	}

    		Statement stmt = connection.createStatement();
        	ResultSet rs = stmt.executeQuery("SELECT * FROM Pregunta NATURAL JOIN Contenido " + where + " ORDER BY fechaRealizacion DESC");
            do {
            	PreguntaVO pregunta = extractPreguntaFromResultSet(rs);
            	listPregunta.add(pregunta);
            } while (rs.next() && listPregunta.size() < num);
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErrorInternoException();
        }
        return listPregunta;
	}
	
	/**
	 * Extrae los datos de una pregunta dado un ResultSet.
	 * IMPORTANTE: El resultado de la consulta debe tener los atributos de Contenido adem�s de Pregunta.
	 * @param rs
	 * @return Datos de la pregunta de la fila que apunta rs
	 * @throws SQLException
	 */
	private PreguntaVO extractPreguntaFromResultSet(ResultSet rs) throws SQLException {
		PreguntaVO pregunta = new PreguntaVO(
         	rs.getLong("idContenido"),
         	rs.getLong("idUsuario"),
         	rs.getLong("numVisitas"),
         	rs.getDate("fechaRealizacion"),
         	ContenidoVO.Estado.valueOf(rs.getString("estado")),
         	rs.getString("enunciado")
         );
         return pregunta;
	}
	
	/**
	 * Extrae los datos de una respuesta dado un ResultSet.
	 * @param rs
	 * @return Datos de la respuesta de la fila que apunta rs
	 * @throws SQLException
	 */
	private RespuestaVO extractRespuestaFromResultSet(ResultSet rs) throws SQLException {
		RespuestaVO respuesta = new RespuestaVO(
         	rs.getLong("idRespuesta"),
         	rs.getLong("idPregunta"),
         	rs.getString("enunciado"),
         	rs.getBoolean("coorecta")
         );
         return respuesta;
	}

}