login() ::= <<

<!DOCTYPE html>
<html>
<head>
<title>login</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />



    <link href="bootstrap-3.2.0-dist/css/style.css" rel="stylesheet" type="text/css" />

     <script type='text/javascript' src="bootstrap-3.2.0-dist/js/jquery-1.4.4.min.js"></script>

    <script type="text/javascript">
        jQuery(document).ready(function() {
            jQuery(".username").focus(function() {
                jQuery(".user-icon").css("left","-48px");
            });
            jQuery(".username").blur(function() {
                jQuery(".user-icon").css("left","0px");
            });

            jQuery(".password").focus(function() {
                jQuery(".pass-icon").css("left","-48px");
            });
            jQuery(".password").blur(function() {
                jQuery(".pass-icon").css("left","0px");
            });
        });
    </script>

</head>
<body>

<!--WRAPPER-->
<div id="wrapper">

    <!--SLIDE-IN ICONS-->
    <div class="user-icon"></div>
    <div class="pass-icon"></div>
    <!--END SLIDE-IN ICONS-->

    <!--LOGIN FORM-->
    <form name="login-form" class="login-form" action="login" method="post">

        <!--HEADER-->

        <div class="header">
            <!--TITLE--><h1>Login Dmail</h1><!--END TITLE-->
            <!--DESCRIPTION--><br><br><!--END DESCRIPTION-->
        </div>
        <!--END HEADER-->

        <!--CONTENT-->
        <div class="content">
            <!--USERNAME--><input name="username" type="text" class="input username" value="Username" onfocus="this.value=''" /><!--END USERNAME-->
            <!--PASSWORD--><input name="password" type="password" class="input password" value="Password" onfocus="this.value=''" /><!--END PASSWORD-->
        </div>
        <!--END CONTENT-->

        <!--FOOTER-->
        <div class="footer">
            <!--LOGIN BUTTON--><input type="submit" name="submit" value="Login" class="button" /><!--END LOGIN BUTTON-->
            <!--REGISTER BUTTON--><input type="button" name="submit" value="Register" class="register" onclick="javascript:window.location.href='register'"/><!--END REGISTER BUTTON-->
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