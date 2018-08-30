<?php
         include 'config.php';

         // Check whether username or password is set from android
     if(isset($_POST['username']) && isset($_POST['password']))
     {
          // Innitialize Variable
          $result='';
          $username = $_POST['username'];
          $password = $_POST['password'];

          // Query database for row exist or not
          $sql = 'SELECT *  FROM user WHERE user_email = :username AND user_pass = :password';
          $stmt = $conn->prepare($sql);
          $stmt->bindParam(':username', $username, PDO::PARAM_STR);
          $stmt->bindParam(':password', $password, PDO::PARAM_STR);
          $stmt->execute();
          if($stmt->rowCount())
          {
                $row = $stmt->fetch(PDO::FETCH_ASSOC);
                $result=$row['user_type'];
          }
          elseif(!$stmt->rowCount())
          {
                $result="false";
          }

                  // send result back to android
                  echo $result;
        }

?>

