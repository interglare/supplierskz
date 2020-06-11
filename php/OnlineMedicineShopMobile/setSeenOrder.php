<?php

	require "init.php";
	
	$userId=$_GET["userId"];

	$sql="update orders set seen=1 where userId='$userId'"; 
	if(mysqli_query($con, $sql)){
		$status="update";
	}
	else{
		$status="not update";
	}

	echo json_encode(array("response"=>$status));
	mysqli_close($con);

?>