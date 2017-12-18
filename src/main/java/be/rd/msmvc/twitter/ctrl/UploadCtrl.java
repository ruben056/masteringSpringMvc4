package be.rd.msmvc.twitter.ctrl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import be.rd.msmvc.config.upload.PicturesUploadProperties;
import be.rd.msmvc.twitter.UserProfileSession;

@Controller
@SessionAttributes("picturePath")
@RequestMapping(path= "/mvc/twitter/profile")
public class UploadCtrl {
	
	@Autowired
	private UserProfileSession userProfileSession;
	
	@Autowired
	private PicturesUploadProperties picturesUploadProperties;
	
	@Autowired
	private MessageSource messageSource; 
	
	
	@ExceptionHandler(IOException.class)
	public ModelAndView handleIOException(Locale locale) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/twitter/profile/profilePage");
		mv.addObject("", messageSource.getMessage("upload.io.exception", null, locale));
		return mv;
	}
	
	@RequestMapping(path = "/upload", method = RequestMethod.POST)
	public String doUpload(MultipartFile file, RedirectAttributes redirectAttr, Model model) throws IOException {
	
//		uncomment for testing exception handlers
//		if(true) {
//			throw new MultipartException("some info");
//			throw new IOException("some info");
//		}
		
		if(file.isEmpty() || !isImage(file)) {
			redirectAttr.addFlashAttribute("error", "This is not a valid image file");
			return "redirect:/mvc/twitter/profile";
		}
		
		File tmpFile = copyFileToServer(file);
		
		userProfileSession.setPicturePath(tmpFile.getCanonicalPath());
		
		return "redirect:/mvc/twitter/profile";
	}


	@RequestMapping(path = "/uploadedPicture")
	public void getUploadedPicture(HttpServletResponse response) throws IOException {
		
		String picturePath = userProfileSession.getPicturePath();
		if(picturePath == null) {
			picturePath = picturesUploadProperties.getAnonymousPicture().getFile().getCanonicalPath();
		}
		
		response.setHeader("Content-Type",URLConnection.guessContentTypeFromName(picturePath));
		Files.copy(Paths.get(picturePath), response.getOutputStream());
	}
	
	private boolean isImage(MultipartFile file) {
		return file.getContentType().startsWith("image");
	}
	
	private File copyFileToServer(MultipartFile file) throws IOException, FileNotFoundException {
		String fileName = file.getOriginalFilename();
		String[] fileInfo = fileName.split("[.]");
		File tmpFile = File.createTempFile(fileInfo[0], "."+fileInfo[1], 
				picturesUploadProperties.getUploadPath().getFile());
		
		
		OutputStream out = new FileOutputStream(tmpFile);
		try(InputStream in = file.getInputStream()){
			IOUtils.copy(in, out);	
		}
		return tmpFile;
	}
}
