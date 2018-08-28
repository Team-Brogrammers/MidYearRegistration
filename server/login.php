<?php
	$username = "rhea";
	$password = "brograms";
	$database = "mid_year_registration";
	$link = mysqli_connect("127.0.0.1", $username, $password, $database);
	$output=array();

	if($_SERVER["REQUEST_METHOD"] == "POST") {
      		// username and password sent from android form

  		$myemail = mysqli_real_escape_string($db,$_POST['user_email']);
      		$mypassword = mysqli_real_escape_string($db,$_POST['password']);

      		$sql = "SELECT * FROM user WHERE user_email = '$myemail' and user = '$user_pass'";
      		$result = mysqli_query($db,$sql);
      		$row = mysqli_fetch_array($result,MYSQLI_ASSOC);
      		$active = $row['active'];

      		$count = mysqli_num_rows($result);

      		// If result matched $myusername and $mypassword, table row must be 1 row

      		if($count == 1) {
			//TODO: login has been successful, should we return some kind of auth token?
			$output = True;

      		}else {
         		$error = "Your Login Name or Password is invalid";
      		}
   	}

	echo json_encode($output, $error);
?>

