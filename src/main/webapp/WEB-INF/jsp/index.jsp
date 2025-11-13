<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Student Course Management System</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
            padding: 20px;
        }
        
        .container {
            background: white;
            border-radius: 20px;
            box-shadow: 0 20px 60px rgba(0,0,0,0.3);
            padding: 50px;
            max-width: 800px;
            width: 100%;
        }
        
        h1 {
            color: #667eea;
            text-align: center;
            margin-bottom: 15px;
            font-size: 2.5em;
        }
        
        .subtitle {
            text-align: center;
            color: #666;
            margin-bottom: 40px;
            font-size: 1.1em;
        }
        
        .menu {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
            gap: 20px;
            margin-top: 30px;
        }
        
        .menu-item {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 30px;
            border-radius: 15px;
            text-decoration: none;
            text-align: center;
            transition: transform 0.3s, box-shadow 0.3s;
            box-shadow: 0 5px 15px rgba(0,0,0,0.2);
        }
        
        .menu-item:hover {
            transform: translateY(-5px);
            box-shadow: 0 10px 25px rgba(0,0,0,0.3);
        }
        
        .menu-item h2 {
            font-size: 1.5em;
            margin-bottom: 10px;
        }
        
        .menu-item p {
            font-size: 0.9em;
            opacity: 0.9;
        }
        
        .footer {
            text-align: center;
            margin-top: 40px;
            color: #666;
            font-size: 0.9em;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Student Course Management</h1>
        <p class="subtitle">Manage Students and Courses Efficiently</p>
        
        <div class="menu">
            <a href="${pageContext.request.contextPath}/students" class="menu-item">
                <h2>📚 Students</h2>
                <p>View, Add, and Manage Students</p>
            </a>
            
            <a href="${pageContext.request.contextPath}/courses" class="menu-item">
                <h2>📖 Courses</h2>
                <p>View, Add, and Manage Courses</p>
            </a>
            
            <a href="${pageContext.request.contextPath}/students/with-courses" class="menu-item">
                <h2>🔗 Student-Course</h2>
                <p>View Students with Enrolled Courses</p>
            </a>
        </div>
        
        <div class="footer">
            <p>&copy; 2024 Student Course Management System | BITS Pilani</p>
        </div>
    </div>
</body>
</html>
