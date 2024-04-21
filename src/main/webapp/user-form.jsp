<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>

<head>
    <title>User Management Application</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>

<body>

<header>
    <nav class="navbar navbar-expand-md navbar-dark" style="background-color: tomato">
        <div>
            <a href="#" class="navbar-brand"> User Management App </a>
        </div>

        <ul class="navbar-nav">
            <li><a href="<%=request.getContextPath()%>/list" class="nav-link">Users</a></li>
        </ul>
    </nav>
</header>
<br>
<div class="container col-md-5">
    <div class="card">
        <div class="card-body">
           	<form action="${user!=null ? 'update':'insert' }" method="post" >

                     <h2>${user !=null ? "Edit User":"Add New User"}</h2>

                    <c:if test="${user != null}">
                        <input type="hidden" name="id" value="${user.id}" />
                    </c:if>

                    <fieldset class="form-group">
                        <label>User Name</label> <input type="text" value="${user.name}" class="form-control" name="name" required="required">
                    </fieldset>

                    <fieldset class="form-group">
                        <label>User Email</label> <input type="text" value="${user.email}" class="form-control" name="email">
                    </fieldset>

                    <fieldset class="form-group">
                        <label>User Country</label> <input type="text" value="${user.country}" class="form-control" name="country">
                    </fieldset>

                    <button type="submit" class="btn btn-success">Save</button>
                </form>
                
        </div>
    </div>
</div>
</body>

</html>
