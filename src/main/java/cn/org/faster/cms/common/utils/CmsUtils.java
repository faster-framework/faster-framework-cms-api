package cn.org.faster.cms.common.utils;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author zhangbowen
 * @since 2019-12-13
 */
public class CmsUtils {

    public static String getJarRootPath() {
        try {
            String path = ResourceUtils.getURL("classpath:").getPath();
            File rootFile = new File(path);
            if (!rootFile.exists()) {
                rootFile = new File("");
            }
            return rootFile.getAbsolutePath();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void generateHtml(Template template, String outputFileName,Object dataModel) throws IOException, TemplateException {
        File file = new File(outputFileName);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        FileWriter fileWriter = new FileWriter(outputFileName);
        template.process(dataModel, fileWriter);

    }
}
