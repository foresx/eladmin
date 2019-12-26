<!DOCTYPE html>
<!-- saved from url=(0014)about:internet -->
<html lang="en">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">
  <title>Please sign in</title>
  <!--<link href="./sign_files/bootstrap.min.css" rel="stylesheet" integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M" crossorigin="anonymous">-->
  <!--<link href="./sign_files/signin.css" rel="stylesheet" crossorigin="anonymous">-->
</head>
<body>
<div class="container">
  <form name = "user" class="form-signin" method="post" action="/custom/login">
    <h2 class="form-signin-heading">Please sign in</h2>
    <p>
      <label for="email" class="sr-only">Username</label>
      <input type="text" id="email" name="email" class="form-control" placeholder="Username" required="" autofocus="">
    </p>
    <p>
      <label for="password" class="sr-only">Password</label>
      <input type="password" id="password" name="password" class="form-control" placeholder="Password" required="">
    </p>
    <p>
      <label for="challenge" class="sr-only">Password</label>
      <input type="hidden" id="challenge" name="challenge" class="form-control" placeholder="Password" required="" value="${challenge}">
    </p>
    <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
  </form>
  <h2 class="form-signin-heading">Login with OAuth 2.0</h2>
  <table class="table table-striped">
    <tbody><tr><td><a href="${hydraUrl}">Azure</a></td></tr>
    </tbody>
    <!--<tbody>-->
    <!--<tr>-->
    <!--<button onclick="goto()">Click me</button>    </tr>-->
    <!--</tbody>-->
  </table>
</div>
</body>
</html>