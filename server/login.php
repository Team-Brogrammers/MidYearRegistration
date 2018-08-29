<?php
   $con=mysqli_connect("127.0.0.1","s1153631","s1153631","d1153631");

   if (mysqli_connect_errno($con)) {
      echo "Failed to connect to MySQL: " . mysqli_connect_error();
   }

   $user_email = $_POST['user_email'];
   $user_pass = $_POST['user_pass'];
   $result = mysqli_query($con,"SELECT * FROM user where
   user_email='$user_email' and user_pass='$user_pass'");
   $row = mysqli_fetch_array($result);
   $data = $row[0];

   if($data){
      echo $data;
   }

   mysqli_close($con);
?>
