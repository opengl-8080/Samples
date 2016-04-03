package sample.kotlin.struts

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

import org.apache.struts.action.Action
import org.apache.struts.action.ActionForm
import org.apache.struts.action.ActionForward
import org.apache.struts.action.ActionMapping

class HelloAction: Action() {
    
    override fun execute(
            mapping: ActionMapping,
            form: ActionForm,
            request: HttpServletRequest,
            response: HttpServletResponse) : ActionForward {
        val helloForm = form as HelloForm;
        
        println("kotlin message = ${helloForm.getMessage()}");
        
        return mapping.findForward("success");
    }
}