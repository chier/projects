  function hello(){
     
     var user = $('user').value;
     alert(user);
     Hello.sayHello(user,callback);
  }
  function callback(msg){
    DWRUtil.setValue('result',msg);
  }