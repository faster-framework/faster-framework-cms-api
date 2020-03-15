package cn.org.faster.cms.common.config;

import cn.hutool.core.io.file.FileReader;
import cn.org.faster.cms.common.properties.CmsProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author zhangbowen
 * @since 2019-12-11
 * 静态化页面拦截
 * 基于url生成生成静态页面到文件夹中，基于url生成文件路径
 * 请求时基于url转换为文件路径，寻找文件内容返回
 */
public class StaticInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    protected CmsProperties cmsProperties;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        //转换当前的请求路径为静态页面地址
        String requestUri = request.getRequestURI();
        if(StringUtils.isEmpty(requestUri)|| requestUri.equals("/")){
            requestUri = "/index";
        }
        String fileDirName = requestUri.substring(0, requestUri.indexOf("/"));
        String fileName = requestUri.substring(requestUri.indexOf("/")) + ".html";

        String filePath = cmsProperties.getSiteDir() + fileDirName + fileName;
        File file = new File(filePath);
        //如果未发现当前路由的静态页面，则请求动态页面，跳过。
        if (!file.exists()) {
            return true;
        }
        response.setContentType(MediaType.TEXT_HTML_VALUE);
        response.setContentLength((int) file.length());
        response.setCharacterEncoding("utf-8");
        try (PrintWriter printWriter = response.getWriter()) {
            FileReader fileReader = new FileReader(file);
            String result = fileReader.readString();
            printWriter.write(result);
            printWriter.flush();
        }
        return false;

    }

}
