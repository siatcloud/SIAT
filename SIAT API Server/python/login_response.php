<?php
include_once 'C:/xampp/htdocs/siat/admin/HTML/model/model';
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$cctv_ip = "165.1125.15.454";
$username = "AnwarAbir";
$institute_name = "khu";

$model = new model();
$model->createCCTV($cctv_ip, $username, $institute_name);






