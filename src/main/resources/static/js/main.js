
    function passwordCheck() {
    const pwd = document.getElementById("pwd").value;
    const cpwd = document.getElementById("cpwd").value;

    if(cpwd && pwd !== cpwd){
    document.getElementById("passwordError").innerHTML="Password not match";
    }else{
     document.getElementById("passwordError").innerHTML="";
    }

    }