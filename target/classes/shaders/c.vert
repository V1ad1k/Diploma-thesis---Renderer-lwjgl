#version 330 core

// Vertex coordinates originally given in object space
layout(location = 0) in vec4 vertex;
layout(location = 3) in vec3 color;
layout(location = 1) in vec4 normal;
layout(location = 2) in vec2 texts;

// MODELVIEW matrix that transforms form object space to eye space.
uniform mat4 viewMatrix;
uniform mat4 projectionMatrix;
//uniform mat4 mvp = mat4(1.0);
//uniform vec4 u_MaterialParameters;

varying  vec4 v_color;
out vec4 position_eye_coords;


struct Light {
    vec4 position;

    vec3 ambient;
    vec3 diffuse;
    vec3 specular;

    float constant;
    float linear;
    float quadratic;
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
    float  glossiness;
};

uniform PointLight  lights;
uniform int current_light_count;
uniform Material material;

uniform mat4 model_matrix;


void main(){
    // Compute pozition in NDC space - it is obligatory
    //    gl_Position =  PROJ_mtx * MV_mtx * vec4(vertex.x, vertex.y, vertex.z, 5.0);
    mat4 model_view_matrix = viewMatrix * model_matrix;
    //    gl_Position =  PROJ_mtx * MV_mtx * vec4(vertex, 1.0);
    gl_Position =  projectionMatrix * model_view_matrix * vertex;
    // Compute vertex position eye coordinates
    position_eye_coords = viewMatrix * vertex;

    // Compute normal in eye coordinates
    vec4 normal_eye = viewMatrix * normal;
    vec3 normal_eye_v3 = normal_eye.xyz / normal_eye.w;

    vec3 normal_eye_coords3f = normal_eye_v3 - (viewMatrix * vec4(0, 0, 0, 1)).xyz;

    // ==========================================================================
    // Compute color of vertex
    // ==========================================================================
    vec3 position_eye_coords3f = position_eye_coords.xyz / position_eye_coords.w;
    //    PointLight  light = lights;
    //    float attenuation = 1.0 / (light.constant + light.linear * distance +
    //    light.quadratic * (distance * distance));

    //    vec3 ambient  = lightt.ambient * material.mat_amb;



    //    vec3 ambient = vec3(1,1,1) * material.mat_amb;
    //    //    vec3 ambient = light.ambient * material.mat_amb;
    //    //    vec3 ambient = light.color * material.mat_amb;
    //    // diffuse
    //    vec3 norm = normalize(normal_eye_coords3f);
    //    vec3 light_position_eye_coords3f = light.position.xyz / light.position.w;
    //    vec3 lightDir = normalize(light_position_eye_coords3f - position_eye_coords3f);
    //    float diff = max(dot(norm, lightDir), 0.0);
    //    //    vec3 diffuse  = lightt.diffuse * (diff * material.mat_diff);
    //
    //    vec3 diffuse = light.diffuse * (diff * material.mat_diff);
    //    //    vec3 diffuse = light.color * (diff *material.mat_diff);
    //    // specular
    //    vec3 viewDir = normalize(position_eye_coords3f);
    //
    //
    //    vec3 reflectDir = reflect(-lightDir, norm);
    //    float spec = pow(max(dot(viewDir, reflectDir), 0.0),  material.glossiness);
    //    //    float spec = pow(max(dot(unit_normal, halfwayDir), 0.0),  material.glossiness);
    //    //    vec3 specular = lightt.specular * (spec * material.mat_spec);
    //    //    vec3 specular = light.specular * spec;
    //    vec3 specular =  light.specular * (spec * material.mat_spec);
    vec3 result = vec3( 0.0, 0.0, 0.0);
//    for (int i = 3; i < 4; i ++)
//    {
        Light light = lightt;

        vec3 norm = normalize(normal_eye_coords3f);
        vec3 light_position_eye_coords3f = light.position.xyz / light.position.w;
        vec3 lightDir = normalize(light_position_eye_coords3f - position_eye_coords3f);
        vec3 reflectDir = reflect(-lightDir, norm);
        float diff = max(dot(norm, lightDir), 0.0);
        vec3 viewDir = normalize(position_eye_coords3f);
        float spec = pow(max(dot(viewDir, reflectDir), 0.0), material.glossiness);

//        float distance = length(light.position - gl_Position);
        float distance = length( position_eye_coords3f - light_position_eye_coords3f);
        float attenuation = 1.0 / (light.constant + light.linear * distance +
        light.quadratic * (distance * distance));


        vec3 ambient  = light.ambient  * material.mat_amb;
        vec3 diffuse  = light.diffuse  * material.mat_diff;
//        vec3 diffuse  = light.diffuse  * material.mat_diff;
        vec3 specular = light.specular * spec * material.mat_spec;
        ambient  *= attenuation;
//        diffuse  *= attenuation;
        specular *= attenuation;

        result += ambient+ diffuse + specular;
        //    vec3 result = ambient+ material.mat_diff  + specular;

        //    vec3 result = material.mat_amb + material.mat_diff + material.mat_spec;


        //    vec3 result =  diffuse + specular;
//    }

    v_color = vec4(result, 1.0);
}