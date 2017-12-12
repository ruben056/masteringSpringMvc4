package be.rd.msmvc.ctrl.twitter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(path= "/mvc/twitter/profile")
public class UploadCtrl {

	private static Resource PICTURES_DIR = new FileSystemResource("./pictures");
	
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
		File tmpFile = File.createTempFile(fileInfo[0], "."+fileInfo[1], PICTURES_DIR.getFile());
		
		OutputStream out = new FileOutputStream(tmpFile);
		try(InputStream in = file.getInputStream()){
			IOUtils.copy(in, out);	
		}
		
		return "/twitter/profile/uploadPage";
	}

	private boolean isImage(MultipartFile file) {
		return file.getContentType().startsWith("image");
	}
}
