<?php
defined('BASEPATH') OR exit('No direct script access allowed');
require APPPATH . '/libraries/REST_Controller.php';

class Welcome extends REST_Controller {
	function __construct($config = 'rest') {
			parent::__construct($config);
	}
	function index_get(){
		$data = array("What are you doing here ?");
		$this->response($data,501);
	}
	function index_post(){
		$this->response(array("status"=>"ok"),201);
	}
}
