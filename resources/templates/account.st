account() ::= <<


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>

<title>change password</title>
    <!--META-->
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>Login Form</title>

    <!--STYLESHEETS-->
    <link href="bootstrap-3.2.0-dist/css/style.css" rel="stylesheet" type="text/css" />

    <!--SCRIPTS-->
     <script type='text/javascript' src="bootstrap-3.2.0-dist/js/jquery-1.4.4.min.js"></script>
    <!--Slider-in icons-->
</head>
<body>

<!--WRAPPER-->
<div id="wrapper">

    <!--SLIDE-IN ICONS-->
    <div class="user-icon"></div>
    <div class="pass-icon"></div>
    <!--END SLIDE-IN ICONS-->

    <!--LOGIN FORM-->
    <form name="register" class="login-form" action="account" method="post">

        <!--HEADER-->

        <div class="header">
            <!--TITLE--><h1>Change Password</h1><!--END TITLE-->
            <!--DESCRIPTION--><br><br><!--END DESCRIPTION-->
        </div>
        <!--END HEADER-->

        <!--CONTENT-->
        <div class="content">
            <!--PASSWORD-->old password <input name="oldpassword" type="password" class="input password" value="" onfocus="this.value=''" /><!--END PASSWORD-->
            <!--PASSWORD-->new password <input name="newPassword" type="password" class="input password" value="" onfocus="this.value=''" /><!--END PASSWORD-->
            <!--PASSWORD--> confirm <input name="confirm" type="password" class="input password" value="" onfocus="this.value=''" /><!--END PASSWORD-->
        </div>
        <!--END CONTENT-->

        <!--FOOTER-->
        <div class="footer">
            <!--LOGIN BUTTON--><input type="submit" name="submit" value="submit" class="button" /><!--END LOGIN BUTTON-->
             <!--LOGIN BUTTON--><input type="button" name="login" value="back" class="button" onclick="javascript:window.location.href='mail'"/><!--END LOGIN BUTTON-->
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