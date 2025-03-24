import pyray as pr
import numpy as np
import math
from pyray import Vector3
import trimesh
from exo1_tp1_copy import cross_product, dot_product, vector_normalize, vector_length, initialize_camera, update_camera_position


def draw_plane(axis, size=5, color=pr.GRAY):
    """Dessine un plan basé sur un vecteur normal et une taille."""
    axis = vector_normalize(axis)
    orthogonal_vector = Vector3(1, 0, 0) if abs(axis.x) < abs(axis.y) else Vector3(0, 1, 0)
    v1 = cross_product(axis, orthogonal_vector)
    v2 = cross_product(axis, v1)

    for i in range(-size, size + 1):
        start1 = Vector3(v1.x * -size + v2.x * i, v1.y * -size + v2.y * i, v1.z * -size + v2.z * i)
        end1 = Vector3(v1.x * size + v2.x * i, v1.y * size + v2.y * i, v1.z * size + v2.z * i)
        start2 = Vector3(v2.x * -size + v1.x * i, v2.y * -size + v1.y * i, v2.z * -size + v1.z * i)
        end2 = Vector3(v2.x * size + v1.x * i, v2.y * size + v1.y * i, v2.z * size + v1.z * i)
        
        pr.draw_line_3d(start1, end1, color)
        pr.draw_line_3d(start2, end2, color)

def draw_vector_3(start, end, color, thickness=0.05, head_size_factor=0.8):
    """Dessine un vecteur en utilisant un cylindre et un cône."""
    direction = Vector3(end.x - start.x, end.y - start.y, end.z - start.z)
    length = vector_length(direction)
    head_size = length * head_size_factor
    
    n_direction = vector_normalize(direction)
    
    arrow_start = Vector3(start.x + n_direction.x * head_size, 
                          start.y + n_direction.y * head_size, 
                          start.z + n_direction.z * head_size)
    
    pr.draw_cylinder_ex(start, end, thickness / 2, thickness / 2, 8, color)
    pr.draw_cylinder_ex(arrow_start, end, thickness * 2, thickness / 5, 8, color)

def draw_coordinate_axes(origin, scale=3):
    """Dessine les axes de coordonnées standard X, Y, Z à partir de l'origine."""
    # X-axis (Rouge)
    draw_vector_3(origin, Vector3(origin.x + scale, origin.y, origin.z), pr.RED, thickness=0.05)
    
    # Y-axis (Vert)
    draw_vector_3(origin, Vector3(origin.x, origin.y + scale, origin.z), pr.GREEN, thickness=0.05)
    
    # Z-axis (Bleu)
    draw_vector_3(origin, Vector3(origin.x, origin.y, origin.z + scale), pr.BLUE, thickness=0.05)

def draw_transformation_axis(origin, axis, scale=3):
    """Dessine l'axe de transformation personnalisé à partir de l'origine dans la direction de l'axe donné."""
    # Mettre à l'échelle le vecteur d'axe pour la visualisation
    scaled_axis = Vector3(origin.x + axis.x * scale, origin.y + axis.y * scale, origin.z + axis.z * scale)
    draw_vector_3(origin, scaled_axis, pr.PURPLE, thickness=0.05)

def draw_mesh(mesh):
    """Dessine le mesh complet avec sommets, arêtes et faces."""
    for face in mesh.faces:
        v0 = Vector3(*mesh.vertices[face[0]])
        v1 = Vector3(*mesh.vertices[face[1]])
        v2 = Vector3(*mesh.vertices[face[2]])
        pr.draw_triangle_3d(v0, v1, v2, pr.LIGHTGRAY)
    
    for edge in mesh.edges:
        v_start = Vector3(*mesh.vertices[edge[0]])
        v_end = Vector3(*mesh.vertices[edge[1]])
        pr.draw_line_3d(v_start, v_end, pr.BLACK)
    
    for vertex in mesh.vertices:
        pr.draw_sphere(Vector3(*vertex), 0.05, pr.RED)

def load_ply_file(file_path):
    """Charge un fichier PLY et retourne le mesh en tant que structure de données trimesh."""
    mesh = trimesh.load(file_path)
    return mesh

