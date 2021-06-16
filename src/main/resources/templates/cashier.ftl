<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <head>
        <meta name="description" content="拿OFFER，支付中心">
        <meta name="copyright" content="YiKeZaiXian All Rights Reserved">
        <title>
            拿OFFER - 支付
        </title>
        <link rel="shortcut icon" href="https://cdn-r-test.oss-cn-hangzhou.aliyuncs.com/pro/image/PC/favicon.ico"
              type="image/x-icon">
        <link type="text/css" rel="stylesheet"
              href="https://cdn-r-test.oss-cn-hangzhou.aliyuncs.com/pro/css/PC/common.css">
        <link type="text/css" rel="stylesheet"
              href="https://cdn-r-test.oss-cn-hangzhou.aliyuncs.com/pro/css/PC/pay.css">
        <link rel="stylesheet" type="text/css"
              href="https://cdn-r-test.oss-cn-hangzhou.aliyuncs.com/pro/js/layer.js/skin/layer.css">
        <link rel="stylesheet" href="https://www.naoffer.com/s/layer.js/skin/layer.css" id="layui_layer_skinlayercss"
              style="">
        <script src="https://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>
    </head>

<body class="backImage" style="zoom: 1;">
<div class="topR1" id="topBar">
    <div class="wrapper">
        <a href="https://www.naoffer.com" class="topR_logo2"></a>
        <div class="headerName">收银台</div>
        <div class="topR_login fr">
            <div class="per_box">
                <a href="https://www.naoffer.com/uCenter" class="person_center">
                    <img class="u_username_avatar"
                         src="https://cdn-r-test.oss-cn-hangzhou.aliyuncs.com/pro/image/userImage/a7.jpg?t=0.5364809365446681">
                    <script type="text/javascript">
                        $(function () {
                                $(".u_username_avatar").attr("src", "https://cdn-r-test.oss-cn-hangzhou.aliyuncs.com/pro/image/userImage/a7.jpg?t=" + Math.random());
                            }
                        );
                    </script>
                    <span>二大爷</span>
                </a>
                <!-- 鼠标放在用户名上才出现 -->
                <ul class="top-nav-dropdown" style="display:none">
                    <li class="common_underline">
                        <a href="https://www.naoffer.com/uCenter">
                            <i class="zg-icon zg-icon-dd-home"></i>我的主页
                        </a>
                    </li>
                    <li class="common_no_underline">
                        <a href="/u/logout">
                            <i class="zg-icon zg-icon-dd-logout"></i>退出
                        </a>
                    </li>
                </ul>
            </div>

        </div>


    </div>
</div>

<div class="wrap">
    <div class="content">
        <div class="myorder_box">
            <div class="myorder">我的订单</div>
        </div>
        <div class="course_project_box">
            <div class="course_project">
						    <span class="project_text">订单名称：${order.title!""}
						    </span>
            </div>
            <div class="money_box">
						    <span class="money">应付金额:						
						    </span>
                <span class="money_1">￥${order.amount}
						    </span>
            </div>
        </div>

        <div class="myorder_box">
            <div class="myorder">支付方式</div>
            <p class="cut-off_rule"></p>
        </div>
        <div class="way_payment_box">
            <#--<div>
                <select class="form-control" id="couponId" style="    margin-bottom: 50px;">
                    <#if (couponList?size > 0 ) >
                        <#list couponList as coupon>
                            <option value="${coupon.couponNum}">${ coupon.getName()}</option>
                        </#list>
                    </#if>
                </select>
            </div>-->
            <form>
                <div class="z_box">
                    <input type="radio" name="payway" value="zhifubao" channel="1" method="1" id="zfb" checked="">
                    <label for="zfb">
                        <div class="image_box">
                            <i class="zhifubao_image"></i>
                        </div>
                    </label>
                </div>

                <div class="z_box">
                    <input type="radio" name="payway" value="weixin" channel="2" method="1" id="weixin">
                    <label for="weixin">
                        <div class="image_box">
                            <i class="weixin_image"></i>
                        </div>
                    </label>
                </div>
            </form>
            <div class="money_box2">
            </div>
            <a class="go_payment">
                <span class="payment_text">去付款</span>
            </a>
            <input type="hidden" id="amount" value="${order.amount?c}" name="amount">
            <input type="hidden" id="orderId" value="${order.id?c}" name="orderId">
            <input type="hidden" id="userId" value="${order.userId?c}" name="userId">
        </div>
    </div>
</div>

<div id="footer" style="background-color:white">
    <div class="footerdiv">
        <p>客服电子邮箱：service@rdaxue.com &nbsp;&nbsp; 苏ICP备15035201号</p>
        <div class="copyright">
            <p>
                Powered by <a href="https://www.naoffer.com" target="_blank">南京易课信息技术有限公司</a> © 2014-2016
            </p>
        </div>
    </div>
</div>


<script>
    $(function () {
        $(".go_payment").click(function () {

            var a = $("input[name='payway']:checked");
            var channel = a.attr("channel");
            var method = a.attr("method");

            $.ajax({
                type: "post",
                url: "/pay/unipay/ajax/toPay",
                data: {
                    "userId": $("#userId").val(),
                    "orderId": $("#orderId").val(),
                    "payMethod": method,
                    "payChannel": channel,
                    "payPlatform": 1000,
                    "amount": $("#amount").val(),
                    "couponNum": $("#couponId").val()
                },

                async: false, //.post是异步的，导致同步的全局赋值失败
                success: function (data) {
                    console.log(data);
                    if (data.success) {
                        window.location.href = data.data;
                    } else {
                        alert(data.msg);
                    }
                }
            });
        });
    })
</script>
<script type="text/javascript" src="https://www.naoffer.com/s/layer.js/layer.js"></script>
</body>
</html>


