register() ::= <<


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>

<title>register</title>
    <!--META-->
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>Login Form</title>

    <!--STYLESHEETS-->
    <link href="bootstrap-3.2.0-dist/css/style.css" rel="stylesheet" type="text/css" />
     <link href="bootstrap-3.2.0-dist/css/bootstrap.css" rel="stylesheet" type="text/css" />
     <link href="bootstrap-3.2.0-dist/css/bootstrap-theme.css" rel="stylesheet" type="text/css" />

    <!--SCRIPTS-->
     <script type='text/javascript' src="bootstrap-3.2.0-dist/js/jquery-1.4.4.min.js"></script>
     <script type='text/javascript' src="bootstrap-3.2.0-dist/js/bootstrap.js"></script>
    <!--Slider-in icons-->
</head>
<body>
     <script type='text/javascript' src="bootstrap-3.2.0-dist/js/jquery-1.4.4.min.js"></script>
 <script type='text/javascript' src="bootstrap-3.2.0-dist/js/register.js"></script>
<!--WRAPPER-->
<div id="wrapper">

    <!--SLIDE-IN ICONS-->
    <div class="user-icon"></div>
    <div class="pass-icon"></div>
    <!--END SLIDE-IN ICONS-->

    <!--LOGIN FORM-->
    <form name="register" id="form" class="login-form" action="register" method="post">

        <!--HEADER-->

        <div class="header">
            <!--TITLE--><h1>Register Dmail</h1><!--END TITLE-->
            <!--DESCRIPTION--><!--END DESCRIPTION-->
        </div>
        <!--END HEADER-->

        <!--CONTENT-->
        <div class="content">
            <div class="input-group">
              <span class="input-group-addon">username</span>
              <input type="text" name = "username" id="username" class="form-control" placeholder="username">
              <span class="input-group-addon" id ="u"></span>
            </div>
            </div>
            <div class="content">
             <div class="input-group">
              <span class="input-group-addon">password</span>
               <input type="password" name = "password" id="password" class="form-control" placeholder="password">
               <span class="input-group-addon" id ="p" ></span>
               </div>
             </div>
             <div class="content">
             <div class="input-group">
              <span class="input-group-addon">confirm password</span>
               <input type="password" name = "confirmpassword" id="confirmpassword" class="form-control" placeholder="password">
               <span class="input-group-addon" id ="cp"></span>
                </div>
                 </div>
             <div class="content">
             <div class="input-group">
             <span class="input-group-addon">email address</span>
              <input type="email" id="emailaddress" name ="emailaddress" class="form-control" placeholder="email address">
              <span class="input-group-addon" id ="e"></span>
               </div>
                </div>
           <div class="content">
            <div class="input-group">
             <span class="input-group-addon">email password</span>
              <input type="password" id="emailpassword" name ="emailpassword" class="form-control" placeholder="password">
              <span class="input-group-addon" id ="ep"></span>
                </div>
                 </div>

        <!--END CONTENT-->

        <!--FOOTER-->
        <div class="footer">
            <!--LOGIN BUTTON--><input type="button" id = "register" name="register" value="submit" class="button" /><!--END LOGIN BUTTON-->
             <!--LOGIN BUTTON--><input type="button" name="login" value="login" class="button" onclick="javascript:window.location.href='login'"/><!--END LOGIN BUTTON-->
        </div>
        <!--END FOOTER-->

    </form>
    <!--END LOGIN FORM-->

</div>
<!--END WRAPPER-->

<!--GRADIENT--><div class="gradient"></div><!--END GRADIENT-->

</body>
</html>
  >>