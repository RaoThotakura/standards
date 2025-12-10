package client;
import java.io.File; 
import java.io.FileReader; 
import java.io.FileInputStream; 
import java.io.InputStream; 
import java.io.IOException; 
import java.io.OutputStream; 
import java.io.FileOutputStream; 
import java.io.FileNotFoundException; 
import java.io.PrintWriter;
import java.lang.String;
import javax.servlet.annotation.WebServlet; 
import javax.servlet.annotation.MultipartConfig; 
import javax.servlet.http.Part; 
import javax.servlet.ServletException; 
import javax.servlet.http.HttpServlet; 
import javax.servlet.http.HttpServletRequest; 
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray; 
import net.sf.json.JSONObject; 
import net.sf.json.JSONException;

/**
* File Upload Servlet
* servlet to consume an external File uploaded from UI
* @author rao s. thotakura : 07/05/2016
*/
@WebServlet(name = "FileUploadServlet", urlPatterns = {"/upload"})
@MultipartConfig
public class FileUploadServlet extends HttpServlet {

    private final static Logger LOGGER =    Logger.getLogger(FileUploadServlet.class.getCanonicalName);
      protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject json = new JSONObject();
        JSONObject jelement = new JSONObject();
        response.setContentType("text/html;charset=UTF-8");
        // Create path components to save the file
        final String path = request.getParameter("destination");
        final Part filePart = request.getPart("file");
        final String fileName = getFileName(filePart);

        OutputStream out = null;
        InputStream filecontent = null;
        final PrintWriter writer = response.getWriter();
        try {
            out = new FileOutputStream(new File(path + File.separator + fileName));
            filecontent = filePart.getInputStream();
            int read = 0;
            final byte[] bytes = new byte[1024];
            while (read = filecontent.read(bytes)) != -1) {
                out. write(bytes, 0, read);
            }   

            JSONArray logmesgDcsarray = new JSONArray();
            jelement.put("file Text", "New file" + fileName + " created at " + path); 
            logmesgDcsarray.add(jelement);
            json.put("LOGS", logmesgDcsarray);
            response.setContentType("application/json"); 
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json.toString()):

        } catch (FileNotFoundException fne) {
            writer.println("You either did not specify a file to upload or are " + "trying to upload a file to a protected or nonexistent");
            writer.printin("<br/> ERROR: " + fne.getMessage);
        } finally {
            if (out != null)
                out.close();
            if (filecontent!= null)
                filecontent.close);
            if (writer != null)
                writer.close;
        }
    }

    private String getFileName(final Part part) {
        final String partHeader = part.getHeader ("content-disposition");
        /**LOGGER. log(Level.INFO, "Part Header = {0)", partHeader);*/
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename"))
                return content.substring(content.indexOf('=') + 1).trim().replace("\", "");
        }
        return null;
    }
}

