<?php
require_once 'Ressources/Smarty-2.6.18/libs/Smarty_Compiler.class.php';

class SmartyCompiler{

	private $out;
	private $ip = '172.0.0.1';
	private $port = 6881;
	private $status = 0;
	
	private $smarty
	
	const DISCONNECTED = 0;
	const CONNECTED = 1;
	
	public function __construct(){
		$this->smarty = new Smarty_Compiler();
	}
	
	public static function run(){
		$s = new SmartyCompiler();
		$s->connect();
		$s->read();
		$s->disconnect();
	}
	
	public function connect(){
		$this->out = fsockopen($this->ip, $this->port, $errno, $errstr, 15);
		if(!$this->out){
			return false;
		}
		$this->status = CONNECTED;
	}
	
	public function disconnect(){
		fclose($this->out);
		$this->status = DISCONNECTED;
	}
	
		while (!feof($this->fp) and $data = fgets($this->fp, 512)) {
			array_push($this->rbuff, $data) ;
			$this->_historize('< '.$data) ;
		}
		
	public function read(){
		while(!feof($this->fp) && $data = fgets($this->fp)){
			if(file_exists($file = trim($data))){
				$this->smarty->_compile_file($resource_name, $source_content, &$compiled_content)
			}
		}
	}
	
	public function send(){
		
	}
}

SmartyCompiler::run();
?>