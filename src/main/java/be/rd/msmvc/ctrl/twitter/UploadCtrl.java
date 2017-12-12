package be.rd.msmvc.ctrl.twitter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.nio.file.Path;

import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import be.rd.msmvc.config.upload.PicturesUploadProperties;

@Controller
@RequestMapping(path= "/mvc/twitter/profile")
public class UploadCtrl {
	
	@Autowired
	private PicturesUploadProperties picturesUploadProperties;

	@RequestMapping(path = "/upload", method = RequestMethod.GET)
	public String uploadPage() {
		return "/twitter/profile/uploadPage";
	}
	
	@RequestMapping(path = "/upload", method = RequestMethod.POST)
	public String doUpload(MultipartFile file, RedirectAttributes redirectAttr) throws IOException {
		
		if(file.isEmpty() || !isImage(file)) {
			redirectAttr.addFlashAttribute("error", "This is not a valid image file");
			return "redirect:/mvc/twitter/profile/upload";
		}
		
		String fileName = file.getOriginalFilename();
		String[] fileInfo = fileName.split("[.]");
		File tmpFile = File.createTempFile(fileInfo[0], "."+fileInfo[1], 
				picturesUploadProperties.getUploadPath().getFile());
		
		
		OutputStream out = new FileOutputStream(tmpFile);
		try(InputStream in = file.getInputStream()){
			IOUtils.copy(in, out);	
		}
		
		return "/twitter/profile/uploadPage";
	}

	@RequestMapping(path = "/uploadedPicture")
	public void getUploadedPicture(HttpServletResponse response) throws IOException {
		Resource anonymousPcResource = picturesUploadProperties.getAnonymousPicture();
		response.setHeader("Content-Type",
				URLConnection.guessContentTypeFromName(anonymousPcResource.getFilename()));
		IOUtils.copy(anonymousPcResource.getInputStream(), response.getOutputStream());
	}
	
	private boolean isImage(MultipartFile file) {
		return file.getContentType().startsWith("image");
	}
}
