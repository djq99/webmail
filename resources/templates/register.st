register() ::= <<


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>


    <!--META-->
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>Login Form</title>

    <!--STYLESHEETS-->
    <link href="bootstrap-3.2.0-dist/css/style.css" rel="stylesheet" type="text/css" />

    <!--SCRIPTS-->
    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.2.6/jquery.min.js"></script>
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
    <form name="register" class="login-form" action="register" method="post">

        <!--HEADER-->

        <div class="header">
            <!--TITLE--><h1>Register Dmail</h1><!--END TITLE-->
            <!--DESCRIPTION--><br><br><!--END DESCRIPTION-->
        </div>
        <!--END HEADER-->

        <!--CONTENT-->
        <div class="content">
            <!--USERNAME--><input name="username" type="text" class="input username" value="Username" onfocus="this.value=''" /><!--END USERNAME-->
            <!--PASSWORD--><input name="password" type="password" class="input password" value="Password" onfocus="this.value=''" /><!--END PASSWORD-->
            <!--PASSWORD--><input name="confirmPassword" type="password" class="input password" value="Confirm Password" onfocus="this.value=''" /><!--END PASSWORD-->
            <!--PASSWORD--><input name="emailAddress" type="email" class="input password"  value="Email Address" onfocus="this.value=''" /><!--END PASSWORD-->
            <!--PASSWORD--><input name="emailPassword" type="password" class="input password" value="Email Password" onfocus="this.value=''" /><!--END PASSWORD-->
        </div>
        <!--END CONTENT-->

        <!--FOOTER-->
        <div class="footer">
            <!--LOGIN BUTTON--><input type="submit" name="submit" value="submit" class="button" /><!--END LOGIN BUTTON-->
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