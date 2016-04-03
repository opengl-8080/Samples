package sample.kotlin.struts

import org.apache.struts.action.ActionForm

class HelloForm: ActionForm() {
    private var message : String? = null
    
    fun getMessage() : String? {
        return this.message
    }
    
    fun setMessage(message : String?) {
        this.message = message
    }
}