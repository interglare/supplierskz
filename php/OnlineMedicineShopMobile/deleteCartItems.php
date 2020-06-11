<?php

	require "init.php";
	$userId=$_GET["userId"];

	$sql="delete from cart where userId='$userId'";
	if(mysqli_query($con, $sql)){
		$status="delete";
	}
	else{
		$status="not delete";
	}
	echo json_encode(array("response"=>$status));
	mysqli_close($con);

?>