def initialize_mesh_for_transforming(mesh):
    """Stocke les sommets originaux du mesh pour permettre un redimensionnement dynamique."""
    mesh.original_vertices = np.copy(mesh.vertices)

def scaling_matrix(axis, k):
    """Génère une matrice de mise à l'échelle le long d'un axe arbitraire."""
   
    n = vector_normalize(axis)
    return np.array([
        [1 + (k-1) * n.x*n.x, (k-1)*n.x*n.y, (k-1)*n.x*n.z],
        [(k-1)*n.x*n.y, 1 + (k-1)*n.y*n.y, (k-1)*n.y*n.z],
        [(k-1)*n.x*n.z, (k-1)*n.y*n.z, 1 + (k-1)*n.z*n.z]
    ])

def shearing_matrix_xy(s, t):
    return np.array([
       [1,0,s],
       [0,1,t],
       [0,0,1]
    ])

def rotation_matrix(axis, theta):
    """Génère une matrice de rotation autour d'un axe arbitraire."""
 
    n = vector_normalize(axis)
    cos_theta = np.cos(theta)
    sin_theta = np.sin(theta)
    return np.array([
        [n.x*n.x*(1-cos_theta) + cos_theta, n.x*n.y*(1-cos_theta)-n.z*sin_theta, n.x*n.z*(1-cos_theta) + n.y*sin_theta],
        [n.x*n.y*(1-cos_theta) + n.z*sin_theta, n.y*n.y*(1-cos_theta) + cos_theta, n.y*n.z*(1-cos_theta) - n.x*sin_theta],
        [n.x*n.z*(1-cos_theta) - n.y*sin_theta, n.y*n.z*(1-cos_theta) + n.x*sin_theta, n.z*n.z*(1-cos_theta) + cos_theta]
    ])

def orthographic_projection_matrix(axis):
    """Génère une matrice de projection orthographique pour la projection sur un plan normal à un axe donné."""

    n = vector_normalize(axis)
    return np.array([
        [1-n.x*n.x, -1*n.x*n.y, -1*n.x*n.z],
        [-1*n.x*n.y, 1-n.y*n.y, -1*n.y*n.z],
        [-1*n.x*n.z, -1*n.y*n.z, 1-n.z*n.z]
    ])

def apply_transformations(mesh, rotation_mat, scaling_mat, projection_mat, shearing_mat):
    """Applique les transformations de rotation, de mise à l'échelle et de projection aux sommets du mesh."""
    mesh.vertices = mesh.original_vertices @ shearing_mat.T @ rotation_mat.T @ scaling_mat.T @ projection_mat.T

def test_scaling_vs_projection(mesh):
    # Matrices de transformation
    scaling_mat = scaling_matrix(Vector3(1, 0, 0), 2)  # Mise à l'échelle sur l'axe X
    projection_mat = orthographic_projection_matrix(Vector3(0, 0, 1))  # Projection orthographique selon Z
    
    # Appliquer les transformations : mise à l'échelle puis projection
    mesh.vertices = mesh.original_vertices @ scaling_mat.T @ projection_mat.T

    # Afficher les résultats (uniquement les coordonnées projetées)
    for vertex in mesh.vertices:
        print(f"Projections après mise à l'échelle et projection : ({vertex[0]}, {vertex[1]})")
    
    # Vérifier les coordonnées avant et après mise à l'échelle
    print("Coordonnées avant transformation :")
    for vertex in mesh.original_vertices:
        print(f"Original: ({vertex[0]}, {vertex[1]})")

    print("\nCoordonnées après mise à l'échelle et projection :")
    for vertex in mesh.vertices:
        print(f"Projections : ({vertex[0]}, {vertex[1]})")

