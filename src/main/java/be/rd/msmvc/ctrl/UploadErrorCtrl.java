package be.rd.msmvc.ctrl;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UploadErrorCtrl {

	@Autowired
	private MessageSource messageSource;
	
	@RequestMapping("/uploadError")
	public ModelAndView onUploadError(Locale locale) {
		ModelAndView mv = new ModelAndView("/uploadErrorPage");
		mv.addObject("error", messageSource.getMessage("upload.file.too.big", null, locale));
		return mv;
	}
}
