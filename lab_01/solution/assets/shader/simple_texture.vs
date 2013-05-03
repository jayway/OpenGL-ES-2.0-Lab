attribute vec3 aPosition;
attribute vec2 aTextureCoord;

uniform mat4 uMVPMatrix;

varying vec2 texCoord;

void main()
{
    texCoord = aTextureCoord;
    gl_Position =  uMVPMatrix * vec4(aPosition, 1.0);
}