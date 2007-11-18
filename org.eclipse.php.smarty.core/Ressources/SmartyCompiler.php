<?php
chdir(dirname(__FILE__));

require_once 'Smarty-2.6.18/libs/Smarty.class.php';
require_once 'Smarty-2.6.18/libs/Smarty_Compiler.class.php';

// constructs compiler
require_once 'JsonedSmartyCompiler.php';
$template = file_get_contents($_SERVER['argv'][1]);
$smarty_compiler = new JsonedSmartyCompiler();

//  TODO: include configuration file here, or set properties $smarty_compiler->left_delimiter = '{--'
$smarty_compiler->_compile_file('foo', $template, $compiled);

exit(1);
?>