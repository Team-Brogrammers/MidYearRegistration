<?php
$username = "s1153631";
$password = "s1153631";
$database = "d1153631";

     $link = mysqli_connect("127.0.0.1", $username, $password ,$database);
     if($_SERVER['REQUEST_METHOD'] == 'POST'){
        $user_email =$_REQUEST[ "user_email"];
        $user_pass =$_REQUEST["user_pass"];
        $user_type =$_REQUEST["user_type"];
     }

    //if($user_email && $user_pass){
         $statement = mysqli_prepare($link, "INSERT INTO user(user_email, user_pass, user_type) VALUES('$user_email','$$         mysqli_stmt_bind_param($statement, "sss", $user_email, $user_pass, $user_type);
         $result = mysqli_stmt_execute($statement);

         if ($result){
             echo "Successfully inserted";
         }else{
             echo "Insert failed";
         }

         mysqli_stmt_close($statement);
         mysqli_close($link);
        //}

?>

