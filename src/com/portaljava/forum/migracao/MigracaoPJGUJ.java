package com.portaljava.forum.migracao;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet Filter implementation class MigracaoPJGUJ
 */
public class MigracaoPJGUJ implements Filter {

    private static final long POST_ID_DIFF = 0;

	/**
     * Default constructor. 
     */
    public MigracaoPJGUJ() {

    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {

	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		
		String uri = httpServletRequest.getRequestURI();
		String context = httpServletRequest.getContextPath();

		// HTTP Status Code 301: Moved Permanently
		httpServletResponse.setStatus(301);
		
		/* Redirecionar links dos posts do fórum do PJ para o fórum do GUJ
		 * URI exemplo: forum/posts/list/1668.page
		 * Do PJ: http://www.portaljava.com/forum/posts/list/1668.page
		 * Para o GUJ: http://www.guj.com.br/posts/list/[1668 + POST_ID_DIFF].java
		 */
		String postUrlPattern = context + "/posts/list/.*.page";
		boolean isPostUrl = Pattern.matches(postUrlPattern, uri);
		if(isPostUrl) {
			long postIdPj = Long.parseLong(uri.split("/")[4].toString().replaceAll(".page", ""));
			long postIdGuj = POST_ID_DIFF + postIdPj;
			
			String postUrlGuj = "http://www.guj.com.br/posts/list/" + postIdGuj + ".java";
			
			httpServletResponse.sendRedirect(postUrlGuj);
			return;
		}
		
		/* Redirecionar links dos fóruns
		 * Exemplo: forum/forums/show/12.page
		 */
		String forumUrlPattern = context + "/forums/show/.*.page";
		boolean isForumUrl = Pattern.matches(forumUrlPattern, uri);
		if(isForumUrl) {
			// TODO Não sei como vai ficar o mapeamento dos IDs dos fóruns ainda
		}

		// Se não achar nenhum pattern vai para a home do fórum
		httpServletResponse.sendRedirect("http://www.guj.com.br/forums/list.java");
		return;
		
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {

	}

}
