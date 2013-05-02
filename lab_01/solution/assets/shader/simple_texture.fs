precision mediump float;

uniform sampler2D uTexture0;

varying vec2 texCoord;

void main()
{
    float tex_on = 1.0;
    gl_FragColor.rgb = mix( vec3(texCoord.x, texCoord.y, 1.0 ), texture2D(uTexture0, texCoord).rgb, tex_on);
    gl_FragColor.a = 1.0;
}