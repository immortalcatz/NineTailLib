precision mediump float;
uniform float time;
uniform vec2 resolution;
uniform vec3 color;

void main(void){
	float r = abs(sin(time * 0.1));
	float g = abs(cos(time * 0.3));
	float c = (r * g) / 2.0;
	float a = gl_FragCoord.x / 512.0;
	float b = gl_FragCoord.y / 512.0;
	vec2 p = (gl_FragCoord.xy * 2.0 - resolution) / min(resolution.x, resolution.y);

	for (float i = 0.0; i < 5.0; i++){
		float j = i + 0.1;
		vec2 q = p + vec2(cos(time * j), sin(time * j)) * 0.5;
		color += 0.05 / length(q);
	}

	p += vec2(cos(time * 5.0), sin(time)) * 0.5;
	float l = 0.1 * abs(sin(time)) / length(p);
    gl_FragColor = vec4(color, 1.0);
}