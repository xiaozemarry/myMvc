package Module.Login.Action;

import org.springframework.web.portlet.bind.annotation.RenderMapping;

import Base.Base;

public class Login extends Base{
    
	/**
	 * 供系统启动的时候调用 默认加载 再访问的时候不用从新加载
	 */
	@RenderMapping(value="contextStart.do")
	public void ContextStartRequst(){
   		
	}
}
