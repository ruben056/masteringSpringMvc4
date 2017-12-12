package be.rd.msmvc.ctrl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

@Controller
public class UploadErrorCtrl {

	@RequestMapping("/uploadError")
	public ModelAndView onUploadError(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("/uploadErrorPage");
		mv.addObject("error", request.getAttribute(WebUtils.ERROR_MESSAGE_ATTRIBUTE));
		return mv;
	}
}
