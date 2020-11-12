function checkName(){
    //获取参数
    let userName = document.getElementById("userName").value;
    let password = document.getElementById("password").value;
    //创建XMLHttpRequest对象
    let xhr;
    if(window.ActiveXObject){
        //IE浏览器
        xhr = new ActiveXObject("Microsoft.XMLHTTP");
    }
    else {
        //非IE浏览器
        xhr = new XMLHttpRequest();
    }
    //发送请求
    xhr.open("GET","/springboot/login?userName="+userName+"&password="+password);
    //用于发送POST请求
    xhr.send(null);
    //监听响应
    xhr.onreadystatechange = function login(){
        if(xhr.readyState==4&&xhr.status==200){
            let checkMessage = document.getElementById("checkMessage");
            checkMessage.innerHTML = xhr.responseText;
        }
    };
}