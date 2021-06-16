<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>购买商品</title>
    <script src="https://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>
</head>
<style>
    .content img {
        width: 100px;
        float: left;
    }

    .btn {
        margin-top: 50px;
        background-color: orange;
        width: 100px;
        text-align: center;
        border-radius: 5px;
        height: 40px;
        line-height: 40px;
        color: white;
    }
</style>
<body>
<div>
    <#-- <#if isUserExist>true<#else>false</#if>-->
    <div class="content">
        <img src="/static/image/OIP.jpg">
        <div class="name">${goods}</div>
        <div class="amount" id="amount" v="${amount}">${amount}</div>
    </div>
    <div class="btn">支付</div>


</div>

</body>
<script>
    $(function () {
        $(".btn").click(function () {

            $.ajax({
                type: "post",
                url: "/vip/order/ajax/toPay",
                data: {"amount": $("#amount").attr("v")},
                async: false, //.post是异步的，导致同步的全局赋值失败
                success: function (data) {
                    console.log(data);
                    if (data.success) {
                        window.location.href = "/pay/unipay/" + data.data;
                    } else {
                        alert(data.msg);
                    }
                }
            });
        });
    })
</script>
</html>
