package sistinfo.filters.contenido;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sistinfo.capadatos.dao.ComentarioDAO;
import sistinfo.capadatos.dao.NoticiaDAO;
import sistinfo.capadatos.vo.NoticiaVO;
import sistinfo.excepciones.ErrorInternoException;

public class ListaNoticiasFilter implements Filter {

	FilterConfig filterConfig = null;

	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}

	/**
	 * Obtiene los datos de las noticias validadas del sistema e incluirlo en el atributo preguntas
	 */
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		
		servletRequest.setCharacterEncoding("UTF-8");
        servletResponse.setCharacterEncoding("UTF-8");
		
		if (servletRequest instanceof HttpServletRequest && servletResponse instanceof HttpServletResponse) {
			HttpServletRequest request = (HttpServletRequest)servletRequest;
			HttpServletResponse response = (HttpServletResponse)servletResponse;
			
			// Barra de búsqueda: string a buscar y filtrar por preguntas no contestadas o no
			String busqueda = request.getParameter("busqueda");
			NoticiaDAO noticiaDAO = new NoticiaDAO();
			ComentarioDAO comentarioDAO = new ComentarioDAO();
			try {
				// Según la barra de busqueda obtener las preguntas con unas caracteristicas u otras
				List<NoticiaVO> noticias;
				if (busqueda == null || busqueda.trim().isEmpty()) {
					noticias = noticiaDAO.getNoticiasUltimas(10);
				} else {
					noticias = noticiaDAO.getNoticiasBySearch(busqueda, 10);
				}
				// Añadir información especial e incluirlo en la request
				noticias = comentarioDAO.addNumComentariosToContenido(noticias);
				request.setAttribute("noticias", noticias);
			} catch (ErrorInternoException e) {
				e.printStackTrace();
				response.sendRedirect(request.getContextPath() + "/error-interno");
			}
			filterChain.doFilter(request, response);
		}
	}

}
