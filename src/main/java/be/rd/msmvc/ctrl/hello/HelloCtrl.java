package be.rd.msmvc.ctrl.hello;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloCtrl {

	@RequestMapping(path = "/rest/hello", method = RequestMethod.GET)
	@ResponseBody
	public String hello() {
		return "Hello there ...!" + System.lineSeparator();
	}
	
	@RequestMapping(path = "/mvc")
	public String resultPage(Model model) {	
		model.addAttribute("msg", "This msg is passed to the view from the ctrl");
		return "resultPage";
	}
	
	@RequestMapping(path = "/mvc/hello")
	public String helloWithTemplate(@RequestParam(name="name", defaultValue="Default name")String name ,Model model) {		
		model.addAttribute("msg", "hello " + name);
		return "resultPage";
	}
}
