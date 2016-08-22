package net.roseindia.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.ui.Model;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import javax.servlet.ServletConfig;
import java.io.FileOutputStream;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.BindException;

import net.roseindia.form.UploadItem;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Controller
@RequestMapping(value = "/uploadfile")
public class UploadFileController {
	private String uploadFolderPath;
	ServletConfig config;

	public String getUploadFolderPath() {
		return uploadFolderPath;
	}

	public void setUploadFolderPath(String uploadFolderPath) {
		this.uploadFolderPath = uploadFolderPath;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String getUploadForm(Model model) {
		model.addAttribute(new UploadItem());
		return "/uploadfile";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String create(UploadItem uploadItem, HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors,
			HttpSession session) {
		try {

			MultipartFile filea = uploadItem.getFileData();

			InputStream inputStream = null;
			OutputStream outputStream = null;
			if (filea.getSize() > 0) {
				inputStream = filea.getInputStream();
				// File realUpload = new File("C:/");
				outputStream = new FileOutputStream("C:\\test111\\"
						+ filea.getOriginalFilename());
				//System.out.println("====22=========");
				System.out.println(filea.getOriginalFilename());
				//System.out.println("=============");
				int readBytes = 0;
				byte[] buffer = new byte[8192];
				while ((readBytes = inputStream.read(buffer, 0, 8192)) != -1) {
					//System.out.println("===ddd=======");
					outputStream.write(buffer, 0, readBytes);
				}
				outputStream.close();
				inputStream.close();
				session.setAttribute("uploadFile", "C:\\test111\\"
						+ filea.getOriginalFilename());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/forms/uploadfileindex";
	}
}
