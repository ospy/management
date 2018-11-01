package com.manage.servlet.code;

import java.io.IOException;
import java.io.PrintWriter;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.manage.utils.LOG;
import com.manage.utils.RandomUtils;
import com.manage.utils.SecurityImageTool;


@WebServlet(name = "code.do", urlPatterns = { "/code.do" })
public class Code extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 /**
     * 不缓存
     */
    private static final String NO_CACHE = "No-cache";
    
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		getCode(name, request, response);
	}


	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		String captcha ="";
		if(name.equals("user_reg")){
			captcha = (String) request.getSession().getAttribute(name);
		}
		PrintWriter out = response.getWriter();
		out.print(captcha.toLowerCase());
	}

	
	public void getCode(String name, HttpServletRequest request,
            HttpServletResponse response) {

        if (null==name){
            return;
        }
            
        String code = RandomUtils.getDefaultString(4);

        LOG.info("产生一个验证码[" + code + "],保存于[" + name + "]");

        response.setHeader("Pragma",NO_CACHE);// 禁止缓存
        response.setHeader("Cache-Control",NO_CACHE);
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");// 指定生成的响应是图片

        request.getSession().setAttribute(name, code);
        try {
            ImageIO.write(SecurityImageTool.createImage(code), "JPEG",
                    response.getOutputStream());
        } catch (IOException e) {
            LOG.error("生成验证图片失败！", e);
        }
    }
}
