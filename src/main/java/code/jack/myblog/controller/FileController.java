package code.jack.myblog.controller;

import code.jack.myblog.dto.FileDto;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Controller
public class FileController {
    @PostMapping("/file/upload")
    @ResponseBody
    public FileDto upload(HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        Map<String, String> map = new HashMap<String, String>();
        //1.创建DiskFileItemFactory对象，配置缓存信息
        DiskFileItemFactory factory = new DiskFileItemFactory();
        //创建一个ServletFileUpload
        ServletFileUpload sfu = new ServletFileUpload(factory);
        sfu.setHeaderEncoding("utf-8");
        //开始解析文件
        try {
            List<FileItem> items = sfu.parseRequest(request);
            for (FileItem fileItem : items) {
                //判断是文件 还是普通的数据
                if (fileItem.isFormField()) {
                    //普通数据
                    String fieldName = fileItem.getFieldName();
                    if (fieldName.equals("info")) {
                        //获取普通数据的信息
                        String info = fileItem.getString("utf-8");
                        System.out.println(info);
                    }
                } else {
                    //文件
                    //获取文件的名称
                    String name = fileItem.getName();
                    map.put(name, fileItem.getSize() + "");
                    //获取文件的实际内容
                    InputStream is = fileItem.getInputStream();
                    FileUtils.copyInputStreamToFile(is, new File(  name));
                }
            }
        } catch (FileUploadException | IOException ex) {
        }

        FileDto fileDto = new FileDto();
        fileDto.setSuccess(1);
        fileDto.setUrl("/uploadimages/1348814103993.jpg");
        return fileDto;
    }

}
