precision mediump float;

uniform sampler2D uSampler0;

varying vec2 texCoord;

void main()
{
    gl_FragColor.rgb = vec3(1.0, 1.0, 1.0 ) * texture2D(uSampler0, texCoord).rgb;
}