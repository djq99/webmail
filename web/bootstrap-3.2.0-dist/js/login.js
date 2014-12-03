$(document).ready(function() {
    var ok1=false;
    var ok2=false;
    $('input[name="username"]').focus(function(){
    }).blur(function(){
        if($(this).val().length >= 3 && $(this).val().length <=12 && $(this).val()!=''){
            //check if username is in the database
            $.getJSON("checkRegister",{req:"checkname", username:$(this).val()},function(data){
                if(data.check == false)
                {

                }
                else
                {
                    ok1=true;
                }
            })
        }else{
            alert("the length of username should more than 2 less than 13");
        }
    });
    $('input[name="password"]').focus(function(){
    }).blur(function(){
        if($(this).val().length >= 6 && $(this).val().length <=20 && $(this).val()!=''){
            ok2=true;
        }else{

        }

    });
    $('#loginMail').click(function() {
        if(ok1 && ok2 )
        {
            $.getJSON("loginCheck",{req:"login",username:$('input[name="username"]').val(), password:$('input[name="password"]').val()},function(data){
                if(data.result == true)
                {
                    $(".login-form").submit();
                }
                else
                {
                    alert("username or password incorrect");
                    window.location.reload();
                }
            })

        }
        else
        {
            alert("username or password incorrect");
            window.location.reload();
        }
    })

})