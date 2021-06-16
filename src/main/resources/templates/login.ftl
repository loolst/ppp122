<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>登录</title>
    <script src="https://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>
</head>
<style>
    .login-box {
        margin: 100px 152px;
    }

    .name-line {
        height: 50px;
        line-height: 50px;
    }

    input {
        margin-left: 10px;
    }

    .button {
        margin-top: 40px;
        background: orange;
        width: 96px;
        text-align: center;
        border-radius: 10px;
        height: 30px;
        line-height: 30px;
        color: white;
        margin-left: 108px;
    }
</style>
<body>
<div>
    <input type="hidden" id="callback" value="${callback}">
    <div class="login-box">
        <div class="name-line">
            <span>姓名</span> <input class="name" id="userName">
        </div>
        <div class="name-line">
            <span>密码</span> <input class="password" id="password">
        </div>

        <div class="button">登录</div>

    </div>

</div>

</body>
<script>
    $(function () {

        $(".button").click(function () {
            var name = $("#userName").val();
            var password = $("#password").val();

            $.ajax({
                type: "post",
                url: "/u/login/verify",
                data: {"userName": name, "password": password},
                async: false, //.post是异步的，导致同步的全局赋值失败
                success: function (data) {
                    console.log(data);
                    if (data.success) {
                        var call = $("#callback").val();
                        window.location.href = call;
                    } else {
                        alert(data.msg);
                    }
                }
            });
        });
    });
</script>
</html>