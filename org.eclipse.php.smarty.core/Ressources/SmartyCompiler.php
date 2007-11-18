<?php
chdir(dirname(__FILE__));

require_once 'Smarty-2.6.18/libs/Smarty.class.php';
require_once 'Smarty-2.6.18/libs/Smarty_Compiler.class.php';

$template = file_get_contents($_SERVER['argv'][1]);
$smarty_compiler = new Smarty_Compiler();

/**
 * Add here your specific configuration to Smarty compiler
 * $smarty_compiler->left_delimiter = '{--';
 */
 
$smarty_compiler->_compile_file('foo', $template, $compiled);

exit(1);
?>