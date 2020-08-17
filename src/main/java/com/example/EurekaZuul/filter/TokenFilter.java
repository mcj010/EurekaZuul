package com.example.EurekaZuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class TokenFilter extends ZuulFilter {
    @Override
    public String filterType() {
        //pre路由前，routing路由时，post路由后，error发生错误时
        return "pre";
    }
    @Override
    public int filterOrder() {
        //过滤的顺序，数字越大，优先级越低
        return 0;
    }
    @Override
    public boolean shouldFilter() {
        //是否执行当前过滤器，true是需要执行
        return true;
    }
    @Override
    public Object run() {
        RequestContext rc = RequestContext.getCurrentContext();
        HttpServletRequest request = rc.getRequest();
        //获取token参数
        String token = request.getParameter("token");
        if (StringUtils.isNotBlank(token)) {
            //对请求进行路由
            rc.setSendZuulResponse(true);
            rc.setResponseStatusCode(200);
            rc.set("isSuccess", true);
            return null;
        } else {
            //不进行路由
            rc.setSendZuulResponse(false);
            rc.setResponseStatusCode(400);
            rc.setResponseBody("token is empty");
            rc.set("isSuccess", false);
            return null;
        }
    }
}
