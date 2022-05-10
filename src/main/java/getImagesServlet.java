

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Servlet implementation class getImagesServlet
 */
@WebServlet("/getImagesServlet")
public class getImagesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public getImagesServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		String listOfLabels = request.getParameter("listOfLabels");
		
		
		int strLength=listOfLabels.length();
		List<String> labels = new ArrayList<String>();  
		strLength--;
		int i=0;
		while(listOfLabels.charAt(i)!=',') {
			i++;
			strLength--;
		}
		i++;String midWord="";
		while(i<=strLength) {
			
			if(listOfLabels.charAt(i)!=',') {
				midWord+=listOfLabels.charAt(i);
			}
			i++;
		}
		
		labels.add(midWord);
		
		
		
		//webscrapping
		Document document;
		List<String> imageUrls=new ArrayList<String>();
		 
		 for(String keyword:labels) {
			 try {
				 document = Jsoup.connect("https://www.google.com/search?tbm=isch&q="+keyword).get();
				 
				 
				 Elements images = document.getElementsByAttributeStarting("data-id");
				 for(Element image:images) {
					 String imageId = image.attr("data-id");
					 if(imageId.length() > 4) {
						 String midUrl = "https://www.google.com/search?tbm=isch&q=" + keyword + "#imgrc=" + imageId;
						 imageUrls.add(midUrl);
						 break;
					 }
				 }
				 
			    } catch (IOException e) {
				e.printStackTrace();
			    }  
			 
 
		 }
		 
		pw.println("Link : ");
		Iterator<String> iterator = imageUrls.iterator();
		String link=(String) iterator.next();
//		pw.println(link);
		pw.println("<a href=\"");
		pw.println(link+"\"");
		pw.println(">"+link+"</a>");	
	}

}
