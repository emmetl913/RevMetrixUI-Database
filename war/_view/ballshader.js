// Initialize WebGL context
const canvas = document.getElementById('shaderCanvas');
const gl = canvas.getContext('webgl');

// Vertex shader code
const vertexShaderSource = `
    attribute vec2 position;

    void main() {
        gl_Position = vec4(position, 0.0, 1.0);
    }
`;

// Fragment shader code (replace with the provided ShaderToy shader code)
const fragmentShaderSource = `
    precision mediump float;

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
        return snoise(p * 3.5 + vec2(0.0, iTime * 0.5));
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
        vec3 baseColor = vec3(0.9, 0.8, 0.2); // Example color (reddish)
        vec3 blackColor = vec3(.4,.1,.4); // Customizable black color

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

// Render function
function render() {
    // Set canvas size
    canvas.width = 40.0;
    canvas.height = 40.0;
    gl.viewport(0, 0, canvas.width, canvas.height);

    // Calculate scaling factor to fit shader content in circular canvas
    const scaleFactor = Math.min(canvas.width, canvas.height) / 2;

    // Set resolution and time uniforms
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