def main():
    
    pr.init_window(1000, 800, "3D Viewer with Rotation, Scaling, and Projection Control")
    pr.set_window_min_size(800, 600)
    camera = initialize_camera()
    pr.set_target_fps(60)
    movement_speed = 0.1

    ply_file_path = "dolphin.ply"
    mesh = load_ply_file(ply_file_path)
    
    initialize_mesh_for_transforming(mesh)
    test_scaling_vs_projection(mesh)
    scale_factor_ptr = pr.ffi.new('float *', 1.0)
    angle_ptr = pr.ffi.new('float *', 0.0)
    axis_x_ptr = pr.ffi.new('float *', 1.0)
    axis_y_ptr = pr.ffi.new('float *', 0.0)
    axis_z_ptr = pr.ffi.new('float *', 0.0)
    projection_ptr = pr.ffi.new('bool *', 0)

    shear_s_ptr = pr.ffi.new('float *', 0.0)
    shear_t_ptr = pr.ffi.new('float *', 0.0)

    while not pr.window_should_close():
        update_camera_position(camera, movement_speed)
        
        pr.begin_drawing()
        pr.clear_background(pr.RAYWHITE)
        
        pr.begin_mode_3d(camera)
        
        axis = Vector3(axis_x_ptr[0], axis_y_ptr[0], axis_z_ptr[0])
        angle = angle_ptr[0]
        scale_factor = scale_factor_ptr[0]
        
        rotation_mat = rotation_matrix(axis, np.radians(angle))
        scaling_mat = scaling_matrix(axis, scale_factor)
        shearing_mat = shearing_matrix_xy(shear_s_ptr[0], shear_t_ptr[0])
        projection_mat = np.eye(3)
        if projection_ptr[0]:
            projection_mat = orthographic_projection_matrix(axis)
            
        # Dessiner les axes de coordonnées standard
        draw_coordinate_axes(Vector3(0, 0, 0), scale=3)
        
        # Obtenir l'axe de transformation en fonction des valeurs du curseur
        axis = Vector3(axis_x_ptr[0], axis_y_ptr[0], axis_z_ptr[0])
        # Tracer l'axe de transformation à partir de l'origine
        draw_transformation_axis(Vector3(0, 0, 0), axis, scale=3)            
        
        apply_transformations(mesh, rotation_mat, scaling_mat, projection_mat, shearing_mat)
        draw_plane(axis, 10)
        draw_mesh(mesh)
        pr.end_mode_3d()

        pr.draw_text("Échelle:", 750, 50, 20, pr.BLACK)
        pr.gui_slider_bar(pr.Rectangle(750, 80, 200, 20), "0.5", "10", scale_factor_ptr, 0.4, 10.0)
        pr.draw_text("Angle (degrés):", 750, 110, 20, pr.BLACK)
        pr.gui_slider_bar(pr.Rectangle(750, 140, 200, 20), "0", "360", angle_ptr, 0.0, 360.0)        
        
        pr.draw_text("Axe de transformation X:", 750, 170, 20, pr.BLACK)
        pr.gui_slider_bar(pr.Rectangle(750, 200, 200, 20), "-1.0", "1.0", axis_x_ptr, -1.0, 1.0)
        pr.draw_text("Axe de transformation Y:", 750, 230, 20, pr.BLACK)
        pr.gui_slider_bar(pr.Rectangle(750, 260, 200, 20), "-1.0", "1.0", axis_y_ptr, -1.0, 1.0)
        pr.draw_text("Axe de transformation Z:", 750, 290, 20, pr.BLACK)
        pr.gui_slider_bar(pr.Rectangle(750, 320, 200, 20), "-1.0", "1.0", axis_z_ptr, -1.0, 1.0)

        pr.draw_text("Projection orthographique:", 750, 350, 20, pr.BLACK)
        pr.gui_check_box(pr.Rectangle(750, 380, 20, 20), "Activer", projection_ptr)

        pr.draw_text("s", 750, 440, 20, pr.BLACK)
        pr.gui_slider_bar(pr.Rectangle(750, 470, 200, 20), "-1.0", "1.0", shear_s_ptr, -1.0, 1.0)

        pr.draw_text("t", 750, 500, 20, pr.BLACK)
        pr.gui_slider_bar(pr.Rectangle(750, 530, 200, 20), "-1.0", "1.0", shear_t_ptr, -1.0, 1.0)


        pr.end_drawing()

    pr.close_window()

if __name__ == "__main__":
    main()