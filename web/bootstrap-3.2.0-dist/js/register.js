$(document).ready(function() {
    var ok1=false;
    var ok2=false;
    var ok3=false;
    var ok4=false;
    var ok5 =false;
    $('input[name="username"]').focus(function(){
        $("#u").html("<span class='glyphicon glyphicon-remove'></span>")
    }).blur(function(){
        if($(this).val().length >= 3 && $(this).val().length <=12 && $(this).val()!=''){
            //check if username is in the database
            $.getJSON("checkRegister",{req:"checkname", username:$(this).val()},function(data){
                if(data.check == true)
                {
                    $("#u").html("<span class='glyphicon glyphicon-remove'></span>")
                    alert("username exists!");
                }
                else
                {
                    $("#u").html("<span class='glyphicon glyphicon-ok'></span>")
                    ok1=true;
                }
            })


        }else{
            $("#u").html("<span class='glyphicon glyphicon-remove'></span>")
            alert("the length of username should more than 2 less than 13");
        }

    });

    //验证密码
    $('input[name="password"]').focus(function(){
        $("#p").html("<span class='glyphicon glyphicon-remove'></span>")
    }).blur(function(){
        if($(this).val().length >= 6 && $(this).val().length <=20 && $(this).val()!=''){
            $("#p").html("<span class='glyphicon glyphicon-ok'></span>")
            ok2=true;
        }else{
            $("#p").html("<span class='glyphicon glyphicon-remove'></span>");
            alert("the length of password should more than 5 and less than 21");
        }

    });

    //验证确认密码
    $('input[name="confirmpassword"]').focus(function(){
        $("#cp").html("<span class='glyphicon glyphicon-remove'></span>")
    }).blur(function(){
        if($(this).val().length >= 6 && $(this).val().length <=20 && $(this).val()!='' && $(this).val() == $('input[name="password"]').val()){
            $("#cp").html("<span class='glyphicon glyphicon-ok'></span>");
            ok3=true;
        }else{
            $("#cp").html("<span class='glyphicon glyphicon-remove'></span>")
            alert("the password does not match!");
        }

    });

    //验证邮箱
    $('input[name="emailaddress"]').focus(function(){
        $("#e").html("<span class='glyphicon glyphicon-remove'></span>")
    }).blur(function(){
        if($(this).val().search(/\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*/)==-1){
            $("#e").html("<span class='glyphicon glyphicon-remove'></span>");
            alert("please enter a valid email address!");
        }else{
            $("#e").html("<span class='glyphicon glyphicon-ok'></span>");
            ok4=true;
        }
    });

    $('input[name="emailpassword"]').focus(function(){
        $("#ep").html("<span class='glyphicon glyphicon-remove'></span>")
    }).blur(function(){
        if($(this).val().length >= 6 && $(this).val().length <=20 && $(this).val()!=''){

            $.getJSON("checkRegister",{req:"checkmail", email: $('input[name="emailaddress"]').val(),password:$('input[name="emailpassword"]').val()},function(data){

                if(data.result == true)
                {
                    $("#ep").html("<span class='glyphicon glyphicon-ok'></span>")
                    ok5=true;
                }
                else
                {
                    $("#ep").html("<span class='glyphicon glyphicon-remove'></span>");
                    alert("this software only support gmail now or you enter email address or password incorrect");
                }

            })


        }else{
            $("#ep").html("<span class='glyphicon glyphicon-remove'></span>");
        }

    });




$('#register').click(function() {
    if(ok1 && ok2 && ok3 && ok4 && ok5)
    {
        $(".login-form").submit();
    }
    else
    {
        window.location.reload();
    }
})


})
