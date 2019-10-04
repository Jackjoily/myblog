package code.jack.myblog.controller;

import code.jack.myblog.dto.FileDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FileController {
    @PostMapping("/file/upload")
    @ResponseBody
    public FileDto upload() {
        FileDto fileDto = new FileDto();
        fileDto.setSuccess(1);
        fileDto.setUrl("/images/ji6100.jpg");
        return fileDto;
    }

}
