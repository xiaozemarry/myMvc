package Module.Upload.Action;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import Base.HttpBase;

@Controller
public class FileUploadController extends HttpBase{
	
	/**
	 * 上传文件
	 */
	@RequestMapping("/uploadFiles.do")
	public void uploadFiles(){
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request; // 转型为MultipartHttpRequest  
        System.out.println(request.getParameter("name"));
        MultiValueMap<String,MultipartFile> multiValueMap = multipartRequest.getMultiFileMap();
        Set<String> keyset = multiValueMap.keySet();
        Iterator<String>  it = keyset.iterator();
        while(it.hasNext())
        {
        	String next = it.next();
        	List<MultipartFile> fileList = multiValueMap.get(next);//之所以是集合,因为可能有多个文件
        	System.out.println(fileList.size());
        	//MultipartFile就是每个文件文件
        }
	}
    /** 
     * 1、文件上传 
     * @param request 
     * @param response 
     * @return 
     */  
    public void uploadFiles(HttpServletRequest request, HttpServletResponse response) {  
  
//        // 获得上传的文件（根据前台的name名称得到上传的文件）  
//        MultiValueMap<String, MultipartFile> multiValueMap = multipartRequest.getMultiFileMap();  
//        List<MultipartFile> file = multiValueMap.get("clientFile");  
//        //MultipartFile file = multipartRequest.getFile("clientFile");  
//        if(!file.isEmpty())
//        {  
//            //在这里就可以对file进行处理了，可以根据自己的需求把它存到数据库或者服务器的某个文件夹  
//            System.out.println("================="+file.get(0).getName() + file.get(0).getSize());  
//        }  
    }
}
