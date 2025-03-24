import pyray as pr
import numpy as np
from pyray import Vector3

# Importer les fonctions et utilitaires existants
from exo1 import (
    initialize_camera,
    update_camera_position,
    load_ply_file,
    initialize_mesh_for_transforming,
    apply_transformations_homogeneous,
    draw_mesh,
    rotation_matrix_homogeneous,
    translation_matrix,
    draw_plane,
    orthographic_projection_matrix_homogeneous,
    draw_coordinate_axes,
    draw_transformation_axis,
    perspective_projection_matrix
)

def main():
    pr.init_window(1000, 900, "Cube central tournant avec cubes orbitaux")
    pr.set_target_fps(60)

    # Charger l'objet central
    mesh_file = "cube.ply"  # Remplacez par le chemin réel vers votre fichier PLY
    mesh = load_ply_file(mesh_file)
    initialize_mesh_for_transforming(mesh)

    # Contrôles GUI
    translate_x_ptr = pr.ffi.new('float *', 0.0)
    translate_y_ptr = pr.ffi.new('float *', 0.0)
    translate_z_ptr = pr.ffi.new('float *', 0.0)
    rotation_angle_ptr = pr.ffi.new('float *', 0.0)
    axis_x_ptr = pr.ffi.new('float *', 1.0)
    axis_y_ptr = pr.ffi.new('float *', 0.0)
    axis_z_ptr = pr.ffi.new('float *', 0.0)
    orbit_count_ptr = pr.ffi.new('float *', 5)
    orbit_radius_ptr = pr.ffi.new('float *', 5)
    d_ptr = pr.ffi.new('float *', 1.0)       # Paramètre de distance pour la projection en perspective
    projection_type_ptr = pr.ffi.new('float *', -1)  # Valeur par défaut (aucune projection)

    # Créer une liste de cubes orbitaux
    # TODO : expliquer tous les différents paramètres (e.g., inclination)
    max_orbits = 50
    orbit_cubes = []
    for _ in range(max_orbits):
        orbit_cubes.append({
            "transform": np.eye(4),
            "angle_offset": np.random.uniform(0, 2 * np.pi),
            "inclination": np.random.uniform(-np.pi / 4, np.pi / 4),
            "rotation_axis": Vector3(
                np.random.uniform(-1, 1),
                np.random.uniform(-1, 1),
                np.random.uniform(-1, 1)
            ),
            "clockwise": np.random.choice([True, False]),
            "scale": np.random.uniform(0.5,2),
            "distance_from_orbit": np.random.uniform(1,10)
        })

    camera = initialize_camera()

    while not pr.window_should_close():
        update_camera_position(camera, movement_speed = 0.1)
        pr.begin_drawing()
        pr.clear_background(pr.RAYWHITE)

        pr.begin_mode_3d(camera)

        # Transformation du cube central
        # TODO : construit une matrice représentant la transformation du cube central
        central_translation = translation_matrix(translate_x_ptr[0], translate_y_ptr[0], translate_z_ptr[0])
        rotation_axis = Vector3(axis_x_ptr[0], axis_y_ptr[0], axis_z_ptr[0])
        central_rotation = rotation_matrix_homogeneous(rotation_axis, np.radians(rotation_angle_ptr[0]))
        projection_matrix = np.eye(4)
        axis = Vector3(axis_x_ptr[0], axis_y_ptr[0], axis_z_ptr[0])
        projection_matrix = np.eye(4)

        if projection_type_ptr[0] > -1 and projection_type_ptr[0] < 1:
            projection_matrix = orthographic_projection_matrix_homogeneous(axis)
        elif projection_type_ptr[0] == 1:
            projection_matrix = perspective_projection_matrix(d_ptr[0])
        central_transform = central_translation @ central_rotation @ projection_matrix
        

        # Dessiner le cube central
        apply_transformations_homogeneous(mesh, central_transform, np.eye(4), np.eye(4), np.eye(4))
        draw_mesh(mesh)

        # Dessiner les cubes orbitaux
        for i in range(round(orbit_count_ptr[0])):
            orbit = orbit_cubes[i]
            # TODO : expliquer cette ligne en expérimentant et en lisant la documentation
            angle = pr.get_time() * (1 if orbit["clockwise"] else -1) + orbit["angle_offset"]

            # Rotation autour de l'axe du cube
            orbit_rotation = rotation_matrix_homogeneous(orbit["rotation_axis"], angle)

            # Transformation du cube orbital relative au cube central
            """ Le mouvement se déroule principalement dans le plan xz, tandis 
            que la coordonnée y introduit une inclinaison pour donner l'impression 
            que l'orbite est inclinée dans l'espace 3D."""
            # TODO 
            distance_from_orbit = orbit['distance_from_orbit']
            orbit_x = np.cos(angle) * distance_from_orbit  * orbit_radius_ptr[0] 
            orbit_y = np.sin(orbit['inclination']) * distance_from_orbit * orbit_radius_ptr[0] 
            orbit_z = np.sin(angle) * distance_from_orbit * orbit_radius_ptr[0] 
            orbit_translation = translation_matrix(orbit_x, orbit_y, orbit_z)
            scale = orbit['scale']
            scale_matrix = np.array([
                [scale, 0, 0, 0],
                [0, scale, 0, 0],
                [0,0,scale,0],
                [0,0,0,1]
            ])
            
            
            orbit_transform = central_transform @ orbit_translation @ orbit_rotation @ scale_matrix @ projection_matrix
            apply_transformations_homogeneous(mesh, orbit_transform, np.eye(4), np.eye(4), np.eye(4))
            draw_mesh(mesh)
        draw_plane(axis, 20)
        draw_coordinate_axes(Vector3(0, 0, 0), scale=3)
        draw_transformation_axis(Vector3(0, 0, 0), axis, scale=3)
        pr.end_mode_3d()

        # Contrôles GUI
        pr.draw_text("Contrôles du Cube Central", 10, 10, 20, pr.BLACK)
        pr.draw_text("Translation X:", 10, 40, 20, pr.BLACK)
        pr.gui_slider_bar(pr.Rectangle(10, 60, 200, 20), "-5.0", "5.0", translate_x_ptr, -5.0, 5.0)
        pr.draw_text("Translation Y:", 10, 90, 20, pr.BLACK)
        pr.gui_slider_bar(pr.Rectangle(10, 110, 200, 20), "-5.0", "5.0", translate_y_ptr, -5.0, 5.0)
        pr.draw_text("Translation Z:", 10, 140, 20, pr.BLACK)
        pr.gui_slider_bar(pr.Rectangle(10, 160, 200, 20), "-5.0", "5.0", translate_z_ptr, -5.0, 5.0)
        pr.draw_text("Axe de Rotation X:", 10, 190, 20, pr.BLACK)
        pr.gui_slider_bar(pr.Rectangle(10, 210, 200, 20), "-1.0", "1.0", axis_x_ptr, -1.0, 1.0)
        pr.draw_text("Axe de Rotation Y:", 10, 240, 20, pr.BLACK)
        pr.gui_slider_bar(pr.Rectangle(10, 260, 200, 20), "-1.0", "1.0", axis_y_ptr, -1.0, 1.0)
        pr.draw_text("Axe de Rotation Z:", 10, 290, 20, pr.BLACK)
        pr.gui_slider_bar(pr.Rectangle(10, 310, 200, 20), "-1.0", "1.0", axis_z_ptr, -1.0, 1.0)
        pr.draw_text("Angle de Rotation:", 10, 340, 20, pr.BLACK)
        pr.gui_slider_bar(pr.Rectangle(10, 360, 200, 20), "0", "360", rotation_angle_ptr, 0.0, 360.0)
        pr.draw_text("Cubes Orbitaux:", 10, 400, 20, pr.BLACK)
        pr.gui_slider_bar(pr.Rectangle(10, 420, 200, 20), "0", "50", orbit_count_ptr, 0, max_orbits)
        pr.draw_text("Rayon d'Orbite:", 10, 440, 20, pr.BLACK)
        pr.gui_slider_bar(pr.Rectangle(10, 460, 200, 20), "0", "10", orbit_radius_ptr, 0, 10)
       
         # Ajout d'un espace avant les contrôles de projection
        pr.draw_text("Type de projection:", 10, 560, 20, pr.BLACK)
        pr.gui_slider_bar(pr.Rectangle(10, 620, 200, 20), "-1", "1", projection_type_ptr, -1.0, 1.0)
        if projection_type_ptr[0] == 1:
            pr.draw_text("Distance de projection:", 10, 680, 20, pr.BLACK)
            pr.gui_slider_bar(pr.Rectangle(10, 720, 200, 20), "1.0", "8.0", d_ptr, 1.0, 8.0)

        pr.end_drawing()

    pr.close_window()

if __name__ == "__main__":
    main()