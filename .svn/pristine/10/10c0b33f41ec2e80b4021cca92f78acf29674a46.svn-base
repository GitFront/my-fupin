<?php  
  
$callback = $_GET["callback"];  
$a = array(  
    $date= strtotime($row['cas'])*1000;   //time in format 2013-03-21 16:23:11 
    $values=hexdec($row['data']);         // hex values to decimal
);  
$result = json_encode($a);  
echo "flightHandler($result)";  
exit;

?>