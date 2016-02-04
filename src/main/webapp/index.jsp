<%@ page contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>cache example index</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

  </head>
  
  <body>
    userName:${user.name}
    <br/>
    userName2:${user2.name}
    <br/>
    <a href="updateuser.html">删除缓存</a>
    <br/>
    <a href="cacheadmin" target="_blank">缓存管理后台</a>
  </body>
</html>
