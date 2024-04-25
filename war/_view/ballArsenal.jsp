<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import= "edu.ycp.cs320.RevMetrix.model.Account" %>
<%@ page import= "edu.ycp.cs320.RevMetrix.model.Ball" %>
<%@ page import="edu.ycp.cs320.RevMetrix.model.BallArsenal" %>
<%@ page import = "java.io.*,java.util.*" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<%@ page import="com.google.gson.Gson" %>

<%
// Retrieve ArrayList from session attribute
	//HttpSession session = request.getSession();
	BallArsenal model = (BallArsenal) session.getAttribute("ballArsenalKey");
	ArrayList<Ball> balls = (model != null) ? model.getBalls() : null;
%>

<html lang="en">
	<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<title>Bowling Ball Arsenal</title>
		<style type="text/css">
				           
	        body{
				font-family: Arial, Helvetica, sans-serif;
	        	display:flex;
            	
			}
			#shaderCanvas {
	            border-radius:50%;
	        }
			header {
				text-align: center;
				position: absolute;
				top: 10px; /* Adjust the top position as needed */
				width: 100%; /* Ensure the header spans the full width */
			}
			input {
	            width: 70%;
	            padding: 8px;
	            margin-bottom: 16px;
	            box-sizing: border-box;
	            border: 1px solid #ccc;
	            border-radius: 4px;
	        }
			.color-picker{
			    height: 50px; 
				width: 70px;
				vertical-align: middle;
			}
			input[type="number"]{
				width: 180px;
			}
			
			h1{
				font-size: 50px;
				color: black;
				text-align: center;
			}

			#ball-list{
				margin-top: 20px;
			}

			#add-ball-form, #remove-ball-form{
				margin-bottom: 10px;
			}
			.error{
				color: red; 
	        	font-weight: bold;
			}

	.ball-box {
            width: 800px;
            align-items: center;
            text-align: center;
            position: absolute;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
            margin-bottom: 10px;
            background-color: white;
            box-shadow: 2px;
            /* Set fixed height for the container */
            height: 430px;
            /* Add scrollbar when content overflows */
            overflow: auto;            
        }
        .ball-box2 {
            width: 800px;
            align-items: center;
            text-align: center;
            position: absolute;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
            margin-bottom: 10px;
            background-color: white;
            box-shadow: 2px;
            /* Set fixed height for the container */
            height: 350px;
            /* Add scrollbar when content overflows */
            overflow: auto;
			background-color: green;
            
        }

			.bowling-ball-img{
				width: 150px;	/*Adjust the size of the image*/
				height: auto;
				margin-bottom: 10px;
			}

			/* Style for the black sidebar */
.sidebar {
  height: 100%;
  width: 250px;
  position: fixed;
  top: 0;
  left: 0;
 background: linear-gradient(to bottom, rgba( 243, 0, 178, 1 ), rgba( 28, 144, 243, 1 ) 95%, rgba( 255, 255, 0, 1 ));
  padding-top: 20px;
}

/* Links in the sidebar */
.sidebar a {
  padding: 10px 20px;
  text-decoration: none;
  color: white;
  display: block;
}

/* Change color of links on hover */
.sidebar a:hover {
  background-color: #333;
}

/* Style the main content */
.main-content {
  margin-left: 250px; /* Same width as the sidebar */
  padding: 20px;
}
 
   

/* Responsive layout - when the screen is less than 600px wide, make the sidebar and the main content stack on top of each other */
@media screen and (max-width: 600px) {
  .sidebar {
    width: 100%;
    height: auto;
    position: relative;
  }
  .sidebar a {float: left;}
  div.content {margin-left: 0;}
}
.ball-section {
        border: 1px solid black; /* Add border around each ball section */
        margin-bottom: 10px; /* Add some space between ball sections */
        padding: 10px; /* Add padding inside each ball section */
		background-color: white;

    }
.ball-section:hover{
	background-color: #33B5FF;
}
button {
    background-color: #4caf50;
    color: #fff;
    padding: 10px 20px;
    border: none;
    border-radius: 4px;
    cursor: pointer;
}

