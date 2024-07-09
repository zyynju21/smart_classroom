package com.yiyue.common.aspact;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yiyue.common.enums.LogTypeEnum;
import com.yiyue.common.result.Result;
import com.yiyue.common.util.JsonTools;
import com.yiyue.interceptor.SystemUserContextHolder;
import com.yiyue.model.bean.system.SystemLog;
import com.yiyue.model.vo.system.LoginUser;
import com.yiyue.service.system.SystemLogService;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;

@Slf4j
//@Aspect
@Component
public class ControllerAspact {

    private SystemLog systemLog;
    private long callStartTime;

    @Autowired
    private SystemLogService systemLogService;

    @Before(value="@annotation(com.yiyue.common.aspact.SystemPlatLog)")
    public void doBefore(JoinPoint joinPoint) {
        MethodSignature ms = (MethodSignature) joinPoint.getSignature();
        Method method = ms.getMethod();
        String value = method.getAnnotation(SystemPlatLog.class).value();
        String content = method.getAnnotation(SystemPlatLog.class).content();
        log.debug("content:{}",content);
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        systemLog=new SystemLog();
        systemLog.setServerIp(request.getRequestURL().toString());
        systemLog.setControllerName(joinPoint.getSignature().getDeclaringTypeName());
        systemLog.setMethodName(joinPoint.getSignature().getName());
        log.debug("request paras:{}",Arrays.toString(joinPoint.getArgs()));
        systemLog.setParas(Arrays.toString(joinPoint.getArgs()).getBytes());
        systemLog.setLogType(LogTypeEnum.SYSTEM.getLogTypeId());
        systemLog.setClientIp(getIpAddress(request));
        systemLog.setClientBrowser(getOBrowser(request));
        systemLog.setClientSystem(getOperSystem(request));
        callStartTime=System.currentTimeMillis();
        LoginUser loginUser=SystemUserContextHolder.getLoginUser();
        systemLog.setCreatedBy(loginUser.getUserId());
    }

    @AfterReturning(value="@annotation(com.yiyue.common.aspact.SystemPlatLog)",returning = "object" )
    public void doAfterReturning(Object object) {
        if(object instanceof Result){
            Result result=(Result) object;
            log.debug("result:{}",JsonTools.objectToJson(result));
            systemLog.setResult(JsonTools.objectToJson(result).getBytes());
            long callEndTime=System.currentTimeMillis();
            systemLog.setResponseTime(callEndTime-callStartTime);
            systemLog.setCreatedGmt(new Date());
            systemLog.setStatus(Integer.parseInt(result.getCode()));
            log.debug("systemLog:{}",systemLog);
            systemLogService.insertSystemLog(systemLog);
        }
    }
    @AfterThrowing(value="@annotation(com.yiyue.common.aspact.SystemPlatLog)", throwing = "ex")
    public void aspectAfterThrowing(JoinPoint jp, Exception ex) {
        String methodName = jp.getSignature().getName();
        log.error("【异常通知】" + methodName + "方法异常：" + ex.getMessage());
        systemLog.setResult(("【异常通知】 "+ methodName + "方法异常：" + ex.getMessage()).getBytes());
        long callEndTime=System.currentTimeMillis();
        systemLog.setResponseTime(callEndTime-callStartTime);
        systemLog.setCreatedGmt(new Date());
        systemLog.setStatus(500);
        log.debug("systemLog:{}",systemLog);
        systemLogService.insertSystemLog(systemLog);
    }

    private String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    private String getOperSystem(HttpServletRequest request){
        String os = "";
        String userAgent = request.getHeader("User-Agent");
        if (userAgent.toLowerCase().indexOf("windows") >= 0) {
            int start=userAgent.toLowerCase().indexOf("(")+1;
            int end= userAgent.indexOf(")");
            os = userAgent.substring(start,end);
        }else {
            os = "UnKnown, More-Info: " + userAgent;
        }
        /*if (userAgent.toLowerCase().indexOf("windows") >= 0) {
            os = "Windows";
        } else if (userAgent.toLowerCase().indexOf("mac") >= 0) {
            os = "Mac";
        } else if (userAgent.toLowerCase().indexOf("x11") >= 0) {
            os = "Unix";
        } else if (userAgent.toLowerCase().indexOf("android") >= 0) {
            os = "Android";
        } else if (userAgent.toLowerCase().indexOf("iphone") >= 0) {
            os = "IPhone";
        } else {
            os = "UnKnown, More-Info: " + userAgent;
        }*/
        return os;
    }

    private String getOBrowser(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        String user = userAgent.toLowerCase();

        String browser = "";
        //===============Browser===========================
        if (user.contains("edge")) {
            browser = (userAgent.substring(userAgent.indexOf("Edge")).split(" ")[0]).replace("/", "-");
        } else if (user.contains("msie")) {
            String substring = userAgent.substring(userAgent.indexOf("MSIE")).split(";")[0];
            browser = substring.split(" ")[0].replace("MSIE", "IE") + "-" + substring.split(" ")[1];
        } else if (user.contains("safari") && user.contains("version")) {
            browser = (userAgent.substring(userAgent.indexOf("Safari")).split(" ")[0]).split("/")[0]
                    + "-" + (userAgent.substring(userAgent.indexOf("Version")).split(" ")[0]).split("/")[1];
        } else if (user.contains("opr") || user.contains("opera")) {
            if (user.contains("opera")) {
                browser = (userAgent.substring(userAgent.indexOf("Opera")).split(" ")[0]).split("/")[0]
                        + "-" + (userAgent.substring(userAgent.indexOf("Version")).split(" ")[0]).split("/")[1];
            } else if (user.contains("opr")) {
                browser = ((userAgent.substring(userAgent.indexOf("OPR")).split(" ")[0]).replace("/", "-"))
                        .replace("OPR", "Opera");
            }

        } else if (user.contains("chrome")) {
            browser = (userAgent.substring(userAgent.indexOf("Chrome")).split(" ")[0]).replace("/", "-");
        } else if ((user.indexOf("mozilla/7.0") > -1) || (user.indexOf("netscape6") != -1) ||
                (user.indexOf("mozilla/4.7") != -1) || (user.indexOf("mozilla/4.78") != -1) ||
                (user.indexOf("mozilla/4.08") != -1) || (user.indexOf("mozilla/3") != -1)) {
            browser = "Netscape-?";

        } else if (user.contains("firefox")) {
            browser = (userAgent.substring(userAgent.indexOf("Firefox")).split(" ")[0]).replace("/", "-");
        } else if (user.contains("rv")) {
            String IEVersion = (userAgent.substring(userAgent.indexOf("rv")).split(" ")[0]).replace("rv:", "-");
            browser = "IE" + IEVersion.substring(0, IEVersion.length() - 1);
        } else {
            browser = "UnKnown, More-Info: " + userAgent;
        }

        return browser;
    }

}
