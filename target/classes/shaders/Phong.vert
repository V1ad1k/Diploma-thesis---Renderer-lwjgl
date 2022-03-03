#version 330 core

// Vertex coordinates originally given in object space
layout(location = 0) in vec4 vertex;
layout(location = 3) in vec3 color;
layout(location = 1) in vec4 normal;
layout(location = 2) in vec2 texts;


// MODELVIEW matrix that transforms form object space to eye space.
uniform mat4 MV_mtx;
uniform mat4 PROJ_mtx;
//uniform mat4 lightSpaceMatrix;
//uniform mat4 model;
//uniform mat4 mvp = mat4(1.0);
//uniform vec4 u_MaterialParameters;

varying  vec3 v_color;
out vec4 position_eye_coords;


struct Light {
    vec3 position;

    vec3 ambient;
    vec3 diffuse;
    vec3 specular;
};

uniform Light lightt;

struct PointLight {
    vec4  position;
    vec4  axis;
    float cone_angle;
    vec3  color;
    float attenuation_polynomial[3];
};

struct Material {
    vec3   mat_diff;
    vec3   mat_amb;
    vec3   mat_spec;
    float  mat_transp;
    float  glossiness;
};

uniform PointLight  lights[10];
uniform int current_light_count;
uniform Material material;

uniform mat4 model_matrix;
uniform mat4 viewMatrix;
uniform mat4 projectionMatrix;


void main(){
//    mat4 model_view_matrix = viewMatrix * model_matrix;
//
//    gl_Position = projectionMatrix * viewMatrix *  vertex;
//
//    // Compute vertex position eye coordinates
//    position_eye_coords = model_view_matrix * vertex;


    mat4 model_view_matrix = viewMatrix * model_matrix;
    //    gl_Position =  PROJ_mtx * MV_mtx * vec4(vertex, 1.0);
    gl_Position =  projectionMatrix * model_view_matrix * vec4(vertex.x, vertex.y, vertex.z, 1.0);
    // Compute vertex position eye coordinates
    position_eye_coords = model_view_matrix * vertex;

    // Compute normal in eye coordinates
    vec4 normal_eye = model_view_matrix * normal;
    vec3 normal_eye_v3 = normal_eye.xyz / normal_eye.w;
    //vec4 anchor_eye = MV_mtx * vec4( 0, 0, 0, 1);
    //vec3 anchor_eye_v3 = anchor_eye.xyz / anchor_eye.w;
    vec3 normal_eye_coords3f = normal_eye_v3 - (model_view_matrix * vec4( 0, 0, 0, 1)).xyz;

    // ==========================================================================
    // Compute color of vertex
    // ==========================================================================
    vec3 position_eye_coords3f = position_eye_coords.xyz / position_eye_coords.w;
    vec3 unit_normal = normalize( normal_eye_coords3f );


//    gl_Position =  PROJ_mtx * MV_mtx * vec4(vertex.x, vertex.y, vertex.z, 50.0);
//
//    // Compute vertex position eye coordinates
//    position_eye_coords = MV_mtx * vertex;
//
//    // Compute normal in eye coordinates
//    vec4 normal_eye = MV_mtx * normal;
//    vec3 normal_eye_v3 = normal_eye.xyz / normal_eye.w;
//    //vec4 anchor_eye = MV_mtx * vec4( 0, 0, 0, 1);
//    //vec3 anchor_eye_v3 = anchor_eye.xyz / anchor_eye.w;
//    vec3 normal_eye_coords3f = normal_eye_v3 - (MV_mtx * vec4(0, 0, 0, 1)).xyz;
//
//    // ==========================================================================
//    // Compute color of vertex
//    // ==========================================================================
//    vec3 position_eye_coords3f = position_eye_coords.xyz / position_eye_coords.w;
//    vec3 unit_normal = normalize(normal_eye_coords3f);

    vec3 ilum_intensity = vec3( 0.0, 0.0, 0.0);
    for (int i = 0; i < current_light_count; i++ )
    {
        PointLight  light = lights[i];

        vec3 ambient = light.color * material.mat_amb;

        // diffuse
//        vec3 norm = normalize(normal_eye_coords3f);
//        vec3 light_position_eye_coords3f = light.position.xyz / light.position.w;
//        vec3 lightDir = normalize(light_position_eye_coords3f - position_eye_coords3f);
//        float diff = max(dot(norm, lightDir), 0.0);

        // compute distance to light
        vec3 light_position_eye_coords3f = light.position.xyz / light.position.w;
        float distance = length(  position_eye_coords3f - light_position_eye_coords3f );

        float  attenuation = 1.0 / ( light.attenuation_polynomial[0] +
        light.attenuation_polynomial[1] * distance +
        light.attenuation_polynomial[2] * distance * distance );

        // Take incident angle into account
        vec3 dir_to_light = normalize( light_position_eye_coords3f - position_eye_coords3f );
        float angle_attenuation = dot( dir_to_light, unit_normal );
//
        if ( angle_attenuation < 0 )
        continue;
        vec3 dir_to_observer = normalize( position_eye_coords3f );
        vec3 reflected = 2 * normal_eye_coords3f * dot( normal_eye_coords3f, dir_to_observer ) - dir_to_observer;
        float gloss_strength = pow( dot( reflected, dir_to_light ), material.glossiness);
        vec3 gloss_color = ( light.color * material.mat_spec ) * gloss_strength ;

//        ilum_intensity +=  light.color *  material.mat_diff * angle_attenuation * material.mat_transp  / distance + gloss_color;
        ilum_intensity +=  light.color *  material.mat_diff * angle_attenuation  / distance + gloss_color;
        //        ilum_intensity = ambient;
    }

      v_color = ilum_intensity;
//    v_color = vec4(result, 1.0);
}