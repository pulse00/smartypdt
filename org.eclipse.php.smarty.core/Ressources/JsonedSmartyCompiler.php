<?

/**
 * This SmartyCompiler outputs the errors as jsons
 */
class JsonedSmartyCompiler extends Smarty_Compiler {

    /**
     * JSON error to output stream
     *
     * @param string $error_msg
     * @param integer $error_type
     * @param string $file
     * @param integer $line
     */
    function _syntax_error($error_msg, $error_type = E_USER_ERROR, $file=null, $line=null)
    {
    	echo json_encode(array("message"=>$error_msg, "file"=>$this->_current_file, "line"=>$this->_current_line_no)) . "\n";
    }
}
