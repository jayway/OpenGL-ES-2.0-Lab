precision mediump float;

uniform sampler2D sTexture;

varying vec2 texCoord;

void main()
{
    gl_FragColor.rgb = vec3(1.0, 1.0, 1.0 ) * texture2D(sTexture, texCoord).rgb;
}