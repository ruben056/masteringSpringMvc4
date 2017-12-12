package be.rd.msmvc.ctrl.twitter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import be.rd.msmvc.config.upload.PicturesUploadProperties;

@Controller
@SessionAttributes("picturePath")
@RequestMapping(path= "/mvc/twitter/profile")
public class UploadCtrl {
	
	@Autowired
	private PicturesUploadProperties picturesUploadProperties;
	
	
	@ExceptionHandler(IOException.class)
	public ModelAndView handleIOException(IOException e) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/twitter/profile/uploadPage");
		mv.addObject("error", e.getMessage());
		return mv;
	}
	
	@ModelAttribute("picturePath")
	public Path picturePath() throws IOException {
		return Paths.get(picturesUploadProperties.getAnonymousPicture().getFile().getCanonicalPath());
	}
	
	
	@RequestMapping(path = "/upload", method = RequestMethod.GET)
	public String uploadPage() {
		return "/twitter/profile/uploadPage";
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
		
		model.addAttribute("picturePath", Paths.get(tmpFile.getCanonicalPath()));
		
		return "/twitter/profile/uploadPage";
	}

	@RequestMapping(path = "/uploadedPicture")
	public void getUploadedPicture(HttpServletResponse response, @ModelAttribute("picturePath") Path picturePath) throws IOException {
		response.setHeader("Content-Type",URLConnection.guessContentTypeFromName(picturePath.toString()));
		Files.copy(picturePath, response.getOutputStream());
	}
	
	private boolean isImage(MultipartFile file) {
		return file.getContentType().startsWith("image");
	}
}
