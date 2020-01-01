<!DOCTYPE html>
<!-- saved from url=(0014)about:internet -->
<html lang="en">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">
  <title>Login</title>
  <link rel="stylesheet" href="../login.css">
</head>
<body>
<div class="container">
  <form name = "user" class="form-signin" method="post" action="/custom/login">
    <img src="../logo.png" >
    <input type="text" id="email" name="email"  placeholder="Username" required="" autofocus="">
    <input type="password" id="password" name="password"  placeholder="Password" required="">
    <input type="hidden" id="challenge" name="challenge"  placeholder="Password" required="" value="${challenge}">
    <button class="submit" type="submit">Sign In</button>
    <hr class="underline">
    <a class="azure" href="${hydraUrl}">Login in with Castlery</a>
  </form>
</div>
</body>
</html>