package pers.sdulxt.inspect.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import pers.sdulxt.inspect.model.Constant;
import pers.sdulxt.inspect.model.Response;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

    private Log log;

    private final ErrorAttributes errorAttributes;

    private static final String ERROR_PATH = "/error";

    @Autowired
    public ErrorController(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;

        if(Constant.DEBUG){
            log = LogFactory.getLog(getClass().getName());
        }
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }

    @RequestMapping(ERROR_PATH)
    public Response<Map<String, Object>> error(HttpServletRequest request){
        if(log != null){
            Map<String,Object> errorAttributes = getErrorAttributes(request);
            log.error(errorAttributes);
        }

        return new Response<>(Response.Code.UNKNOWN_ERROR);
    }

    private Map<String, Object> getErrorAttributes(HttpServletRequest request) {
        WebRequest requestAttributes = new ServletWebRequest(request);
        return errorAttributes.getErrorAttributes(requestAttributes, true);
    }
}
