package com.portaljava.forum.migracao;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
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
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {

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
		
		/* Redirecionar links dos fóruns do PJ para os fóruns do GUJ
		 * Exemplo: forum/forums/show/12.page
		 */
		String forumUrlPattern = context + "/forums/show/.*.page";
		boolean isForumUrl = Pattern.matches(forumUrlPattern, uri);
		if(isForumUrl) {
			Integer forumIdPj = Integer.parseInt(uri.split("/")[4].toString().replaceAll(".page", ""));
			Integer forumIdGuj = this.getForumDeParaMap().get(forumIdPj);
			
			if(forumIdGuj != null) {
				String forumUrlGuj = "http://www.guj.com.br/forums/show/" + forumIdGuj + ".java";
				httpServletResponse.sendRedirect(forumUrlGuj);
				return;
			}
			
		}

		// Se não achar nenhum pattern vai para a home do fórum
		httpServletResponse.sendRedirect("http://www.guj.com.br/forums/list.java");
		return;
		
	}

	/*
	 * IDs: pj => guj 
	 * 1 => 5 (Java Avançado)
	 * 2 => 2 (Participe => Off-topic)
	 * 6 => 22 (Persistência & DB)
	 * 8 => 13 (Interface Gráfica)
	 * 12 => 14 (Dispositivos móveis => Java ME)
	 * 13 => 16 (Moderadores => Colaboradores)
	 * 15 => 2 (Off-topic)
	 * 22 => 11 (Certificação)
	 * 26 => 4 (Java básico)
	 * 34 => 6 (Web)
	 * 35 => 18 (Ferramentas)
	 * 36 => 19 (Patterns / frameworks)
	 */
	private Map<Integer, Integer> getForumDeParaMap() {
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		map.put(1, 5);
		map.put(2, 2);
		map.put(6, 22);
		map.put(8, 13);
		map.put(12, 14);
		map.put(13, 16);
		map.put(15, 2);
		map.put(22, 11);
		map.put(26, 4);
		map.put(34, 6);
		map.put(35, 18);
		map.put(36, 19);
		
		return map;
	}
	
}
