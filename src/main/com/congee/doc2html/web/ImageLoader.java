package com.congee.doc2html.web;

import com.congee.doc2html.properties.SingleDoc2HtmlProperties;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFPictureData;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

/**
 * Servlet implementation class ImageLoader
 */
@WebServlet("/ImageLoader")
public class ImageLoader extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ImageLoader() {
        super();
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		OutputStream img = response.getOutputStream();
		InputStream fis = getFileInputStream(); // docx file input stream
		if(fis != null) {
			String imageId = request.getParameter("imageId");
			XWPFDocument document = new XWPFDocument(fis); // load document				
			XWPFPictureData pic = document.getPictureDataByID(imageId);// get picture
			String ext = imageId.substring(imageId.lastIndexOf(".") + 1);
			response.setContentType("image/" + ext);// set correct content type according to file type ServletOutputStream
			if (pic != null)
				img.write(pic.getData());
			else {
				List<XWPFPictureData> pics = document.getAllPictures();
				for (XWPFPictureData p : pics) {
					if (imageId.equals(p.getFileName())) {
						img.write(p.getData());
						break;
					}
				}
			}
		}

	}

//	"E:\\Application Data\\MetricStream\\Attachments\\Sample data.docx"
	private InputStream getFileInputStream() throws FileNotFoundException {
		return new FileInputStream(SingleDoc2HtmlProperties.docLocation());
	}

}
