package be.rd.msmvc.ctrl.hello;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloCtrl {

	@RequestMapping(path = "/", method = RequestMethod.GET)
	@ResponseBody
	public String hello() {
		return "Hello there ...!" + System.lineSeparator();
	}
}
