#version 330 core

// Ouput data
in vec3 v_color;
out vec3 color;

struct PointLight {
    vec4  position;
    vec4  axis;
    float cone_angle;
    vec3  color;
    float attenuation_polynomial[3];
};

uniform PointLight  lights[10];
uniform int current_light_count;

in vec4 position_eye_coords;

//void main()
//{
//
//    // Output color = red
//    color = v_color;
//
//}
void main()
{

    // Output color = red
    color = v_color;

//
//    vec3 position_eye_coords3f = position_eye_coords.xyz / position_eye_coords.w;
//
//    vec3 ilum_intensity = vec3( 0.0, 0.0, 0.0);
//    for (int i = 0; i < current_light_count; i++ )
//    {
//        PointLight  light = lights[i];
//
//        // compute distance to light
//        vec3 light_position_eye_coords3f = light.position.xyz / light.position.w;
//        float distance = length(  position_eye_coords3f - light_position_eye_coords3f );
//
//        float  attenuation = 1.0 / ( light.attenuation_polynomial[0] +
//                                     light.attenuation_polynomial[1] * distance +
//                                     light.attenuation_polynomial[2] * distance * distance );
//        ilum_intensity += light.color * v_color / distance;
//    }
//    color = ilum_intensity;

}