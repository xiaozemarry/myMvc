package controller.upload.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;

import base.HttpBase;
@Controller
@RequestMapping(value="/upload")
public class FileUpload extends HttpBase{
	@RequestMapping(value="simplegate",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public String simpleGateUpload(@RequestParam("file") MultipartFile[] file){
		//@RequestParam("filename") MultipartFile file
		JSONObject obj = new JSONObject();
		System.out.println(file[0].getName());
		System.out.println(file[0].getOriginalFilename());
		System.out.println(file[0].getSize());
		System.out.println(this.requestParameterToMap(request));
		obj.put("aaa","aaaaaaaaaaaaaaa");
		obj.put("flag","success");
		return obj.toString();
	}
} 
