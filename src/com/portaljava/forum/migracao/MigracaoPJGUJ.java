package com.portaljava.forum.migracao;

import java.io.IOException;
import java.util.StringTokenizer;
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
		StringTokenizer tokenizer = new StringTokenizer(uri, "/");
		
		// Redirecionar links da home do fórum
		if(uri == null || uri.isEmpty() || uri.equalsIgnoreCase(context) || 
				uri.equalsIgnoreCase(context + "/") || uri.equalsIgnoreCase(context + "/forum") ||
				uri.equalsIgnoreCase(context + "/forum/forums/list.page")) {
			
			httpServletResponse.setStatus(301);
			httpServletResponse.sendRedirect("http://www.guj.com.br/forums/list.java");
			return;
			
		}
		
		// Redirecionar links dos fórums
		
		String topicUrlPattern = "";
		boolean isTopicUrl = Pattern.matches(topicUrlPattern, uri);
		
		
		while(tokenizer.hasMoreTokens()) {
			System.out.println(tokenizer.nextToken());
		}
		
		System.out.println(uri);

		
		
		
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {

	}

}
