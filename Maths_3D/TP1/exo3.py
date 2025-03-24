import pyray as pr
import math
from pyray import Vector3
import random
from exo1 import cross_product, dot_product, vector_length, vector_normalize

def initialize_camera():
    """Initialise la caméra 3D."""
    camera = pr.Camera3D(
        Vector3(0, 10, 10),  # position
        Vector3(0, 0, 0),    # cible
        Vector3(0, 1, 0),    # haut
        45,                  # fovy (champ de vision dans la direction y)
        pr.CAMERA_PERSPECTIVE
    )
    return camera


def draw_vector_3(start, end, color, thickness=0.05):
    """Nous dessinons un vecteur en utilisant un cylindre et un cône."""
    direction = Vector3(end.x - start.x, end.y - start.y, end.z - start.z)
    length = vector_length(direction)
    head_size = length * 0.8
    
    n_direction = vector_normalize(direction)
    
    arrow_start = Vector3(start.x + n_direction.x * head_size, 
                          start.y + n_direction.y * head_size, 
                          start.z + n_direction.z * head_size)
    
    pr.draw_cylinder_ex(start, end, thickness / 2, thickness / 2, 8, color)
    pr.draw_cylinder_ex(arrow_start, end, thickness * 2, thickness / 5, 8, color)

def draw_points(points):
    colors = [pr.RED, pr.GREEN, pr.BLUE]
    for i, point in enumerate(points):
        pr.draw_sphere(point, 0.1, colors[i % len(colors)])




def draw_text_if_visible_3(camera, text, position_3d, font_size=20, color=pr.BLACK):
    """
    Affiche le texte à une position 2D projetée à partir d'une coordonnée 3D si elle est dans les limites de l'écran.
    
    :param camera: La caméra utilisée pour la projection.
    :param text: Texte à afficher.
    :param position_3d: Position 3D du texte.
    :param font_size: Taille de la police du texte.
    :param color: Couleur du texte.
    """
    text_position_2d = pr.get_world_to_screen(position_3d, camera)
    if 0 <= text_position_2d.x <= pr.get_screen_width() and 0 <= text_position_2d.y <= pr.get_screen_height():
        pr.draw_text(text, int(text_position_2d.x), int(text_position_2d.y), font_size, color)
    else:
        print("La position du texte est hors des limites de l'écran :", text_position_2d)

def parallelogram_area(vec_1, vec_2):
    return vector_length(cross_product(vec_1,vec_2))


def draw_vectors(points):
    for i in range(len(points) - 1):
        draw_vector_3(points[i], points[i + 1], pr.GRAY)

def draw_parallelogram(vec_1, vec_2):
    """Dessine un parallélogramme défini par deux vecteurs en utilisant des vecteurs au lieu de lignes."""
    color = pr.BLUE
   
    trans = Vector3(vec_1.x + vec_2.x, vec_1.y + vec_2.y, vec_1.z + vec_2.z)

    # Dessiner les vecteurs
    draw_vector_3(Vector3(0, 0, 0), vec_1, color)  # Vecteur du point d'origine vers le premier vecteur
    draw_vector_3(Vector3(0, 0, 0), vec_2, color)  # Vecteur du point d'origine vers le deuxième vecteur
    draw_vector_3(vec_1, trans, color)             # Vecteur du premier sommet vers le sommet opposé
    draw_vector_3(vec_2, trans, color)             # Vecteur du deuxième sommet vers le sommet opposé

def update_camera_position(camera, movement_speed):
    """Met à jour la position de la caméra en fonction des touches pressées."""
    if pr.is_key_down(pr.KEY_W):
        camera.position.z -= movement_speed
    if pr.is_key_down(pr.KEY_S):
        camera.position.z += movement_speed
    if pr.is_key_down(pr.KEY_A):
        camera.position.x -= movement_speed
    if pr.is_key_down(pr.KEY_D):
        camera.position.x += movement_speed
    if pr.is_key_down(pr.KEY_Q):
        camera.position.y += movement_speed
    if pr.is_key_down(pr.KEY_E):
        camera.position.y -= movement_speed

def main():
    pr.init_window(800, 600, "Produit Vectoriel pour la Direction de Rotation")
    camera = initialize_camera()
    pr.set_target_fps(60)
    grid_size = 15
    
    # Vecteurs de test

    vec_1 = Vector3(0,1,0)
    vec_2 = Vector3(3,4,0)

    movement_speed = 0.1
    area = parallelogram_area(vec_1, vec_2)

  
    while not pr.window_should_close():
        update_camera_position(camera, movement_speed)

        pr.begin_drawing()
        pr.clear_background(pr.RAYWHITE)
        pr.begin_mode_3d(camera)
        
        pr.draw_grid(grid_size, 1) 
        draw_parallelogram(vec_1, vec_2)
        
        pr.end_mode_3d()
        
        draw_text_if_visible_3(camera, f"Surface: {area:.2f}", Vector3(2, 2, 2), 20, pr.BLACK)

        pr.end_drawing()
    pr.close_window()

# Lancer le programme principal
if __name__ == "__main__":
    main()