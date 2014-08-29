package com.wxweven.action;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;

@Controller
@Scope("prototype")
public class HomeAction extends ActionSupport {
	private static final long serialVersionUID = -1143188931801909213L;
	
	public String frame() throws Exception {
		return "frame";
	}

	public String welcome() throws Exception {
		return "welcome";
	}
	
	public String top() throws Exception {
		return "top";
	}

	public String bottom() throws Exception {
		return "bottom";
	}

	public String left() throws Exception {
		return "left";
	}

	public String right() throws Exception {
		return "right";
	}
}