button:hover {
    background-color: #45a049;
}
		</style>
	</head>

	<body>
		<div class="sidebar">
		 <a href="${pageContext.servletContext.contextPath}/index">
			<img src="${pageContext.request.contextPath}/_view/BowlingBall.png"width="100" height="100">
		  </a>
	      <a href="${pageContext.servletContext.contextPath}/establishmentReg">Establishment Registration</a>
		  <a href="${pageContext.servletContext.contextPath}/logIn">Sign Out</a>
          <a href="${pageContext.servletContext.contextPath}/shot">Shot</a>
          <a href="${pageContext.servletContext.contextPath}/ballArsenal">Ball Arsenal</a>
          <a href="https://github.com/emmetl913/RevMetrixUI-Database">GitHub</a>
          <a href="${pageContext.servletContext.contextPath}/startBowling">Start Bowling</a>
		  </div>
	
		<form id="ballArsenalForm" action="${pageContext.servletContext.contextPath}/ballArsenal" method="post">
			<header><h1>Bowling Ball Arsenal</h1></header>	
	          <input type="hidden" id="type" name="newType" value="">

			<div class="ball-box" id="ballBoxDiv">

				<c:if test="${! empty errorMessage}">
					<div class="error">${errorMessage}</div>
				</c:if>
				<div id="add-ball-form">
					<input type="text" name="ballName" placeholder="Ball Name">
				    <input type="text" name="ballBrand" placeholder="Ball Brand"><br>
				    <input type="number" name="ballWeight" placeholder="Ball Weight (in pounds)" step="0.01" class="color-picker">
				    <input type="color" name="ballColor1" placeholder="Ball Color1" class="color-picker">
				    <input type="color" name="ballColor2" placeholder="Ball Color2" class="color-picker">
				    <br> 
				    
				    <button name="leftHand" type="button"onclick="setToLeft()">Left Hand</button>
				    <button name="rightHand" type="button"onclick="setToRight()">Right Hand</button>
				    <br>
				    <br>
					<button text="Add Ball" name="addBall" type="submit" value="Register Ball">
					Add Ball</button>
					
							
					
				</div>
				<div id="remove-ball-form">
					<input type="text" name = "removeBallName"placeholder="Ball Name to Remove">
					<button name="removeBall" type="submit" value="Remove Ball">
					Remove Ball</button>
				</div>
				<div id="ballsList"> &nbsp		
					<% 
			            if (balls != null && !balls.isEmpty()) {
			            	int i = 0;
			                for (Ball ball : balls) {
			                	String ballColor = ball.getColor1();
			        %>
			        <div class="ball-section" onclick="selectBall ('<%= ball.getBallId() %>')"><!--  style="background-color: <%=ballColor%>;"-->
				    <canvas id="shaderCanvas_<%=i%>" style="border-radius: 50%;"></canvas>
				    
			        <span style="font-size: smaller;">
			        <p>Name: <%= ball.getName() %> | Weight: <%=ball.getWeight()%> |
			        Brand: <%=ball.getBrand()%> | Righthand: <%= ball.getRightHanded() %> 
			        </p>
			        </span>

			    </div>
			  
			        <% 	i++; 
			                }
			            } else { 
			        %>
			        <p>You don't have any balls yet. </p>
			        <p> If you had two you could be a real boy.</p>
			        <% } 		session.setAttribute("ballArsenalKey", model);%> 
				</div>
			</div>

			<input type="hidden" name="selectedBall" id="selectedBall" value="">
		</form>
		
		
		<script>
	    // JavaScript to set hover color for each ball section
			//does not exist
		 function selectBall(ballName) {
		        document.getElementById('selectedBall').value = ballName;
		        document.getElementById('ballArsenalForm').submit();
		    }
		 function setToLeft() {
	          document.getElementById("type").value = "left";
	        }
		 function setToRight() {
	          document.getElementById("type").value = "right";
	        }
		</script>
		<!--webGL Stuff-->
		 <script src="https://cdnjs.cloudflare.com/ajax/libs/gl-matrix/2.8.1/gl-matrix-min.js"></script>
		 <script >
		 //hex input color to rgb
		 function hexToRgb(hex) {
			    // Remove the '#' character if present
			    hex = hex.replace('#', '');

			    // Parse the hexadecimal color string into separate R, G, and B components
			    const r = parseInt(hex.substring(0, 2), 16);
			    const g = parseInt(hex.substring(2, 4), 16);
			    const b = parseInt(hex.substring(4, 6), 16);

			    // Return an object containing the R, G, and B values
			    return { r, g, b };
			}
		 
		 
		 // Initialize WebGL context
		 //const canvas = document.getElementById('shaderCanvas_1');
		 var balls = <%= new Gson().toJson(balls) %>; // Convert Java ArrayList to JavaScript array
		    
		    balls.forEach(function(ball, index) {
		        var canvas = document.getElementById('shaderCanvas_' + index);
		        createCanvas(ball, canvas);
		    });
		
		function createCanvas(ball, canvas) {
		 // Vertex shader code
			const gl = canvas.getContext('webgl');
			const ballColor1 = ball.color1;
			const ballColor2 = ball.color2;
			const outerColor = hexToRgb(ballColor1);
			const innerColor = hexToRgb(ballColor2);
			console.log(outerColor); 
			console.log(innerColor);
			// Get the uniform location for outerColor in your shader

			
			const vertexShaderSource = `
		     attribute vec2 position;

		     void main() {
		         gl_Position = vec4(position, 0.0, 1.0);
		     }
		 `;

		 // Fragment shader code (replace with the provided ShaderToy shader code)
		 const fragmentShaderSource = `
		     precision mediump float;
	         uniform vec3 outerColor; // Define uniform for outer color
	         uniform vec3 innerColor;
		     uniform float iTime;
		     uniform vec2 iResolution;

		     // Permutation table
		     vec3 permute(vec3 x) {
		         return mod((x * 34.0 + 1.0) * x, 289.0);
		     }

		     // Simplex 2D noise function
		     float snoise(vec2 v) {
		        const vec4 C = vec4(0.211324865405187,  // (3.0-sqrt(3.0))/6.0
		                         0.366025403784439,  // 0.5*(sqrt(3.0)-1.0)
		                        -0.577350269189626,  // -1.0 + 2.0 * C.x
		                         0.024390243902439); // 1.0 / 41.0
		     vec2 i = floor(v + dot(v, C.yy));
		     vec2 x0 = v - i + dot(i, C.xx);
		     vec2 i1;
		     i1 = (x0.x > x0.y) ? vec2(1.0, 0.0) : vec2(0.0, 1.0);
		     vec4 x12 = x0.xyxy + C.xxzz;
		     x12.xy -= i1;
		     i = mod(i, 289.0);
		     vec3 p = permute(permute(i.y + vec3(0.0, i1.y, 1.0))
		         + i.x + vec3(0.0, i1.x, 1.0));
		     vec3 m = max(0.5 - vec3(dot(x0, x0), dot(x12.xy, x12.xy),
		                              dot(x12.zw, x12.zw)), 0.0);
		     m = m * m;
		     m = m * m;
		     vec3 x = 2.0 * fract(p * C.www) - 1.0;
		     vec3 h = abs(x) - 0.5;
		     vec3 ox = floor(x + 0.5);
		     vec3 a0 = x - ox;
		     m *= 1.79284291400159 - 0.85373472095314 * (a0 * a0 + h * h);
		     vec3 g;
		     g.x = a0.x * x0.x + h.x * x0.y;
		     g.yz = a0.yz * x12.xz + h.yz * x12.yw;
		     return 130.0 * dot(m, g);
		     }

		     // Swirly noise function
		     float swirlyNoise(vec2 p) {
		         return snoise(p * 3.5 + vec2(0.0, iTime * 1.5));
		     }

		     // Weighted color function
		     vec3 weightedColor(float intensity, vec3 baseColor, vec3 blackColor) {
		         return mix(blackColor, baseColor, intensity);
		     }

		     void main() {
		         vec2 uv = gl_FragCoord.xy / iResolution.xy; // Normalize coordinates

		         // Rotate UV coordinates by 90 degrees
		         uv = vec2(uv.y, uv.x);

		         // Create waves using sine function
		         float wave = sin(uv.x * 20.0 + iTime * 2.0) * 0.01;

		         // Add swirly noise
		         float noise = swirlyNoise(uv * 4.0);

		         // Calculate distance from center
		         vec2 center = vec2(0.5, 0.5);
		         float dist = length(uv - center);

		         // Create black and white stripes based on distance from center
		         float stripe = smoothstep(0.01 + wave, 0.99 + wave, dist);

		         // Combine stripe with noise
		         stripe += noise * 0.2;

		         // Clamp stripe to range [0, 1]
		         stripe = clamp(stripe, 0.0, 1.0);

		         // Map stripe value to color gradient
		         vec3 baseColor =outerColor;//= vec3(.1,.1,.4); // Example color (reddish) //outer color
		         vec3 blackColor = innerColor;//vec3(0.9, 0.1, 0.2); // Customizable black color inner color
		         
		         //get color from ball
		         //vec3 outerColorVec = vec3(parseFloat(outerColor.r), parseFloat(outerColor.g), parseFloat(outerColor.b);

		         
		         // Weight the color based on intensity
		         vec3 finalColor = weightedColor(stripe, baseColor, blackColor);

		         // Output color
		         gl_FragColor = vec4(finalColor, 1.0);
		     }
		 `;

		 // Compile shader function
		 function compileShader(gl, source, type) {
		     const shader = gl.createShader(type);
		     gl.shaderSource(shader, source);
		     gl.compileShader(shader);
		     if (!gl.getShaderParameter(shader, gl.COMPILE_STATUS)) {
		         console.error('Shader compilation error:', gl.getShaderInfoLog(shader));
		         gl.deleteShader(shader);
		         return null;
		     }
		     return shader;
		 }

		 // Create vertex and fragment shaders
		 const vertexShader = compileShader(gl, vertexShaderSource, gl.VERTEX_SHADER);
		 const fragmentShader = compileShader(gl, fragmentShaderSource, gl.FRAGMENT_SHADER);

		 // Create shader program
		 const shaderProgram = gl.createProgram();
		 gl.attachShader(shaderProgram, vertexShader);
		 gl.attachShader(shaderProgram, fragmentShader);
		 gl.linkProgram(shaderProgram);

		 // Check if shader program creation was successful
		 if (!gl.getProgramParameter(shaderProgram, gl.LINK_STATUS)) {
		     console.error('Shader program linking error:', gl.getProgramInfoLog(shaderProgram));
		 }

		 // Use shader program
		 gl.useProgram(shaderProgram);

		 // Get attribute and uniform locations
		 const positionLocation = gl.getAttribLocation(shaderProgram, 'position');
		 const resolutionLocation = gl.getUniformLocation(shaderProgram, 'iResolution');
		 const timeLocation = gl.getUniformLocation(shaderProgram, 'iTime');

		 // Set up vertex buffer with positions for a square
		 const positionBuffer = gl.createBuffer();
		 gl.bindBuffer(gl.ARRAY_BUFFER, positionBuffer);
		 gl.bufferData(gl.ARRAY_BUFFER, new Float32Array([
		     -1, -1,
		     1, -1,
		     -1, 1,
		     1, 1
		 ]), gl.STATIC_DRAW);

		 // Update vertex attribute pointer
		 gl.enableVertexAttribArray(positionLocation);
		 gl.vertexAttribPointer(positionLocation, 2, gl.FLOAT, false, 0, 0);

		 //const size = Math.min(window.innerWidth, window.innerHeight);

		//get location before rendering
	     const outerColorLocation = gl.getUniformLocation(shaderProgram, 'outerColor');
	     const innerColorLocation = gl.getUniformLocation(shaderProgram, 'innerColor');

	     gl.uniform3fv(outerColorLocation, [outerColor.r / 255, outerColor.g / 255, outerColor.b / 255]);
	     gl.uniform3fv(innerColorLocation, [innerColor.r / 255, innerColor.g / 255, innerColor.b / 255]);

		 
		 // Render function
		 function render() {
		     // Set canvas size
		     canvas.width = 52.0;
		     canvas.height = 50.0;
		     gl.viewport(0, 0, canvas.width, canvas.height);

		     // Calculate scaling factor to fit shader content in circular canvas
		     const scaleFactor = Math.min(canvas.width, canvas.height) / 2;

		     // Set resolution and time uniforms
		     gl.useProgram(shaderProgram);
			

		     gl.uniform2f(resolutionLocation, canvas.width, canvas.height);
		     gl.uniform1f(timeLocation, performance.now() / 1000);
		
		     // Clear canvas
		     gl.clearColor(0, 0, 0, 1);
		     gl.clear(gl.COLOR_BUFFER_BIT);

		     // Draw
		     gl.drawArrays(gl.TRIANGLE_STRIP, 0, 4);

		     // Request next frame
		     requestAnimationFrame(render);
		 }

		 // Start rendering
		 render();
		}
		</script>
		 
	</body>
</html>