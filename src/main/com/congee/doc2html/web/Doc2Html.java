package com.congee.doc2html.web;

import com.congee.doc2html.converter.KWBXHTMLMapper;
import com.congee.doc2html.properties.SingleDoc2HtmlProperties;
import org.apache.poi.xwpf.converter.core.IURIResolver;
import org.apache.poi.xwpf.converter.xhtml.DefaultContentHandlerFactory;
import org.apache.poi.xwpf.converter.xhtml.IContentHandlerFactory;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Servlet implementation class Word2Html
 */
@WebServlet("/Doc2Html")
public class Doc2Html extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Doc2Html() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		try {
			docx2Html(request, response);
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}
	
	public void docx2Html(HttpServletRequest request, HttpServletResponse response) throws Exception {
		InputStream fis = null;
		try {
			 
			fis = new FileInputStream(SingleDoc2HtmlProperties.docLocation());
			XWPFDocument document = new XWPFDocument(fis);
			final String imgUrl = "ImageLoader?imageId=";
			XHTMLOptions options = XHTMLOptions.create().URIResolver(new IURIResolver(){

				@Override
				public String resolve(String uri) {
					int ls = uri.lastIndexOf('/');
					if (ls >= 0)
						uri = uri.substring(ls+1);
					return imgUrl+uri;
				}});
			OutputStream out = response.getOutputStream();
			IContentHandlerFactory factory = options.getContentHandlerFactory();
			if (factory == null) {
				factory = DefaultContentHandlerFactory.INSTANCE;
			}
			options.setIgnoreStylesIfUnused(false);
			KWBXHTMLMapper mapper =
					new KWBXHTMLMapper(document, factory.create(out, null, options), options);
		    mapper.start();
		}
		catch(Exception ex) {
			throw ex;
		}
		finally {
			if(fis != null) {
				fis.close();
			}
		}
	}

}
