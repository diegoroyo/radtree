package sistinfo.servlets.jsp.gestion;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sistinfo.servlets.jsp.FooterServlet;
import sistinfo.servlets.jsp.util.IncludeInRequest;

@SuppressWarnings("serial")
public class GestionContenidoServlet extends FooterServlet {

    /**
     * Redirect a doPost de la misma clase
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doPost(request, response);
    }

    /**
     * Carga el contenido del tipo especificado del usuario y lo muestra en una lista según gestionContenido.jsp
     * También recibe el número de elementos en la cola de validación.
     * 
     * Recibe 1 parámetro tipo de contenido a mostrar (noticia, pregunta o reto)
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    	super.doPost(request, response);
    	
    	request.setCharacterEncoding("UTF-8");
    	response.setCharacterEncoding("UTF-8");
    	
    	if (IncludeInRequest.includeNumInValidacion(request, response)
    			&& IncludeInRequest.includeMiContenido(request, response)) {
        	request.getRequestDispatcher("/jsp/gestion/gestionContenido.jsp").forward(request, response);
    	}
			
	}

}
