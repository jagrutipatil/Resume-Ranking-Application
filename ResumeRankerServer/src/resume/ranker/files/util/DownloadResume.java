package resume.ranker.files.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import resume.ranker.constants.PathConfigurations;

/**
 * TODO add good documentation
 */
@WebServlet("/DownloadResume")
public class DownloadResume extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public DownloadResume() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		String fileName = request.getParameter("fileName");
		System.out.println("FileName: " + fileName);

		String filePath = PathConfigurations.SOURCE_RESUME + fileName;
		System.out.println("FilePath: " + filePath);

		String fileType = "application/pdf";
		// Find this file id in database to get file name, and file type

		// You must tell the browser the file type you are going to send
		// for example application/pdf, text/plain, text/html, image/jpg
		response.setContentType(fileType);

		// Make sure to show the download dialog
		response.setHeader("Content-disposition", "attachment; filename=" + fileName);
		response.addHeader("Access-Control-Allow-Origin", "*");

		// Assume file name is retrieved from database
		// For example D:\\file\\test.pdf

		File my_file = new File(filePath);

		// This should send the file to browser
		OutputStream out = response.getOutputStream();
		FileInputStream in = new FileInputStream(my_file);
		byte[] buffer = new byte[4096];
		int length;
		while ((length = in.read(buffer)) > 0) {
			out.write(buffer, 0, length);
		}
		in.close();
		out.flush();

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}
