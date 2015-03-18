package com.youngball.Gather.action;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.youngball.Gather.domain.User;
import com.youngball.Gather.service.UserService;
import com.youngball.Gather.util.ValidateUtil;

@Controller
@Scope("prototype")
public class RegAction extends BaseAction<User> {

	private static final long serialVersionUID = 4618881342868567454L;

	//模型对象
	private User model = new User();
	
	//确认密码
	private String confirmPassword;
	
	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	//注入UserService
	@Resource
	private UserService userService;
	
	public User getModel() {
		return model;
	}
	
	/**
	 * 到达注册页面
	 * @return
	 */
	public String toRegPage(){
		return "regPage";
	}
	
	/**
	 * 进行注册
	 * @return
	 */
	public String doReg(){
		userService.saveEntity(model);
		return SUCCESS;
	}
	
	public void validate(){
		//非空
		if(!ValidateUtil.isValid(model.getEmail())){
			addFieldError("email", "email是必填项目");
		}
		if(!ValidateUtil.isValid(model.getNickname())){
			addFieldError("nickname", "nickname是必填项目");
		}
		if(!ValidateUtil.isValid(model.getPassword())){
			addFieldError("password", "密码是必填项目");
		}
		if(this.hasErrors()){
			return ;
		}
		//密码一致
		if(!model.getPassword().equals(confirmPassword)){
			addFieldError("password", "密码输入不一致");
			return ;
		}
		//email是否占用
		boolean b = userService.isRegisted(model.getEmail());
		if(b){
			addFieldError("email", "邮箱被占用");
		}
	} 
}















