package code.jack.myblog.cache;

import code.jack.myblog.dto.TagDto;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TagCache {
    public List<TagDto> get() {
        List<TagDto> tagDtos = new ArrayList<>();
        TagDto program = new TagDto();
        program.setFirst(true);
        program.setCategoryName("开发语言");
        program.setTags(Arrays.asList("js", "Java", "php", "css", "node", "php", "python", "ruby", "golang", "asp.net"));
        tagDtos.add(program);
        TagDto framework = new TagDto();
        framework.setFirst(false);
        framework.setCategoryName("程序框架");
        framework.setTags(Arrays.asList("vue", "React", "spring", "springboot", "mybatis", "hibernate", "elasticsearch", "struct2"));
        tagDtos.add(framework);
        TagDto server = new TagDto();
        server.setFirst(false);
        server.setCategoryName("服务器");
        server.setTags(Arrays.asList("tomcat", "netty", "jetty", "nginx"));
        tagDtos.add(server);

        return tagDtos;
    }

    public  String filterINvalid(String tags) {
        String[] split = StringUtils.split(tags, ",");
        List<TagDto> tagDtos = get();
        List<String> collect = tagDtos.stream().flatMap(tag -> tag.getTags().stream()).collect(Collectors.toList());
        String invalid = Arrays.stream(split).filter(t ->!collect.contains(t)).collect(Collectors.joining(","));
        Arrays.stream(split).filter(t -> collect.contains(t)).collect(Collectors.joining(","));
        return invalid;
    }
}
